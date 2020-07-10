package convert.myapp.com.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.avtivity.SendArticleActivity;
import convert.myapp.com.myapplication.bean.NickNameBean;
import convert.myapp.com.myapplication.view.ExpandView;


public class NickNameAdapter2 extends CommonAdapter<NickNameBean.Data> {
    Context mContext;
    private LinearLayoutManager mLinearLayoutManager;
    private List<Integer> integerList;

    public NickNameAdapter2(Context context, int layoutId, List<NickNameBean.Data> datas) {
        super(context, layoutId, datas);
        mContext = context;
        integerList = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            integerList.add(0);
        }
    }

    @Override
    protected void convert(final ViewHolder holder, final NickNameBean.Data s, final int position) {
        mLinearLayoutManager = new LinearLayoutManager(mContext, LinearLayout.VERTICAL,false);
       holder.setText(R.id.tv_name,s.getNicknameName());
        final ExpandView mExpandView = holder.getConvertView().findViewById(R.id.xpandView);
        View view = mExpandView.setContentView();
        RecyclerView rvMsgList = view.findViewById(R.id.rv_msg_list);
        rvMsgList.setLayoutManager(mLinearLayoutManager);
        List<NickNameBean.NicknameList> nicknameList = s.getNicknameList();
        NickNameTwoAdapter adapter = new NickNameTwoAdapter(mContext,R.layout.list_item_nickname,nicknameList);
        rvMsgList.setAdapter(adapter);
        holder.setOnClickListener(R.id.rl_item, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("mExpandView",mExpandView.isExpand()+"");
                int int_pos = integerList.get(position);
                if(int_pos == 0){

                    integerList.remove(position);
                    int_pos = 1;
                    integerList.add(position,int_pos);
                    mExpandView.expand();
                    mExpandView.setVisibility(View.VISIBLE);
                    holder.setImageResource(R.id.iv_down,R.mipmap.sanjiaox);

                }else {
                    integerList.remove(position);
                    int_pos = 0;
                    integerList.add(position,int_pos);
                    mExpandView.setVisibility(View.GONE);
                    mExpandView.collapse();
                    holder.setImageResource(R.id.iv_down,R.mipmap.sanjiaos);
                }


            }
        });

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                                  /*  Intent intent = new Intent();
                                    intent.putExtra("nickNameId",s.getNicknameList().get(position).getId()+"");
                                    msetResult(99,intent);
                                    finish();*/

                                    Intent intent = new Intent();
                                    intent.putExtra("nickNameId",s.getNicknameList().get(position).getId()+"");
                                    intent.putExtra("nickNameUrl",s.getNicknameList().get(position).getNicknameUrl()+"");
                                    intent.setAction("refreshClick");
                                    mContext.sendBroadcast(intent);


                                }

                                @Override
                                public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                                    return false;
                                }
                            });

    }

}
