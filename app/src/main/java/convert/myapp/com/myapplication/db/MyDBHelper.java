package convert.myapp.com.myapplication.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创建数据库
 * @author Administrator
 *
 */

public class MyDBHelper extends SQLiteOpenHelper {

    String str="create table collect(id integer primary key autoincrement," +
            "userId varchar(100)," +//当前登陆账户的id
            "articleId varchar(100)," +//帖子id
            "articleUserId varchar(100),"+//帖子发布的用户id
            "articleTitle varchar(100),"+
            "articleContent varchar(100)," +
            "collectNumber integer," +
            "creatTime varchar(100)," +
            "repliesNumber integer," +
            "nicknameId integer," +
            "nicknameName varchar(100)," +
            "nicknameUrl varchar(100))";




    public MyDBHelper(Context context, String name, CursorFactory factory,
                      int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(str);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}

