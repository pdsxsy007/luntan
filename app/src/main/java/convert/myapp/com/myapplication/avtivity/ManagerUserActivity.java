package convert.myapp.com.myapplication.avtivity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.List;

import butterknife.BindView;
import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.adapter.ManagerAdapter;
import convert.myapp.com.myapplication.base.BaseActivity;
import convert.myapp.com.myapplication.bean.AllUserBean;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.utils.JsonUtil;
import convert.myapp.com.myapplication.utils.MyLogUtils;

public class ManagerUserActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ManagerAdapter adapter;

    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected int getResourceId() {
        return R.layout.activity_manager_user;
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


        OkGo.<String>get(Api.baseUrl+Api.userlistUrl)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        MyLogUtils.e("所有角色列表",response.body());

                        AllUserBean nickNameBean = JsonUtil.parseJson(response.body(),AllUserBean.class);
                        int code = nickNameBean.getCode();
                        if(code == 200){
                            final List<AllUserBean.Data> data = nickNameBean.getData();
                            adapter = new ManagerAdapter(ManagerUserActivity.this,R.layout.list_item_people,data);
                            recyclerView.setAdapter(adapter);

                        }
                    }
                });
    }


}
