package convert.myapp.com.myapplication.avtivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.base.BaseActivity;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.utils.SPUtils;
import convert.myapp.com.myapplication.utils.ToastUtils;

public class SendArticleActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.et_phoneNum)
    EditText et_phoneNum;

    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.btn_login)
    Button btn_login;
    @Override
    protected int getResourceId() {
        return R.layout.activity_send_activle;
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("发帖");
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
        String nickNameId = getIntent().getStringExtra("nickNameId");
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_phoneNum.getText().toString().trim();
                String pwd = et_password.getText().toString().trim();
                if(name.equals("") || pwd.equals("")){

                    ToastUtils.showToast(SendArticleActivity.this,"请输入标题或内容!!");
                    return;
                }
                OkGo.<String>post(Api.baseUrl + Api.saveArticleUrl)
                        .params("articleTitle",name)
                        .params("articleContent", pwd)
                        .params("nicknameId", pwd)
                        .params("userId", (String)SPUtils.get(SendArticleActivity.this,"userId",""))
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Log.e("发帖",response.body());


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
