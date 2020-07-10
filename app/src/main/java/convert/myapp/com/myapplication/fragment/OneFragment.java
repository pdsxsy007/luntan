package convert.myapp.com.myapplication.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import convert.myapp.com.myapplication.adapter.CommentAdapter;
import convert.myapp.com.myapplication.adapter.TieAdapter;
import convert.myapp.com.myapplication.avtivity.ArticleDetailsActivity;
import convert.myapp.com.myapplication.avtivity.MainActivity;
import convert.myapp.com.myapplication.base.BaseFragment;
import convert.myapp.com.myapplication.bean.ArticleBean;
import convert.myapp.com.myapplication.bean.CommentBean;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.utils.DensityUtil;
import convert.myapp.com.myapplication.utils.JsonUtil;
import convert.myapp.com.myapplication.utils.MyLogUtils;
import convert.myapp.com.myapplication.utils.SPUtils;
import convert.myapp.com.myapplication.utils.ScreenSizeUtils;
import convert.myapp.com.myapplication.utils.ToastUtils;
import convert.myapp.com.myapplication.view.MyDialog;

public class OneFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.swipeLayout)
    SmartRefreshLayout mSwipeLayout;

    private TieAdapter tieAdapter;

    private LinearLayoutManager mLinearLayoutManager;

    private int num = 1;


    @Override
    public int getLayoutResID() {
        return R.layout.fragment_one;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        mLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        mSwipeLayout.autoRefresh();
    }

    @Override
    public void initListener() {
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
        registerBoradcastReceiver();
    }

    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("refreshHomeData");
        //注册广播
        getActivity().registerReceiver(broadcastReceiver, myIntentFilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("refreshHomeData")){
                num = 1;
                mSwipeLayout.autoRefresh();
            }
        }
    };

    /**
     * 加载更多数据
     * @param refreshlayout
     */
    private void getHomeLoadData(final RefreshLayout refreshlayout) {
        OkGo.<String>get(Api.baseUrl+Api.articleListUrl)
                .params("pageNum",num)
                .params("pageSize",10)
                .params("articleType",1)
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
                                tieAdapter = new TieAdapter(getActivity(),R.layout.list_item_tiezi,data);
                                recyclerView.setAdapter(tieAdapter);
                                num+=1;
                                if(refreshlayout != null){
                                    refreshlayout.finishLoadmore();
                                }

                                tieAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                                        Intent intent = new Intent(getActivity(), ArticleDetailsActivity.class);
                                        intent.putExtra("imageUrl",data.get(position).getNicknameUrl());
                                        intent.putExtra("time",data.get(position).getCreatTime());
                                        intent.putExtra("name",data.get(position).getNicknameName());
                                        intent.putExtra("title",data.get(position).getArticleTitle());
                                        intent.putExtra("content",data.get(position).getArticleContent());
                                        intent.putExtra("number",data.get(position).getRepliesNumber()+"");
                                        intent.putExtra("articleId",data.get(position).getId()+"");
                                        intent.putExtra("nicknameId",data.get(position).getNicknameId()+"");
                                        intent.putExtra("userId",data.get(position).getUserId()+"");
                                        startActivity(intent);
                                    }

                                    @Override
                                    public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                                       /* int id = data.get(position).getId();
                                        int userId = data.get(position).getUserId();
                                        String userId1 = (String) SPUtils.get(getActivity(), "userId", "");
                                        if(userId1.equals(""+userId)){//同一个用户 可以删除
                                            deleteItem(id,position);
                                        }else {
                                            ToastUtils.showToast(getActivity(),"当前帖子不是您发布的！");
                                            return true;
                                        }*/
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

    List<ArticleBean.Data> data;

    /**
     * 刷新数据
     * @param refreshlayout
     */
    private void getHomeRefreshData(final RefreshLayout refreshlayout) {
        OkGo.<String>get(Api.baseUrl+Api.articleListUrl)
                .params("pageNum",num)
                .params("pageSize",10)
                .params("articleType",1)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        MyLogUtils.e("帖子列表刷新",response.body());

                        ArticleBean articleBean = JsonUtil.parseJson(response.body(),ArticleBean.class);
                        int code = articleBean.getCode();
                        if(code == 200){

                            data = articleBean.getData();
                            tieAdapter = new TieAdapter(getActivity(),R.layout.list_item_tiezi,data);
                            recyclerView.setAdapter(tieAdapter);
                            if(refreshlayout != null){
                                refreshlayout.finishRefresh();
                            }

                            num=2;
                            tieAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                                    Intent intent = new Intent(getActivity(), ArticleDetailsActivity.class);
                                    intent.putExtra("imageUrl",data.get(position).getNicknameUrl());
                                    intent.putExtra("time",data.get(position).getCreatTime());
                                    intent.putExtra("name",data.get(position).getNicknameName());
                                    intent.putExtra("title",data.get(position).getArticleTitle());
                                    intent.putExtra("content",data.get(position).getArticleContent());
                                    intent.putExtra("number",data.get(position).getRepliesNumber()+"");
                                    intent.putExtra("articleId",data.get(position).getId()+"");
                                    intent.putExtra("nicknameId",data.get(position).getNicknameId()+"");
                                    intent.putExtra("userId",data.get(position).getUserId()+"");
                                    startActivity(intent);
                                }

                                @Override
                                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                                    /*int id = data.get(position).getId();
                                    int userId = data.get(position).getUserId();
                                    String userId1 = (String) SPUtils.get(getActivity(), "userId", "");
                                    if(userId1.equals(""+userId)){//同一个用户 可以删除
                                        deleteItem(id,position);
                                    }else {
                                        ToastUtils.showToast(getActivity(),"当前帖子不是您发布的！");
                                        return true;
                                    }*/

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


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(broadcastReceiver);
    }


    private MyDialog m_Dialog;
    private void deleteItem(final int id, final int position) {
        m_Dialog = new MyDialog(getActivity(), R.style.dialogdialog);
        Window window = m_Dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_delete, null);
        RelativeLayout rl_sure = view.findViewById(R.id.rl_sure);
        RelativeLayout rl_sure1 = view.findViewById(R.id.rl_sure1);

        int width = ScreenSizeUtils.getWidth(getActivity());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width - DensityUtil.dip2px(getActivity(),24),
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
                                    ToastUtils.showToast(getActivity(),"删除成功！");
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


}
