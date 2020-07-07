package convert.myapp.com.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.bean.ArticleBean;
import convert.myapp.com.myapplication.bean.CommentBean;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.view.XCRoundImageView;


public class CommentAdapter extends CommonAdapter<CommentBean.Data> {
    Context mContext;
    public CommentAdapter(Context context, int layoutId, List<CommentBean.Data> datas) {
        super(context, layoutId, datas);
        mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, final CommentBean.Data s, final int position) {

        holder.setText(R.id.tv_name,s.getNicknameName());
        holder.setText(R.id.tv_time,s.getCreatTime());
        holder.setText(R.id.tv_content,s.getCommentContent());
        holder.setText(R.id.tv_loceng,position+1+"楼");
        String replyNicknameName = s.getReplyNicknameName();
        if(replyNicknameName != null){
            holder.setText(R.id.tv_reply_name," 回复 "+replyNicknameName);
        }

        XCRoundImageView iv = holder.getConvertView().findViewById(R.id.xcimage);
        Glide.with(mContext).load(Api.imgUrl+s.getNicknameUrl()).asBitmap().into(iv);
        holder.setOnClickListener(R.id.btn_replay, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("position",position);
                intent.putExtra("nicknameId",s.getNicknameId()+"");
                intent.putExtra("userId",s.getUserId()+"");
                intent.putExtra("replyNicknameName",s.getReplyNicknameName());
                intent.setAction("refresh");
                mContext.sendBroadcast(intent);
            }
        });

        ImageView iv_time = holder.getConvertView().findViewById(R.id.iv_time);
        int mode = mContext.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if(mode == Configuration.UI_MODE_NIGHT_YES) {

            Glide.with(mContext).load(R.mipmap.timeday).asBitmap().into(iv_time);
        } else if(mode == Configuration.UI_MODE_NIGHT_NO) {

            Glide.with(mContext).load(R.mipmap.timenight).asBitmap().into(iv_time);
        }

    }

}
