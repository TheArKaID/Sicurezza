package id.ac.umy.unires.sicurezza;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

import id.ac.umy.unires.sicurezza.adapters.PoinAdapter;
import id.ac.umy.unires.sicurezza.adapters.TengKoAdapter;
import id.ac.umy.unires.sicurezza.models.PoinModel;
import id.ac.umy.unires.sicurezza.models.TengKoModel;

import static id.ac.umy.unires.sicurezza.Resident.ADDPOINT;
import static id.ac.umy.unires.sicurezza.TankkoFragment.idsenior;
import static id.ac.umy.unires.sicurezza.utils.ServerAPI.CekTengKoURL;
import static id.ac.umy.unires.sicurezza.utils.ServerAPI.TambahPoinURL;

public class AddPoint extends AppCompatActivity {

    ProgressDialog progress;
    ArrayList<TengKoModel> tengKoModels;
    RecyclerView recyclerView;
    String idresident, keterangan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_point);
        Objects.requireNonNull(getSupportActionBar()).hide();
        idresident = Objects.requireNonNull(getIntent().getExtras()).getString("idresident");
        loadingBar();
        tengKoModels = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_addpoin);
        recyclerView.setHasFixedSize(true);
        loadTengKo();

        ItemClick.addTo(recyclerView).setmOnItemClickListener(new ItemClick.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                detailResident(tengKoModels.get(position).getId(), tengKoModels.get(position).getNama());
                showConfirmAddPoin(tengKoModels.get(position).getIdpelanggaran(), tengKoModels.get(position).getPoint());
            }
        });

    }

    private void adapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TengKoAdapter tengKoAdapter = new TengKoAdapter(this);
        tengKoAdapter.setTengKoModels(tengKoModels);
        recyclerView.setAdapter(tengKoAdapter);
    }

    private void loadingBar() {
        if(progress==null)
            progress = new ProgressDialog(this);
        progress.setMessage("Menambahkan Poin");
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.show();
    }

    private void loadTengKo() {
        StringRequest request = new StringRequest(Request.Method.POST, CekTengKoURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i < jsonArray.length(); i++){
                                TengKoModel model = new TengKoModel();
                                String object = jsonArray.getString(i);
                                JSONObject jsonObject = new JSONObject(object);
                                model.setNamapelanggaran(jsonObject.getString("penjelasan"));
                                model.setPoint(jsonObject.getString("poin"));
                                model.setIdpelanggaran(jsonObject.getString("idpelanggaran"));
                                tengKoModels.add(model);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AddPoint.this, "Gagal, " + ((e.getMessage()!=null) ? e.getMessage() : "Coba lagi."), Toast.LENGTH_SHORT).show();
                        }
                        adapter();
                        progress.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddPoint.this, "Gagal, " + ((error.getMessage()!=null) ? error.getMessage() : "Coba lagi."), Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("idsenior", idsenior);
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
    private void showConfirmAddPoin(final String idpelanggaran, String point) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Anda akan menambahkan Poin");
        builder.setMessage("Untuk Resident dengan ID "+idresident + " dengan Poin "+point+"\nTambahkan penjelasan");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.confirm_add_poin, (ViewGroup) getWindow().getDecorView(), false);

        final EditText input = viewInflated.findViewById(R.id.input);
        input.setHintTextColor(getResources().getColor(R.color.whiteMuh3));
        input.invalidate();
        builder.setView(viewInflated);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadingBar();
                keterangan = input.getText().toString();
                addPoin(idresident, idpelanggaran, keterangan);
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                progress.dismiss();
            }
        });

        builder.show();
    }

    private void addPoin(final String idresident, final String idpelanggaran, final String keterangan) {
        StringRequest request = new StringRequest(Request.Method.POST, TambahPoinURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("sukses")){
                            Toast.makeText(AddPoint.this, "Sukses", Toast.LENGTH_SHORT).show();
                            Intent backIntent = new Intent(AddPoint.this, Resident.class);
                            setResult(ADDPOINT, backIntent);
                            finish();
                        } else
                            Toast.makeText(AddPoint.this, response!=null?response:"gagal", Toast.LENGTH_SHORT).show();

                        progress.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddPoint.this, "Gagal: "+(error.getMessage()!=null?error.getMessage():"Fail"), Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("idsenior", idsenior);
                params.put("idresident", idresident);
                params.put("idpelanggaran", idpelanggaran);
                params.put("keterangan", keterangan);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
}
