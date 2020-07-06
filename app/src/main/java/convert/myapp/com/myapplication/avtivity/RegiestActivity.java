package convert.myapp.com.myapplication.avtivity;

import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.base.BaseActivity;

public class RegiestActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Override
    protected int getResourceId() {
        return R.layout.activity_regiest;
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("注册");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
    }
}
