package com.xuan.design;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, HomeFragment.OnFragmentInteractionListener {

    @Bind(R.id.drawer_layout)
    DrawerLayout drawer_layout;
    @Bind(R.id.nav_view)
    NavigationView nav_view;
    @Bind(R.id.fl_content)
    FrameLayout fl_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        initView();

        initFragment(savedInstanceState);
    }

    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            HomeFragment currentFragment = new HomeFragment().newInstance();
            hideShowFragment(currentFragment);
        } else {
//            //activity销毁后记住销毁前所在页面，用于夜间模式切换
//            currentIndex = savedInstanceState.getInt(AppConstants.CURRENT_INDEX);
//            switch (this.currentIndex) {
//                case 0:
//                    currentFragment = new FristFragment();
//                    switchContent(currentFragment);
//                    break;
//                case 1:
//                    currentFragment = new SecondFragment();
//                    switchContent(currentFragment);
//                    break;
//                case 2:
//                    currentFragment = new ThirdFragment();
//                    switchContent(currentFragment);
//                    break;
//            }
        }
    }

    private FragmentTransaction transaction;

    private void initView() {
        ButterKnife.bind(this);

        nav_view.setNavigationItemSelectedListener(this);
    }


    public void initToolbar(Toolbar toolbar) {
        if (toolbar != null) {
            ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.open, R.string.close) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                }
            };
            mDrawerToggle.syncState();
            drawer_layout.addDrawerListener(mDrawerToggle);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        drawer_layout.closeDrawers();

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            item.setChecked(true);
            HomeFragment currentFragment = HomeFragment.newInstance();
            hideShowFragment(currentFragment);

        } else if (id == R.id.nav_gallery) {
            item.setChecked(true);
            HomeFragment currentFragment = HomeFragment.newInstance();
            hideShowFragment(currentFragment);

        } else if (id == R.id.nav_slideshow) {
            item.setChecked(true);
            HomeFragment currentFragment = HomeFragment.newInstance();
            hideShowFragment(currentFragment);

        } else if (id == R.id.nav_manage) {
            item.setChecked(true);
            HomeFragment currentFragment = HomeFragment.newInstance();
            hideShowFragment(currentFragment);

        } else if (id == R.id.nav_share) {
            item.setChecked(true);
            HomeFragment currentFragment = HomeFragment.newInstance();
            hideShowFragment(currentFragment);

        } else if (id == R.id.nav_send) {
            item.setChecked(true);
            HomeFragment currentFragment = HomeFragment.newInstance();
            hideShowFragment(currentFragment);

        }

        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void hideShowFragment(Fragment fragment) {
        transaction = getSupportFragmentManager().beginTransaction();

        transaction.add(R.id.fl_content, fragment);
        transaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.addToBackStack(null);

        if (getSupportFragmentManager().getFragments() != null && getSupportFragmentManager().getFragments().size() > 0) {
            for (Fragment child : getSupportFragmentManager().getFragments()) {
                if (child != null && child != fragment) {
                    if (child.isVisible()) {
                        transaction.hide(child);
                    }
                }
            }
        }

        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
