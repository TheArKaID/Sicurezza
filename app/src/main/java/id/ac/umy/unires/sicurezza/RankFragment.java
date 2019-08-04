package id.ac.umy.unires.sicurezza;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

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

import id.ac.umy.unires.sicurezza.adapters.RankAdapter;
import id.ac.umy.unires.sicurezza.models.RankModel;

import static id.ac.umy.unires.sicurezza.utils.ServerAPI.CekRankURL;

public class RankFragment extends Fragment {

    ProgressDialog progress;
    ArrayList<RankModel> rankModels;
    RecyclerView recyclerView;

    public RankFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rank, container, false);

        recyclerView = view.findViewById(R.id.fragment_rank);
        recyclerView.setHasFixedSize(true);

        if (savedInstanceState != null) {
            rankModels = savedInstanceState.getParcelableArrayList("rank");
            adapter();
        } else {
            loadingBar();
            rankModels = new ArrayList<>();
            loadRanked();
        }


        return view;
    }

    private void loadRanked() {
        StringRequest request = new StringRequest(Request.Method.POST, CekRankURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                RankModel model = new RankModel();
                                String object = jsonArray.getString(i);
                                JSONObject jsonObject = new JSONObject(object);
                                model.setNama(jsonObject.getString("namaresident"));
                                model.setPoin(jsonObject.getString("poin"));
                                model.setUsroh(jsonObject.getString("namausroh"));
                                rankModels.add(model);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Gagal, " + ((e.getMessage() != null) ? e.getMessage() : "Coba lagi."), Toast.LENGTH_SHORT).show();
                        }
                        adapter();
                        progress.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Gagal, " + ((error.getMessage() != null) ? error.getMessage() : "Coba lagi."), Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                }) {
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
        RankAdapter rankAdapter = new RankAdapter(getContext());
        rankAdapter.setRankAdapter(rankModels);
        recyclerView.setAdapter(rankAdapter);
    }

    private void loadingBar() {
        if (progress == null)
            progress = new ProgressDialog(getContext());
        progress.setMessage("Memeriksa Data Ranking");
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);

        progress.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        progress.show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("rank", rankModels);
    }
}
