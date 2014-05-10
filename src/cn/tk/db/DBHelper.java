package cn.tk.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
	private static final String dbName = "moviehistory.db";
	private static final int version = 2;
	public DBHelper(Context context) {
		super(context, dbName, null, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table mytable(id integer primary key autoincrement,name varchar(64),rating varchar(64),year varchar(64),image varchar(128))");
		db.execSQL("create table person(id integer primary key autoincrement,name varchar(64),name_en varchar(64),sex varchar(8),image varchar(128))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

}
