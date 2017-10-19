package coid.progressgroup.cssurvey.model;

import android.database.Cursor;

import java.util.ArrayList;

import coid.progressgroup.cssurvey.dbase.HelperDBAnswer;

/**
 * Created by DEV01 on 14/01/2016.
 */
public class VisitorModel {

    public long ID;

    public String Unique;

    public String Name;

    public String Phone;

    public String Email;

    public String Address;

    public String DateVisit;

    public String KnowFrom;

    public String DoctorName;

    public String ClinicName;

    public String AddedTime;

    public String UpdatedTime;

    public ArrayList<AnswerModel> AswerList;

    public VisitorModel(){

    }

    public void setFromCusrsor(Cursor cursor) {
        this.ID = cursor.getInt(0);
        this.Unique = cursor.getString(1);
        this.Name = cursor.getString(2);
        this.Phone = cursor.getString(3);
        this.Email = cursor.getString(4);
        this.Address = cursor.getString(5);
        this.DateVisit = cursor.getString(6);
        this.KnowFrom = cursor.getString(7);
        this.DoctorName = cursor.getString(8);
        this.ClinicName = cursor.getString(9);
        this.AddedTime = cursor.getString(10);
        this.UpdatedTime = cursor.getString(11);

    }



}
