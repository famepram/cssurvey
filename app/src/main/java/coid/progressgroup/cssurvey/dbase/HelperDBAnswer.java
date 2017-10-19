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
import coid.progressgroup.cssurvey.model.AnswerModel;
import coid.progressgroup.cssurvey.model.VisitorModel;

/**
 * Created by DEV01 on 22/01/2016.
 */
public class HelperDBAnswer {

    // Database fields
    private SQLiteDatabase database;
    private SqlHelper dbHelper;
    private String[] allColumns =
            {
                    Dbase.COL_ANSWER_ID,
                    Dbase.COL_ANSWER_VISITOR_ID,
                    Dbase.COL_ANSWER_QUESTION_ID,
                    Dbase.COL_ANSWER_TEXT,
                    Dbase.COL_ANSWER_ADDED_TIME,
                    Dbase.COL_ANSWER_UPDATED_TIME
            };

    public HelperDBAnswer(Context context){
        dbHelper = new SqlHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addnewAnswer(AnswerModel Answer){
        long insertId;
        ContentValues values = new ContentValues();
        values.put(Dbase.COL_ANSWER_QUESTION_ID, Answer.QuestionID);
        values.put(Dbase.COL_ANSWER_VISITOR_ID, Answer.VisitorID);
        values.put(Dbase.COL_ANSWER_VISITOR_UNIQUE, Answer.VisitorUnique);
        values.put(Dbase.COL_ANSWER_TEXT, Answer.Answer);
        values.put(Dbase.COL_ANSWER_ADDED_TIME, DateTime.GetStrCurrentTime());
        values.put(Dbase.COL_ANSWER_UPDATED_TIME, DateTime.GetStrCurrentTime());
        try {
            this.open();
            insertId = this.database.insert(Dbase.TABLE_ANSWER, null, values);
            this.close();
            return insertId;
        } catch ( SQLException e ){
            Log.i("ERROR INSERT DATABASE: ", e.getMessage());
            this.close();
            return 0;
        }
    }

    public void updateAnswer(AnswerModel Answer){
        ContentValues values = new ContentValues();
        values.put(Dbase.COL_ANSWER_QUESTION_ID, Answer.QuestionID);
        values.put(Dbase.COL_ANSWER_VISITOR_ID, Answer.VisitorID);
        values.put(Dbase.COL_ANSWER_VISITOR_UNIQUE, Answer.VisitorUnique);
        values.put(Dbase.COL_ANSWER_TEXT, Answer.Answer);
        values.put(Dbase.COL_ANSWER_ADDED_TIME, Answer.AddedTime);
        values.put(Dbase.COL_ANSWER_UPDATED_TIME, DateTime.GetStrCurrentTime());
        try {
            this.open();
            this.database.update(Dbase.TABLE_ANSWER, values, Dbase.COL_ANSWER_ID + " = '" + Answer.ID + "'", null);
            this.close();
        } catch ( SQLException e ){
            Log.i("ERROR UPDATE DATABASE: ",e.getMessage());
            this.close();
        }
    }

    public boolean checkByQuestionVisitor(int QuestionID, String VisitorUnique){
        Cursor cursor = null;
        boolean exist = false;
        try {
            this.open();
            String SQL = "SELECT * FROM "+Dbase.TABLE_ANSWER+" WHERE "+ Dbase.COL_ANSWER_QUESTION_ID + " = ? AND "+Dbase.COL_ANSWER_VISITOR_UNIQUE+" = ?";
            String[] args = {String.valueOf(QuestionID),VisitorUnique};
            cursor = this.database.rawQuery(SQL,args);
            if (cursor.getCount() > 0) {
                exist = true;
            }
            cursor.close();
        } catch(SQLException e ) {
            Log.i("Error Fetch DB : ",e.getMessage());
            this.close();
        } finally {

            this.close();
        }
        return exist;
    }

    public AnswerModel getByQuestionVisitor(int QuestionID, String VisitorUnique){
        Cursor cursor = null;
        AnswerModel Answer = null;
        try {
            this.open();
            String SQL = "SELECT * FROM "+Dbase.TABLE_ANSWER+" WHERE "+ Dbase.COL_ANSWER_QUESTION_ID + " = ? AND "+Dbase.COL_ANSWER_VISITOR_UNIQUE+" = ?";
            String[] args = {String.valueOf(QuestionID),VisitorUnique};
            cursor = this.database.rawQuery(SQL,args);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                Answer = new AnswerModel();
                Answer.setFromCusrsor(cursor);
            }
        } catch(SQLException e ) {
            Log.i("Error Fetch DB : ",e.getMessage());
            this.close();
        } finally {
            cursor.close();
            this.close();
        }
        return Answer;
    }

    public ArrayList<AnswerModel> getByVisitorID(int VisitorID){
        Cursor cursor = null;
        ArrayList<AnswerModel> ArrayAnswer = new ArrayList<AnswerModel>();
        try {
            this.open();
            String SQL = "SELECT * FROM "+Dbase.TABLE_ANSWER+" WHERE "+ Dbase.COL_ANSWER_VISITOR_ID + " = ?";
            String[] args = {String.valueOf(VisitorID)};
            cursor = this.database.rawQuery(SQL,args);
            if (cursor.moveToFirst()) {
                do {
                    AnswerModel Answer = new AnswerModel();
                    Answer.setFromCusrsor(cursor);
                    ArrayAnswer.add(Answer);
                    cursor.moveToNext();
                } while (cursor.moveToNext());
            }
        } catch (SQLException e){
            Log.i("Error Fetch DB : ",e.getMessage());
            this.close();
        } finally {
            cursor.close();
            this.close();
        }
        return ArrayAnswer;
    }



}
