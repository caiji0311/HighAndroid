package com.caiji.android.reflecandinocation.Fragment;

import android.content.Context;

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
    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> titleList;

    public LiWuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LiWuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LiWuFragment newInstance(String param1, String param2) {
        LiWuFragment fragment = new LiWuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
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



        return view;

    }

    private void initPagerAdapter() {
        MyPagerAdapter adapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private void initPagerData() {
         fragmentList=new ArrayList<>();
        fragmentList.add(new ChoicenessFragment());
        for (int i=0;i<12;i++){
        fragmentList.add(new OtherFragment());
        }
    }

    private void initTitleData() {
        titleList=new ArrayList<>();

        DownLoadJsonTask task=new DownLoadJsonTask(getActivity(), new DownLoadJsonTask.CallBack() {
            @Override
            public void callback(String result) {
                Gson gson=new Gson();

                ChoiceTitle choiceTitle= gson.fromJson(result, ChoiceTitle.class);
              List<Channels> channelsList= choiceTitle.getData().getchannels();
                for (int i=0;i<channelsList.size();i++){
                    titleList.add(channelsList.get(i).getName());
                }
                Log.i("caicai", "callback:1   "+titleList.toString());
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
