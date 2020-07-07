package convert.myapp.com.myapplication.adapter;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.bean.NickNameBean;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.view.XCRoundImageView;


public class ManagerAdapter extends CommonAdapter<NickNameBean.Data> {
    Context mContext;
    public ManagerAdapter(Context context, int layoutId, List<NickNameBean.Data> datas) {
        super(context, layoutId, datas);
        mContext = context;
    }

    @Override
    protected void convert(ViewHolder holder, final NickNameBean.Data s, int position) {

        holder.setText(R.id.tv_name,s.getNicknameName());

        XCRoundImageView iv = holder.getConvertView().findViewById(R.id.xcimage);
        Glide.with(mContext).load(Api.imgUrl+s.getNicknameUrl()).asBitmap().into(iv);
    }

}
