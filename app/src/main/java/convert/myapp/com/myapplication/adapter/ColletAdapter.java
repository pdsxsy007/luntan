package convert.myapp.com.myapplication.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.bean.CollectBean;
import convert.myapp.com.myapplication.bean.NickNameBean;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.view.XCRoundImageView;


public class ColletAdapter extends CommonAdapter<CollectBean> {
    Context mContext;
    public ColletAdapter(Context context, int layoutId, List<CollectBean> datas) {
        super(context, layoutId, datas);
        mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, final CollectBean s, int position) {
        ImageView iv_comment = holder.getConvertView().findViewById(R.id.iv_comment);
        ImageView iv_collect = holder.getConvertView().findViewById(R.id.iv_collect);
        holder.setText(R.id.tv_name,s.getNicknameName());
        holder.setText(R.id.tv_name,s.getNicknameName());
        holder.setText(R.id.tv_time,s.getCreatTime());
        holder.setText(R.id.tv_title,s.getArticleTitle());
        holder.setText(R.id.tv_content,s.getArticleContent());
        holder.setText(R.id.tv_collect_count,s.getCollectNumber()+ "");
        holder.setText(R.id.tv_comment_count,s.getRepliesNumber()+"");
        ImageView iv = holder.getConvertView().findViewById(R.id.xcimage);
        Glide.with(mContext).load(Api.imgUrl+s.getNicknameUrl()).asBitmap().into(iv); int mode = mContext.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if(mode == Configuration.UI_MODE_NIGHT_YES) {
            Glide.with(mContext).load(R.mipmap.comment_day).asBitmap().into(iv_comment);

        } else if(mode == Configuration.UI_MODE_NIGHT_NO) {
            Glide.with(mContext).load(R.mipmap.comment).asBitmap().into(iv_comment);

        }
        Glide.with(mContext).load(R.mipmap.collect).asBitmap().into(iv_collect);

    }

}
