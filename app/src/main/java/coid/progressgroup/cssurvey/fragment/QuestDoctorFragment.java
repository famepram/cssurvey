package coid.progressgroup.cssurvey.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import coid.progressgroup.cssurvey.R;
import coid.progressgroup.cssurvey.SurveyActivity;

/**
 * Created by DEV01 on 18/01/2016.
 */
public class QuestDoctorFragment extends Fragment implements View.OnClickListener {

    private View mView;

    private SurveyActivity mAct;

    private Button BtnSaveNext;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        mView =  (LinearLayout)inflater.inflate(R.layout.fragment_quest_doctor, container, false);
        mAct = (SurveyActivity) getActivity();
        BtnSaveNext = (Button) mView.findViewById(R.id.ButtonQuestdoctorSave);
        BtnSaveNext.setOnClickListener(this);
        return mView;
    }

    @Override
    public void onClick(View v) {
        mAct.pagerGoNext();
    }
}
