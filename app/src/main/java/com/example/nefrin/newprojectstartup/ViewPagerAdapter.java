package com.example.nefrin.newprojectstartup;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nefrin on 6/23/2017.
 */


public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    int mNumOfTabs;
    public ViewPagerAdapter(FragmentManager manager,int NumOfTabs) {
        super(manager);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                QuestionsFragment tab1 = new QuestionsFragment();
                return tab1;
            case 1:
                AddQuestionFragment tab2 = new AddQuestionFragment();
                return tab2;
            case 2:
                MyQuestionsFragment tab3 = new MyQuestionsFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }
}
