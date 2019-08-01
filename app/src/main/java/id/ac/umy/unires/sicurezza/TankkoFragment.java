package id.ac.umy.unires.sicurezza;


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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import id.ac.umy.unires.sicurezza.adapters.TengKoAdapter;
import id.ac.umy.unires.sicurezza.models.TengKoModel;

import static id.ac.umy.unires.sicurezza.utils.ServerAPI.CekTengKoURL;


/**
 * A simple {@link Fragment} subclass.
 */
public class TankkoFragment extends Fragment {


    public TankkoFragment() {
        // Required empty public constructor
    }

    String idsenior = "U41A";
    ArrayList<TengKoModel> tengKoModels;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tankko, container, false);
        tengKoModels = new ArrayList<>();
        recyclerView = view.findViewById(R.id.tankko_recycler);
        recyclerView.setHasFixedSize(true);
        loadTengKo();

        return view;
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
                                tengKoModels.add(model);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter();
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
                params.put("idsenior", idsenior);
                return params;
            }
        };

        Volley.newRequestQueue(getContext()).add(request);
    }

    private void adapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        TengKoAdapter tengKoAdapter = new TengKoAdapter(getContext());
        tengKoAdapter.setTengKoModels(tengKoModels);
        recyclerView.setAdapter(tengKoAdapter);
    }

}
