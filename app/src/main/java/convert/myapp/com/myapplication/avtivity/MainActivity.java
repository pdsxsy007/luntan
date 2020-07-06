package convert.myapp.com.myapplication.avtivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

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
import convert.myapp.com.myapplication.adapter.NickNameAdapter;
import convert.myapp.com.myapplication.adapter.TieAdapter;
import convert.myapp.com.myapplication.base.BaseActivity;
import convert.myapp.com.myapplication.bean.ArticleBean;
import convert.myapp.com.myapplication.bean.NickNameBean;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.utils.JsonUtil;
import convert.myapp.com.myapplication.utils.MyLogUtils;
import convert.myapp.com.myapplication.utils.SettingUtils;
import okhttp3.Headers;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.rv_msg_list)
    RecyclerView rv_msg_list;
    private NickNameAdapter adapter;
    private TieAdapter tieAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private LinearLayoutManager mLinearLayoutManager2;
    private SettingUtils settingUtils;
    @BindView(R.id.swipeLayout)
    SmartRefreshLayout mSwipeLayout;
    private int num = 0;
    @Override
    protected void initView() {
        super.initView();
        settingUtils = SettingUtils.getInstance();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL,false);
        mLinearLayoutManager2 = new LinearLayoutManager(this, LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        rv_msg_list.setLayoutManager(mLinearLayoutManager2);

        mSwipeLayout.autoRefresh();
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_main;
    }


    @Override
    protected void initListener() {
        super.initListener();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerlayout.openDrawer(GravityCompat.START);

            }
        });
        drawerlayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                initLeftData();
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        mSwipeLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                num = 0;
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
                        MyLogUtils.e("帖子列表刷新",response.body());

                        ArticleBean articleBean = JsonUtil.parseJson(response.body(),ArticleBean.class);
                        int code = articleBean.getCode();
                        if(code == 200){
                            List<ArticleBean.Data> dataMore = articleBean.getData();
                            if(dataMore.size() > 0){
                                data.addAll(dataMore);
                                tieAdapter = new TieAdapter(MainActivity.this,R.layout.list_item_tiezi,data);
                                rv_msg_list.setAdapter(tieAdapter);
                                num++;
                                refreshlayout.finishLoadmore();
                                tieAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                                        Intent intent = new Intent(MainActivity.this, ArticleDetailsActivity.class);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                                        return false;
                                    }
                                });
                            }else {
                                refreshlayout.setLoadmoreFinished(true);
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
                            tieAdapter = new TieAdapter(MainActivity.this,R.layout.list_item_tiezi,data);
                            rv_msg_list.setAdapter(tieAdapter);
                            refreshlayout.finishRefresh();
                            num=1;
                            tieAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                                    Intent intent = new Intent(MainActivity.this, ArticleDetailsActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                                    return false;
                                }
                            });
                        }else {
                            refreshlayout.finishRefresh();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                        refreshlayout.finishRefresh();
                    }
                });
    }


    @Override
    protected void initData() {
        super.initData();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if(mode == Configuration.UI_MODE_NIGHT_YES) {
            menu.findItem(R.id.setting).setTitle("日间模式");
        } else if(mode == Configuration.UI_MODE_NIGHT_NO) {
            menu.findItem(R.id.setting).setTitle("夜间模式");
        }

        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.fatie://发帖
                drawerlayout.openDrawer(GravityCompat.START);

                break;
            case R.id.setting://日夜切换

                int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if(mode == Configuration.UI_MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    settingUtils.saveNightMode(false);
                } else if(mode == Configuration.UI_MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    settingUtils.saveNightMode(true);
                }
                //recreate();
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
        }
        return true;

    }

    private void initLeftData() {


        OkGo.<String>get(Api.baseUrl+Api.nickNameListUrl)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        MyLogUtils.e("左侧角色列表",response.body());

                        NickNameBean nickNameBean = JsonUtil.parseJson(response.body(),NickNameBean.class);
                        int code = nickNameBean.getCode();
                        if(code == 200){
                            final List<NickNameBean.Data> data = nickNameBean.getData();
                            adapter = new NickNameAdapter(MainActivity.this,R.layout.list_item_nickname,data);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                                    Intent intent = new Intent(MainActivity.this,SendArticleActivity.class);
                                    intent.putExtra("nickNameId",data.get(position).getId()+"");
                                    startActivity(intent);
                                }

                                @Override
                                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                                    return false;
                                }
                            });
                        }
                    }
                });
    }


}
