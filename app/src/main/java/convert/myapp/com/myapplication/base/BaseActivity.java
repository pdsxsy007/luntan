package convert.myapp.com.myapplication.base;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.lzy.okgo.OkGo;

import butterknife.ButterKnife;
import convert.myapp.com.myapplication.utils.ActivityUtils;


/**
 * Activity的基类
 *
 */

public abstract class BaseActivity extends AppCompatActivity {
    private ProgressDialog dialog;
    public int netStateType = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(  getResourceId());
        ButterKnife.bind(this);


        initView();
        initHandler();
        initData();
        initListener();
        initSystemBar();
        ActivityUtils.getActivityManager().addActivity(this);


    }

    /**
     * 初始化通知栏
     */
    protected void initSystemBar() {
    }

    /**
     * 初始化监听
     */
    protected void initListener() {};

    /**
     * 设置布局文件
     *
     * @return
     */
    protected abstract int getResourceId();

    /**
     * 初始化控件
     */
    protected void initView() {}

    /**
     * 初始化数据
     */
    protected void initData() {}

    /**
     * 初始化handler
     */
    protected void initHandler() {}
    /**
     * 显示dialog
     */
    protected void showDialog(String s) {
        dialog = new ProgressDialog(this);
        dialog.setMessage(s);
        dialog.setCanceledOnTouchOutside(false);

        dialog.show();
    }



    /**
     * dialog停止
     */
    protected void stopDialog() {
        if (null != dialog ) {
            if (dialog.isShowing()){
                dialog.dismiss();
            }
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
       stopDialog();
        ActivityUtils.getActivityManager().finishActivity(this);
        OkGo.getInstance().cancelTag(this);

    }

    public void onBack(View view){
        finish();
    }

    @Override
    public void setRequestedOrientation(int requestedOrientation) {
        super.setRequestedOrientation(requestedOrientation);
        return ;
    }


    @Override
    protected void onResume() {
        super.onResume();


    }


    @Override
    protected void onPause() {
        super.onPause();

        OkGo.getInstance().cancelTag(this);
    }


}
