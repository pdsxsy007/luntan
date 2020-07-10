package convert.myapp.com.myapplication.avtivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

import butterknife.BindView;
import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.adapter.ManagerAdapter;
import convert.myapp.com.myapplication.adapter.ManagerTieAdapter;
import convert.myapp.com.myapplication.adapter.NickNameAdapter;
import convert.myapp.com.myapplication.adapter.TieAdapter;
import convert.myapp.com.myapplication.base.BaseActivity;
import convert.myapp.com.myapplication.bean.AllUserBean;
import convert.myapp.com.myapplication.bean.ArticleBean;
import convert.myapp.com.myapplication.bean.CommentBean;
import convert.myapp.com.myapplication.bean.NickNameBean;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.utils.DensityUtil;
import convert.myapp.com.myapplication.utils.JsonUtil;
import convert.myapp.com.myapplication.utils.MyLogUtils;
import convert.myapp.com.myapplication.utils.SPUtils;
import convert.myapp.com.myapplication.utils.ScreenSizeUtils;
import convert.myapp.com.myapplication.utils.ToastUtils;
import convert.myapp.com.myapplication.view.MyDialog;

public class ManagerActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeLayout)
    SmartRefreshLayout mSwipeLayout;

    private ManagerTieAdapter tieAdapter;

    private LinearLayoutManager mLinearLayoutManager;

    private int num = 1;

    @Override
    protected int getResourceId() {
        return R.layout.activity_manager;
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(mLinearLayoutManager);

        mSwipeLayout.autoRefresh();
        registerBoradcastReceiver();
    }

    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("refreshManager");
        //注册广播
        registerReceiver(broadcastReceiver, myIntentFilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("refreshManager")){
                mSwipeLayout.autoRefresh();
            }
        }
    };



    @Override
    protected void initListener() {
        super.initListener();
        mSwipeLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                num = 1;
                getHomeRefreshData(refreshlayout);
            }
        });

        mSwipeLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore( RefreshLayout refreshlayout) {
                getHomeLoadData(refreshlayout);

            }
        });
    }



    List<ArticleBean.Data> data;

    /**
     * 刷新数据
     * @param refreshlayout
     */
    private void getHomeRefreshData(final RefreshLayout refreshlayout) {
        OkGo.<String>get(Api.baseUrl+Api.articleListUrl)
                .params("pageNum",num)
                .params("pageSize",10)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        MyLogUtils.e("帖子列表刷新",response.body());

                        ArticleBean articleBean = JsonUtil.parseJson(response.body(),ArticleBean.class);
                        int code = articleBean.getCode();
                        if(code == 200){

                            data = articleBean.getData();
                            tieAdapter = new ManagerTieAdapter(ManagerActivity.this,R.layout.list_item_tiezi,data);
                            recyclerView.setAdapter(tieAdapter);
                            if(refreshlayout != null){
                                refreshlayout.finishRefresh();
                            }

                            num=2;
                            tieAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                                }

                                @Override
                                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                                    int id = data.get(position).getId();

                                    deleteItem(id,position);
                                    return false;
                                }
                            });
                        }else {
                            if(refreshlayout != null){
                                refreshlayout.finishRefresh();
                            }

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        if(refreshlayout != null){
                            refreshlayout.finishRefresh();
                        }

                    }
                });
    }


    /**
     * 加载更多数据
     * @param refreshlayout
     */
    private void getHomeLoadData(final RefreshLayout refreshlayout) {
        OkGo.<String>get(Api.baseUrl+Api.articleListUrl)
                .params("pageNum",num)
                .params("pageSize",10)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        MyLogUtils.e("帖子列表加载",num+"");
                        MyLogUtils.e("帖子列表加载",response.body());

                        ArticleBean articleBean = JsonUtil.parseJson(response.body(),ArticleBean.class);
                        int code = articleBean.getCode();
                        if(code == 200){
                            List<ArticleBean.Data> dataMore = articleBean.getData();
                            if(dataMore.size() > 0){
                                data.addAll(dataMore);
                                tieAdapter = new ManagerTieAdapter(ManagerActivity.this,R.layout.list_item_tiezi,data);
                                recyclerView.setAdapter(tieAdapter);
                                num+=1;
                                if(refreshlayout != null){
                                    refreshlayout.finishLoadmore();
                                }

                                tieAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                                    }

                                    @Override
                                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                                        int id = data.get(position).getId();

                                        deleteItem(id,position);
                                        return false;
                                    }
                                });
                            }else {
                                refreshlayout.finishLoadmore();
                                //ToastUtils.showToast(MainActivity.this,"暂无更多数据啊!");
                            }

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        refreshlayout.finishLoadmore();
                    }
                });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manager,menu);

        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){


            case R.id.setting1:

                Intent intent4 = new Intent(ManagerActivity.this,MyDeleteActivity.class);
                startActivity(intent4);
                break;
            case R.id.setting2:
                Intent intent3 = new Intent(ManagerActivity.this,ManagerUserActivity.class);
                startActivity(intent3);
                break;


        }
        return true;

    }



    private MyDialog m_Dialog;
    private void deleteItem(final int id, final int position) {
        m_Dialog = new MyDialog(ManagerActivity.this, R.style.dialogdialog);
        Window window = m_Dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        View view = LayoutInflater.from(ManagerActivity.this).inflate(R.layout.dialog_delete, null);
        RelativeLayout rl_sure = view.findViewById(R.id.rl_sure);
        RelativeLayout rl_sure1 = view.findViewById(R.id.rl_sure1);

        int width = ScreenSizeUtils.getWidth(ManagerActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width - DensityUtil.dip2px(ManagerActivity.this,24),
                LinearLayout.LayoutParams.WRAP_CONTENT);
        m_Dialog.setContentView(view, layoutParams);
        m_Dialog.show();
        m_Dialog.setCanceledOnTouchOutside(true);
        rl_sure1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_Dialog.dismiss();
            }
        });
        rl_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_Dialog.dismiss();
                OkGo.<String>post(Api.baseUrl+Api.articleDeletedUrl)
                        .params("id",id)
                        .params("deleted",1)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {

                                MyLogUtils.e("删除帖子",response.body());
                                CommentBean commentBean = JsonUtil.parseJson(response.body(),CommentBean.class);
                                int code = commentBean.getCode();
                                if(code ==200){
                                    data.remove(position);
                                    tieAdapter.notifyDataSetChanged();
                                    ToastUtils.showToast(ManagerActivity.this,"删除成功！");
                                }

                            }

                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);


                            }
                        });
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

}
