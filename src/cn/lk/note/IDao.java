package cn.lk.note;

import java.util.List;

public interface IDao<T> {
	
	long insert(T t);
	
	int delete(long id);
	
	int update(T t);
	
	List<T> query(String whereClause, String[] whereArgs, String orderBy);
}
