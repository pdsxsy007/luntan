package convert.myapp.com.myapplication.avtivity;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

import butterknife.BindView;
import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.adapter.NickNameAdapter;
import convert.myapp.com.myapplication.base.BaseActivity;
import convert.myapp.com.myapplication.bean.NickNameBean;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.utils.JsonUtil;
import convert.myapp.com.myapplication.utils.MyLogUtils;

public class ChooseActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private NickNameAdapter adapter;

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
        initLeftData();
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
                            adapter = new NickNameAdapter(ChooseActivity.this,R.layout.list_item_nickname,data);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                                    Intent intent = new Intent();
                                    intent.putExtra("nickNameId",data.get(position).getId()+"");
                                    setResult(99,intent);
                                    finish();
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