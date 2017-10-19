package coid.progressgroup.cssurvey;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import coid.progressgroup.cssurvey.constant.General;
import coid.progressgroup.cssurvey.helper.HttpAsyncTask;

public class PostActivity extends AppCompatActivity implements View.OnClickListener, HttpAsyncTask.callback {

    LinearLayout LLSyncData;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        LLSyncData = (LinearLayout)findViewById(R.id.LLSyncData);
        LLSyncData.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        new AlertDialog.Builder(this)
            .setTitle("Confirmation")
            .setMessage("Do you really want to continue?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                    String url = General.URL_STORAGE;
                    HttpAsyncTask task = new HttpAsyncTask(PostActivity.this,getApplicationContext());
                    task.execute(url);
                    mDialog = ProgressDialog.show(PostActivity.this, "","Sending data. Please wait...", true);
                }
            })
            .setNegativeButton("Cancel", null).show();
    }


    @Override
    public void postExecuteCallback( String str) {
        mDialog.setMessage((CharSequence) str);
        mDialog.dismiss();
    }
}
