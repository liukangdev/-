package cn.lk.note;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

	public DBOpenHelper(Context context) {
		super(context, "dict.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//创建数据表
		String sql = "create table words("+
				"_id integer primary key autoincrement, "+
				"en varchar(16) not null unique, "+
				"zh varchar(50) not null);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
