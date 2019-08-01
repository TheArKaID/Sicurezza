package id.ac.umy.unires.sicurezza;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class NavPage extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private TankkoFragment tankkoFragment;
    private RankFragment rankFragment;
    private PointFragment pointFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_page);
        getSupportActionBar().hide();

        mMainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mMainNav = (BottomNavigationView) findViewById(R.id.bottom_nav);

        tankkoFragment = new TankkoFragment();
        rankFragment = new RankFragment();
        pointFragment = new PointFragment();

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.navtankko :
                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        setFragment(tankkoFragment);
                        return true;

                    case R.id.navrank :
                        mMainNav.setItemBackgroundResource(R.color.colorAccent);
                        setFragment(rankFragment);
                        return true;

                    case R.id.navpoint :
                        mMainNav.setItemBackgroundResource(R.color.colorPrimaryDark);
                        setFragment(pointFragment);
                        return  true;

                        default:
                            return false;

                }
            }
        });
    }

    private void setFragment(Fragment fragment) {

        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();


    }
}


