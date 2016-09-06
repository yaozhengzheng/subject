package com.yeebee.invest.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class PushDataDB {
	
	private static final String DATABASE_NAME = "PushData.db";
	
	public SQLiteDatabase sqlitedb;
	Context mContext;
	
	public PushDataDB(Context context) {
		super();
		this.mContext = context;
		sqlitedb = context.openOrCreateDatabase(DATABASE_NAME, 0, null);
		CreateTable_pushdb();
	}
	
	public void CreateTable_pushdb(){
		
		try{
			sqlitedb.execSQL("CREATE TABLE pushdatatable("
					+"_ID INTEGER PRIMARY KEY,"
					+"notifactionId INTEGER,"
					+"tvTime VARCHAR(30) NOT NULL,"
					+"tvTitle TEXT,"
					+"tvContent TEXT,"	
					+"tvUrl TEXT,"
					+"tvRemarks TEXT,"
					+"tvState TEXT"
					+");");
			Log.v("CreateTable", "创建表成功");
		}catch(Exception e){
			
			Log.v("CreateTable", "创建表失败,表已存在");
			
		}
	}
	
	public void Pushdata_save(int notifactionId,String tvTime ,String tvTitle,String tvContent,String tvUrl,String tvRemarks,String tvState){
		Log.v("PushDataDB", "插入数据");
		String sql = "";
		try{
//			sql = "insert into chat(chatUser,chatDate,chatContext,chatType) values(?,?,?,?)";
//			sqlitedb.execSQL(sql,new Object[]{chatUser,chatDate,chatContext,chatType});
//			sql = "insert into chat values(null,"+chatUser+","+chatDate+","+chatContext+",'"+chatType+"');";
			sql = "insert into pushdatatable(notifactionId,tvTime,tvTitle,tvContent,tvUrl,tvRemarks,tvState) values(?,?,?,?,?,?,?)";
			sqlitedb.execSQL(sql,new Object[]{notifactionId,tvTime,tvTitle,tvContent,tvUrl,tvRemarks,tvState});
		//	db.execSQL(sql);
			Log.v("Pushdata_save","insert table PushData ok");
		//	return true;
		}catch(Exception e){
			Log.v("Pushdata_save","insert Table PushData err="+sql);
		//	return false;
		}
	}
	
	public Cursor Pushdata_read(){
			
		String sql2 = "select _ID,tvTime,tvContent,tvState from pushdatatable where _ID=?or tvTime=?or tvContent=?or tvState=?";
	//	String sql2 = "select * from pushdatatable";
		//String sql2 = "select * from pushdatatable order by _ID desc limit 100 ";
		sqlitedb.execSQL(sql2);
		Cursor c = sqlitedb.query("pushdatatable", null, null, null, null, null, "_ID desc");

		return c;		
	}
	//读取url
	public String Pushdata_readurl(String id){
		String url = null;
		String sql2 = "select tvUrl from pushdatatable where _ID = ?";
		Cursor c = sqlitedb.rawQuery(sql2, new String[]{id});
		//String sql2 = "select * from pushdatatable order by _ID desc limit 100 ";
		sqlitedb.execSQL(sql2);
	//	Cursor c = sqlitedb.query("pushdatatable", null, null, null, null, null, "_ID asc");
		while(c.moveToNext()){
			url = c.getString(0);
		}
		return url;		
	}
	
	public int Pushdata_count(){
//		String sql2 = "select count(*) from pushdatatable where tvState = 'false'";
//		sqlitedb.execSQL(sql2);
		String sql = "select * from pushdatatable where tvState = ?";
		Cursor c = sqlitedb.rawQuery(sql, new String[]{"unread"});
//		Cursor c = sqlitedb.query("pushdatatable", null, null, null, null, null, "_ID asc");
		int count = c.getCount();
	//	String sql = "select _ID,tvTime,tvContent from pushdatatable where _ID=?or tvTime=?or tvContent=?";
		return count;
		
	}
	//更新一条状体通过notifactionId
	public void Pushdata_updatebynotifactionId(int notifactionId){
		ContentValues values = new ContentValues();
		values.put("tvState", "read");
		
		sqlitedb.update("pushdatatable", values, " notifactionId=" + notifactionId ,new String[]{});
		
	}
	//更新一条状体通过_id
	public void Pushdata_updatebyid(String _id){
		ContentValues values = new ContentValues();
		values.put("tvState", "read");
		
		sqlitedb.update("pushdatatable", values, " _ID=" + _id ,new String[]{});
		
	}
	//更新所有状体
	public void Pushdata_updateall(){
		ContentValues values = new ContentValues();
		values.put("tvState", "read");
		sqlitedb.update("pushdatatable", values, "tvState=?", new String[]{"unread"});
		
	}
	
	public void Pushdata_delete(int id){
		sqlitedb.execSQL("delete from pushdatatable where _id=" + id);
		
	}
	
}
