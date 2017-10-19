package coid.progressgroup.cssurvey.dbase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import coid.progressgroup.cssurvey.constant.Dbase;
import coid.progressgroup.cssurvey.helper.DateTime;
import coid.progressgroup.cssurvey.model.VisitorModel;

/**
 * Created by DEV01 on 14/01/2016.
 */
public class HelperDBVisitor {

    // Database fields
    private SQLiteDatabase database;
    private SqlHelper dbHelper;
    private String[] allColumns =
            {
                    Dbase.COL_VISITOR_ID,
                    Dbase.COL_VISITOR_UNIQUE,
                    Dbase.COL_VISITOR_NAME,
                    Dbase.COL_VISITOR_PHONE,
                    Dbase.COL_VISITOR_EMAIL,
                    Dbase.COL_VISITOR_ADDRESS,
                    Dbase.COL_VISITOR_DATE_VISIT,
                    Dbase.COL_VISITOR_KNOW_FROM,
                    Dbase.COL_DOCTOR_NAME,
                    Dbase.COL_CLINIC_NAME,
                    Dbase.COL_ADDED_TIME,
                    Dbase.COL_UPDATED_TIME
            };
    public HelperDBVisitor(Context context) {
        dbHelper = new SqlHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public VisitorModel AddVisitor(VisitorModel visitor) {
        ContentValues values = new ContentValues();
        values.put(Dbase.COL_VISITOR_NAME, visitor.Name);
        values.put(Dbase.COL_VISITOR_UNIQUE, DateTime.GetUnixTimeStamp());
        values.put(Dbase.COL_VISITOR_PHONE, visitor.Phone);
        values.put(Dbase.COL_VISITOR_EMAIL, visitor.Email);
        values.put(Dbase.COL_VISITOR_ADDRESS, visitor.Address);
        values.put(Dbase.COL_VISITOR_DATE_VISIT, visitor.DateVisit);
        values.put(Dbase.COL_VISITOR_KNOW_FROM, visitor.KnowFrom);
        values.put(Dbase.COL_DOCTOR_NAME, visitor.DoctorName);
        values.put(Dbase.COL_CLINIC_NAME, visitor.ClinicName);
        values.put(Dbase.COL_ADDED_TIME, visitor.AddedTime);
        values.put(Dbase.COL_UPDATED_TIME, visitor.UpdatedTime);

        try {
            this.open();
            long insertId = this.database.insert(Dbase.TABLE_VISITOR, null, values);
            Cursor cursor = this.database.query(Dbase.TABLE_VISITOR,
                    allColumns, Dbase.COL_VISITOR_ID + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            VisitorModel NewVisitor = new VisitorModel();
            NewVisitor.setFromCusrsor(cursor);
            cursor.close();
            this.close();
            return NewVisitor;
        } catch ( SQLException e ){
            Log.i("ERROR INSERT DATABASE: ",e.getMessage());
            this.close();
            return null;
        }
    }

    public VisitorModel UpdateVisitor(VisitorModel visitor){
        ContentValues values = new ContentValues();
        values.put(Dbase.COL_VISITOR_NAME, visitor.Name);
        values.put(Dbase.COL_VISITOR_PHONE, visitor.Phone);
        values.put(Dbase.COL_VISITOR_EMAIL, visitor.Email);
        values.put(Dbase.COL_VISITOR_ADDRESS, visitor.Address);
        values.put(Dbase.COL_VISITOR_DATE_VISIT, visitor.DateVisit);
        values.put(Dbase.COL_VISITOR_KNOW_FROM, visitor.KnowFrom);
        values.put(Dbase.COL_DOCTOR_NAME, visitor.DoctorName);
        values.put(Dbase.COL_CLINIC_NAME, visitor.ClinicName);
        values.put(Dbase.COL_ADDED_TIME, visitor.AddedTime);
        values.put(Dbase.COL_UPDATED_TIME, visitor.UpdatedTime);
        try {
            this.open();
            this.database.update(Dbase.TABLE_VISITOR, values, Dbase.COL_VISITOR_ID + " = '" + visitor.ID + "'", null);

            this.close();
            return visitor;
        } catch ( SQLException e ){
            Log.i("ERROR UPDATE DATABASE: ",e.getMessage());
            this.close();
            return null;
        }
    }

    public VisitorModel getByID(long ID){
        Cursor cursor = null;
        VisitorModel visitor = null;
        String empName = "";
        try {
            this.open();
            cursor = this.database.rawQuery("SELECT * FROM " + Dbase.TABLE_VISITOR + " WHERE " + Dbase.COL_VISITOR_ID + "=?", new String[]{String.valueOf(ID) + ""});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                visitor = new VisitorModel();
                visitor.setFromCusrsor(cursor);
            }
        } catch(SQLException e ) {
            Log.i("Error Fetch DB : ",e.getMessage());
        } finally {
            cursor.close();
        }
        return visitor;
    }

    public ArrayList<VisitorModel> getByDate(String dateVisit){
        Cursor cursor = null;
        ArrayList<VisitorModel> ArrayVisitor = new ArrayList<VisitorModel>();
        try {
            this.open();
            String SQL = "SELECT * FROM "+Dbase.TABLE_VISITOR+" WHERE "+ Dbase.COL_VISITOR_DATE_VISIT+ "='"+dateVisit+"'";
            cursor = this.database.rawQuery(SQL,null);
            if(cursor.moveToFirst()){
                do {
                    VisitorModel Visitor = new VisitorModel();
                    Visitor.setFromCusrsor(cursor);
                    ArrayVisitor.add(Visitor);
                } while(cursor.moveToNext());
            }


        } catch (SQLException e){
            this.close();
            e.printStackTrace();
        } finally {
            this.close();
        }
        return ArrayVisitor;
    }


}
