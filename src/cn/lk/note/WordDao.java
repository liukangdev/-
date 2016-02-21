package cn.lk.note;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WordDao implements IDao<Word> {
	private Context context;
	
	public WordDao(Context context) {
		if(context == null) {
			throw new IllegalArgumentException("参数Context不允许为null！！");
		}
		this.context = context;
	}

	@Override
	public long insert(Word word) {
		SQLiteOpenHelper openHelper = new DBOpenHelper(context);
		SQLiteDatabase db = openHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("en", word.getEn());
		values.put("zh", word.getZh());
		long id = db.insert("words", null, values);
		db.close();
		return id;
	}

	@Override
	public int delete(long id) {
		SQLiteOpenHelper openHelper = new DBOpenHelper(context);
		SQLiteDatabase db = openHelper.getWritableDatabase();
		int affectedRows = db.delete("words", "_id=?", new String[]{id+""});
		db.close();
		return affectedRows;
	}

	@Override
	public int update(Word word) {
		SQLiteOpenHelper openHelper = new DBOpenHelper(context);
		SQLiteDatabase db = openHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("en", word.getEn());
		values.put("zh", word.getZh());
		int affectedRows = db.update("words", values, "_id=?", new String[]{word.getId()+""});
		db.close();
		return affectedRows;
	}

	@Override
	public List<Word> query(String whereClause, String[] whereArgs,
			String orderBy) {
		SQLiteOpenHelper openHelper = new DBOpenHelper(context);
		SQLiteDatabase db = openHelper.getWritableDatabase();
		List<Word> words = new ArrayList<Word>();
		Cursor c = db.query("words", null, whereClause, whereArgs, null, null, orderBy);
		if(c.moveToFirst()) {
			for(;!c.isAfterLast();c.moveToNext()) {
				Word word = new Word();
				word.setId(c.getLong(c.getColumnIndex("_id")));
				word.setEn(c.getString(c.getColumnIndex("en")));
				word.setZh(c.getString(c.getColumnIndex("zh")));
				words.add(word);
			}
		}
		db.close();
		return words;
	}

}
