package com.yaokantv.yaokanui.frag;



import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class ChannelCtrlFragment extends Fragment {
//    private String mTitle;
//    private ArrayList<Fragment> mFragments = new ArrayList<>();
//    private String[] mTitles = {"喜欢", "最近", "本地", "高清", "央视", "卫视", "其他"};
//    private int[] types = {9, 0, 3, 5, 1, 2, 4};
//    private CommonTabLayout mTabLayout_4;
//    private ViewPager mViewPager;
//    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
//
//    public static ChannelCtrlFragment getInstance(String title, String localId) {
//        ChannelCtrlFragment sf = new ChannelCtrlFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString(Co.LOCAL_ID, localId);
//        sf.setArguments(bundle);
//        sf.mTitle = title;
//        return sf;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View v = inflater.inflate(R.layout.frg_stb_ctrl, null);
//        for (int i = 0; i < mTitles.length; i++) {
//            mTabEntities.add(new TabEntity(mTitles[i], R.mipmap.tab_rc, R.mipmap.tab_rc));
//        }
//        String localId = getArguments().getString(Co.LOCAL_ID);
//        for (int title : types) {
//            mFragments.add(ChannelFragment.getInstance(title, localId));
//        }
//        mViewPager = v.findViewById(R.id.vp_4);
//        mTabLayout_4 = v.findViewById(R.id.tl_4);
//        mViewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager()));
//        mTabLayout_4.setTabData(mTabEntities);
//
//        mTabLayout_4.setOnTabSelectListener(new OnTabSelectListener() {
//            @Override
//            public void onTabSelect(int position) {
//                mViewPager.setCurrentItem(position);
//                Logger.e(Co.TAG, "-:" + position);
//            }
//
//            @Override
//            public void onTabReselect(int position) {
//
//            }
//        });
//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                mTabLayout_4.setCurrentTab(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//        mViewPager.setCurrentItem(4);
//        return v;
//    }
//
//    private class MyPagerAdapter extends FragmentPagerAdapter {
//        public MyPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public int getCount() {
//            return mFragments.size();
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return mTitles[position];
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return mFragments.get(position);
//        }
//    }
}
