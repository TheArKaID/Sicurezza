package id.ac.umy.unires.sicurezza.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import id.ac.umy.unires.sicurezza.R;
import id.ac.umy.unires.sicurezza.models.PoinModel;
import id.ac.umy.unires.sicurezza.models.TengKoModel;

public class PoinAdapter extends RecyclerView.Adapter<PoinAdapter.ViewHolder>  {

    private Context context;
    private ArrayList<PoinModel> poinModels;

    public PoinAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_tankko, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.nama.setText(getPoinAdapter().get(i).getNama());
        viewHolder.nokamar.setText(getPoinAdapter().get(i).getKamar());
        viewHolder.point.setText(getPoinAdapter().get(i).getPoin());
    }

    @Override
    public int getItemCount() {
        return getPoinAdapter().size();
    }

    public ArrayList<PoinModel> getPoinAdapter() {
        return poinModels;
    }

    public void setPoinAdapter(ArrayList<PoinModel> poinModels) {
        this.poinModels = poinModels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama, nokamar, point;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tv_Tankko);
            nokamar = itemView.findViewById(R.id.tv_pointResident);
            point = itemView.findViewById(R.id.tv_Point);
        }
    }
}
