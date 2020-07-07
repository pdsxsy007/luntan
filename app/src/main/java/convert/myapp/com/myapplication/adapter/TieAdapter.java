package convert.myapp.com.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.avtivity.ArticleDetailsActivity;
import convert.myapp.com.myapplication.avtivity.MainActivity;
import convert.myapp.com.myapplication.bean.ArticleBean;
import convert.myapp.com.myapplication.bean.CollectBean;
import convert.myapp.com.myapplication.bean.NickNameBean;
import convert.myapp.com.myapplication.bean.RegisterBean;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.utils.JsonUtil;
import convert.myapp.com.myapplication.utils.MyLogUtils;
import convert.myapp.com.myapplication.utils.ToastUtils;
import convert.myapp.com.myapplication.view.XCRoundImageView;


public class TieAdapter extends CommonAdapter<ArticleBean.Data> {
    Context mContext;
    public TieAdapter(Context context, int layoutId, List<ArticleBean.Data> datas) {
        super(context, layoutId, datas);
        mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, final ArticleBean.Data s, int position) {

        holder.setText(R.id.tv_name,s.getNicknameName());
        holder.setText(R.id.tv_time,s.getCreatTime());
        holder.setText(R.id.tv_title,s.getArticleTitle());
        holder.setText(R.id.tv_content,s.getArticleContent());
        holder.setText(R.id.tv_collect_count,s.getCollectNumber()+ "");
        holder.setText(R.id.tv_comment_count,s.getRepliesNumber()+"");

        XCRoundImageView iv = holder.getConvertView().findViewById(R.id.xcimage);
        ImageView iv_comment = holder.getConvertView().findViewById(R.id.iv_comment);
        ImageView iv_collect = holder.getConvertView().findViewById(R.id.iv_collect);
        Glide.with(mContext).load(Api.imgUrl+s.getNicknameUrl()).asBitmap().into(iv);
        int mode = mContext.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if(mode == Configuration.UI_MODE_NIGHT_YES) {
            Glide.with(mContext).load(R.mipmap.comment_day).asBitmap().into(iv_comment);

        } else if(mode == Configuration.UI_MODE_NIGHT_NO) {
            Glide.with(mContext).load(R.mipmap.comment).asBitmap().into(iv_comment);

        }
        Glide.with(mContext).load(R.mipmap.collect).asBitmap().into(iv_collect);

        iv_collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkGo.<String>get(Api.baseUrl+Api.articleCollectionUrl)
                        .params("id",s.getId())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {

                                Log.e("获取到的string",response.body());
                                CollectBean registerBean = JsonUtil.parseJson(response.body(),CollectBean.class);
                                if(registerBean.getCode()== 200){
                                    ToastUtils.showToast(mContext,"收藏成功");
                                    Intent intent = new Intent(mContext,MainActivity.class);
                                    mContext.startActivity(intent);

                                }else{
                                    ToastUtils.showToast(mContext,"收藏失败");
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
