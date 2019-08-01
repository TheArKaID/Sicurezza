package id.ac.umy.unires.sicurezza;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.util.Objects;

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
        Objects.requireNonNull(getSupportActionBar()).hide();

        mMainFrame = findViewById(R.id.main_frame);
        mMainNav = findViewById(R.id.bottom_nav);

        tankkoFragment = new TankkoFragment();
        rankFragment = new RankFragment();
        pointFragment = new PointFragment();

        mMainNav.setItemBackgroundResource(R.color.colorPrimaryDark);
        setFragment(tankkoFragment);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.navtankko :
                        setFragment(tankkoFragment);
                        return true;

                    case R.id.navrank :
                        setFragment(rankFragment);
                        return true;

                    case R.id.navpoint :
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


