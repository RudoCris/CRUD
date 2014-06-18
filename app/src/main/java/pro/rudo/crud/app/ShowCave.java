package pro.rudo.crud.app;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import pro.rudo.crud.app.model.Cave;
import pro.rudo.crud.app.model.Picket;


public class ShowCave extends FragmentActivity implements ActionBar.TabListener {

    CavePagerAdapter mCavePagerAdapter;
    ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Топосъемки");
        setContentView(R.layout.activity_show_cave);
        Intent intent = getIntent();
        int caveId = (Integer)intent.getExtras().get("caveId");
        mCavePagerAdapter = new CavePagerAdapter(getSupportFragmentManager(), caveId);
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


//    @Override
//    public void onDialogClick(DialogFragment dialogFragment, int which, int position) {
//        Toast.makeText(this, (which == 0) ? "EDIT!" : "DELETE!", Toast.LENGTH_SHORT).show();
//    }


    public static class CavePagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments = new ArrayList<Fragment>();

        public CavePagerAdapter(FragmentManager fm, int caveId) {
            super(fm);
            Bundle args = new Bundle();
            args.putInt("caveId", caveId);

            PicketsFragment picketsFragment = new PicketsFragment();
            CaveFragment caveFragment = new CaveFragment();

            picketsFragment.setArguments(args);
            caveFragment.setArguments(args);

            fragments.add(picketsFragment);
            fragments.add(caveFragment);
        }

        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
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
