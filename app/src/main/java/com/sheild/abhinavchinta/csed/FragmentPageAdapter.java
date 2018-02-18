package com.sheild.abhinavchinta.csed;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class FragmentPageAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[] { "Home", "Tasks", "Messages","Divisions" };
    public FragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }





    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        if (position == 0) {
            //Bundle args = new Bundle();
            //args.putInt("key",0);
            return new HomeFragment();
        } else if (position == 1){
            return new TasksFragment();
        } else if (position ==2){
            return new MessagesFragment();
        }
        else {return new BlankFragment();}
    }


    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
