package id.ac.umy.unires.sicurezza;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

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
        ImageView logout = findViewById(R.id.iv_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });

        tankkoFragment = new TankkoFragment();
        rankFragment = new RankFragment();
        pointFragment = new PointFragment();

        mMainNav.setItemBackgroundResource(R.color.colorPrimaryDark);
        setFragment(tankkoFragment, "Teng-Ko");

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.navtankko :
                        setFragment(tankkoFragment, "Teng-Ko");
                        return true;

                    case R.id.navrank :
                        setFragment(rankFragment, "The Most Valuable");
                        return true;

                    case R.id.navpoint :
                        setFragment(pointFragment, "Tambah Poin");
                        return  true;

                        default:
                            return false;

                }
            }
        });
    }

    private void Logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Anda akan keluar ?")
                .setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("id.ac.umy.unires.sicurezza", MODE_PRIVATE);
                                SharedPreferences.Editor prefEdit = pref.edit();
                                prefEdit.putString("username", null);
                                prefEdit.putString("password", null);
                                prefEdit.apply();

                                Intent logoutIntent = new Intent(NavPage.this, PageSignIn.class);
                                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(logoutIntent);
                            }
                        })
                .setNeutralButton("Batal",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
        builder.show();
    }

    private void setFragment(Fragment fragment, String title) {
        TextView navTitle = findViewById(R.id.tv_TitleNav);
        navTitle.setText(title);
        FragmentTransaction fragmentTransaction =getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();


    }
}


