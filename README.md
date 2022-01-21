# Yaokan SDK4 Android 开发文档


    文件编号：YKSDK4ANDROID-20200724
    版本：v1.01

    深圳遥看科技有限公司
    （版权所有，切勿拷贝）



| 版本 | 说明 | 备注 | 日期 |
| --- | --- | --- | --- |
| v1 | 新建 | Peer | 20210311 |
| v1.01 | 支持启用/禁用设备麦克风 | alex | 20220121 |


## 1. 概述
YaokanSDK4 提供完整的设备配网，设备管理，遥控器管理功能，开箱即用，快速与已有App对接的目的。

![image](https://github.com/yaokantv/YaokanSDK3-Android/blob/master/img/net_config.png)

![image](https://github.com/yaokantv/YaokanSDK3-Android/blob/master/img/create_rc.png)

![image](https://github.com/yaokantv/YaokanSDK3-Android/blob/master/img/create_stb.png)

## 2. 文档阅读对象
使用 YaokanSDK4 Android 版的客户

## 3. 环境配置
1. 清单文件

    `AndroidManifest.xml` 添加如下权限

    ```xml
     <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
    <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
    <uses-permission android:name="android.permission.CHANGE_WIFI_MULTICAST_STATE" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.WAKE_LOCK" />
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.VIBRATE" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
    ```

2. 网络配置

 - 在`res`文件夹下新建xml文件夹，创建`network_security_config.xml`文件，配置如下：

    ```xml
    <?xml version="1.0" encoding="utf-8"?>
    <network-security-config>
        <base-config cleartextTrafficPermitted="true" />
    </network-security-config>
    ```

 - 在`AndroidManifest`的`application`节点下添加：

    ```sh
    android:networkSecurityConfig="@xml/network_security_config"
    ```

    这是为了解决 `Android9.0` 下 `Okhttp3.0` 的bug
3. 按键对应表和数据库表配置

    新建`Assets`文件夹，将Demo中的`yaokan.xml`文件拷贝到文件夹里

4. 添加遥看SDK库

    在`lib`下添加`yaokansdk.aar`文件

5. gradle 配置
    ```groovy
    android {
    ...
    compileOptions {
            sourceCompatibility JavaVersion.VERSION_1_8
            targetCompatibility JavaVersion.VERSION_1_8
        }
        repositories  {
            flatDir{ dirs 'libs'  }
        }
        configurations.all {
            resolutionStrategy.eachDependency { DependencyResolveDetails details ->
                def requested = details.requested
                if (requested.group == 'com.android.support') {
                    if (!requested.name.startsWith("multidex")) {
                        details.useVersion '28.0.0'//这里改为你项目里的版本
                    }
                }
            }
        }
    }

    dependencies {
        implementation fileTree(dir: 'libs', include: ['*.jar'])
        implementation 'androidx.appcompat:appcompat:1.0.0'
        implementation 'com.android.support.constraint:constraint-layout:1.1.3'
        implementation 'org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.0'
        implementation 'org.eclipse.paho:org.eclipse.paho.android.service:1.1.1'
        implementation 'com.google.code.gson:gson:2.8.2'
        implementation 'com.squareup.okhttp3:okhttp:3.11.0'
        implementation "com.orhanobut:hawk:2.0.1"//用于记录数据
        implementation 'com.jaeger.statusbarutil:library:1.5.1'//UI相关
        implementation(name:'yaokansdk', ext:'aar')
    }

    ```

## 4. API 列表
### 4.1 初始化接口

使用Appid和AppSecret进行初始化SDK，初始化成功后才能使用SDK的功能

```java
class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Yaokan.initialize(this);
    }
    @Override
    public void onTerminate() {
        super.onTerminate();
        Yaokan.instance().onTerminate(this);
    }
}

class YourActivity extends Activity {
    @Override
    public void onCreate() {
        super.onCreate();
        Yaokan.instance().init(getApplication(), "appId", "appSecret");
    }
}

Yaokan.instance().addSdkListener(YaokanSDKListener);

//回调
@Override
public void onReceiveMsg(MsgType msgType, YkMessage ykMessage) {
    switch (msgType) {
        case Init:
            if (ykMessage != null && ykMessage.getCode() == 0) {
                //初始化成功
            } else {
                //初始化失败
            }
            break;
    }
}
```

### 4.2 设备接口

1. 配置入网


    SmartConfig（广播配网）为旧的配网方式，成功率较低，所以新增了SoftAp（热点配网）的配网方式，成功率有较大的提升，建议后来接入的客户都使用SoftAp

    - 使用非5G Wi-Fi 网络配网


         /**
         * @param context 上下文
         * @param ssid    WI-FI名称
         * @param psw     WI-FI密码
         * @param hotspot 产品类型  YKK-1011/YKK-1013/YKK-DS16A 分别对应小苹果/大苹果/空调伴侣
         */

        Yaokan.instance().softApConfigSwitch(context,ssid,psw,hotspot);

        /**
        * 停止配置入网
        */

        Yaokan.instance().stopSoftApConfig();

        /**
         * 配置入网
         *
         * @param context 上下文
         * @param psw     Wi-Fi密码
         */

        Yaokan.instance().smartConfig(context,psw);

        /**
         * 停止配置入网
         */

        Yaokan.instance().stopSmartConfig();

        /**
         * 手机热点配置入网，需要将手机热点名称设置为YKK，密码设置为12345678
         *
         * @param context 上下文
         * @param psw     Wi-Fi密码
         */

        Yaokan.instance().hotPointConfig();

        /**
         * 停止配置入网
         */

        Yaokan.instance().stopHotPointConfig();

        /**
         *  参数配网
         * @param ssid  WI-FI名称
         * @param psw WI-FI密码
         */

        Yaokan.instance().paramConfig("","");

        /**
         * 停止参数配置入网
         */

        Yaokan.instance().stopParamConfig();
    - 回调

        ```java
        @Override
        public void onReceiveMsg(MsgTypemsgType, final YkMessage ykMessage {
            switch (msgType) {
                case SoftApConfigStart:
                //SoftAp配网开始
                    break;
                case SoftApConfigResult:
                //SoftAp配网结果
                    break;
                case StartSmartConfig:
                //SmartConfig配网开始
                    break;
                case SmartConfigResult:
                //SmartConfig配网结果
                    break;
                case HotPointStart:
                //手机热点配网开始
                    break;
                case HotPointConfigResult:
                //手机热点配网结果
                    break;
                case ParamConfigStart:
                //参数配网开始
                    break;
                case GetConfigParam:
                //获得参数配网的参数
                    String data = ykMessage.getData();
                    break;
                case ParamConfigResult:
                //参数配网结果
                    break;
            }
        }
        ```

1. 获取设备列表

    获取设备列表，本地发现的也会实时显示到列表中

    - 获取设备方式一：字符串格式

    ```java
    String s = Yaokan.instance().exportDeviceListStringFromDB();
    ```

    - 获取设备方式二：集合格式
    ```java
    List<YkDevice> mList = Yaokan.instance().getDeviceList();
    ```
1. 导入设备列表

    导入设备，客户可以用来同步用户的设备列表

    - 导入设备方式一：字符串格式导入
    ```java
    Yaokan.instance().inputYkDevicesToDB("[{\"did\":\"XXXXXXXXXXXXXXXX\",\"mac\":\"XXXXXXXXXXXX\",\"name\":\"YKK_1.0\"}]");
    ```

    - 导入设备方式二：集合格式导入
    ```java
    if (mList.size() > 0) {
        Yaokan.instance().inputYkDevicesToDB(mList);
    }
    ```
1. 刷新设备列表
   ```java
   Yaokan.instance().loadDevices(List<YkDevice> devices);
   ```
1. 删除设备
    ```java
    Yaokan.instance().deleteDevice(mac);
    ```

1. 设备测试

    发送测试指令，设备收到后指示灯会快闪一次

    ```java
    /**
    *
    * @param did 设备Did
    */
    Yaokan.instance().test(did);
    ```

1. 发送命令

   - 常规遥控器发码

        发送非空调遥控指令，包括红外遥控和射频指令

        ```java
        /**
        * 常规遥控器发码
        * @param did 设备Did
        * @param rid 遥控器Rid
        * @param key 指令名称
        * @param type 家电类型
        */
        Yaokan.instance().sendCmd(did,rid,key,type);
        ```
   - 空调遥控器发码

        发送空调遥控指令

        ```java
        /**
        * 空调遥控器发码
        * @param did   设备Did
        * @param rid   遥控器Rid
        * @param order 遥控器指令对象
        */
        Yaokan.instance().sendAirCmd(did,rid,order)

        /**
        *
        * @param mode 模式
        * @param speed 风速
        * @param temp 温度
        * @param ver 上下扫风
        * @param hor 左右扫风
        */
        public AirOrder(String mode, String speed, String temp, String ver, String hor)
        ```
   - 有独立扫风开关的空调遥控器的扫风发码

        ```java
        /**
        * 有独立开关的空调遥控器的扫风发码
        *
        * @param did 设备Did
        * @param rid 遥控器Rid
        * @param swing 扫风类型(上下/左右)
        * @param isOn 是否打开
        */
        public void sendAirCmd(did,rid,swing,isOn)
        ```

        ```java
        @Override
        public void onReceiveMsg(final MsgType msgType, final YkMessage ykMessage) {
            switch (msgType) {
                case SendCodeResponse:
                   //发码结果回调
                    break;
            }
        }
        ```

1. 学习红外和射频

    修改已创建遥控器的按键功能

    ```java
    /**
    *
    * @param did 设备Did
    * @param rc 遥控器对象
    * @param key 要学习的指令名称
    */
    Yaokan.instance().study(did,rc,key)

    //停止红外学习
    Yaokan.instance().stopStudy(did)

    //开始射频学习
    Yaokan.instance().studyRf(did,rc,key)

    //停止射频学习
    Yaokan.instance().stopStudyRf(did,rc)
    ```

    ```java
    @Override
    public void onReceiveMsg(final MsgType msgType, final YkMessage ykMessage) {
        switch (msgType) {
            case StudyError:
            //学习失败
                break;
            case StudySuccess:
            //学习成功
                break;
        }
    }
    ```

1. 设备开灯/关灯

    控制设备的指示灯开关

    ```java
    //开灯
    Yaokan.instance().lightOn(did);
    //关灯
    Yaokan.instance().lightOff(did);
    ```
1. 设置唤醒时长

    ```java
    /**
     *
     * @param time 唤醒时长 15-60
     */
    Yaokan.instance().setKeepAlive(did, time);
    ```
1. 设置设备音量

    ```java
    /**
     *
     * @param voice 设备音量大小 0-20
     */
    Yaokan.instance().setVoice(did, voice);
    ```
1. 启用设备麦克风

    ```java

    Yaokan.instance().enableMic(did);
    ```
1. 禁用设备麦克风

    ```java

    Yaokan.instance().disableMic(did);
    ```

1. 获取接收模式

    ```java
    Yaokan.instance().getProtocolBrand();
    回调：MsgType.ProtocolBrand
    ```

1. 设置接收模式

    ```java
    /**
     *
     * @param time 唤醒时长 15-60
     */
    Yaokan.instance().setReceiveMode(did, ReceiveModeResult.ResultBean);
    回调：MsgType.BindRFResult
    ```

1. 固件升级OTA

    OTA升级硬件固件，支持显示升级进度

    ```java
    Yaokan.instance().updateDevice(did);
    ```

    OTA升级语音固件，支持显示升级进度

    ```java
    Yaokan.instance().updateVoice(did);
    ```
1. 获取设备内的遥控器列表

    ```java
    /**
     * 获取设备内的遥控器列表
     * @param did
     * @param start 开始位 比如 0
     * @param size 获取个数 最大15
     */
    Yaokan.instance().deviceCtrlList(String did, String start, int size);
    ```
1. 删除设备内的遥控器

    ```java
     /**
     * 删除设备内的遥控器
     * @param curDid
     * @param rid
     */
    Yaokan.instance().deleteDeviceCode(String curDid, String rid);
    ```
1. 获取遥控器名称

    ```java
     /**
     * 获取遥控器名称
     *
     * @param rid
     * @return
     */
    public String getNameByRid(String rid);
    ```
1. 获取硬件版本

    获取硬件当前的版本号,显示硬件版本号，格式为 x.x.x

    ```java
    Yaokan.instance().checkDeviceVersion(did);
    ```
    ```java
    @Override
    public void onReceiveMsg(final MsgType msgType, final YkMessage ykMessage) {
        switch (msgType) {
            case otaVersion:
            //版本信息
                break;
        }
    }
    ```

1. 设备复位

    - 复位设备，进入配网状态

    ```java
    Yaokan.instance().apReset(mac,did);
    ```

1. 判断是否需要下载码库的设备

    - 型号为YKK-1013、DS16A 的设备需要下载码库

    ```java
    Yaokan.instance().isNeedDownloadDevice(mac);
    ```

1. 获取GFSK回路数

    -

    ```java
    Yaokan.instance().getGfskModels(tid,bid);
    回调：MsgType.RfModels
    ```

1. 获取GFSK配码信息

    ```java
    /**
    *
    * @param road 回路数
    */
    Yaokan.instance().getProduceMac(mac,tid,road)
    ```

1. 生成GFSK码库

    ```java
    /**
    *
    * @param road 回路数
    * @param subMac 子设备Mac
    */
    Yaokan.instance().rfOsmV2(mac, tid, bid, road, subMac);
    ```

### 4.3 遥控器接口
1. 获取被遥控家电类型列表

    ```java
    Yaokan.instance().getDeviceType()
    ```
1. 获取设备品牌

    获取某遥控器类别下品牌列表

    ```java
    /**
    *
    * @param tid 家电类型
    */
    Yaokan.instance().getBrandsByType(tid)
    ```
1. 进入一级匹配

    ```java
    /**
    *
    * @param tid 家电类型
    * @param bid 品牌ID
    */
    Yaokan.instance().getMatchingResult(tid,bid)
    ```
1. 进入二级匹配

    ```java
    /**
    *
    * @param tid 家电类型
    * @param bid 品牌ID
    * @param gid 组Id(一级匹配接口返回,空调设备传0)
    */
    Yaokan.instance().getMatchingResult(tid,bid,gid)
    ```

    ```java
    @Override
    public void onReceiveMsg(final MsgType msgType, final YkMessage ykMessage) {
        switch (msgType) {
            case Types:
            //家电类型列表
                break;
            case Brands:
            //品牌列表
                break;
            case Matching:
            //一级匹配
                break;
            case SecondMatching:
            //二级匹配
                break;
            case RemoteInfo:
            //遥控器详情
                break;
        }
    }
    ```

1. 获取房间类型

    ```java
    /**
    * @param rc 遥控器对象
    */
    Yaokan.instance().placeList();
    ```

1. 获取房间类型

    ```java
    /**
    * @param tid 家电类型
    */
    Yaokan.instance().aliceList(tid);
    ```

1. 设置遥控器的房间和别名

    ```java
    /**
     *
     * @param did 设备DID
     * @param bean 房间参数
     */
     Yaokan.instance().setRoomMsg(Yaokan.instance().getDid(did, whereBean);
    ```

1. 保存遥控器

    匹配完成后，先要将码库下载到设备，下载成功后可以通过遥控器id下载详情保存至本地

    ```java
    /**
     * 判断是否需要下载码库到设备（目前只有大苹果/空调伴侣需要）
     *
     * @param mac
     */
    Yaokan.instance().isNeedDownloadDevice(mac);
    /**
     * 将码库下载到设备
     *
     * @param rid  遥控器ID
     * @param type 家电类型
     */
    Yaokan.instance().downloadCodeToDevice(App.curDid, rc.getRid(), rc.getBe_rc_type());
    /**
     * 将射频码库下载到设备
     *
     * @param rid  遥控器ID
     * @param type 家电类型
     */
    Yaokan.instance().downloadRFCodeToDevice(App.curDid, rc.getRid(), rc.getBe_rc_type());

    /**
    * @param rc 遥控器对象
    */
    Yaokan.instance().saveRc(rc);
    ```

    ```java
    @Override
    public void onReceiveMsg(final MsgType msgType, final YkMessage ykMessage) {
        switch (msgType) {
            case DownloadCode:
                final DeviceResult result = (DeviceResult) ykMessage.getData();
                switch (result.getCode()) {
                    case Contants.YK_DOWNLOAD_CODE_RESULT_START_FAIL:
                        msg = "开启下载遥控器失败";
                        break;
                    case Contants.YK_DOWNLOAD_CODE_RESULT_START:
                    //开始下载遥控器
                        break;
                    case Contants.YK_DOWNLOAD_CODE_RESULT_SUC:
                    //下载遥控器成功
                        break;
                    case Contants.YK_DOWNLOAD_CODE_RESULT_FAIL:
                        msg = "下载遥控器失败";
                        break;
                    case Contants.YK_DOWNLOAD_CODE_RESULT_EXIST:
                        msg = "遥控器已存在设备中";
                        break;
                    case Contants.YK_DOWNLOAD_CODE_RESULT_AIR_MAX:
                        msg = "空调遥控器达到极限";
                        break;
                    case Contants.YK_DOWNLOAD_CODE_RESULT_IR_MAX:
                        msg = "非空调遥控器达到极限";
                        break;
                    case Contants.YK_DOWNLOAD_CODE_RESULT_RF_MAX:
                        msg = "射频遥控器达到极限";
                        break;
                    case Contants.YK_DOWNLOAD_CODE_RESULT_DOOR_MAX:
                        msg = "门铃遥控器达到极限";
                        break;
                    }
                break;

        }
    }
    ```
1. 更新遥控器

    更新遥控器信息，比如名称

    ```java
    //更新遥控器
    Yaokan.instance().updateRc(rc);
    ```
1. 删除遥控器

    ```java

     /**
     * 删除设备端的遥控器
     * @param did
     * @param rid 遥控器ID
     */
     Yaokan.instance().deleteDeviceRc(rc.getRid());
     //在设备端删除成功后在删除手机数据库里的遥控器


     /**
     * 删除数据库中的遥控器
     *
     * @param uuid UUID为本地生成的遥控器唯一ID
     */
    Yaokan.instance().deleteRcByUUID(String uuid)

    /**
     * 删除设备内所有遥控器
     * @param did
     */
    Yaokan.instance().deleteAllDevice(String did)

    /**
     * 删除手机数据库内所有遥控器
     * @param did
     */
    Yaokan.instance().deleteAllRcDevice()

    /**
     * 删除数据库中包含该Mac的遥控器
     * @param mac
     */
    Yaokan.instance().deleteRemoteByMac(String mac);
    ```
     ```java
    @Override
    public void onReceiveMsg(final MsgType msgType, final YkMessage ykMessage) {
        switch (msgType) {
            case DelAppleCtrl:
            DeviceResult result = (DeviceResult) ykMessage.getData();
            switch (result.getCode()) {
                case Contants.YK_DELETE_CODE_RESULT_SINGLE_SUC:
                    //删除遥控器成功
                    break;
                case Contants.YK_DELETE_CODE_RESULT_SINGLE_FAIL:
                    //删除遥控器失败
                    break;
                case Contants.YK_DELETE_CODE_RESULT_ALL_SUC:
                    //删除所有遥控器成功
                    break;
                case Contants.YK_DELETE_CODE_RESULT_ALL_FAIL:
                    //删除所有遥控器失败
                    break;
            }
                break;
        }
    }
    ```
1. 获取遥控器详情

    遥控器详情，用于在二级匹配中切换时调用

    ```java
    RemoteCtrl rc = Yaokan.instance().getRcDataByUUID(uuid);
    ```
1. 获取遥控器列表

    ```java
    List<RemoteCtrl> list = Yaokan.instance().getRcList();
    ```

1. 导出遥控器JSON数据

    ```java
    String json = Yaokan.instance().exportRcString();
    ```
1. 导入遥控器列表

    ```java
    Yaokan.instance().inputRcString(json);
    ```
1. 电量统计

    ```java
    /**
     *
     * @param did 设备DID
     * @param unit  查询单位 day,month
     * @param timeBegin 起始时间戳 秒
     * @param timeEnd 结束时间戳 秒
     */
    Yaokan.instance().powerQuery(String did, String unit, long timeBegin, long timeEnd);

    回调
      case AirPowerResult:
        List<AirPowerResult> list = (List<AirPowerResult>) ykMessage.getData();

    //返回的Model说明 tag:第几天或月；value：电量统计值，保留4位小数；createAt：电量上报的时间戳，用于tag显示的排序
    ```
### 4.4 其他接口
1. 设置发码是否震动

    ```java
    /**
     * 发码时是否震动
     *
     * @param b
     */
    Yaokan.instance().setVibrate(boolean b);
    ```
1. 机顶盒运营商相关

    ```java
    /**
     * 获取区域信息
     *
     * @param id 地区ID，传0表示获取省级和直辖市行政单位
     */
    Yaokan.instance().getArea(int id);
    /**
     * 获取运营商列表
     *
     * @param id 地区ID
     */
    public void getProvidersByAreaId(int id);
    ```
1. 获取设备信息
    ```java
    /**
     * 固件信息
     *
     * @param did
     */
    Yaokan.instance().deviceInfo(String did);
    ```
