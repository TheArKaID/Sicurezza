package id.ac.umy.unires.sicurezza;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import id.ac.umy.unires.sicurezza.adapters.PoinAdapter;
import id.ac.umy.unires.sicurezza.models.PoinModel;

import static id.ac.umy.unires.sicurezza.utils.ServerAPI.CekResidentURL;

public class PointFragment extends Fragment {


    public PointFragment() {}


    ProgressDialog progress;
    ArrayList<PoinModel> poinModels;
    RecyclerView recyclerView;
    public static int CEKDETAIL = 100;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point, container, false);

        recyclerView = view.findViewById(R.id.poin_recycler);
        recyclerView.setHasFixedSize(true);

        if(savedInstanceState!=null){
            poinModels = savedInstanceState.getParcelableArrayList("point");
            adapter();
        } else{
            loadingBar();
            poinModels = new ArrayList<>();
            loadResident();
        }

        ItemClick.addTo(recyclerView).setmOnItemClickListener(new ItemClick.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                detailResident(poinModels.get(position).getId(), poinModels.get(position).getNama());
            }
        });
        return view;
    }

    private void detailResident(String id, String nama) {
        Intent move = new Intent(getContext(), Resident.class);
        move.putExtra("idresident", id);
        move.putExtra("namaresident", nama);
        startActivityForResult(move, CEKDETAIL);
    }

    private void loadResident() {
        poinModels = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.POST, CekResidentURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for(int i = 0; i < jsonArray.length(); i++){
                                PoinModel model = new PoinModel();
                                String object = jsonArray.getString(i);
                                JSONObject jsonObject = new JSONObject(object);
                                model.setId(jsonObject.getString("idresident"));
                                model.setKamar(jsonObject.getString("idresident"));
                                model.setNama(jsonObject.getString("namaresident"));
                                model.setPoin(jsonObject.getString("poin"));
                                poinModels.add(model);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Gagal, " + ((e.getMessage()!=null) ? e.getMessage() : "Coba lagi."), Toast.LENGTH_SHORT).show();
                        }
                        adapter();
                        progress.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Gagal"+ ((error.getMessage()!=null) ? error.getMessage() : "Coba lagi."), Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();
                params.put("idsenior", TankkoFragment.idsenior);
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(request);
    }

    private void adapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        PoinAdapter poinAdapter = new PoinAdapter(getContext());
        poinAdapter.setPoinAdapter(poinModels);
        recyclerView.setAdapter(poinAdapter);
    }


    private void loadingBar() {
        if(progress==null)
            progress = new ProgressDialog(getContext());
        progress.setMessage("Memeriksa Data Resident");
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            loadingBar();
            loadResident();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("point", poinModels);
    }
}
