package coid.progressgroup.cssurvey;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button BtnMMSurvey;

    private Button BtnMMSync;

    private Button BtnMMReport;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign element
        BtnMMSurvey = (Button) findViewById(R.id.btn_main_menu_survey);
        BtnMMSync   = (Button) findViewById(R.id.btn_main_menu_sync);
        BtnMMReport = (Button) findViewById(R.id.btn_main_menu_report);

        BtnMMSurvey.setOnClickListener(this);
        BtnMMSync.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        int ID = v.getId();
        Intent intentTo;
        if(ID == R.id.btn_main_menu_survey){
            intentTo = new Intent(this, SurveyActivity.class);
        } else if(ID == R.id.btn_main_menu_sync){
            intentTo = new Intent(this, PostActivity.class);
        } else {
            intentTo = new Intent(this, SurveyActivity.class);
        }
        startActivity(intentTo);

    }
}
