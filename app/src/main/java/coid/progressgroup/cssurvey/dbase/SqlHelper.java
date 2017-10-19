package coid.progressgroup.cssurvey.dbase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import coid.progressgroup.cssurvey.constant.Dbase;

/**
 * Created by DEV01 on 14/01/2016.
 */
public class SqlHelper extends SQLiteOpenHelper {

    public SqlHelper(Context context) {
        super(context, Dbase.DB_NAME, null, Dbase.DB_VERSION);
    }

    private static final String getScriptTableVisitor(){
        String sql_create = "create table "
                + Dbase.TABLE_VISITOR + "(" + Dbase.COL_VISITOR_ID
                + " integer primary key autoincrement, "
                + Dbase.COL_VISITOR_UNIQUE+" text not null, "
                + Dbase.COL_VISITOR_NAME+" text not null, "
                + Dbase.COL_VISITOR_PHONE+" text not null, "
                + Dbase.COL_VISITOR_EMAIL+" text not null, "
                + Dbase.COL_VISITOR_ADDRESS+" text not null, "
                + Dbase.COL_VISITOR_DATE_VISIT+" text not null, "
                + Dbase.COL_VISITOR_KNOW_FROM+" text not null, "
                + Dbase.COL_DOCTOR_NAME+" text not null, "
                + Dbase.COL_CLINIC_NAME+" text not null, "
                + Dbase.COL_ADDED_TIME+" text not null, "
                + Dbase.COL_UPDATED_TIME+" text not null "
                +");";
        return sql_create;
    }

    private static final String getScriptTableAnswer(){
        String sql_create = "create table "
                + Dbase.TABLE_ANSWER + "(" + Dbase.COL_ANSWER_ID
                + " integer primary key autoincrement, "
                + Dbase.COL_ANSWER_VISITOR_ID+" integer, "
                + Dbase.COL_ANSWER_VISITOR_UNIQUE+" text not null, "
                + Dbase.COL_ANSWER_QUESTION_ID+" integer, "
                + Dbase.COL_ANSWER_TEXT+" text not null, "
                + Dbase.COL_ANSWER_ADDED_TIME+" text not null, "
                + Dbase.COL_ANSWER_UPDATED_TIME+" text not null "
                +");";
        return sql_create;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(getScriptTableVisitor());
        db.execSQL(getScriptTableAnswer());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SqlHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + Dbase.TABLE_VISITOR);
        db.execSQL("DROP TABLE IF EXISTS " + Dbase.TABLE_ANSWER);
        onCreate(db);
    }


}
