package com.yaokantv.yaokanui.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.yaokantv.yaokansdk.model.RemoteCtrl;

public class UiRc implements Parcelable {
    private String uuid;
    private int id;
    private String name = "";
    private String rmodel;
    private String rf = "0";
    private String be_rmodel;
    private String rid;
    private int be_rc_type;
    private int bid;
    private String mac;
    private String studyId = "0";
    private int rc_command_type = 1;
    private String provider = "";

    public static UiRc transRc(RemoteCtrl ctrl) {
        UiRc uiRc = new UiRc();
        uiRc.setName(ctrl.getName());
        uiRc.setUuid(ctrl.getUuid());
        uiRc.setStudyId(ctrl.getStudyId());
        uiRc.setBe_rc_type(ctrl.getBe_rc_type());
        uiRc.setMac(ctrl.getMac());
        uiRc.setRf(ctrl.getRf());
        return uiRc;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRmodel() {
        return rmodel;
    }

    public void setRmodel(String rmodel) {
        this.rmodel = rmodel;
    }

    public String getRf() {
        return rf;
    }

    public void setRf(String rf) {
        this.rf = rf;
    }

    public String getBe_rmodel() {
        return be_rmodel;
    }

    public void setBe_rmodel(String be_rmodel) {
        this.be_rmodel = be_rmodel;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public int getBe_rc_type() {
        return be_rc_type;
    }

    public void setBe_rc_type(int be_rc_type) {
        this.be_rc_type = be_rc_type;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getStudyId() {
        return studyId;
    }

    public void setStudyId(String studyId) {
        this.studyId = studyId;
    }

    public int getRc_command_type() {
        return rc_command_type;
    }

    public void setRc_command_type(int rc_command_type) {
        this.rc_command_type = rc_command_type;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.rmodel);
        dest.writeString(this.rf);
        dest.writeString(this.be_rmodel);
        dest.writeString(this.rid);
        dest.writeInt(this.be_rc_type);
        dest.writeInt(this.bid);
        dest.writeString(this.mac);
        dest.writeString(this.studyId);
        dest.writeInt(this.rc_command_type);
        dest.writeString(this.provider);
    }

    public UiRc() {
    }

    protected UiRc(Parcel in) {
        this.uuid = in.readString();
        this.id = in.readInt();
        this.name = in.readString();
        this.rmodel = in.readString();
        this.rf = in.readString();
        this.be_rmodel = in.readString();
        this.rid = in.readString();
        this.be_rc_type = in.readInt();
        this.bid = in.readInt();
        this.mac = in.readString();
        this.studyId = in.readString();
        this.rc_command_type = in.readInt();
        this.provider = in.readString();
    }

    public static final Creator<UiRc> CREATOR = new Creator<UiRc>() {
        @Override
        public UiRc createFromParcel(Parcel source) {
            return new UiRc(source);
        }

        @Override
        public UiRc[] newArray(int size) {
            return new UiRc[size];
        }
    };
}
