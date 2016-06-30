package com.caiji.android.reflecandinocation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.caiji.android.reflecandinocation.Fragment.ClassifyFragment;
import com.caiji.android.reflecandinocation.Fragment.HotFragment;
import com.caiji.android.reflecandinocation.Fragment.LiWuFragment;
import com.caiji.android.reflecandinocation.Fragment.MeFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.activity_main_framlayout)
    public FrameLayout frameLayout;
    @BindView(R.id.activity_main_rg)
    public RadioGroup radioGroup;

    public FragmentManager manager=getSupportFragmentManager();
    public ArrayList<Fragment> fragmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initFragment();
        initListener();
    }

    private void initListener() {
        radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction=manager.beginTransaction();

                for (Fragment fragment:fragmentList){
                    if (fragment.isAdded()){
                        transaction.hide(fragment);
                    }
                }
                switch (checkedId){
                    case R.id.activity_main_rb1:
                        if (fragmentList.get(0).isAdded()){
                            transaction.show(fragmentList.get(0));
                        }else{
                            transaction.add(R.id.activity_main_framlayout,fragmentList.get(0));
                        }
                        break;
                    case R.id.activity_main_rb2:
                        if (fragmentList.get(1).isAdded()){
                            transaction.show(fragmentList.get(1));
                        }else{
                            transaction.add(R.id.activity_main_framlayout,fragmentList.get(1));
                        }
                        break;
                    case R.id.activity_main_rb3:
                        if (fragmentList.get(2).isAdded()){
                            transaction.show(fragmentList.get(2));
                        }else{
                            transaction.add(R.id.activity_main_framlayout,fragmentList.get(2));
                        }
                        break;
                    case R.id.activity_main_rb4:
                        if (fragmentList.get(3).isAdded()){
                            transaction.show(fragmentList.get(3));
                        }else{
                            transaction.add(R.id.activity_main_framlayout,fragmentList.get(3));
                        }
                        break;
                }
                transaction.commit();
            }
        });
    }

    private void initFragment() {
        LiWuFragment liWuFragment=LiWuFragment.newInstance();
        HotFragment hotFragment=HotFragment.newInstance();
        ClassifyFragment classifyFragment=ClassifyFragment.newInstance();
        MeFragment meFragment=MeFragment.newInstance();
        fragmentList=new ArrayList<>();
        fragmentList.add(liWuFragment);
        fragmentList.add(hotFragment);
        fragmentList.add(classifyFragment);
        fragmentList.add(meFragment);

        //设置初始默认fragment
        FragmentTransaction transaction=manager.beginTransaction();
        transaction.add(R.id.activity_main_framlayout,liWuFragment);
        transaction.commit();
    }
}
