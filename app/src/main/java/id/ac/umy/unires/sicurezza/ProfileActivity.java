package id.ac.umy.unires.sicurezza;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.angmarch.views.NiceSpinner;
import org.angmarch.views.OnSpinnerItemSelectedListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static id.ac.umy.unires.sicurezza.MainActivity.THEMEDARK;
import static id.ac.umy.unires.sicurezza.MainActivity.THEMELIGHT;
import static id.ac.umy.unires.sicurezza.MainActivity.UNITHEME;
import static id.ac.umy.unires.sicurezza.NavPage.idsenior;
import static id.ac.umy.unires.sicurezza.utils.ServerAPI.CekProfile;
import static id.ac.umy.unires.sicurezza.utils.ServerAPI.UpdateProfileURL;

public class ProfileActivity extends AppCompatActivity {

    TextView tvNama;
    EditText etNama, etPassword, etRepassword, etPasscode, etConfirmPass;
    String Nama, Password, Repassword, Passcode, ConfirmPass;
    Button btnSimpan;
    NiceSpinner spinTheme;

    boolean isChangePass = false;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ProgressDialog progress;
    String[] namasenior;
    MainActivity main = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(main.getSavedTheme(getApplicationContext()));
        setContentView(R.layout.activity_profile);
        Objects.requireNonNull(getSupportActionBar()).hide();

        tvNama = findViewById(R.id.tv_epNama);
        etNama = findViewById(R.id.et_epNama);
        etPassword = findViewById(R.id.et_epPassword);
        etPasscode = findViewById(R.id.et_epPasscode);
        etConfirmPass = findViewById(R.id.et_confirmpass);
        etRepassword = findViewById(R.id.et_repRePassword);
        btnSimpan = findViewById(R.id.btn_epSimpan);
        spinTheme = findViewById(R.id.spinTheme);

        List<String> dataset = new LinkedList<>(Arrays.asList("dark", "light", "unires", "Four", "Five"));
        spinTheme.attachDataSource(dataset);
        spinTheme.setSelectedIndex(getIndexSelectedTheme());
        spinTheme.setOnSpinnerItemSelectedListener(new OnSpinnerItemSelectedListener() {
            @Override
            public void onItemSelected(NiceSpinner parent, View view, int position, long id) {
                editor = pref.edit();
                switch (position){
                    case 0:
                        editor.putString("theme", THEMEDARK);
                        break;
                    case 1:
                        editor.putString("theme", THEMELIGHT);
                        break;
                    case 2:
                        editor.putString("theme", UNITHEME);
                        break;
                    default:
                        editor.putString("theme", THEMEDARK);
                }

                Toast.makeText(ProfileActivity.this, "Tema Diubah. "+parent.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
                restartApp();
                editor.apply();
            }
        });

        if(savedInstanceState!=null){
            namasenior = savedInstanceState.getStringArray("senior");
            adapter();
        }else{
            loadingBar("Mengecek Data");
            cekData(idsenior);
        }

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nama = etNama.getText().toString();
                Password = etPassword.getText().toString();
                Passcode = etPasscode.getText().toString();
                ConfirmPass = etConfirmPass.getText().toString();
                Repassword = etRepassword.getText().toString();

                loadingBar("Mengupdate Data");

                isChangePass = !TextUtils.isEmpty(Password);
                pref = getSharedPreferences("id.ac.umy.unires.sicurezza", MODE_PRIVATE);
                if(TextUtils.isEmpty(Nama)){
                    Toast.makeText(ProfileActivity.this, "Nama tidak boleh kosong", Toast.LENGTH_LONG).show();
                    progress.dismiss();
                } else if(TextUtils.isEmpty(ConfirmPass)) {
                    Toast.makeText(ProfileActivity.this, "Konfirmasi Password tidak boleh kosong", Toast.LENGTH_LONG).show();
                    progress.dismiss();
                } else {
                    if(!Password.equals(Repassword)){
                        Toast.makeText(ProfileActivity.this, "Password baru tidak sama", Toast.LENGTH_LONG).show();
                        progress.dismiss();
                    } else{
                        updateProfile(Nama, Password, ConfirmPass, isChangePass);
                    }
                }
            }
        });
    }

    private int getIndexSelectedTheme() {
        pref = getApplicationContext().getSharedPreferences("id.ac.umy.unires.sicurezza", MODE_PRIVATE);
        String currentTheme = pref.getString("theme", "dark");

        switch (Objects.requireNonNull(currentTheme)){
            case "dark":
                return 0;
            case "light":
                return 1;
            case "unires":
                return 2;
            default:
                return 0;
        }
    }

    private void restartApp() {
        Intent restartApp = new Intent(this, NavPage.class);
        restartApp.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(restartApp);
    }

    private void adapter() {
        tvNama.setText(namasenior[1]);
        etNama.setText(namasenior[0]);
    }

    private void cekData(final String idsenior) {
        StringRequest request = new StringRequest(Request.Method.POST, CekProfile,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        namasenior = response.split("\n");
                        namasenior[1]= response;
                        adapter();
                        progress.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfileActivity.this, error.getMessage()!=null?error.getMessage():"Fail. Refresh Needed", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("idsenior", idsenior);
                return params;
            }
        }
        ;
        Volley.newRequestQueue(this).add(request);
    }

    private void updateProfile(final String nama, final String password, final String confirmPass, final boolean isChangePass) {
        StringRequest request = new StringRequest(Request.Method.POST, UpdateProfileURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("berhasil")){
                            pref = getSharedPreferences("id.ac.umy.unires.sicurezza", MODE_PRIVATE);
                            editor = pref.edit();
                            editor.putString("password", password);
                            editor.apply();

                            if(!TextUtils.isEmpty(Passcode)){
                                editor = pref.edit();
                                if(Passcode.equals("0000")){
                                    editor.putString("pin", null);
                                    editor.apply();
                                    Toast.makeText(ProfileActivity.this, "PIN dihapus", Toast.LENGTH_SHORT).show();
                                }else{
                                    editor.putString("pin", Passcode);
                                    editor.apply();
                                    Toast.makeText(ProfileActivity.this, "PIN diperbaharui", Toast.LENGTH_SHORT).show();
                                }
                            }
                            tvNama.setText(nama);
                            etPasscode.setText("");
                            etConfirmPass.setText("");
                            etPassword.setText("");
                            etRepassword.setText("");
                            Toast.makeText(ProfileActivity.this, response, Toast.LENGTH_LONG).show();
                        } else{
                            Toast.makeText(ProfileActivity.this, response, Toast.LENGTH_LONG).show();
                        }
                        progress.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfileActivity.this, error.getMessage()!=null ? error.getMessage() : "Fail", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("idsenior", idsenior);
                params.put("nama", nama);
                params.put("password", password);
                params.put("confirmpass", confirmPass);
                params.put("ischangepass", String.valueOf(isChangePass));
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void loadingBar(String message) {
        if (progress == null)
            progress = new ProgressDialog(this);
        progress.setMessage(message);
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);

        progress.show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray("senior", namasenior);
    }
}
