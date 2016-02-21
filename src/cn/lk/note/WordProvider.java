package cn.lk.note;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class WordProvider extends ContentProvider {
	//��֤Uri�Ƿ�ƥ��Ĺ���
	UriMatcher matcher;

	private static final int MATCH_URI = 1;
	@Override
	public boolean onCreate() {
		//����UriMatcher�����췽��������ʾ��ƥ����֤����ʱ�����صĽ��
		matcher = new UriMatcher(UriMatcher.NO_MATCH);
		//�����֤����
		matcher.addURI("www.lk.cn", null, MATCH_URI);
		return false;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		//��֤Uri
		if(matcher.match(uri) == MATCH_URI) {
			DBOpenHelper helper = new DBOpenHelper(getContext());
			SQLiteDatabase db = helper.getWritableDatabase();
			long id = db.insert("words", null, values);
			return ContentUris.withAppendedId(uri, id);
		} else {
			throw new IllegalArgumentException("�Ƿ���Uri --> "+uri);
		}
	}



	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}



}
