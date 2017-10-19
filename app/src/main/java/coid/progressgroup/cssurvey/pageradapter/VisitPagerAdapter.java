package coid.progressgroup.cssurvey.pageradapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by DEV01 on 13/01/2016.
 */
public class VisitPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> ListFragment;

    private Fragment mCurrentFragment;

    public VisitPagerAdapter(FragmentManager fm,ArrayList<Fragment> ListFragment) {
        super(fm);
        this.ListFragment = ListFragment;
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            mCurrentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        return this.ListFragment.get(position);
    }

    @Override
    public int getCount() {
        return this.ListFragment.size();
    }


}