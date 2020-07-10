package convert.myapp.com.myapplication.avtivity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.List;

import butterknife.BindView;
import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.adapter.TieAdapter;
import convert.myapp.com.myapplication.base.BaseActivity;
import convert.myapp.com.myapplication.bean.AnswerBean;
import convert.myapp.com.myapplication.bean.ArticleBean;
import convert.myapp.com.myapplication.bean.CommentBean;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.utils.DensityUtil;
import convert.myapp.com.myapplication.utils.JsonUtil;
import convert.myapp.com.myapplication.utils.MyLogUtils;
import convert.myapp.com.myapplication.utils.SPUtils;
import convert.myapp.com.myapplication.utils.ScreenSizeUtils;
import convert.myapp.com.myapplication.utils.ToastUtils;
import convert.myapp.com.myapplication.view.MyDialog;

public class AnswerActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_name2)
    TextView tv_name2;
    @BindView(R.id.tv_name3)
    TextView tv_name3;
    @BindView(R.id.tv_choose01)
    TextView tv_choose01;
    @BindView(R.id.tv_choose02)
    TextView tv_choose02;
    @BindView(R.id.tv_choose03)
    TextView tv_choose03;
    @BindView(R.id.tv_choose04)
    TextView tv_choose04;
    @BindView(R.id.tv_choose05)
    TextView tv_choose05;
    @BindView(R.id.tv_choose06)
    TextView tv_choose06;
    @BindView(R.id.tv_choose07)
    TextView tv_choose07;
    @BindView(R.id.tv_choose08)
    TextView tv_choose08;
    @BindView(R.id.et01)
    EditText et01;
    @BindView(R.id.et02)
    EditText et02;
    @BindView(R.id.et03)
    EditText et03;
    @BindView(R.id.btn_login)
    Button btn_login;

    private int flag = 0;
    private int flag1 = 0;
    private int flag2 = 0;
    @Override
    protected int getResourceId() {
        return R.layout.activity_answer;
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
        getData();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String trim = et01.getText().toString().trim();
                String trim2 = et02.getText().toString().trim();
                String trim3 = et03.getText().toString().trim();

                if(trim.equals("") || trim2.equals("") ||trim3.equals("")){
                    ToastUtils.showToast(AnswerActivity.this,"请输入答案!");
                    return;
                }

                if(trim.equals(answer1)){
                    flag = flag+1;
                }
                if(trim2.equals(answer2)){
                    flag1 = flag1+1;
                }
                if(trim3.equals(answer3)){
                    flag2 = flag2+1;
                }

                int i = flag + flag1 + flag2;

                SPUtils.put(AnswerActivity.this,"flagZHi",flag+flag1+flag2+"");
                finish();
            }
        });
    }

    private String answer1;
    private String answer2;
    private String answer3;
    private void getData() {

        OkGo.<String>get(Api.baseUrl+Api.questionBankListUrl)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        MyLogUtils.e("问题列表",response.body());

                        AnswerBean articleBean = JsonUtil.parseJson(response.body(),AnswerBean.class);
                        int code = articleBean.getCode();
                        if(code == 200){

                            List<AnswerBean.Data> data = articleBean.getData();
                            for (int i = 0; i < data.size(); i++) {

                                int type = data.get(i).getType();
                                switch (type){
                                    case 1:
                                        tv_name.setText(data.get(i).getQuestionName());
                                        answer1 = data.get(i).getAnswerList().get(0).getOptiona();
                                        break;

                                    case 2:
                                        tv_name2.setText(data.get(i).getQuestionName());
                                        List<AnswerBean.AnswerList> answerList = data.get(i).getAnswerList();
                                        for (int j = 0; j < answerList.size(); j++) {
                                            if(j == 0){
                                               tv_choose01.setText(answerList.get(0).getOptiona()+" "+answerList.get(0).getOptionDesc());
                                            }else if(j == 1){
                                                tv_choose02.setText(answerList.get(1).getOptiona()+" "+answerList.get(1).getOptionDesc());
                                            }else if(j == 2){
                                                tv_choose03.setText(answerList.get(2).getOptiona()+" "+answerList.get(2).getOptionDesc());
                                            }else {
                                                tv_choose04.setText(answerList.get(3).getOptiona()+" "+answerList.get(3).getOptionDesc());
                                            }

                                            String answer = answerList.get(j).getAnswer();
                                            if(answer.equals("1")){
                                                answer2 = answerList.get(j).getOptiona();
                                            }
                                        }
                                        break;

                                    case 3:
                                        tv_name3.setText(data.get(i).getQuestionName());
                                        List<AnswerBean.AnswerList> answerList2 = data.get(i).getAnswerList();
                                        for (int j = 0; j < answerList2.size(); j++) {
                                            if(j == 0){
                                                tv_choose05.setText(answerList2.get(0).getOptiona()+" "+answerList2.get(0).getOptionDesc());
                                            }else if(j == 1){
                                                tv_choose06.setText(answerList2.get(1).getOptiona()+" "+answerList2.get(1).getOptionDesc());
                                            }else if(j == 2){
                                                tv_choose07.setText(answerList2.get(2).getOptiona()+" "+answerList2.get(2).getOptionDesc());
                                            }else {
                                                tv_choose08.setText(answerList2.get(3).getOptiona()+" "+answerList2.get(3).getOptionDesc());
                                            }

                                            String answer = answerList2.get(j).getAnswer();
                                            if(answer.equals("1")){
                                                answer3 = answerList2.get(j).getOptiona();
                                            }

                                        }
                                        break;
                                }
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



}