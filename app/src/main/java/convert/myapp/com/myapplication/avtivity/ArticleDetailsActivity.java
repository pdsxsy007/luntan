package convert.myapp.com.myapplication.avtivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import org.w3c.dom.Comment;

import java.util.List;

import butterknife.BindView;
import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.adapter.CommentAdapter;
import convert.myapp.com.myapplication.adapter.TieAdapter;
import convert.myapp.com.myapplication.base.BaseActivity;
import convert.myapp.com.myapplication.bean.ArticleBean;
import convert.myapp.com.myapplication.bean.CommentBean;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.utils.JsonUtil;
import convert.myapp.com.myapplication.utils.MyLogUtils;
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
    XCRoundImageView xcimage;
    @BindView(R.id.iv_comment)
    ImageView iv_comment;
    @BindView(R.id.iv_time)
    ImageView iv_time;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.et_content)
    EditText et_content;
    private int num = 1;
    String articleId;
    private CommentAdapter adapter;
    private LinearLayoutManager mLinearLayoutManager;
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
    }

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

        articleId = getIntent().getStringExtra("articleId");
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

                        MyLogUtils.e("帖子列表刷新",response.body());
                        CommentBean commentBean = JsonUtil.parseJson(response.body(),CommentBean.class);
                        int code = commentBean.getCode();
                        if(code ==200){
                            List<CommentBean.Data> data = commentBean.getData();
                            adapter = new CommentAdapter(ArticleDetailsActivity.this,R.layout.list_item_comment,data);
                            recycler_view.setAdapter(adapter);
                        }

                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);


                    }
                });
    }
}
