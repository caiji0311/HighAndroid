package com.caiji.android.reflecandinocation.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.caiji.android.reflecandinocation.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by lj on 2016/6/27.
 */
public class ChoicenessFragment extends Fragment {

    @BindView(R.id.fragment_choiceness_explv)
    public ExpandableListView listview;

    private Map<String,List<String>> data=new ArrayMap<>();
    private ArrayList<Object> groupList = new ArrayList<>();
    private FragmentActivity mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_choiceness,null);
        ButterKnife.bind(this,view);
        mContext=getActivity();
        initData();
        initListViewAdapter();
        return null;
    }

    private void initListViewAdapter() {

    }

    /**
     * 初始化listview数据源
     * */
    private void initData() {
        for(int i=0;i<10;i++){
            groupList.add("小组"+i);
            ArrayList<String> childList=new ArrayList<>();
            data.put("小组"+i,childList);
            for (int j = 0; j <10 ; j++) {
                childList.add("成员"+j+"个");
            }
        }
    }

    class MyListViewAdapter extends BaseExpandableListAdapter{

        /*
        * 得到分组的数目
        * */
        @Override
        public int getGroupCount() {
            return  groupList==null? 0:groupList.size();
        }

        /*
        * 得到每组里面数据的数目
        * **/
        @Override
        public int getChildrenCount(int groupPosition) {
            List<String> list=data.get(groupList.get(groupPosition));
            return list.size();
        }

        /**
         * 得到每组的对象
         * */
        @Override
        public Object getGroup(int groupPosition) {
            return groupList.get(groupPosition);
        }

        /*
        * 得到各组每个item的对象
        * **/
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return data.get(groupList.get(groupPosition));
        }

        @Override
        public long getGroupId(int groupPosition) {
            return 0;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        /**
         * 创建每个分组的视图
         * */
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            GroupViewHolder mViewHolder=null;
           if (convertView==null){
               convertView= LayoutInflater.from(mContext).inflate(R.layout.fragment_choiceness_grouplist,null);
               mViewHolder=new GroupViewHolder(convertView);

           }else{
               mViewHolder= (GroupViewHolder) convertView.getTag();
           }

             mViewHolder.tvLeft.setText("6月27日 星期一");
            mViewHolder.tvRight.setText("下次更新8:00");


            return convertView;
        }

        class GroupViewHolder{
            @BindView(R.id.fragment_choiceness_grouplist_tvLeft)
            public TextView tvLeft;
            @BindView(R.id.fragment_choiceness_grouplist_tvRight)
            public TextView tvRight;

            public GroupViewHolder(View view){
                view.setTag(this);
                ButterKnife.bind(this,view);
            }

        }

        /**
         * 创建各组item的视图
         * */
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildViewHolder mViewHolder=null;
            if (convertView==null){
                convertView= LayoutInflater.from(mContext).inflate(R.layout.fragment_choiceness_childlist,null);
                mViewHolder=new ChildViewHolder(convertView);

            }else{
                mViewHolder= (ChildViewHolder) convertView.getTag();
            }

            mViewHolder.imageView.setImageResource(R.mipmap.xiaohuang);
            return convertView;
        }

        class ChildViewHolder{
            @BindView(R.id.fragment_choiceness_childlist_iv)
           public ImageView imageView;

            public ChildViewHolder(View view){
                view.setTag(this);
                ButterKnife.bind(this,view);
            }

        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }


}
