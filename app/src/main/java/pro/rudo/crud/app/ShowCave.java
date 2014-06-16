package pro.rudo.crud.app;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Toast;


public class ShowCave extends FragmentActivity implements ActionBar.TabListener, PicketsFragment.OnFragmentInteractionListener, EditOrDeleteDialog.EditOrDeleteDialogListener {

    CavePagerAdapter mCavePagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Топосъемки");
        setContentView(R.layout.activity_show_cave);
        mCavePagerAdapter = new CavePagerAdapter(getSupportFragmentManager());
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mCavePagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mCavePagerAdapter.getCount(); i++ ) {
            actionBar.addTab(actionBar.newTab()
                                      .setText(mCavePagerAdapter.getPageTitle(i))
                                      .setTabListener(this));
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onFragmentInteraction(String id) {
        Toast.makeText(this, "Wheee!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogClick(DialogFragment dialogFragment, int which) {
        Toast.makeText(this, (which == 0) ? "EDIT!" : "DELETE!", Toast.LENGTH_SHORT).show();

    }


    public static class CavePagerAdapter extends FragmentPagerAdapter {

        public CavePagerAdapter(FragmentManager fm) {
            super(fm);
        }

            @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new PicketsFragment();
                default:
                    return new CaveFragment();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position){
            String[] tabNames = {"Пикеты", "План"};
            return tabNames[position];
        }
    }
}
