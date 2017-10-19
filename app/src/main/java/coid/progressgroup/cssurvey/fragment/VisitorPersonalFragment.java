package coid.progressgroup.cssurvey.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import coid.progressgroup.cssurvey.R;
import coid.progressgroup.cssurvey.SurveyActivity;
import coid.progressgroup.cssurvey.dbase.HelperDBVisitor;
import coid.progressgroup.cssurvey.model.VisitorModel;
import coid.progressgroup.cssurvey.helper.DateTime;

/**
 * Created by DEV01 on 13/01/2016.
 */
public class VisitorPersonalFragment extends Fragment implements View.OnClickListener, View.OnFocusChangeListener, DatePickerDialog.OnDateSetListener {


    TextView txtName;
    TextView txtAddress;
    TextView txtPhone;
    TextView txtEmail;
    TextView txtDateVisit;
    Button BtnSubmit;

    SurveyActivity mAct;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        View view =  (LinearLayout)inflater.inflate(R.layout.fragment_visit_personal, container, false);
        this.initElements(view);
        return view;
    }

    @Override
    public void onClick(View v) {
        VisitorModel newVisitor = this.submitData();
        Log.i("New Visitor Email :",newVisitor.Email);
        Log.i("New Visitor ID :",String.valueOf(newVisitor.ID));
        mAct.visitorID = newVisitor.ID;
        mAct.visitorUnique = newVisitor.Unique;
        mAct.pagerGoNext();
    }

    public void save(){
        VisitorModel newVisitor = this.submitData();
        Log.i("New Visitor Email :",newVisitor.Email);
        Log.i("New Visitor ID :",String.valueOf(newVisitor.ID));
        Log.i("New Visitor Inoque :",String.valueOf(newVisitor.Unique));
        mAct.visitorID = newVisitor.ID;
        mAct.visitorUnique = newVisitor.Unique;
    }

    private void initElements(View view){
        txtName         = (TextView) view.findViewById(R.id.etxt_survey_act_name);
        txtPhone        = (TextView) view.findViewById(R.id.etxt_phone);
        txtEmail        = (TextView) view.findViewById(R.id.etxt_email);
        txtAddress      = (TextView) view.findViewById(R.id.etxt_address);
//        txtDateVisit    = (TextView) view.findViewById(R.id.etxt_survey_act_date_visit);
        BtnSubmit       = (Button) view.findViewById(R.id.button_visit_personal);
        mAct            = (SurveyActivity)getActivity();
//        txtDateVisit.setText(DateTime.GetStrCurrentDate());
//        txtDateVisit.setOnFocusChangeListener(this);
//        txtDateVisit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar now = Calendar.getInstance();
//                DatePickerDialog dpd = DatePickerDialog.newInstance(
//                        (DatePickerDialog.OnDateSetListener) VisitorPersonalFragment.this,
//                        now.get(Calendar.YEAR),
//                        now.get(Calendar.MONTH),
//                        now.get(Calendar.DAY_OF_MONTH)
//                );
//                dpd.setAccentColor(R.color.colorBlue);
//                dpd.show(mAct.getFragmentManager(), "Datepickerdialog");
//            }
//        });
        BtnSubmit.setOnClickListener(this);
    }

    private boolean formValidation(){
        String name = txtName.getText().toString();
        String phone = txtPhone.getText().toString();
        String email = txtEmail.getText().toString();
        if(name != "" && phone !="" && email!=""){
            return true;
        } else {
            return false;
        }
    }

    public VisitorModel submitData(){
        String name         = txtName.getText().toString();
        String phone        = txtPhone.getText().toString();
        String email        = txtEmail.getText().toString();
        String address      = txtAddress.getText().toString();
//        String dateVisit    = txtDateVisit.getText().toString();

        if(this.formValidation()){
            HelperDBVisitor mHelperDBVisitor = new HelperDBVisitor(getActivity());
            VisitorModel visitor = new VisitorModel();
            visitor.ID          = 0;
            visitor.Name        = name;
            visitor.Phone       = phone;
            visitor.Email       = email;
            visitor.Address     = address;
            visitor.DateVisit   = DateTime.GetStrCurrentDate();
            visitor.KnowFrom    = "";
            visitor.DoctorName  = "";
            visitor.ClinicName  = "";
            visitor.AddedTime   = DateTime.GetStrCurrentTime();
            visitor.UpdatedTime = DateTime.GetStrCurrentTime();
            VisitorModel newVisitor = mHelperDBVisitor.AddVisitor(visitor);
            return newVisitor;
        } else {
            return null;
        }


    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(hasFocus){
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    (DatePickerDialog.OnDateSetListener) VisitorPersonalFragment.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.setAccentColor(R.color.colorBlue);
            dpd.show(mAct.getFragmentManager(), "Datepickerdialog");
        } else {

        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String sYear = String.valueOf(year);
        String sMonth;
        if(monthOfYear < 9 ){
            sMonth = "0"+String.valueOf(monthOfYear+1);
        } else {
            sMonth = String.valueOf(monthOfYear+1);
        }
        String sDay = String.valueOf(dayOfMonth);
        String sDate = sDay+"-"+sMonth+"-"+sYear;
        txtDateVisit.setText(sDate);
    }
}