package coid.progressgroup.cssurvey.fragment;

import android.app.ActionBar;
import android.content.Context;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import coid.progressgroup.cssurvey.R;
import coid.progressgroup.cssurvey.SurveyActivity;
import coid.progressgroup.cssurvey.dbase.HelperDBAnswer;
import coid.progressgroup.cssurvey.model.AnswerModel;

/**
 * Created by DEV01 on 20/01/2016.
 */
public class QuestAutoFragment extends Fragment {

    private View mView;

    private SurveyActivity mAct;

    private int indexSlide;

    private JSONObject jsonItem;

    public String Key;

    private HelperDBAnswer mHelperDBAnswer;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        mView   = (LinearLayout)inflater.inflate(R.layout.fragment_quest_auto, container, false);
        mHelperDBAnswer = new HelperDBAnswer(mAct);
        Bundle args = getArguments();
        indexSlide = args.getInt("QuestAutoFragmentIndex");
        try {
            jsonItem = (JSONObject)mAct.m_jArry.get(indexSlide);
            Key = jsonItem.getString("Key");
//            Log.i("FMY LOG Json Data",jsonItem.toString());
            BuildPage(jsonItem);
        } catch (JSONException e) {
            e.printStackTrace();
        }



        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAct    = (SurveyActivity) getActivity();

    }



    public boolean save(){
        try {
            JSONArray jsonArray = jsonItem.getJSONArray("Items");
            int VisitorID = (int) mAct.visitorID;
            String VisitorUnique = mAct.visitorUnique;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject Question = jsonArray.getJSONObject(i);
                int QuestID = Question.getInt("QuestID");
                String Tag_question = "Question_"+String.valueOf(QuestID);

                String Type = Question.getString("Type");
                AnswerModel AM;
                String Answer = "Abstain";
                if(Type.equals("Input")){
                    TextInputLayout TIL = (TextInputLayout) mView.findViewWithTag(Tag_question);
                    Answer              = TIL.getEditText().getText().toString();
                    if(Answer == null){
                        Answer = "Abstain";
                    }

                } else {
                    RadioGroup RG       = (RadioGroup) mView.findViewWithTag(Tag_question);
                    int radioButtonID   = RG.getCheckedRadioButtonId();
                    RadioButton RB      = (RadioButton) RG.findViewById(radioButtonID);
                    if(RB != null){
                        Answer              = (String) RB.getTag();
                    } else {
                        Answer = "Abstain";
                    }


                }

                Log.i("FEMMY LOG","---------------------THE Vistor unique---------------------"+VisitorUnique);

                if(mHelperDBAnswer.checkByQuestionVisitor(QuestID, VisitorUnique)){
                    AM = mHelperDBAnswer.getByQuestionVisitor(QuestID, VisitorUnique);
                    AM.Answer = Answer;
                    mHelperDBAnswer.updateAnswer(AM);
                } else {
                    AM = new AnswerModel(0,VisitorID,VisitorUnique,QuestID,Answer);
                    mHelperDBAnswer.addnewAnswer(AM);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void BuildPage(JSONObject Json){
        LinearLayout LLWrapOuttest = (LinearLayout) mView.findViewById(R.id.wrap_list_quest);

        try {
            String FragmentTitle = jsonItem.getString("TitleEN") + " / "+ jsonItem.getString("TitleID");

            TextView txtFragemntTitle = (TextView) mView.findViewById(R.id.txt_survey_act_title);
            RelativeLayout visit_header_form_personal = (RelativeLayout) mView.findViewById(R.id.visit_header_form_personal);
            if(FragmentTitle.length()> 40){
                txtFragemntTitle.setTextSize(16);
            } else {
                txtFragemntTitle.setTextSize(22);
            }
            txtFragemntTitle.setText(FragmentTitle);
            JSONArray jsonArray = jsonItem.getJSONArray("Items");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject Question = jsonArray.getJSONObject(i);
                LinearLayout LLInner = new LinearLayout(mAct);
                LinearLayout.LayoutParams LP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                LP.setMargins(0, 24, 0, 24);
                LLInner.setLayoutParams(LP);
                LLInner.setOrientation(LinearLayout.VERTICAL);
                String QuestionID = Question.getString("TextID");
                int QuestID = Question.getInt("QuestID");
                String Tag_question = "Question_"+String.valueOf(QuestID);
                String QuestionEn = Question.getString("TextEN");
                String Type = Question.getString("Type");
                if(Type.equals("Input") ){
                    String QuestionText = QuestionID+" / "+QuestionEn;
                    TextInputLayout ETIL = BuildETIL(QuestionText);
                    EditText EText = BuildEText(QuestionText);
                    ETIL.addView(EText);
                    ETIL.setTag(Tag_question);
                    LLInner.addView(ETIL);
                } else {
                    TextView tvQuestID = BuildQuestionID(QuestionID);
                    TextView tvQuestEN = BuildQuestionEn(QuestionEn);
                    JSONArray jsonOptions = Question.getJSONArray("Options");
                    RadioGroup rgOptions = BuildRadioGroup(jsonOptions);
                    rgOptions.setTag(Tag_question);
                    LLInner.addView(tvQuestID);
                    LLInner.addView(tvQuestEN);
                    LLInner.addView(rgOptions);
                }

                LLWrapOuttest.addView(LLInner);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    private TextInputLayout BuildETIL(String Text){
        TextInputLayout ETIL = new TextInputLayout(mAct);
        LinearLayout.LayoutParams LP = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        ETIL.setLayoutParams(LP);
        ETIL.setHint(Text);
        return ETIL;
    }

    private EditText BuildEText(String Text){
        EditText Etxt = new EditText(mAct);
        ViewGroup.LayoutParams LP = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        Etxt.setLayoutParams(LP);
        return Etxt;
    }

    private TextView BuildQuestionID(String text){
        TextView nTview = new TextView(mAct);
        nTview.setText(text);
        nTview.setTextColor(ContextCompat.getColor(mAct, R.color.colorPrimaryDark));
        TableRow.LayoutParams LP = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT);
        nTview.setLayoutParams(LP);
        return nTview;
    }

    private TextView BuildQuestionEn(String text){
        TextView nTview = new TextView(mAct);
        TableRow.LayoutParams LP = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT);
        nTview.setText(text);
        LP = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.WRAP_CONTENT);
        nTview.setLayoutParams(LP);
        return nTview;
    }

    private RadioGroup BuildRadioGroup(JSONArray jarray){
        RadioGroup nRGroup = new RadioGroup(mAct);
        nRGroup.setWeightSum(100);
        RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.MATCH_PARENT,
                RadioGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0,12,0,12);
        nRGroup.setLayoutParams(params);

        try {
            float len = jarray.length();
            float weight = 100 / len;
            RadioGroup.LayoutParams LPRadio = new RadioGroup.LayoutParams(
                                                            RadioGroup.LayoutParams.MATCH_PARENT,
                                                            RadioGroup.LayoutParams.WRAP_CONTENT,weight);
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject item = jarray.getJSONObject(i);
                RadioButton radio = new RadioButton(mAct);
                radio.setVisibility(View.VISIBLE);
                radio.setLayoutParams(LPRadio);
                radio.setTag(item.getString("TextEN"));
                String Caption = item.getString("TextID")+" / "+item.getString("TextEN");
                radio.setText(Caption);
                nRGroup.addView(radio);
            }
        } catch (JSONException e){
            e.printStackTrace();
        }

        return nRGroup;
    }



}
