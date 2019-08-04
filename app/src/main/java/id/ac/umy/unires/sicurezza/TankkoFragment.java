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
import java.util.Objects;

import id.ac.umy.unires.sicurezza.adapters.TengKoAdapter;
import id.ac.umy.unires.sicurezza.models.TengKoModel;

import static id.ac.umy.unires.sicurezza.utils.ServerAPI.CekTengKoURL;

public class TankkoFragment extends Fragment {


    public static String idsenior = "U41A";
    ProgressDialog progress;
    ArrayList<TengKoModel> tengKoModels;
    RecyclerView recyclerView;
    public TankkoFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tankko, container, false);

        recyclerView = view.findViewById(R.id.tankko_recycler);
        recyclerView.setHasFixedSize(true);

        if (savedInstanceState != null) {
            tengKoModels = savedInstanceState.getParcelableArrayList("tengko");
            adapter();
        } else {
            loadingBar();
            tengKoModels = new ArrayList<>();
            loadTengKo();
        }

        return view;
    }

    private void loadTengKo() {
        StringRequest request = new StringRequest(Request.Method.POST, CekTengKoURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                TengKoModel model = new TengKoModel();
                                String object = jsonArray.getString(i);
                                JSONObject jsonObject = new JSONObject(object);
                                model.setNamapelanggaran(jsonObject.getString("penjelasan"));
                                model.setPoint(jsonObject.getString("poin"));
                                tengKoModels.add(model);
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
                params.put("idsenior", idsenior);
                return params;
            }
        };

        Volley.newRequestQueue(Objects.requireNonNull(getContext())).add(request);
    }

    private void adapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        TengKoAdapter tengKoAdapter = new TengKoAdapter(getContext());
        tengKoAdapter.setTengKoModels(tengKoModels);
        recyclerView.setAdapter(tengKoAdapter);
    }

    private void loadingBar() {
        if (progress == null)
            progress = new ProgressDialog(getContext());
        progress.setMessage("Memeriksa Data TengKo");
        progress.setCancelable(false);
        progress.setCanceledOnTouchOutside(false);

        progress.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);

        progress.show();

//        progress.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("tengko", tengKoModels);
    }

}
