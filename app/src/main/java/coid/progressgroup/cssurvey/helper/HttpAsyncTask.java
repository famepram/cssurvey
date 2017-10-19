package coid.progressgroup.cssurvey.helper;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import coid.progressgroup.cssurvey.constant.Dbase;
import coid.progressgroup.cssurvey.constant.General;
import coid.progressgroup.cssurvey.dbase.HelperDBAnswer;
import coid.progressgroup.cssurvey.dbase.HelperDBVisitor;
import coid.progressgroup.cssurvey.model.AnswerModel;
import coid.progressgroup.cssurvey.model.VisitorModel;

/**
 * Created by DEV01 on 25/01/2016.
 */
public class HttpAsyncTask extends AsyncTask<String, Void, String> {

    public interface callback {
        void postExecuteCallback(String str);
    }

    callback httpCallback;
    Context mCtx;


    public HttpAsyncTask(callback callback, Context ctx){
        httpCallback = callback;
        mCtx = ctx;
    }

    @Override
    protected String doInBackground(String... urls) {

        String response = "";
        InputStream inputStream = null;
        for (String url : urls) {

            DefaultHttpClient httpclient = new DefaultHttpClient();
            try {
                HttpPost httpPost = new HttpPost(url);
                String json = "";
                JSONObject jsonObject = new JSONObject();
                jsonObject = buildJson();
                json = jsonObject.toString();
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair(General.JSON_KEY_POST, json));
                UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
                httpPost.setEntity(ent);
                HttpResponse httpResponse = httpclient.execute(httpPost);
                inputStream = httpResponse.getEntity().getContent();
                if(inputStream != null)
                    response = convertInputStreamToString(inputStream);
                else
                    response = "Did not work!";

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.i("FMY LOG RESPONSE SERVER", response);
        return response;
    }


    @Override
    protected void onPostExecute(String result) {
        httpCallback.postExecuteCallback(result);
    }

    private JSONObject buildJson(){
        HelperDBVisitor mHelperDBVisitor = new HelperDBVisitor(mCtx);
        HelperDBAnswer mHelperDBAnswer = new HelperDBAnswer(mCtx);
        String datevisit = DateTime.GetStrCurrentDate();
        //String datevisit = "27-01-2016";
        ArrayList<VisitorModel> ArrayVisitor = mHelperDBVisitor.getByDate(datevisit);
        JSONObject retval = new JSONObject();
        JSONArray JSonArrayVisitor = new JSONArray();
        try {
            for (int i = 0; i < ArrayVisitor.size(); i++) {
                JSONObject jsonVisitor = new JSONObject();
                VisitorModel Visitor = ArrayVisitor.get(i);
                jsonVisitor.accumulate(Dbase.COL_VISITOR_ID,Visitor.ID);
                jsonVisitor.accumulate(Dbase.COL_VISITOR_UNIQUE,Visitor.Unique);
                jsonVisitor.accumulate(Dbase.COL_VISITOR_NAME,Visitor.Name);
                jsonVisitor.accumulate(Dbase.COL_VISITOR_EMAIL,Visitor.Email);
                jsonVisitor.accumulate(Dbase.COL_VISITOR_PHONE,Visitor.Phone);
                jsonVisitor.accumulate(Dbase.COL_VISITOR_ADDRESS,Visitor.Address);
                jsonVisitor.accumulate(Dbase.COL_VISITOR_DATE_VISIT,Visitor.DateVisit);
                jsonVisitor.accumulate(Dbase.COL_VISITOR_KNOW_FROM,Visitor.KnowFrom);
                jsonVisitor.accumulate(Dbase.COL_DOCTOR_NAME,Visitor.DoctorName);
                jsonVisitor.accumulate(Dbase.COL_CLINIC_NAME,Visitor.ClinicName);
                jsonVisitor.accumulate(Dbase.COL_ADDED_TIME,Visitor.AddedTime);
                jsonVisitor.accumulate(Dbase.COL_UPDATED_TIME,Visitor.UpdatedTime);
                ArrayList<AnswerModel> ArrayAnswer = mHelperDBAnswer.getByVisitorID((int)Visitor.ID);
                JSONArray AnswerListJson = new JSONArray();
                for (int x = 0; x < ArrayAnswer.size(); x++) {
                    AnswerModel Answer = ArrayAnswer.get(x);
                    JSONObject jsonAnswer = AnswerModelToJson(Answer);
                    AnswerListJson.put(jsonAnswer);
                }
                jsonVisitor.accumulate(General.JSON_KEY_ANSWER_LIST,AnswerListJson);
                JSonArrayVisitor.put(jsonVisitor);
            }
            retval.accumulate(General.JSON_KEY_ROOT,JSonArrayVisitor);
        } catch (JSONException e){
            e.printStackTrace();
        }

        return retval;
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    private JSONObject AnswerModelToJson(AnswerModel Answer){
        JSONObject jsonObjAnswer = null;
        try {
            jsonObjAnswer = new JSONObject();
            jsonObjAnswer.accumulate(Dbase.COL_ANSWER_ID,Answer.ID);
            jsonObjAnswer.accumulate(Dbase.COL_ANSWER_VISITOR_ID,Answer.VisitorID);
            jsonObjAnswer.accumulate(Dbase.COL_ANSWER_VISITOR_UNIQUE,Answer.VisitorUnique);
            jsonObjAnswer.accumulate(Dbase.COL_ANSWER_QUESTION_ID,Answer.QuestionID);
            jsonObjAnswer.accumulate(Dbase.COL_ANSWER_TEXT,Answer.Answer);
            jsonObjAnswer.accumulate(Dbase.COL_ANSWER_ADDED_TIME,Answer.AddedTime);
            jsonObjAnswer.accumulate(Dbase.COL_ANSWER_UPDATED_TIME,Answer.UpdatedTime);
        } catch (JSONException e){
            e.printStackTrace();
        }
        return jsonObjAnswer;
    }
}
