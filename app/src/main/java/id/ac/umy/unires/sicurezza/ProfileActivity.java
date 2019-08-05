package id.ac.umy.unires.sicurezza;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static id.ac.umy.unires.sicurezza.NavPage.idsenior;
import static id.ac.umy.unires.sicurezza.utils.ServerAPI.UpdateProfileURL;

public class ProfileActivity extends AppCompatActivity {

    EditText etNama, etPassword, etRepassword, etPasscode, etConfirmPass;
    String Nama, Password, Repassword, Passcode, ConfirmPass;
    Button btnSimpan;
    boolean isChangePass = false;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Objects.requireNonNull(getSupportActionBar()).hide();

        etNama = findViewById(R.id.et_epNama);
        etPassword = findViewById(R.id.et_epPassword);
        etPasscode = findViewById(R.id.et_epPasscode);
        etConfirmPass = findViewById(R.id.et_confirmpass);
        etRepassword = findViewById(R.id.et_repRePassword);
        btnSimpan = findViewById(R.id.btn_epSimpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nama = etNama.getText().toString();
                Password = etPassword.getText().toString();
                Passcode = etPasscode.getText().toString();
                ConfirmPass = etConfirmPass.getText().toString();
                Repassword = etRepassword.getText().toString();

                loadingBar();

                isChangePass = !TextUtils.isEmpty(Password);
                pref = getSharedPreferences("id.ac.umy.unires.sicurezza", MODE_PRIVATE);
                if(TextUtils.isEmpty(Nama)){
                    Toast.makeText(ProfileActivity.this, "Nama tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else if(TextUtils.isEmpty(ConfirmPass)) {
                    Toast.makeText(ProfileActivity.this, "Password tidak boleh kosong", Toast.LENGTH_LONG).show();
                } else {
                    if(!Password.equals(Repassword)){
                        Toast.makeText(ProfileActivity.this, "Password baru tidak sama", Toast.LENGTH_LONG).show();
                    } else{
                        updateProfile(Nama, Password, ConfirmPass, isChangePass);
                    }
                }
            }
        });
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
                                SharedPreferences pref = getApplicationContext().getSharedPreferences("id.ac.umy.unires.sicurezza", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putString("pin", Passcode);
                                editor.apply();
                                Toast.makeText(ProfileActivity.this, "PIN diperbaharui", Toast.LENGTH_SHORT).show();
                            }

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

    private void loadingBar() {
        if (progress == null)
            progress = new ProgressDialog(this);
        progress.setMessage("Memeriksa Data Anda");
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);

        progress.show();
    }
}
