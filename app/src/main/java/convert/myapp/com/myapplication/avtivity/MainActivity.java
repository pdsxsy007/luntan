package convert.myapp.com.myapplication.avtivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.adapter.NickNameAdapter;
import convert.myapp.com.myapplication.adapter.TieAdapter;
import convert.myapp.com.myapplication.base.BaseActivity;
import convert.myapp.com.myapplication.bean.ArticleBean;
import convert.myapp.com.myapplication.bean.NickNameBean;
import convert.myapp.com.myapplication.fragment.FourFragment;
import convert.myapp.com.myapplication.fragment.OneFragment;
import convert.myapp.com.myapplication.fragment.ThreeFragment;
import convert.myapp.com.myapplication.fragment.TwoFragment;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.utils.ActivityUtils;
import convert.myapp.com.myapplication.utils.JsonUtil;
import convert.myapp.com.myapplication.utils.MyLogUtils;
import convert.myapp.com.myapplication.utils.SPUtils;
import convert.myapp.com.myapplication.utils.SettingUtils;
import convert.myapp.com.myapplication.utils.ToastUtils;
import okhttp3.Headers;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class MainActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private NickNameAdapter adapter;
    private TieAdapter tieAdapter;
    private LinearLayoutManager mLinearLayoutManager;

    private SettingUtils settingUtils;

    private int num = 1;

    @BindView(R.id.tl_tabs)
    TabLayout tabLayout;

    @BindView(R.id.vp_content)
    ViewPager viewPager;

    List<Fragment> fragments = new ArrayList<>();
    List<String> titles = new ArrayList<>();
    @Override
    protected void initView() {
        super.initView();
        settingUtils = SettingUtils.getInstance();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(mLinearLayoutManager);


        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tabLayout, 20, 20);
            }
        });

        fragments.add(new OneFragment());
        fragments.add(new TwoFragment());
        fragments.add(new ThreeFragment());
        fragments.add(new FourFragment());
        titles.add("前进营地");
        titles.add("据点宿舍");
        titles.add("心智云图");
        titles.add("版务");

        viewPager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_main;
    }


    private class MyViewPagerAdapter extends FragmentStatePagerAdapter{

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }


        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {

            return titles.get(position);
        }


    }
    @Override
    protected void initListener() {
        super.initListener();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerlayout.openDrawer(GravityCompat.START);

            }
        });
        drawerlayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                initLeftData();
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });



    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if(mode == Configuration.UI_MODE_NIGHT_YES) {
            menu.findItem(R.id.setting).setTitle("日间模式");
        } else if(mode == Configuration.UI_MODE_NIGHT_NO) {
            menu.findItem(R.id.setting).setTitle("夜间模式");
        }

        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.fatie://发帖
                String forbiddenWordsId = (String) SPUtils.get(MainActivity.this, "ForbiddenWordsId", "");
                if(forbiddenWordsId.equals("0")){
                    drawerlayout.openDrawer(GravityCompat.START);
                }else {
                    ToastUtils.showToast(MainActivity.this,"您的账号已禁言!");
                }


                break;
            case R.id.setting://日夜切换

                int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                if(mode == Configuration.UI_MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    settingUtils.saveNightMode(false);
                } else if(mode == Configuration.UI_MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    settingUtils.saveNightMode(true);
                }

                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.logout:
                Intent intent2 = new Intent(MainActivity.this,LoginActivity.class);
                SPUtils.put(MainActivity.this,"userId","");
                SPUtils.put(MainActivity.this,"flag","");
                SPUtils.put(MainActivity.this,"nickNameIdByComment","");
                SPUtils.put(MainActivity.this,"nickNameUrlByComment","");

                startActivity(intent2);
                finish();

                break;
            case R.id.setting2:
                Intent intent3 = new Intent(MainActivity.this,ColletActivity.class);
                startActivity(intent3);
                break;

            case R.id.setting3:
                Intent intent4 = new Intent(MainActivity.this,MyDeleteActivity.class);
                startActivity(intent4);
                break;

        }
        return true;

    }

    private void initLeftData() {


        OkGo.<String>get(Api.baseUrl+Api.nickNameListUrl)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        MyLogUtils.e("左侧角色列表",response.body());

                        NickNameBean nickNameBean = JsonUtil.parseJson(response.body(),NickNameBean.class);
                        int code = nickNameBean.getCode();
                        if(code == 200){
                            final List<NickNameBean.Data> data = nickNameBean.getData();
                            adapter = new NickNameAdapter(MainActivity.this,R.layout.list_item_nickname2,data);
                            recyclerView.setAdapter(adapter);

                        }
                    }
                });
    }

    /**
     * 点击2次退出
     */
    long waitTime = 2000;
    long touchTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {

            long currentTime = System.currentTimeMillis();
            if ((currentTime - touchTime) >= waitTime) {
                ToastUtils.showToast(MainActivity.this,"再按一次退出");

                touchTime = currentTime;
            } else {
                ActivityUtils.getActivityManager().finishAllActivity();
            }

            return true;

        }
        return super.onKeyDown(keyCode, event);
    }



    public static void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*if(drawerlayout.is){

        }*/
        drawerlayout.closeDrawer(GravityCompat.START);
    }
}
