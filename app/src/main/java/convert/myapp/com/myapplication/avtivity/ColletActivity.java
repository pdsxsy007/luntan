package convert.myapp.com.myapplication.avtivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import convert.myapp.com.myapplication.R;
import convert.myapp.com.myapplication.adapter.ColletAdapter;
import convert.myapp.com.myapplication.adapter.NickNameAdapter;
import convert.myapp.com.myapplication.base.BaseActivity;
import convert.myapp.com.myapplication.bean.CollectBean;
import convert.myapp.com.myapplication.bean.NickNameBean;
import convert.myapp.com.myapplication.db.MyDBHelper;
import convert.myapp.com.myapplication.http.Api;
import convert.myapp.com.myapplication.utils.JsonUtil;
import convert.myapp.com.myapplication.utils.MyLogUtils;
import convert.myapp.com.myapplication.utils.SPUtils;

public class ColletActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ColletAdapter adapter;
    MyDBHelper myDBHelper ;
    private LinearLayoutManager mLinearLayoutManager;
    List<CollectBean> collectBeans = new ArrayList<>();
    CollectBean collectBean = new CollectBean();
    @Override
    protected int getResourceId() {
        return R.layout.activity_choose;
    }

    @Override
    protected void initView() {
        super.initView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL,false);
        recyclerView.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    protected void initData() {
        super.initData();
        getData();
    }

    private void getData() {
        collectBeans.clear();
        myDBHelper = new MyDBHelper(ColletActivity.this,"collect.db",null,1);

        SQLiteDatabase db = myDBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from collect where userId = ?", new String[]{(String)SPUtils.get(ColletActivity.this,"userId","")});
        while (cursor.moveToNext()) {

            collectBean = new CollectBean();
            collectBean.setId(cursor.getInt(0));
            collectBean.setUserId(cursor.getString(1));
            collectBean.setArticleId(cursor.getString(2));
            collectBean.setArticleUserId(cursor.getString(3));
            collectBean.setArticleTitle(cursor.getString(4));
            collectBean.setArticleContent(cursor.getString(5));
            collectBean.setCollectNumber(cursor.getInt(6));
            collectBean.setCreatTime(cursor.getString(7));
            collectBean.setRepliesNumber(cursor.getInt(8));
            collectBean.setNicknameId(cursor.getInt(9));
            collectBean.setNicknameName(cursor.getString(10));
            collectBean.setNicknameUrl(cursor.getString(11));
            collectBeans.add(collectBean);
        }
        cursor.close();
          if(collectBeans.size() >0){
              adapter = new ColletAdapter(this,R.layout.list_item_tiezi,collectBeans);
              recyclerView.setAdapter(adapter);
          }


    }


}
