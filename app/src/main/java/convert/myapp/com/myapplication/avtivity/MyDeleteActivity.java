package convert.myapp.com.myapplication.avtivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.adapter.ColletAdapter;
import convert.myapp.com.myapplication.adapter.TieAdapter;
import convert.myapp.com.myapplication.base.BaseActivity;
import convert.myapp.com.myapplication.bean.ArticleBean;
import convert.myapp.com.myapplication.bean.CollectBean;
import convert.myapp.com.myapplication.bean.CommentBean;
import convert.myapp.com.myapplication.db.MyDBHelper;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.utils.DensityUtil;
import convert.myapp.com.myapplication.utils.JsonUtil;
import convert.myapp.com.myapplication.utils.MyLogUtils;
import convert.myapp.com.myapplication.utils.SPUtils;
import convert.myapp.com.myapplication.utils.ScreenSizeUtils;
import convert.myapp.com.myapplication.utils.ToastUtils;
import convert.myapp.com.myapplication.view.MyDialog;

public class MyDeleteActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private TieAdapter tieAdapter;

    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected int getResourceId() {
        return R.layout.activity_choose;
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
    }

    @Override
    protected void initData() {
        super.initData();
        getData();
    }

    private void getData() {

        OkGo.<String>get(Api.baseUrl+Api.articleDeletedListUrl)
                .params("pageNum",1)
                .params("pageSize",100)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        MyLogUtils.e("我的删除列表",response.body());

                        ArticleBean articleBean = JsonUtil.parseJson(response.body(),ArticleBean.class);
                        int code = articleBean.getCode();
                        if(code == 200){

                            final List<ArticleBean.Data> data = articleBean.getData();
                            if(data.size() > 0){
                                tieAdapter = new TieAdapter(MyDeleteActivity.this,R.layout.list_item_tiezi,data);
                                recyclerView.setAdapter(tieAdapter);
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

                            }


                        }else {

                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);

                    }
                });



    }


    private MyDialog m_Dialog;
    private void deleteItem(final int id, final int position) {
        m_Dialog = new MyDialog(this, R.style.dialogdialog);
        Window window = m_Dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_huifu, null);
        RelativeLayout rl_sure = view.findViewById(R.id.rl_sure);
        RelativeLayout rl_sure1 = view.findViewById(R.id.rl_sure1);

        int width = ScreenSizeUtils.getWidth(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width - DensityUtil.dip2px(this,24),
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
                        .params("deleted",0)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {

                                MyLogUtils.e("删除帖子",response.body());
                                CommentBean commentBean = JsonUtil.parseJson(response.body(),CommentBean.class);
                                int code = commentBean.getCode();
                                if(code ==200){
                                    Intent intent = new Intent();
                                    intent.setAction("refreshManager");
                                    sendBroadcast(intent);
                                    finish();
                                    ToastUtils.showToast(MyDeleteActivity.this,"恢复成功！");
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