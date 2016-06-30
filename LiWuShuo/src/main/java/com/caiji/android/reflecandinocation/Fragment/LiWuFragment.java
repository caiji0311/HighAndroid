package com.caiji.android.reflecandinocation.Fragment;

import android.content.Context;

import android.graphics.AvoidXfermode;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.caiji.android.reflecandinocation.R;
import com.caiji.android.reflecandinocation.bean.Candidates;
import com.caiji.android.reflecandinocation.bean.Channels;
import com.caiji.android.reflecandinocation.bean.ChoiceTitle;
import com.caiji.android.reflecandinocation.bean.Constant;
import com.caiji.android.reflecandinocation.downloadJsonAsyncTask.DownLoadJsonTask;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiWuFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    @BindView(R.id.fragment_liwu_tablayout)
    public TabLayout tabLayout;

    @BindView(R.id.fragment_liwu_viewpager)
    public ViewPager viewPager;

    public  List<Channels> channelsList;
    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> titleList;

    public LiWuFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static LiWuFragment newInstance() {
        LiWuFragment fragment = new LiWuFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_li_wu, container, false);
        ButterKnife.bind(this,view);
        initTitleData();
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        return view;

    }

    private void initPagerAdapter() {
        MyPagerAdapter adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private void initPagerData() {
         fragmentList=new ArrayList<>();
        fragmentList.add(ChoicenessFragment.newInstance(channelsList.get(0).getId()));
        for (int i=0;i<12;i++){
        fragmentList.add(OtherFragment.newInstance(channelsList.get(i+1).getId()));
        }
    }

    /**
     * 初始化TabLayout的标题内容   网上解析
     * */
    private void initTitleData() {
        titleList=new ArrayList<>();

        DownLoadJsonTask task=new DownLoadJsonTask(getActivity(), new DownLoadJsonTask.CallBack() {
            @Override
            public void callback(String result) {
                Gson gson=new Gson();
                if (null==result){
                    return;
                }
                ChoiceTitle choiceTitle= gson.fromJson(result, ChoiceTitle.class);
                 channelsList= choiceTitle.getData().getChannels();
                for (int i=0;i<channelsList.size();i++){
                    titleList.add(channelsList.get(i).getName());
                }
                //绑定适配器
                initPagerData();
                initPagerAdapter();
                tabLayout.setupWithViewPager(viewPager);
            }
        });
        task.execute(Constant.GiderTitle);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    /**
     * 自定义适配器
     * */
  class MyPagerAdapter extends FragmentPagerAdapter {


      public MyPagerAdapter(FragmentManager fm) {
          super(fm);
      }

      @Override
      public Fragment getItem(int position) {
          return fragmentList.get(position);
      }

      @Override
      public int getCount() {
          return fragmentList.size();
      }

      @Override
      public CharSequence getPageTitle(int position) {
          return titleList.get(position);
      }
  }

}
