package convert.myapp.com.myapplication.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzy.okgo.OkGo;

import butterknife.ButterKnife;


public abstract class BaseFragment extends Fragment {
    public int netStateType = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), getLayoutResID(), null);
        ButterKnife.bind(this, view);
        initView(view);
        initData();
        initListener();
        /*if (!netState.isConnect(getActivity()) ){
            ToastUtils.showToast(getActivity(),"网络异常!");
            netStateType = 1;
        }*/
        return view;
    }

    /**
     * 获取Activity显示的布局：
     *
     * @return：布局id
     */
    public abstract int getLayoutResID();

    /**
     * 初始化View
     */
    public void initView(View view){

    }

    /**
     * 初始化监听：点击监听、设置适配器、设置条目点击监听
     */
    public void initListener() {

    }

    /**
     * 初始化数据
     */
    public void initData() {

    }

    @Override
    public void onPause() {
        super.onPause();
        OkGo.getInstance().cancelTag(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //根据 Tag 取消请求
        OkGo.getInstance().cancelTag(this);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

    }
}
