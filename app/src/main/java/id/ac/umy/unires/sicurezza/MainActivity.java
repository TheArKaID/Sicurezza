package id.ac.umy.unires.sicurezza;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chaos.view.PinView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        hideSystemUI();

        pref = getApplicationContext().getSharedPreferences("id.ac.umy.unires.sicurezza", MODE_PRIVATE);

        Button btnsignin = findViewById(R.id.btnsignin);
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("pin", "1234");
                editor.apply();
                if(pref.getString("username", null)!=null && pref.getString("password", null)!=null){
                    if(pref.getString("pin", null)!=null){
                        PIN(pref.getString("pin", null));
                    }else{
                        Intent autoIntent = new Intent(MainActivity.this, NavPage.class);
                        autoIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(autoIntent);
                    }
                }else{
                    Intent intent = new Intent(MainActivity.this, PageSignIn.class);
                    startActivity(intent);
                }

            }
        });
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
    AlertDialog ad;
    private void PIN(final String pin) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.pin_view, (ViewGroup) getWindow().getDecorView(), false);

        final PinView pinView = viewInflated.findViewById(R.id.firstPinView);
        pinView.invalidate();
        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==4){
                    if(s.toString().equals(pin)){
                        Intent autoIntent = new Intent(MainActivity.this, NavPage.class);
                        autoIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(autoIntent);
                    } else{
                        Toast.makeText(MainActivity.this, "PIN yang anda Masukkan Salah", Toast.LENGTH_SHORT).show();
                        ad.dismiss();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        builder.setView(viewInflated);
        builder.setTitle("Masukkan PIN");
        builder.setMessage("atau Logout");
        builder.setNegativeButton("LogOut", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("pin", null);
                editor.putString("username", null);
                editor.putString("password", null);
                editor.apply();
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Anda telah Logout", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, PageSignIn.class);
                startActivity(intent);
            }
        });
        builder.setNeutralButton("Batal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        ad = builder.show();
        ad.show();
    }
}
