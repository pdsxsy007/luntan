package convert.myapp.com.myapplication.avtivity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import butterknife.BindView;
import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.base.BaseActivity;
import convert.myapp.com.myapplication.bean.BaseBean;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.utils.JsonUtil;
import convert.myapp.com.myapplication.utils.MyLogUtils;
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

    @BindView(R.id.tv_fenlei)
    TextView tv_fenlei;

    @BindView(R.id.sp)
    Spinner sp;

    private int position = 1;
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
        final String[] mItems = getResources().getStringArray(R.array.spinnerclass);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, mItems);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                position = i+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    protected void initData() {
        super.initData();
        final String nickNameId = getIntent().getStringExtra("nickNameId");
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
                        .params("nicknameId", nickNameId)
                        .params("articleType", position)
                        .params("userId", (String)SPUtils.get(SendArticleActivity.this,"userId",""))
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                MyLogUtils.e("发帖",response.body());
                                BaseBean baseBean = JsonUtil.parseJson(response.body(),BaseBean.class);
                                int code = baseBean.getCode();
                                if(code == 200){
                                    String msg = baseBean.getMsg();
                                    ToastUtils.showToast(SendArticleActivity.this,msg);
                                    stopDialog();

                                    Intent intent = new Intent();
                                    intent.setAction("refreshHomeData");
                                    sendBroadcast(intent);
                                    finish();
                                }

                            }
                            @Override
                            public void onError(Response<String> response) {
                                super.onError(response);

                            }

                            @Override
                            public void onStart(Request<String, ? extends Request> request) {
                                super.onStart(request);
                                showDialog("发布中");
                            }
                        });

            }
        });
    }
}
