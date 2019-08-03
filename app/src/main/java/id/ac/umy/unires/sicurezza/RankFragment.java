package id.ac.umy.unires.sicurezza;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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

import id.ac.umy.unires.sicurezza.adapters.PoinAdapter;
import id.ac.umy.unires.sicurezza.adapters.RankAdapter;
import id.ac.umy.unires.sicurezza.models.PoinModel;
import id.ac.umy.unires.sicurezza.models.RankModel;

public class RankFragment extends Fragment {

    ProgressDialog progress;
    ArrayList<RankModel> rankModels;
    RecyclerView recyclerView;

    public RankFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rank, container, false);

        loadingBar();
        rankModels = new ArrayList<>();
        recyclerView = view.findViewById(R.id.poin_recycler);
        recyclerView.setHasFixedSize(true);
        loadRanked();

        return view;
    }

    private void loadRanked() {
        StringRequest request = new StringRequest(Request.Method.POST, null,
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

    private void adapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        RankAdapter rankAdapter = new RankAdapter(getContext());
        rankAdapter.setRankAdapter(rankModels);
        recyclerView.setAdapter(rankAdapter);
    }

    private void loadingBar() {
        if(progress==null)
            progress = new ProgressDialog(getContext());
        progress.setMessage("Memeriksa Data Ranking");
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);
        progress.show();
    }

}
