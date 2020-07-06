package convert.myapp.com.myapplication.avtivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import butterknife.BindView;
import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.base.BaseActivity;
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
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
