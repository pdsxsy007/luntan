package convert.myapp.com.myapplication.adapter;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.avtivity.MainActivity;
import convert.myapp.com.myapplication.avtivity.ManagerActivity;
import convert.myapp.com.myapplication.avtivity.SplashActivity;
import convert.myapp.com.myapplication.bean.AllUserBean;
import convert.myapp.com.myapplication.bean.NickNameBean;
import convert.myapp.com.myapplication.bean.RegisterBean;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.utils.JsonUtil;
import convert.myapp.com.myapplication.utils.MyLogUtils;
import convert.myapp.com.myapplication.utils.SPUtils;
import convert.myapp.com.myapplication.utils.ToastUtils;
import convert.myapp.com.myapplication.view.XCRoundImageView;


public class ManagerAdapter extends CommonAdapter<AllUserBean.Data> {
    Context mContext;
    public ManagerAdapter(Context context, int layoutId, List<AllUserBean.Data> datas) {
        super(context, layoutId, datas);
        mContext = context;
    }

    @Override
    protected void convert(final ViewHolder holder, final AllUserBean.Data s, int position) {

        holder.setText(R.id.tv_name,s.getUserAccount());

        Button btn1 = (Button)holder.getView(R.id.btn01);
        if(s.getForbiddenWords() == 0){
            holder.setText(R.id.btn01,"禁言");
            holder.setOnClickListener(R.id.btn01, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    OkGo.<String>post(Api.baseUrl+Api.updatauserUrl)
                            .params("userId",(String) SPUtils.get(mContext, "userId", ""))
                            .params("forbiddenWords","1")
                            .params("deleted","0")
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    MyLogUtils.e("修改结果",response.body());

                                    RegisterBean registerBean = JsonUtil.parseJson(response.body(),RegisterBean.class);
                                    if(registerBean.getCode() == 200){
                                        ToastUtils.showToast(mContext,"禁言成功");
                                        holder.setText(R.id.btn01,"解禁");

                                    }else{
                                        ToastUtils.showToast(mContext,"禁言失败");
                                    }
                                }
                            });
                }
            });
        }else {
            holder.setText(R.id.btn01,"解禁");
            if(btn1.getText().equals("解禁")){

                holder.setOnClickListener(R.id.btn01, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        OkGo.<String>post(Api.baseUrl+Api.updatauserUrl)
                                .params("userId",(String) SPUtils.get(mContext, "userId", ""))
                                .params("forbiddenWords","0")
                                .params("deleted","0")
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {
                                        MyLogUtils.e("修改结果",response.body());
                                        RegisterBean registerBean = JsonUtil.parseJson(response.body(),RegisterBean.class);
                                        if(registerBean.getCode() == 200){
                                            ToastUtils.showToast(mContext,"解禁成功");
                                            holder.setText(R.id.btn01,"禁言");
                                        }else{
                                            ToastUtils.showToast(mContext,"解禁失败");
                                        }
                                    }
                                });
                    }
                });
            }
        }

        if(s.getDeleted() == 0){
            holder.setText(R.id.btn02,"封禁");
            holder.setOnClickListener(R.id.btn02, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    OkGo.<String>post(Api.baseUrl+Api.updatauserUrl)
                            .params("userId",(String) SPUtils.get(mContext, "userId", ""))
                            .params("forbiddenWords","0")
                            .params("deleted","1")
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    MyLogUtils.e("修改结果",response.body());
                                    RegisterBean registerBean = JsonUtil.parseJson(response.body(),RegisterBean.class);
                                    if(registerBean.getCode() == 200){
                                        ToastUtils.showToast(mContext,"封禁成功");
                                        holder.setText(R.id.btn02,"解封");
                                    }else{
                                        ToastUtils.showToast(mContext,"封禁失败");
                                    }
                                }
                            });
                }
            });

        }else{
            holder.setText(R.id.btn02,"解封");
            holder.setOnClickListener(R.id.btn02, new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    OkGo.<String>post(Api.baseUrl+Api.updatauserUrl)
                            .params("userId",(String) SPUtils.get(mContext, "userId", ""))
                            .params("forbiddenWords","0")
                            .params("deleted","0")
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    MyLogUtils.e("修改结果",response.body());
                                    RegisterBean registerBean = JsonUtil.parseJson(response.body(),RegisterBean.class);
                                    if(registerBean.getCode() == 200){
                                        ToastUtils.showToast(mContext,"解封成功");
                                        holder.setText(R.id.btn02,"封禁");
                                    }else{
                                        ToastUtils.showToast(mContext,"解封失败");
                                    }
                                }
                            });

                }
            });
        }


    }

}
