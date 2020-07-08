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
            "userid integer,articleid integer," +
            "pid varchar(50),"+
            "articleTitle varchar(50),"+
            "articleContent varchar(50)," +
            "collectNumber integer,creatTime varchar(50)," +
            "repliesNumber integer," +
            "nicknameId integer,nicknameName varchar(50)," +
            "nicknameUrl varchar(50))";




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

