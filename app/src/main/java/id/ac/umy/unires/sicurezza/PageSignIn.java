package id.ac.umy.unires.sicurezza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import static id.ac.umy.unires.sicurezza.utils.ServerAPI.LoginURL;

public class PageSignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_sign_in);
        getSupportActionBar().hide();

        EditText etUser = findViewById(R.id.etUsername);
        EditText etPass = findViewById(R.id.etPassword);
        final String Username = etUser.getText().toString();
        final String Password = etPass.getText().toString();

        Button btnsignin2 = findViewById(R.id.button2);
        btnsignin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signingIn(Username, Password);
            }
        });
    }

    private void signingIn(final String username, final String password) {

        StringRequest request = new StringRequest(Request.Method.POST, LoginURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Logged In")){
                            Intent intentLogin = new Intent(PageSignIn.this, NavPage.class);
                            intentLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intentLogin);
                        } else{
                            Toaster("Gagal, "+ response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toaster(error.getMessage()!=null?error.getMessage():"");
                        Log.d("ErrorSignIn", error.getMessage()!=null?error.getMessage():"");
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("username", username);
                hashMap.put("password", password);
                return hashMap;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void Toaster(String response) {
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
    }
}
