package id.ac.umy.unires.sicurezza;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;


public class NavPage extends AppCompatActivity {

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;

    private Fragment tankkoFragment;
    private Fragment rankFragment;
    private Fragment pointFragment;

    private TextView navTitle;

    SharedPreferences pref;
    SharedPreferences.Editor prefEdit;
    public static String idsenior;
    MainActivity main = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(main.getSavedTheme(getApplicationContext()));
        setContentView(R.layout.activity_nav_page);
        Objects.requireNonNull(getSupportActionBar()).hide();
        hideSystemUI();

        pref = getApplicationContext().getSharedPreferences("id.ac.umy.unires.sicurezza", MODE_PRIVATE);
        idsenior = pref.getString("username", null);

        mMainFrame = findViewById(R.id.main_frame);
        mMainNav = findViewById(R.id.bottom_nav);
        navTitle = findViewById(R.id.tv_TitleNav);

        ImageView logout = findViewById(R.id.iv_logout);
        ImageView setting = findViewById(R.id.iv_setting);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Setting();
            }
        });

        tankkoFragment = new TankkoFragment();
        rankFragment = new RankFragment();
        pointFragment = new PointFragment();

        if (savedInstanceState == null)
            setFragment(tankkoFragment, "Teng-Ko");

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navtankko:
                        setFragment(tankkoFragment, "Teng-Ko");
                        return true;

                    case R.id.navrank:
                        setFragment(rankFragment, "Ranking");
                        return true;

                    case R.id.navpoint:
                        setFragment(pointFragment, "Data Resident");
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void Setting() {
        Intent settingIntent = new Intent(this, ProfileActivity.class);
        startActivity(settingIntent);
    }

    private void Logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NavPage.this);
        AlertDialog confirmLogout = builder
                .setTitle("Logout")
                .setMessage("Anda akan logout ?\nSetelah ini anda harus masuk kembali untuk mengakses Sicurezza.")
                .setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                prefEdit = pref.edit();
                                prefEdit.putString("username", null);
                                prefEdit.putString("password", null);
                                prefEdit.apply();

                                Intent logoutIntent = new Intent(NavPage.this, PageSignIn.class);
                                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(logoutIntent);
                            }
                        })
                .setNeutralButton("Batal", null).create();
        confirmLogout.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        confirmLogout.show();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        confirmLogout.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        confirmLogout.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    private void setFragment(Fragment fragment, String title) {
        navTitle.setText(title);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hideSystemUI();
    }

    @Override
    public void onBackPressed() {
        keluar();
    }

    private void keluar() {
        AlertDialog.Builder builder = new AlertDialog.Builder(NavPage.this);
        AlertDialog confirmKeluar = builder
                .setTitle("Keluar")
                .setMessage("Anda akan keluar tanpa logout ?")
                .setPositiveButton("Ya",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finishAndRemoveTask();
                                System.exit(0);
                            }
                        })
                .setNeutralButton("Batal", null).create();
        confirmKeluar.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        confirmKeluar.show();
        int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        confirmKeluar.getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        confirmKeluar.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMainNav.getSelectedItemId() == R.id.navtankko)
            getSupportFragmentManager().putFragment(outState, "tengko", tankkoFragment);
        else if (mMainNav.getSelectedItemId() == R.id.navrank)
            getSupportFragmentManager().putFragment(outState, "rank", rankFragment);
        else if (mMainNav.getSelectedItemId() == R.id.navpoint)
            getSupportFragmentManager().putFragment(outState, "point", pointFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (mMainNav.getSelectedItemId() == R.id.navtankko) {
            tankkoFragment = getSupportFragmentManager().getFragment(savedInstanceState, "tengko");
            setFragment(tankkoFragment, "Teng-Ko");
        } else if (mMainNav.getSelectedItemId() == R.id.navrank) {
            rankFragment = getSupportFragmentManager().getFragment(savedInstanceState, "rank");
            setFragment(rankFragment, "The Most Valuable");
        } else if (mMainNav.getSelectedItemId() == R.id.navpoint) {
            pointFragment = getSupportFragmentManager().getFragment(savedInstanceState, "point");
            setFragment(pointFragment, "Tambah Data");
        }
    }
}


