package convert.myapp.com.myapplication.avtivity;

import android.support.v4.view.GravityCompat;
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
import convert.myapp.com.myapplication.bean.RegisterBean;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.utils.JsonUtil;
import convert.myapp.com.myapplication.utils.ToastUtils;

public class RegiestActivity extends BaseActivity {
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
        return R.layout.activity_regiest;
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("注册");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_phoneNum.getText().toString().trim();
                String pwd = et_password.getText().toString().trim();
                if(name.equals("") || pwd.equals("")){

                    ToastUtils.showToast(RegiestActivity.this,"请输入用户名或密码!");
                    return;
                }
                OkGo.<String>post(Api.baseUrl + Api.registerUrl)
                        .params("userAccount",name)
                        .params("userPassword", pwd)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                Log.e("注册",response.body());
                                RegisterBean registerBean = JsonUtil.parseJson(response.toString(),RegisterBean.class);
                             if(registerBean.getCode() == 200){
                                 ToastUtils.showToast(RegiestActivity.this,"注册成功");
                                 finish();
                             }else{
                                 ToastUtils.showToast(RegiestActivity.this,"注册失败");
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
