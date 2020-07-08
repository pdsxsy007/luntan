package convert.myapp.com.myapplication.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.avtivity.ArticleDetailsActivity;
import convert.myapp.com.myapplication.avtivity.MainActivity;
import convert.myapp.com.myapplication.bean.ArticleBean;
import convert.myapp.com.myapplication.bean.CollectBean;
import convert.myapp.com.myapplication.bean.NickNameBean;
import convert.myapp.com.myapplication.bean.RegisterBean;
import convert.myapp.com.myapplication.db.MyDBHelper;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.utils.JsonUtil;
import convert.myapp.com.myapplication.utils.MyLogUtils;
import convert.myapp.com.myapplication.utils.SPUtils;
import convert.myapp.com.myapplication.utils.ToastUtils;
import convert.myapp.com.myapplication.view.XCRoundImageView;


public class TieAdapter extends CommonAdapter<ArticleBean.Data> {
    Context mContext;
    MyDBHelper myDBHelper ;

    public TieAdapter(Context context, int layoutId, List<ArticleBean.Data> datas) {
        super(context, layoutId, datas);
        mContext = context;
        myDBHelper = new MyDBHelper(mContext,"collect.db",null,1);
    }

    @Override
    protected void convert(ViewHolder holder, final ArticleBean.Data s, int position) {

        final SQLiteDatabase db = myDBHelper.getWritableDatabase();
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
                ContentValues values = new ContentValues();
                values.put("userId",(String) SPUtils.get(mContext,"userId",""));
                values.put("articleUserId", s.getUserId());
                values.put("articleId",s.getId());
                values.put("articleTitle",s.getArticleTitle());
                values.put("articleContent",s.getArticleContent());
                values.put("collectNumber",s.getCollectNumber());
                values.put("creatTime",getTime());
                values.put("repliesNumber",s.getRepliesNumber());
                values.put("nicknameId",s.getNicknameId());
                values.put("nicknameName",s.getNicknameName());
                values.put("nicknameUrl",s.getNicknameUrl());

              db.insert("collect",null,values);
                ToastUtils.showToast(mContext,"收藏成功");

            }


        });


    }

    private String getTime(){

        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        Date now = new Date();
        String time = sdf.format(now);
        return  time;
    }

}
