package id.ac.umy.unires.sicurezza;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static id.ac.umy.unires.sicurezza.utils.ServerAPI.CekDetailResidentURL;

public class Resident extends AppCompatActivity {

    String idresident;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident);
        Objects.requireNonNull(getSupportActionBar()).hide();

        idresident = Objects.requireNonNull(getIntent().getExtras()).getString("idresident");

        loadDataResident(idresident);
    }

    private void loadDataResident(final String idresident) {
        StringRequest request = new StringRequest(Request.Method.POST, CekDetailResidentURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("idsenior", TankkoFragment.idsenior);
                params.put("idresident", idresident);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}
