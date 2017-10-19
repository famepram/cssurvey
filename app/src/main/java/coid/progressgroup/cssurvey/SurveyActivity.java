package coid.progressgroup.cssurvey;

import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.manuelpeinado.fadingactionbar.extras.actionbarcompat.FadingActionBarHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import coid.progressgroup.cssurvey.fragment.QuestAutoFragment;
import coid.progressgroup.cssurvey.fragment.QuestDoctorFragment;
import coid.progressgroup.cssurvey.fragment.QuestFinishFragment;
import coid.progressgroup.cssurvey.fragment.QuestRegisterFragment;
import coid.progressgroup.cssurvey.fragment.VisitorPersonalFragment;
import coid.progressgroup.cssurvey.fragment.VisitorProgressFragment;
import coid.progressgroup.cssurvey.pageradapter.VisitPagerAdapter;

/**
 * Created by DEV01 on 13/01/2016.
 */
public class SurveyActivity extends AppCompatActivity {

    ArrayList<Fragment> ListFragment;

    private VisitPagerAdapter mPagerAdapter;

    private CustomViewPager viewPager;

    public ImageView ImgHeader;

    public Button BtnPrev;

    public Button BtnNext;

    public JSONArray m_jArry;

    public long visitorID;

    public String visitorUnique;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        super.setContentView(R.layout.pager_visit_form);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        this.initPager();

        //Log.i("FMY LOG", String.valueOf(idx));
    }

    private void initPager(){
        BtnPrev = (Button) findViewById(R.id.button_prev);
        BtnNext = (Button) findViewById(R.id.button_next);
//        ImgHeader = (ImageView) findViewById(R.id.img_header);

        BtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagerGoPrev();
            }
        });

        BtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pagerGoNext();

            }
        });

        ListFragment = new ArrayList<Fragment>();
        ListFragment.add(new VisitorPersonalFragment());
        ListFragment.add(new VisitorProgressFragment());
        try {
            JSONObject obj = new JSONObject(loadJson());
            m_jArry = obj.getJSONArray("Items");
            for (int i = 0; i < m_jArry.length(); i++) {
                JSONObject item = m_jArry.getJSONObject(i);
                QuestAutoFragment AutoFragment = new QuestAutoFragment();
                Bundle args = new Bundle();
                args.putInt("QuestAutoFragmentIndex", i);
                AutoFragment.setArguments(args);
                ListFragment.add(AutoFragment);
            }
            ListFragment.add(new QuestFinishFragment());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        this.mPagerAdapter  = new VisitPagerAdapter(super.getSupportFragmentManager(), ListFragment);
        viewPager = (CustomViewPager)super.findViewById(R.id.vpager_visit);
        viewPager.setAdapter(this.mPagerAdapter);
    }

    private String loadJson(){
        String json = null;
        try {
            InputStream is = getResources().openRawResource(R.raw.data);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }



    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    public void pagerGoNext(){
        int idx = getItem(+1);
        Log.i("LOG FEMMY LIST FRAGMENT",String.valueOf(idx)+"---------------"+String.valueOf(ListFragment.size()));
        if(idx == (ListFragment.size()-1)){
            BtnPrev.setVisibility(View.INVISIBLE);
            BtnNext.setVisibility(View.INVISIBLE);
//            ImgHeader.setVisibility(View.GONE);
        } else if(idx == 1){
            VisitorPersonalFragment mCurr = (VisitorPersonalFragment) mPagerAdapter.getCurrentFragment();
            mCurr.save();
        } else if(idx == 2){
            VisitorProgressFragment mCurr = (VisitorProgressFragment) mPagerAdapter.getCurrentFragment();
            mCurr.save();
        } else {
            QuestAutoFragment mCurr = (QuestAutoFragment) mPagerAdapter.getCurrentFragment();
            mCurr.save();
        }
        viewPager.setCurrentItem(idx, true);
    }

    public void pagerGoPrev(){
        viewPager.setCurrentItem(getItem(-1), true);

    }

}
