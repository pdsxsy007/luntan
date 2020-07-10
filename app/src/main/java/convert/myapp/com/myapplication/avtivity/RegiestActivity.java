package convert.myapp.com.myapplication.avtivity;

import android.content.Intent;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import butterknife.BindView;
import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.base.BaseActivity;
import convert.myapp.com.myapplication.bean.RegisterBean;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.utils.JsonUtil;
import convert.myapp.com.myapplication.utils.MyLogUtils;
import convert.myapp.com.myapplication.utils.SPUtils;
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

    @BindView(R.id.tv_answer)
    TextView tv_answer;
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

                String flagZHi = (String) SPUtils.get(RegiestActivity.this, "flagZHi", "");
                if(flagZHi.equals("0")){
                    ToastUtils.showToast(RegiestActivity.this,"请回答问题");

                }else if(flagZHi.equals(1)){
                    ToastUtils.showToast(RegiestActivity.this,"请重新回答问题");

                }else {
                    if(flagZHi.equals("")){
                        ToastUtils.showToast(RegiestActivity.this,"请回答问题");
                    }else {
                        OkGo.<String>post(Api.baseUrl + Api.registerUrl)
                                .params("userAccount",name)
                                .params("userPassword", pwd)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        MyLogUtils.e("注册",response.body());
                                        RegisterBean registerBean = JsonUtil.parseJson(response.toString(),RegisterBean.class);
                                        if(registerBean.getCode() == 200){
                                            ToastUtils.showToast(RegiestActivity.this,"注册成功");
                                            finish();
                                            SPUtils.put(RegiestActivity.this,"flagZHi","");
                                        }else{
                                            ToastUtils.showToast(RegiestActivity.this,"注册失败");
                                            SPUtils.put(RegiestActivity.this,"flagZHi","");
                                        }

                                    }
                                    @Override
                                    public void onError(Response<String> response) {
                                        super.onError(response);
                                        SPUtils.put(RegiestActivity.this,"flagZHi","");
                                    }
                                });

                    }

                }


            }
        });

        tv_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =  new Intent(RegiestActivity.this,AnswerActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        String flagZHi = (String) SPUtils.get(RegiestActivity.this, "flagZHi", "");
        if(flagZHi.equals("0")){
            tv_answer.setText("请回答问题");
            SPUtils.put(RegiestActivity.this,"flagZHi","");
        }else if(flagZHi.equals("1")){
            tv_answer.setText("请重新回答问题");
            SPUtils.put(RegiestActivity.this,"flagZHi","");
        }else if(flagZHi.equals("2") || flagZHi.equals("3")){
            tv_answer.setText("回答问题通过");
        }

    }
}
