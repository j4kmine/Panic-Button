package com.example.asus.panic;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by ASUS on 4/21/2018.
 */

public class SectionPageAdapter  extends FragmentPagerAdapter {
    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                HelperFragment helperFragment =new HelperFragment();
                return helperFragment;
            case 1:
                MainFragment mainFragment = new MainFragment();
                return mainFragment;
            case 2:
                ThirdFragment thirdFragment = new ThirdFragment();
                return  thirdFragment;
            default:
                return  null;
        }


    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }



    @Override
    public int getCount() {
        return 3;
    }
    //magic method in giving name
    public  CharSequence getPageTitle(int position){
        switch (position){
            case 0:
                return "HELPER";
            case 1:
                return "MAIN";
            case 2:
                return "HOW TO";
            default:
                return null;
        }

    }
}
