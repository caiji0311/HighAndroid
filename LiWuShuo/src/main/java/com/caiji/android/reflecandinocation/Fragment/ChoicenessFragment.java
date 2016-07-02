package com.caiji.android.reflecandinocation.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.AvoidXfermode;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.style.BulletSpan;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.caiji.android.reflecandinocation.R;
import com.caiji.android.reflecandinocation.activity.ChoicnessBannerItemActivity;
import com.caiji.android.reflecandinocation.bean.BannerInfo;
import com.caiji.android.reflecandinocation.bean.ChoiceTitle;
import com.caiji.android.reflecandinocation.bean.Constant;
import com.caiji.android.reflecandinocation.bean.Data;
import com.caiji.android.reflecandinocation.bean.Items;
import com.caiji.android.reflecandinocation.bean.RecycleInfo;
import com.caiji.android.reflecandinocation.imageloader.ImageLoader;
import com.caiji.android.reflecandinocation.utils.IOKCallBack;
import com.caiji.android.reflecandinocation.utils.OkHttpTool;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lj on 2016/6/27.
 */
public class ChoicenessFragment extends Fragment {

    @BindView(R.id.fragment_choiceness_explv)
    public PullToRefreshExpandableListView pullToRefreshExpandableListView;

    public  Map<String,List<Items>> datas=new ArrayMap<>();

    public ArrayList<String> groupList = new ArrayList<>();
    private FragmentActivity mContext;
    private MyListViewAdapter adapter;
    private String url;
    private HeaderViewHolder headerViewHolder;
    private ArrayList<Object> imageDatas=new ArrayList<>();
    private ArrayList<Object> smallImage=new ArrayList<>();
    private ExpandableListView listview;


    public static ChoicenessFragment newInstance(String id) {
        
        Bundle args = new Bundle();
        args.putString("id",id);
        ChoicenessFragment fragment = new ChoicenessFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=getActivity();
        String id=getArguments().getString("id");
        url= Constant.GideInfo_Before+id+Constant.GideInfo_Behind;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_choiceness,null);
        ButterKnife.bind(this,view);
         listview=pullToRefreshExpandableListView.getRefreshableView();
        initHeaderView(inflater);
        mContext=getActivity();
        initListViewAdapter();
        initData();
        initHeaderListener();
        initListViewScrollListener();
        setupPullRefreshLV();


