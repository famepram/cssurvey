package coid.progressgroup.cssurvey.constant;

/**
 * Created by DEV01 on 14/01/2016.
 */
public final class Dbase {

    public static final String DB_NAME = "survey_db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_VISITOR = "visitor";

    public static final String COL_VISITOR_ID          = "visitor_id";
    public static final String COL_VISITOR_UNIQUE      = "visitor_unique";
    public static final String COL_VISITOR_NAME        = "name";
    public static final String COL_VISITOR_PHONE       = "phone";
    public static final String COL_VISITOR_EMAIL       = "email";
    public static final String COL_VISITOR_ADDRESS     = "address";
    public static final String COL_VISITOR_DATE_VISIT  = "date_visit";
    public static final String COL_VISITOR_KNOW_FROM   = "know_from";
    public static final String COL_DOCTOR_NAME         = "doctor_name";
    public static final String COL_CLINIC_NAME         = "clinic_name";
    public static final String COL_ADDED_TIME          = "added_time";
    public static final String COL_UPDATED_TIME        = "updated_time";



    public static final String TABLE_ANSWER = "answer";

    public static final String COL_ANSWER_ID           = "answer_id";
    public static final String COL_ANSWER_VISITOR_ID   = "visitor_id";
    public static final String COL_ANSWER_VISITOR_UNIQUE   = "visitor_unique";
    public static final String COL_ANSWER_QUESTION_ID  = "question_id";
    public static final String COL_ANSWER_TEXT         = "answer";
    public static final String COL_ANSWER_ADDED_TIME   = "added_time";
    public static final String COL_ANSWER_UPDATED_TIME = "updated_time";

}
