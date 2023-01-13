package com.harismehmood.i200902;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Db_helper extends SQLiteOpenHelper {
//now we have to create second table that stores categories
    public static final String DATABASE_NAME="notes.db";
    public static final String TABLE_NAME="notes_table";
    public static final Integer DATABASE_VERSION=1;
    public static final String KEY_ID="ID";
    public static final String KEY_NAME="NAME";

    //make key type to store current date and time

    public static final String KEY_TYPE="TYPE";
    public static final String KEY_DATE="CURDATE";
    public static final String KEY_CATEGORY="CATEGORY";
    public static final String KEY_DATA="DATA";
    public static final String KEY_PASSWORD="PASSWORD";
    public static final String KEY_IMG="IMAGE";

    public static final String TABLE_NAME2="categories_table";
    public static final String KEY_ID2="ID";
    public static final String KEY_CATEGORY2="CATEGORY";
    //public static final String KEY_FK="FK";

    public Db_helper(@NonNull Context context) {
        super(context,
                DATABASE_NAME,
                null,
                DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_NAME2+"("+KEY_ID2+" INTEGER PRIMARY KEY, "+KEY_CATEGORY2+" TEXT)");
        //db.execSQL("CREATE TABLE "+TABLE_NAME2+"("+KEY_ID2+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_CATEGORY2+" TEXT, "+KEY_FK+" INTEGER, FOREIGN KEY ("+KEY_FK+") REFERENCES "+TABLE_NAME+"("+KEY_ID+"))");
        db.execSQL("CREATE TABLE "+TABLE_NAME+"("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+KEY_NAME+" TEXT, "+KEY_TYPE+" TEXT,"+KEY_DATE+" DATETIME,"+KEY_CATEGORY+" TEXT,"+KEY_DATA+" TEXT,"+KEY_IMG+" TEXT,"+KEY_PASSWORD+" TEXT)");
//inserting default categories
        db.execSQL("INSERT INTO "+TABLE_NAME2+" VALUES(1,'ADD NEW CATEGORY')");
        db.execSQL("INSERT INTO "+TABLE_NAME2+" VALUES(2,'Notes')");
        //create category table having id as a primary key and category column and fk as froign key from notes_table

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        onCreate(db);
    }

    public  void  insert(String name,String type, String category,String data, String img,String password){
        SQLiteDatabase db=this.getWritableDatabase();
        //String sql="insert into "+TABLE_NAME+" ( "+KEY_NAME+" , "+KEY_PHONE+" ) values ( "+name+" , "+phone+" )";
        //write a query to insert data in table
        String sql="insert into "+TABLE_NAME+" ( "+KEY_NAME+" , "+KEY_TYPE+" , "+KEY_DATE+" , "+KEY_CATEGORY+" , "+KEY_DATA+" , "+KEY_IMG+" , "+KEY_PASSWORD+" ) values ( '"+name+"' , '"+type+"' , datetime('now','localtime') , '"+category+"' , '"+data+"' , '"+img+"' , '"+password+"' )";
        //sql query to enter date in table

//        ContentValues values= new ContentValues();
//        values.put(KEY_NAME, name);
//        values.put(KEY_TYPE, type);
//        values.put(KEY_CATEGORY, category);
//        values.put(KEY_DATA, data);
//        values.put(KEY_IMG, img);
//        values.put(KEY_PASSWORD, password);
//        db.insert(TABLE_NAME, null, values);

        db.execSQL(sql);

      //  db.close();
    }

    public  void delete_note(Integer id){
        SQLiteDatabase db=this.getWritableDatabase();
        String sql= "delete from " + TABLE_NAME +" where "+ KEY_ID + " = "+id;
        db.execSQL(sql);
        db.close();
    }
    public  void update_note_name(Integer id, String name){
        SQLiteDatabase db=this.getWritableDatabase();
        String sql= "update " + TABLE_NAME +" set ";
        sql+=KEY_NAME+" = '"+name+"' where id = " + id ;
        db.execSQL(sql);
        db.close();
    }

    public  void update_image(Integer id, String img){
        SQLiteDatabase db=this.getWritableDatabase();
        String sql= "update " + TABLE_NAME +" set image = '"+img+"' where id = " + id;
        db.execSQL(sql);
        db.close();
    }
    public  void update_type(Integer id, String type){
        SQLiteDatabase db=this.getWritableDatabase();
        String sql= "update " + TABLE_NAME +" set type = '"+type+"' where id = " + id;
        db.execSQL(sql);
        db.close();
    }
    public  void update_category(Integer id, String category){
        SQLiteDatabase db=this.getWritableDatabase();
        String sql= "update " + TABLE_NAME +" set category = '"+category+"' where id = " + id;
        db.execSQL(sql);
        db.close();
    }
    public  void update_data(Integer id, String data){
        SQLiteDatabase db=this.getWritableDatabase();
        String sql= "update " + TABLE_NAME +" set data = '"+data+"' where id = " + id;
        db.execSQL(sql);
        db.close();
    }
    public ArrayList<contact_model> select_all(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME, null);
        ArrayList<contact_model> arrayList=new ArrayList<>();

        while (cursor.moveToNext()){
            Integer id=cursor.getInt(0);
            String name=cursor.getString(1);
            String type=cursor.getString(2);
            String category=cursor.getString(4);
            String data=cursor.getString(5);
            String img=cursor.getString(6);
            String pass=cursor.getString(7);
            contact_model contact=new contact_model(id,name,type,category,data, Uri.parse(img),pass);
            arrayList.add(contact);
        }
       // db.close();
        return arrayList;
    }
    public ArrayList<contact_model> search(String pattern){
        //get all elements from database and add it to arraylist that match with name
       // String temp_name=""+name+"";
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="select * from "+TABLE_NAME+" where lower("+KEY_NAME+") LIKE '%"+pattern+"%' or lower("+KEY_TYPE+") " +
                "LIKE '%"+pattern+"%' or lower("+KEY_CATEGORY+") LIKE '%"+pattern+"%' or lower("+KEY_DATA+") LIKE '%"+pattern+"%'";
        Cursor cursor = db.rawQuery(sql, null);
        ArrayList<contact_model> arrayList=new ArrayList<>();
        while(cursor.moveToNext()){
            Integer id=cursor.getInt(0);
            String name=cursor.getString(1);
            String type=cursor.getString(2);
            String category=cursor.getString(4);
            String data=cursor.getString(5);
            String img=cursor.getString(6);
            String pass=cursor.getString(7);
            contact_model contact=new contact_model(id,name,type,category,data, Uri.parse(img),pass);
            arrayList.add(contact);

        }

        return arrayList;
    }
 //create function to selectAllCategories
    public String[] selectAllCategories(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME2, null);
        int count=0;
        String[] arrayList = new String[cursor.getCount()];
        while (cursor.moveToNext()){
            String category=cursor.getString(1);
            arrayList[count]=category;;
            count++;
        }

        return arrayList;
    }
    //create function to add category in the category table
    public void insertCategory(String category){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values= new ContentValues();
        values.put(KEY_CATEGORY2, category);
        db.insert(TABLE_NAME2, null, values);
    }
    public boolean authentication(String pass){
        //find the password in the database if it correct return te id of the user
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="select * from "+TABLE_NAME+" where "+KEY_PASSWORD+" = '"+pass+"'";
        Cursor cursor = db.rawQuery(sql, null);
        //if the password is correct return true else false
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }
    //create function of update date thate take input of id and update its date to current date
    public void update_date(Integer id){
        SQLiteDatabase db=this.getWritableDatabase();
        String sql= "update " + TABLE_NAME +" set curdate = datetime('now','localtime') where id = " + id;
        db.execSQL(sql);
      //  db.close();
    }
    //create functio to string of a date macthin wiht input id
    public String get_date(Integer id){
        SQLiteDatabase db=this.getReadableDatabase();
        String sql="select curdate from "+TABLE_NAME+" where id = "+id;
        Cursor cursor = db.rawQuery(sql, null);
        String date="";
        while (cursor.moveToNext()){
            date=cursor.getString(0);
        }
        return date;
    }
    //create a function that select all the data from the notes_table on the bases of key_type in descending order and store in arraylist and return it
    public ArrayList<contact_model> sort_new_created(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME+" order by "+KEY_DATE+" desc", null);
        ArrayList<contact_model> arrayList=new ArrayList<>();

        while (cursor.moveToNext()){
            Integer id=cursor.getInt(0);
            String name=cursor.getString(1);
            String type=cursor.getString(2);
            String category=cursor.getString(4);
            String data=cursor.getString(5);
            String img=cursor.getString(6);
            String pass=cursor.getString(7);
            contact_model contact=new contact_model(id,name,type,category,data, Uri.parse(img),pass);
            arrayList.add(contact);
        }
        // db.close();
        return arrayList;
    }
    public ArrayList<contact_model> sort_old_created(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from "+TABLE_NAME+" order by "+KEY_DATE+" asc", null);
        ArrayList<contact_model> arrayList=new ArrayList<>();

        while (cursor.moveToNext()){
            Integer id=cursor.getInt(0);
            String name=cursor.getString(1);
            String type=cursor.getString(2);
            String category=cursor.getString(4);
            String data=cursor.getString(5);
            String img=cursor.getString(6);
            String pass=cursor.getString(7);
            contact_model contact=new contact_model(id,name,type,category,data, Uri.parse(img),pass);
            arrayList.add(contact);
        }
        // db.close();
        return arrayList;
    }
}
