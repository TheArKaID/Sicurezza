package id.ac.umy.unires.sicurezza;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.ac.umy.unires.sicurezza.models.PoinModel;
import id.ac.umy.unires.sicurezza.models.TengKoModel;

import static id.ac.umy.unires.sicurezza.utils.ServerAPI.CekResidentURL;

public class PointFragment extends Fragment {


    public PointFragment() {}


    ProgressDialog progress;
    ArrayList<PoinModel> poinModels;
    RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_point, container, false);

        loadingBar();
        poinModels = new ArrayList<>();
        recyclerView = view.findViewById(R.id.poin_recycler);
        recyclerView.setHasFixedSize(true);
        loadResident();

        return view;
    }

    private void loadResident() {
        StringRequest request = new StringRequest(Request.Method.POST, CekResidentURL,
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
                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(request);
    }


    private void loadingBar() {
        if(progress==null)
            progress = new ProgressDialog(getContext());
        progress.setMessage("Memeriksa Data Resident");
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.show();
    }

}