        return view;
    }

    /**
     * 对PullToRefreshListView设置上拉刷新  下拉刷新
     * */
    private void setupPullRefreshLV() {
        //设置 上拉  下拉 两种模式
        pullToRefreshExpandableListView.setMode(PullToRefreshBase.Mode.BOTH);
        //对刷新动作进行监听
        pullToRefreshExpandableListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ExpandableListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
                //上拉刷新时对刷新动画进行自己的设置     改写PullToRefreshExpandleListView源码

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {

            }
        });
    }

    private void initListViewScrollListener() {
        listview.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    /**
     * 对BannerListView的item进行监听
     * */
    private void initHeaderListener() {
        headerViewHolder.convenientBanner.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent=new Intent(getActivity(), ChoicnessBannerItemActivity.class);
                BannerInfo.DataBean.BannersBean bannersBean= (BannerInfo.DataBean.BannersBean) imageDatas.get(position);
                BannerInfo.DataBean.BannersBean.TargetBean targetBean=bannersBean.getTarget();
                int postCount=targetBean.getPosts_count();
                intent.putExtra("id",postCount);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始自动滚动
        headerViewHolder.convenientBanner.startTurning(3000);
    }

    @Override
    public void onStop() {
        //停止滚动
        headerViewHolder.convenientBanner.stopTurning();
        super.onStop();
    }

    /**
     * 初始化头部视图
     * */
    private void initHeaderView(LayoutInflater inflater) {
                View headerView=inflater.inflate(R.layout.fragment_choiceness_top,null);
                headerViewHolder=new HeaderViewHolder(headerView);
                //加载RecycleView数据
                initRecycleData();
                setupRecycleView(headerViewHolder);
              //加载binner数据
                initBannerData();
                setupBanner(headerViewHolder);
        listview.addHeaderView(headerView);
    }

    private void initRecycleData() {
        //请求网络，获得rv图片网址
        OkHttpTool.newInstance().start(Constant.Choicness_rv).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                Gson gson=new Gson();
                RecycleInfo recycleInfo=gson.fromJson(result, RecycleInfo.class);
             //  List<BannerInfo.DataBean.BannersBean> bannersBeen= bannerInfo.getData().getBanners();
              //  Log.i("caicai", "success: "+recycleInfo.getData().getSecondary_banners().get(0).getImage_url().toString());
                smallImage.addAll(recycleInfo.getData().getSecondary_banners());
                headerViewHolder.mRecyclerView.getAdapter().notifyDataSetChanged();
            }
        });

    }

    private void setupRecycleView(HeaderViewHolder headerViewHolder) {
        //初始化布局管理器
        LinearLayoutManager manager=new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false);
        headerViewHolder.mRecyclerView.setLayoutManager(manager);
        headerViewHolder.mRecyclerView.setAdapter(new HeaderRVAdapter());

    }

    private void initBannerData() {
       if(imageDatas!=null && !imageDatas.isEmpty()){
           return;
       }
        //网络获取资源
        OkHttpTool.newInstance().start(Constant.Choiceness_banner).callback(new IOKCallBack() {
            @Override
            public void success(String result) {
                Gson gson = new Gson();
                BannerInfo bannerInfo = gson.fromJson(result, BannerInfo.class);
                imageDatas.addAll(bannerInfo.getData().getBanners());
                headerViewHolder.convenientBanner.getViewPager().getAdapter().notifyDataSetChanged();
            }
        });
        //设置原点指示器，要在数据源加载到视图上之后，否则会覆盖
        headerViewHolder.convenientBanner.setPageIndicator(new int[]{R.drawable.btn_check_disabled_nightmode,R.drawable.btn_check_normal});
    }

    /**
     * 对banner绑定适配器
     * */
    private void setupBanner( HeaderViewHolder headerViewHolder) {
        headerViewHolder.convenientBanner
                .setPages(new CBViewHolderCreator<HeaderBannerViewHolder>() {

            @Override
            public HeaderBannerViewHolder createHolder() {
                return new HeaderBannerViewHolder();
            }
        },imageDatas);
    }


    class HeaderViewHolder {

        @BindView(R.id.fragment_choiceness_top_recylerview)
        RecyclerView mRecyclerView;
        @BindView(R.id.fragment_choiceness_top_banner)
        ConvenientBanner convenientBanner;

        public HeaderViewHolder(View headerView) {
            ButterKnife.bind(this,headerView);
        }
    }

    class HeaderRecycleView extends RecyclerView.ViewHolder{
            ImageView imageView;
        public HeaderRecycleView(View itemView) {
            super(itemView);
            imageView= (ImageView) itemView;
        }
    }

    class HeaderRVAdapter extends RecyclerView.Adapter<HeaderRecycleView>{

        @Override
        public HeaderRecycleView onCreateViewHolder(ViewGroup parent, int viewType) {
           ImageView imageView=new ImageView(mContext);
            return new HeaderRecycleView(imageView);
        }

        @Override
        public void onBindViewHolder(HeaderRecycleView holder, int position) {
            RecycleInfo.DataBean.SecondaryBannersBean bean= (RecycleInfo.DataBean.SecondaryBannersBean) smallImage.get(position);
            Picasso.with(mContext).load(bean.getImage_url()).into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return smallImage.size();
        }
    }

    class HeaderBannerViewHolder implements Holder<BannerInfo.DataBean.BannersBean> {
        ImageView imageView;
        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);

            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, BannerInfo.DataBean.BannersBean data) {

            Picasso.with(mContext).load(data.getImage_url()).into(imageView);
        }
    }
    private void initListViewAdapter() {
        adapter=new MyListViewAdapter();
        listview.setAdapter(adapter);



    }

    private void setupExpListView() {
        //设置所有的group 点击不收缩
        listview.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        //设置所有的group默认展开
        for (int i=0;i<groupList.size();i++){
            listview.expandGroup(i);
        }
    }

    /**
     * 初始化listview数据源
     * */
    private void initData() {

        //再次进入时，不再重新加载
        if (groupList != null && !groupList.isEmpty()) {
            initListViewAdapter();
            setupExpListView();
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
            List<Items> list=datas.get(groupList.get(groupPosition));
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
         * */
        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            GroupViewHolder mViewHolder=null;
           if (convertView==null){
               convertView= LayoutInflater.from(mContext).inflate(R.layout.fragment_choiceness_grouplist,null);
               mViewHolder=new GroupViewHolder(convertView);

           }else{
               mViewHolder= (GroupViewHolder) convertView.getTag();
           }


             mViewHolder.tvLeft.setText(groupList.get(groupPosition));


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
        public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            ChildViewHolder mViewHolder=null;

            if (convertView==null){
                convertView= LayoutInflater.from(mContext).inflate(R.layout.fragment_choiceness_childlist,null);
                mViewHolder=new ChildViewHolder(convertView);

            }else{
                mViewHolder= (ChildViewHolder) convertView.getTag();
            }


           ImageView imageView= mViewHolder.imageView;
            Items items=datas.get(groupList.get(groupPosition)).get(childPosition);
            String url=items.getCover_image_url();
            String text01=items.getTitle();
            String text02=items.getLikes_count();
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
 * */
    public String switchTime(String created){
        SimpleDateFormat SDformat=new SimpleDateFormat("yyyy-MM-dd E");
        long time=Long.valueOf(created);
        String dataTime = SDformat.format(new Date(time*1000));
        return dataTime;
    }
}
