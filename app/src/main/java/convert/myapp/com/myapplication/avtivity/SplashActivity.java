package convert.myapp.com.myapplication.avtivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.ImageView;


import java.lang.ref.WeakReference;

import butterknife.BindView;
import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.utils.SPUtils;


/**
 * Created by Administrator on 2018/12/11 0011.
 */

public class SplashActivity extends AppCompatActivity {


    private Handler handler = new MyHandler(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();


    }

    protected void initView() {
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    /**
     * 暂时闪屏界面不需要执行什么操作，所以先发个2秒的延时空消息,其实可以把软件所需的申请权限放和检查版本更新放这
     */
    private class MyHandler extends Handler {

        private WeakReference<Context> reference;

        public MyHandler(Context context) {
            reference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            if (TextUtils.isEmpty((String) SPUtils.get(SplashActivity.this, "userId", ""))) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            } else {

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();


            }

        }
    }







    }



