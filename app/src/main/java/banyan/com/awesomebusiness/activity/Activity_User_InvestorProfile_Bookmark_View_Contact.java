package banyan.com.awesomebusiness.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import banyan.com.awesomebusiness.R;

/**
 * Created by Banyan on 07-Dec-17.
 */

public class Activity_User_InvestorProfile_Bookmark_View_Contact extends AppCompatActivity {

    private Toolbar mToolbar;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profiles);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Activity_UserProfile.class);
                startActivity(i);
                finish();
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new Activity_User_InvestorProfile_Bookmark_View_Contact.MyAdapter(getSupportFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });


    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Tab_User_InvestorProfile_Bookmarked();
                case 1:
                    return new Tab_User_InvestorProfile_Viewed();
                case 2:
                    return new Tab_User_InvestorProfile_Contacted();

            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {

                case 0:
                    return "Bookmarked";
                case 1:
                    return "Viewed";
                case 2:
                    return "Contacted";

            }
            return null;
        }
    }

}
