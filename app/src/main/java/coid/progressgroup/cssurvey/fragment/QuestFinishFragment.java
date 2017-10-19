package coid.progressgroup.cssurvey.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import coid.progressgroup.cssurvey.MainActivity;
import coid.progressgroup.cssurvey.R;
import coid.progressgroup.cssurvey.SurveyActivity;

/**
 * Created by DEV01 on 26/01/2016.
 */
public class QuestFinishFragment extends Fragment implements View.OnClickListener {

    private SurveyActivity mAct;

    private View mView;

    private Button mBtnBackHome;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        mView =  (LinearLayout)inflater.inflate(R.layout.fragment_quest_finish, container, false);
        mAct  = (SurveyActivity) getActivity();
        mBtnBackHome = (Button) mView.findViewById(R.id.btn_back_home);
//        mAct.BtnNext.setVisibility(View.INVISIBLE);
//        mAct.BtnPrev.setVisibility(View.INVISIBLE);
//        mAct.ImgHeader.setVisibility(View.INVISIBLE);
        mBtnBackHome.setOnClickListener(this);
        return mView;
    }

    @Override
    public void onClick(View v) {
        Intent intentTo = new Intent(mAct, MainActivity.class);
        startActivity(intentTo);
        mAct.finish();
    }
}
