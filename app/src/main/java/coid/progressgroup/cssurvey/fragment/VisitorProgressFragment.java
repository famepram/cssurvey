package coid.progressgroup.cssurvey.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import coid.progressgroup.cssurvey.R;
import coid.progressgroup.cssurvey.SurveyActivity;
import coid.progressgroup.cssurvey.dbase.HelperDBVisitor;
import coid.progressgroup.cssurvey.helper.DateTime;
import coid.progressgroup.cssurvey.model.VisitorModel;

/**
 * Created by DEV01 on 13/01/2016.
 */
public class VisitorProgressFragment extends Fragment implements View.OnClickListener {

    SurveyActivity mAct;

    RadioGroup RadioKnowFrom;
    EditText EtxtDoctorname;
    EditText EtxtClinicname;
    Button ButtonProgressSubmit;
    View mView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        mView =  (LinearLayout)inflater.inflate(R.layout.fragment_visit_progress, container, false);
        this._initElement(mView);
        return mView;
    }

    private void  _initElement(View v){
        mAct            = (SurveyActivity) getActivity();
        RadioKnowFrom   = (RadioGroup) v.findViewById(R.id.radio_group_progress_knowfrom);
        EtxtDoctorname  = (EditText) v.findViewById(R.id.etxt_doctor_name);
        EtxtClinicname  = (EditText) v.findViewById(R.id.etxt_clinic_name);
        ButtonProgressSubmit = (Button) v.findViewById(R.id.button_visit_progress);
        ButtonProgressSubmit.setOnClickListener(this);
    }

    private void submitData(){
        HelperDBVisitor mHelperDBVisitor = new HelperDBVisitor(getActivity());
        int selectedId = RadioKnowFrom.getCheckedRadioButtonId();
        RadioButton radioButtonKnowFrom = (RadioButton) mView.findViewById(selectedId);
//        VisitorModel visitor = mHelperDBVisitor.getByID(mAct.visitorID);
//        visitor.KnowFrom = radioButtonKnowFrom.getText().toString();
//        visitor.DoctorName = EtxtDoctorname.getText().toString();
//        visitor.ClinicName = EtxtClinicname.getText().toString();
//        visitor.UpdatedTime = DateTime.GetStrCurrentTime();
//        mHelperDBVisitor.UpdateVisitor(visitor);

        Log.i("FMY LOGGING", String.valueOf(selectedId));

    }

    @Override
    public void onClick(View v) {
        HelperDBVisitor mHelperDBVisitor = new HelperDBVisitor(getActivity());
        int selectedId = RadioKnowFrom.getCheckedRadioButtonId();
        RadioButton radioButtonKnowFrom = (RadioButton) mView.findViewById(selectedId);
        VisitorModel visitor = mHelperDBVisitor.getByID(mAct.visitorID);
        visitor.KnowFrom = radioButtonKnowFrom.getText().toString();
        visitor.DoctorName = EtxtDoctorname.getText().toString();
        visitor.ClinicName = EtxtClinicname.getText().toString();
        visitor.UpdatedTime = DateTime.GetStrCurrentTime();
        mHelperDBVisitor.UpdateVisitor(visitor);
        Log.i("FMY LOGGING",String.valueOf(visitor.DateVisit));
        mAct.pagerGoNext();
        //this.submitData();
    }

    public void save(){
        HelperDBVisitor mHelperDBVisitor = new HelperDBVisitor(getActivity());
        int selectedId = RadioKnowFrom.getCheckedRadioButtonId();
        RadioButton radioButtonKnowFrom = (RadioButton) mView.findViewById(selectedId);
        VisitorModel visitor = mHelperDBVisitor.getByID(mAct.visitorID);
        visitor.KnowFrom = radioButtonKnowFrom.getText().toString();
        visitor.DoctorName = EtxtDoctorname.getText().toString();
        visitor.ClinicName = EtxtClinicname.getText().toString();
        visitor.UpdatedTime = DateTime.GetStrCurrentTime();
        mHelperDBVisitor.UpdateVisitor(visitor);
        Log.i("FMY LOGGING",String.valueOf(visitor.DateVisit));
    }
}

