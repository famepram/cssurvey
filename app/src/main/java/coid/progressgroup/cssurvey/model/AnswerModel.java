package coid.progressgroup.cssurvey.model;

import android.database.Cursor;

/**
 * Created by DEV01 on 22/01/2016.
 */
public class AnswerModel {

    public int ID;

    public int VisitorID;

    public String VisitorUnique;

    public int QuestionID;

    public String Answer;

    public String AddedTime;

    public String UpdatedTime;

    public AnswerModel (){

    }

    public AnswerModel (int ID,int VisitorID,int QuestionID,String Answer){
        this.ID = ID;
        this.VisitorID = VisitorID;
        this.QuestionID = QuestionID;
        this.Answer = Answer;
    }

    public AnswerModel (int ID,int VisitorID,String VisitorUnique,int QuestionID,String Answer){
        this.ID = ID;
        this.VisitorUnique = VisitorUnique;
        this.VisitorID = VisitorID;
        this.QuestionID = QuestionID;
        this.Answer = Answer;
    }

    public void setFromCusrsor(Cursor cursor) {
        this.ID = cursor.getInt(0);
        this.VisitorID = cursor.getInt(1);
        this.VisitorUnique = cursor.getString(2);
        this.QuestionID = cursor.getInt(3);
        this.Answer = cursor.getString(4);
        this.AddedTime = cursor.getString(5);
        this.UpdatedTime = cursor.getString(6);
    }


}
