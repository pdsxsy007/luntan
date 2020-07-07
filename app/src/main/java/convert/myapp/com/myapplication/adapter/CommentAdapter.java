package convert.myapp.com.myapplication.adapter;

import android.content.Context;
import android.content.res.Configuration;
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
    protected void convert(ViewHolder holder, final CommentBean.Data s, int position) {

        holder.setText(R.id.tv_name,s.getNicknameName());
        holder.setText(R.id.tv_time,s.getCreatTime());
        holder.setText(R.id.tv_content,s.getCommentContent());
        holder.setText(R.id.tv_loceng,position+1+"楼");


        XCRoundImageView iv = holder.getConvertView().findViewById(R.id.xcimage);
        Glide.with(mContext).load(Api.imgUrl+s.getNicknameUrl()).asBitmap().into(iv);

    }

}
