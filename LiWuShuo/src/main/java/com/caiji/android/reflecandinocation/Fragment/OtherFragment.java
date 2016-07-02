package com.caiji.android.reflecandinocation.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.caiji.android.reflecandinocation.R;
import com.caiji.android.reflecandinocation.bean.ChoiceTitle;
import com.caiji.android.reflecandinocation.bean.Constant;
import com.caiji.android.reflecandinocation.bean.Data;
import com.caiji.android.reflecandinocation.bean.Items;
import com.caiji.android.reflecandinocation.utils.IOKCallBack;
import com.caiji.android.reflecandinocation.utils.OkHttpTool;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lj on 2016/6/27.
 */
public class OtherFragment extends Fragment {

    public Context mContext = getActivity();
    private String url;
    private ExpandableListView listView;

    public final List<String> groupList = new ArrayList<>();
    public Map<String, List<Items>> datas = new HashMap<>();
    private MyListViewAdapter adapter;

    public static OtherFragment newInstance(String id) {

        Bundle args = new Bundle();
        args.putString("id",id);
        OtherFragment fragment = new OtherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        String id = getArguments().getString("id");
        url = Constant.GideInfo_Before + id + Constant.GideInfo_Behind;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choiceness, null);
        PullToRefreshExpandableListView pullToRefreshExpandableListView=
                (PullToRefreshExpandableListView) view.findViewById(R.id.fragment_choiceness_explv);
        listView = pullToRefreshExpandableListView.getRefreshableView();
        initListViewAdapter();
        initData();
        return view;
    }


    /**
     * 初始化ExpListView的数据源
     */
    private void initData() {


        //再次进入时，不再重新加载
        if (groupList != null && !groupList.isEmpty()) {
            initListViewAdapter();
            adapter.notifyDataSetInvalidated();
            return;
        }
        //OkHttp  Get方法，获取网络返回的数据
        OkHttpTool.newInstance().start(url).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                ChoiceTitle choiceTitle = gson.fromJson(result, ChoiceTitle.class);
                Data data = choiceTitle.getData();
                ArrayList<Items> itemsList = data.getItems();
                initDatas(itemsList);
                setupExpListView();
                adapter.notifyDataSetChanged();
            }
        });
    }


    private void initDatas(ArrayList<Items> itemsList) {
        for(Items items:itemsList){
            if (!groupList.contains(switchTime(items.getCreated_at()))){
                groupList.add(switchTime(items.getCreated_at()));
            }
        }
        for (String create:groupList){
            ArrayList<Items> childList=new ArrayList<>();
            for (Items items:itemsList){

                if (create.equals(switchTime(items.getCreated_at()))){
                    childList.add(items);
                }
            }
            datas.put(create,childList);
        }
    }

    private void initListViewAdapter() {

        adapter = new MyListViewAdapter();

        listView.setAdapter(adapter);



    }

    private void setupExpListView() {
        //设置所有的group 点击不收缩
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });

        //设置所有的group默认展开
        for (int i = 0; i < groupList.size(); i++) {
            listView.expandGroup(i);
        }
    }


    class MyListViewAdapter extends BaseExpandableListAdapter {


        /*
            * 得到分组的数目
            * */
        @Override
        public int getGroupCount() {
            return groupList == null ? 0 : groupList.size();
        }

        /*
        * 得到每组里面数据的数目
        * **/
        @Override
        public int getChildrenCount(int groupPosition) {
            List<Items> list = datas.get(groupList.get(groupPosition));
            return list.size();
        }

        /**
         * 得到每组的对象
         */
        @Override
        public Object getGroup(int groupPosition) {

            return groupList.get(groupPosition);
        }

        /*
        * 得到各组每个item的对象
        * **/
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return datas.get(groupList.get(groupPosition));
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
         */
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            GroupViewHolder mViewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_choiceness_grouplist, null);
                mViewHolder = new GroupViewHolder(convertView);

            } else {
                mViewHolder = (GroupViewHolder) convertView.getTag();
            }
            mViewHolder.tvLeft.setText(groupList.get(groupPosition));


            return convertView;
        }

        class GroupViewHolder {
            @BindView(R.id.fragment_choiceness_grouplist_tvLeft)
            public TextView tvLeft;
            @BindView(R.id.fragment_choiceness_grouplist_tvRight)
            public TextView tvRight;

            public GroupViewHolder(View view) {
                view.setTag(this);
                ButterKnife.bind(this, view);
            }

        }

        /**
         * 创建各组item的视图
         */
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildViewHolder mViewHolder = null;

            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.fragment_choiceness_childlist, null);
                mViewHolder = new ChildViewHolder(convertView);

            } else {
                mViewHolder = (ChildViewHolder) convertView.getTag();
            }


            ImageView imageView = mViewHolder.imageView;
            Items items = datas.get(groupList.get(groupPosition)).get(childPosition);
            String url = items.getCover_image_url();
            String text01 = items.getTitle();
            String text02 = items.getLikes_count();


            // Glide.with(getActivity()).load("http://img01.liwushuo.com/image/160628/8dcy29vca.jpg-w720").into(mViewHolder.imageView);
            //ImageLoader.build(getActivity()).bindView(imageView,url,300,300);
            Picasso.with(mContext).load(url).into(imageView);
            mViewHolder.tv01.setText(text01);
            mViewHolder.tv02.setText(text02);


            return convertView;
        }

        class ChildViewHolder {
            @BindView(R.id.fragment_choiceness_childlist_iv)
            public ImageView imageView;
            @BindView(R.id.fragment_choiceness_childlist_tv1)
            public TextView tv01;
            @BindView(R.id.fragment_choiceness_childlist_tv2)
            public TextView tv02;

            public ChildViewHolder(View view) {
                view.setTag(this);
                ButterKnife.bind(this, view);
            }

        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }

    }

    /**
     * 将秒数转换成要求的时间格式
     */
    public String switchTime(String created) {
        SimpleDateFormat SDformat = new SimpleDateFormat("yyyy-MM-dd E");
        long time=Long.valueOf(created);
        String dataTime = SDformat.format(new Date(time * 1000));
        return dataTime;
    }
}





