package convert.myapp.com.myapplication.avtivity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewUtils;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import butterknife.BindView;
import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.base.BaseActivity;
import convert.myapp.com.myapplication.bean.LoginBean;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.utils.JsonUtil;
import convert.myapp.com.myapplication.utils.MyLogUtils;
import convert.myapp.com.myapplication.utils.SPUtils;
import convert.myapp.com.myapplication.utils.ToastUtils;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.rl_reg)
    RelativeLayout rl_reg;

    @BindView(R.id.et_phoneNum)
    EditText et_phoneNum;

    @BindView(R.id.et_password)
    EditText et_password;

    @BindView(R.id.btn_login)
    Button btn_login;

    @Override
    protected int getResourceId() {
        return R.layout.activity_login;
    }



    @Override
    protected void initListener() {
        super.initListener();
        String userId = (String) SPUtils.get(this, "userId", "");
        if(!userId.equals("")){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        rl_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegiestActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_phoneNum.getText().toString().trim();
                String pwd = et_password.getText().toString().trim();
                if(name.equals("") || pwd.equals("")){

                    ToastUtils.showToast(LoginActivity.this,"请输入用户名或密码!");
                    return;
                }
                OkGo.<String>post(Api.baseUrl + Api.loginUrl)
                        .params("userAccount",name)
                        .params("userPassword", pwd)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                MyLogUtils.e("登录",response.body());
                                LoginBean loginBean = JsonUtil.parseJson(response.body(),LoginBean.class);
                                if(loginBean.getCode() == 200){
                                    String administrator = loginBean.getData().getUser().getAdministrator();
                                    if(administrator.equals("1")){//管理员
                                        Intent intent = new Intent(LoginActivity.this,ManagerActivity.class);SPUtils.put(LoginActivity.this,"userId",loginBean.getData().getUser().getUserId()+"");

                                        startActivity(intent);
                                        finish();
                                    }else {
                                        SPUtils.put(LoginActivity.this,"userId",loginBean.getData().getUser().getUserId()+"");
                                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                }else{
                                    ToastUtils.showToast(LoginActivity.this,"登录失败");
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
