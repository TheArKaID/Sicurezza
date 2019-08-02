package id.ac.umy.unires.sicurezza;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import id.ac.umy.unires.sicurezza.adapters.PoinDetailAdapter;
import id.ac.umy.unires.sicurezza.models.DetailPoinModel;

import static id.ac.umy.unires.sicurezza.utils.ServerAPI.CekDetailResidentURL;

public class Resident extends AppCompatActivity {

    ProgressDialog progress;
    String idresident, namaresident;
    ImageView ivOk;
    ArrayList<DetailPoinModel> detailPoinModels;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resident);
        Objects.requireNonNull(getSupportActionBar()).hide();
        hideSystemUI();
        loadingBar();

        TextView namaResident = findViewById(R.id.tv_DetailNamaReident);
        TextView idResident = findViewById(R.id.tv_DetailIDResident);
        ivOk = findViewById(R.id.iv_okay);

        idresident = Objects.requireNonNull(getIntent().getExtras()).getString("idresident");
        namaresident = Objects.requireNonNull(getIntent().getExtras()).getString("namaresident");

        detailPoinModels = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_detailpoin);
        recyclerView.setHasFixedSize(true);

        namaResident.setText(namaresident);
        idResident.setText(idresident);

        loadDataResident(idresident);

        ItemClick.addTo(recyclerView).setmOnItemClickListener(new ItemClick.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Resident.this)
                        .setMessage("Penjelasan : "+detailPoinModels.get(position).getPenjelasan()+"\nKeterangan : "+detailPoinModels.get(position).getKeterangan())
                        .setNeutralButton("Ok", null);
                builder.show();
            }
        });
    }

    private void loadDataResident(final String idresident) {
        StringRequest request = new StringRequest(Request.Method.POST, CekDetailResidentURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("null")){
                            if((System.currentTimeMillis()%2)==0){
                                ivOk.setImageResource(R.drawable.ic_custom_ok);
                            } else{
                                ivOk.setImageResource(R.drawable.ic_custom_ok_white);
                            }
                            ivOk.setVisibility(View.VISIBLE);
                        } else{
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for(int i = 0; i < jsonArray.length(); i++){
                                    DetailPoinModel model = new DetailPoinModel();
                                    String object = jsonArray.getString(i);
                                    JSONObject jsonObject = new JSONObject(object);
                                    model.setPenjelasan(jsonObject.getString("penjelasan"));
                                    model.setTanggal(jsonObject.getString("tanggalpoin"));
                                    model.setPoin(jsonObject.getString("poin"));
                                    model.setKeterangan(jsonObject.getString("keterangan"));
                                    detailPoinModels.add(model);
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Resident.this, "Gagal, " + ((e.getMessage()!=null) ? e.getMessage() : "Coba lagi."), Toast.LENGTH_SHORT).show();
                            }
                            adapter();
                        }
                        progress.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ResidentDetailError", error.getMessage()!=null?error.getMessage():"Error");
                        progress.dismiss();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("idsenior", TankkoFragment.idsenior);
                params.put("idresident", idresident);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }

    private void adapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PoinDetailAdapter poinDetailAdapter = new PoinDetailAdapter(this);
        poinDetailAdapter.setPoinDetailAdapter(detailPoinModels);
        recyclerView.setAdapter(poinDetailAdapter);
    }

    private void loadingBar() {
        if(progress==null)
            progress = new ProgressDialog(this);
        progress.setMessage("Memeriksa Data Resident");
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.show();
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
}
