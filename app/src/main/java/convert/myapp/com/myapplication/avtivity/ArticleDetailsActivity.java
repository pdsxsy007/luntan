package convert.myapp.com.myapplication.avtivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.w3c.dom.Comment;

import java.util.List;

import butterknife.BindView;
import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.adapter.CommentAdapter;
import convert.myapp.com.myapplication.adapter.TieAdapter;
import convert.myapp.com.myapplication.base.BaseActivity;
import convert.myapp.com.myapplication.bean.ArticleBean;
import convert.myapp.com.myapplication.bean.ArticleItemBean;
import convert.myapp.com.myapplication.bean.BaseBean;
import convert.myapp.com.myapplication.bean.CommentBean;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.utils.JsonUtil;
import convert.myapp.com.myapplication.utils.MyLogUtils;
import convert.myapp.com.myapplication.utils.SPUtils;
import convert.myapp.com.myapplication.utils.ToastUtils;
import convert.myapp.com.myapplication.view.XCRoundImageView;

public class ArticleDetailsActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_louceng)
    TextView tv_louceng;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_comment_count)
    TextView tv_comment_count;
    @BindView(R.id.xcimage)
    ImageView xcimage;
    @BindView(R.id.iv_comment)
    ImageView iv_comment;
    @BindView(R.id.iv_time)
    ImageView iv_time;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.et_content)
    EditText et_content;
    @BindView(R.id.rl_top)
    RelativeLayout rl_top;
    @BindView(R.id.views)
    View views;
    @BindView(R.id.tv_replay_name)
    TextView tv_replay_name;
    @BindView(R.id.tv_commit)
    TextView tv_commit;
    @BindView(R.id.btn_replay)
    Button btn_replay;
    @BindView(R.id.ll_bottom)
    LinearLayout ll_bottom;
    @BindView(R.id.touxiang)
    ImageView touxiang;

    String articleId;
    private CommentAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;

    private String replayName;
    private String replayId;
    String nicknameId;
    String userId;
    @BindView(R.id.swipeLayout)
    SmartRefreshLayout mSwipeLayout;
    private int num = 1;
    @Override
    protected int getResourceId() {
        return R.layout.activity_article_details;
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
        registerBoradcastReceiver();

    }

    public void registerBoradcastReceiver() {
        IntentFilter myIntentFilter = new IntentFilter();
        myIntentFilter.addAction("refresh");
        //注册广播
        registerReceiver(broadcastReceiver, myIntentFilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("refresh")){
                replayId = intent.getStringExtra("nicknameId");
                userId = intent.getStringExtra("userId");
                rl_top.setVisibility(View.VISIBLE);
                views.setVisibility(View.VISIBLE);
                showSoftInputFromWindow(et_content);
                tv_replay_name.setText(replayName);
            }
        }
    };


    @Override
    protected void initData() {
        super.initData();
        tv_louceng.setText("楼主");
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL,false);
        recycler_view.setLayoutManager(mLinearLayoutManager);
        recycler_view.setHasFixedSize(true);
        recycler_view.setNestedScrollingEnabled(false);
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String time = getIntent().getStringExtra("time");
        String name = getIntent().getStringExtra("name");
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
        String number = getIntent().getStringExtra("number");

        nicknameId = getIntent().getStringExtra("nicknameId");

        userId = getIntent().getStringExtra("userId");

        articleId = getIntent().getStringExtra("articleId");
        replayName = name;
        replayId = nicknameId;

        tv_name.setText(name);
        tv_time.setText(time);
        tv_title.setText(title);
        tv_content.setText(content);
        tv_comment_count.setText(number);
        Glide.with(this).load(Api.imgUrl+imageUrl).asBitmap().into(xcimage);
        int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if(mode == Configuration.UI_MODE_NIGHT_YES) {
            Glide.with(this).load(R.mipmap.comment_day).asBitmap().into(iv_comment);
            Glide.with(this).load(R.mipmap.timeday).asBitmap().into(iv_time);
        } else if(mode == Configuration.UI_MODE_NIGHT_NO) {
            Glide.with(this).load(R.mipmap.comment).asBitmap().into(iv_comment);
            Glide.with(this).load(R.mipmap.timenight).asBitmap().into(iv_time);
        }

        getCommentList();

    }

    private void getCommentList() {
        OkGo.<String>get(Api.baseUrl+Api.articleCommentListUrl)
                .params("pageNum",num)
                .params("pageSize",10)
                .params("articleId",articleId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        MyLogUtils.e("评论列表",response.body());
                        CommentBean commentBean = JsonUtil.parseJson(response.body(),CommentBean.class);
                        int code = commentBean.getCode();
                        if(code ==200){
                            data = commentBean.getData();
                            adapter = new CommentAdapter(ArticleDetailsActivity.this,R.layout.list_item_comment,data);
                            recycler_view.setAdapter(adapter);
                            num = 2;
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);


                    }
                });
    }
    String content;
    @Override
    protected void initListener() {
        super.initListener();


        String nickNameUrlByComment = (String) SPUtils.get(this, "nickNameUrlByComment", "");
        if(!nickNameUrlByComment.equals("")){
            Glide.with(this).load(Api.imgUrl+ this.nickNameUrlByComment).asBitmap().into(touxiang);
        }



        et_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rl_top.setVisibility(View.VISIBLE);
                views.setVisibility(View.VISIBLE);
                showSoftInputFromWindow(et_content);
                tv_replay_name.setText(replayName);
                String nickNameUrlByComment = (String) SPUtils.get(ArticleDetailsActivity.this, "nickNameUrlByComment", "");
                if(!nickNameUrlByComment.equals("")){
                    Glide.with(ArticleDetailsActivity.this).load(Api.imgUrl+ nickNameUrlByComment).asBitmap().into(touxiang);
                }

            }
        });

        btn_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_top.setVisibility(View.VISIBLE);
                views.setVisibility(View.VISIBLE);
                showSoftInputFromWindow(et_content);
                tv_replay_name.setText(replayName);
                String nickNameUrlByComment = (String) SPUtils.get(ArticleDetailsActivity.this, "nickNameUrlByComment", "");
                if(!nickNameUrlByComment.equals("")){
                    Glide.with(ArticleDetailsActivity.this).load(Api.imgUrl+ nickNameUrlByComment).asBitmap().into(touxiang);
                }
            }
        });

        mSwipeLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                num = 1;
                getTopData();
                getCommentRefreshData(refreshlayout);
            }
        });

        mSwipeLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore( RefreshLayout refreshlayout) {
                getTopData();
                getCommentLoadData(refreshlayout);

            }
        });

        tv_commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                content = et_content.getText().toString().trim();
                if(content.equals("")){
                    ToastUtils.showToast(ArticleDetailsActivity.this,"请输入回复内容!");
                    return;
                }

                String forbiddenWordsId = (String) SPUtils.get(ArticleDetailsActivity.this, "ForbiddenWordsId", "");
                if(forbiddenWordsId.equals("0")){
                    String userIdByLogin = (String) SPUtils.get(ArticleDetailsActivity.this, "userId", "");//当前登陆的用户
                    if(userIdByLogin.equals(userId)){//同一个用户评论，不需要弹出选择角色框

                        OkGo.<String>post(Api.baseUrl + Api.articleCommentSaveUrl)
                                .params("articleId",articleId)
                                .params("commentContent", content)
                                .params("nicknameId", nicknameId)
                                .params("userId", (String) SPUtils.get(ArticleDetailsActivity.this,"userId",""))
                                .params("replyUserId", userId)
                                .params("replyNicknameId", replayId)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        MyLogUtils.e("评论",response.body());
                                        BaseBean baseBean = JsonUtil.parseJson(response.body(),BaseBean.class);
                                        int code = baseBean.getCode();
                                        if(code == 200){
                                            String msg = baseBean.getMsg();
                                            ToastUtils.showToast(ArticleDetailsActivity.this,msg);
                                            stopDialog();

                                            num=1;
                                            getCommentList();
                                            Intent intent = new Intent();
                                            intent.setAction("refreshHomeData");
                                            sendBroadcast(intent);

                                        }

                                    }
                                    @Override
                                    public void onError(Response<String> response) {
                                        super.onError(response);

                                    }

                                    @Override
                                    public void onStart(Request<String, ? extends Request> request) {
                                        super.onStart(request);
                                        showDialog("评论中");
                                    }
                                });

                    }else {//其他用户评论本帖子

                        String flag = (String) SPUtils.get(ArticleDetailsActivity.this, "flag", "");
                        if(!flag.equals("")){
                            String nickNameIdByComment = (String) SPUtils.get(ArticleDetailsActivity.this, "nickNameIdByComment", "");
                            addCommentByOther(nickNameIdByComment);
                        }else {
                            ToastUtils.showToast(ArticleDetailsActivity.this,"请先选择角色!");
                        }


                    }

                }else {
                    ToastUtils.showToast(ArticleDetailsActivity.this,"您的账号已禁言!");
                }

            }
        });

        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArticleDetailsActivity.this,ChooseActivity.class);
                startActivityForResult(intent,99);
            }
        });
    }

    private void getCommentLoadData(final RefreshLayout refreshlayout) {

        OkGo.<String>get(Api.baseUrl+Api.articleCommentListUrl)
                .params("pageNum",num)
                .params("pageSize",10)
                .params("articleId",articleId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        MyLogUtils.e("评论列表",response.body());
                        CommentBean commentBean = JsonUtil.parseJson(response.body(),CommentBean.class);
                        int code = commentBean.getCode();
                        if(code ==200){

                            List<CommentBean.Data> dataMore = commentBean.getData();
                            if(dataMore.size() > 0) {
                                data.addAll(dataMore);
                                adapter = new CommentAdapter(ArticleDetailsActivity.this,R.layout.list_item_comment, ArticleDetailsActivity.this.data);
                                recycler_view.setAdapter(adapter);
                                num += 1;
                            }
                            refreshlayout.finishLoadmore();

                        }else {
                            refreshlayout.finishLoadmore();
                        }


                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        refreshlayout.finishLoadmore();

                    }
                });
    }

    List<CommentBean.Data> data;
    private void getCommentRefreshData(final RefreshLayout refreshlayout) {
        OkGo.<String>get(Api.baseUrl+Api.articleCommentListUrl)
                .params("pageNum",num)
                .params("pageSize",10)
                .params("articleId",articleId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        MyLogUtils.e("评论列表",response.body());
                        CommentBean commentBean = JsonUtil.parseJson(response.body(),CommentBean.class);
                        int code = commentBean.getCode();
                        if(code ==200){

                            data = commentBean.getData();
                            adapter = new CommentAdapter(ArticleDetailsActivity.this,R.layout.list_item_comment,data);
                            recycler_view.setAdapter(adapter);
                            num = 2;
                            refreshlayout.finishRefresh();
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

    public void showSoftInputFromWindow(EditText editText){
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, 0);
    }


    private int flag = 0;
    String nickNameIdByComment;
    String nickNameUrlByComment;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 99){

            nickNameIdByComment = data.getStringExtra("nickNameId");

            nickNameUrlByComment = data.getStringExtra("nickNameUrl");
            flag = 1;
            SPUtils.put(ArticleDetailsActivity.this,"flag",flag+"");
            SPUtils.put(ArticleDetailsActivity.this,"nickNameIdByComment",nickNameIdByComment+"");
            SPUtils.put(ArticleDetailsActivity.this,"nickNameUrlByComment",nickNameUrlByComment+"");

            Glide.with(this).load(Api.imgUrl+nickNameUrlByComment).asBitmap().into(touxiang);
        }
    }

    private void addCommentByOther(String nickNameIdByComment) {
        OkGo.<String>post(Api.baseUrl + Api.articleCommentSaveUrl)
                .params("articleId",articleId)
                .params("commentContent", content)
                .params("nicknameId", nickNameIdByComment)
                .params("userId", (String) SPUtils.get(ArticleDetailsActivity.this,"userId",""))
                .params("replyUserId", userId)
                .params("replyNicknameId", replayId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        MyLogUtils.e("评论。。。",response.body());
                        BaseBean baseBean = JsonUtil.parseJson(response.body(),BaseBean.class);
                        int code = baseBean.getCode();
                        if(code == 200){
                            String msg = baseBean.getMsg();
                            ToastUtils.showToast(ArticleDetailsActivity.this,msg);
                            stopDialog();

                            num = 1;
                            getCommentList();
                            Intent intent = new Intent();
                            intent.setAction("refreshHomeData");
                            sendBroadcast(intent);
                            rl_top.setVisibility(View.INVISIBLE);
                            views.setVisibility(View.GONE);
                            tv_replay_name.setText("");
                            et_content.setText("");
                        }

                    }
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }

                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        showDialog("评论中");
                    }
                });
    }


    private void getTopData() {
        OkGo.<String>get(Api.baseUrl+Api.articleGetOneUrl)
                .params("id",articleId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        MyLogUtils.e("顶部数据",response.body());
                        ArticleItemBean commentBean = JsonUtil.parseJson(response.body(),ArticleItemBean.class);
                        int code = commentBean.getCode();
                        if(code ==200){
                            ArticleItemBean.Data data = commentBean.getData();
                            tv_name.setText(data.getNicknameName());
                            tv_time.setText(data.getCreatTime());
                            tv_title.setText(data.getArticleTitle());
                            tv_content.setText(data.getArticleContent());
                            tv_comment_count.setText(data.getRepliesNumber()+"");
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);


                    }
                });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }
}
