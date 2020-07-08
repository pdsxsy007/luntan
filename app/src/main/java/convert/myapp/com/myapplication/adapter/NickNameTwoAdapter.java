package convert.myapp.com.myapplication.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.bean.NickNameBean;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.view.XCRoundImageView;


public class NickNameTwoAdapter extends CommonAdapter<NickNameBean.NicknameList> {
    Context mContext;

    public NickNameTwoAdapter(Context context, int layoutId, List<NickNameBean.NicknameList> datas) {
        super(context, layoutId, datas);
        this.mContext = context;
    }



    @Override
    protected void convert(ViewHolder holder, NickNameBean.NicknameList s, int position) {
        holder.setText(R.id.tv_name,s.getNicknameName());

        ImageView iv = holder.getConvertView().findViewById(R.id.xcimage);
        Glide.with(mContext).load(Api.imgUrl+s.getNicknameUrl()).asBitmap().into(iv);
    }
}
