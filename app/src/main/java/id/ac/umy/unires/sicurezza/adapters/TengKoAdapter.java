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
import id.ac.umy.unires.sicurezza.models.TengKoModel;

public class TengKoAdapter extends RecyclerView.Adapter<TengKoAdapter.ViewHolder>  {

    private Context context;
    private ArrayList<TengKoModel> tengKoModels;

    public TengKoAdapter(Context context) {
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
        viewHolder.namapelanggaran.setText(getTengKoModels().get(i).getNamapelanggaran());
        viewHolder.point.setText(getTengKoModels().get(i).getPoint());
    }

    @Override
    public int getItemCount() {
        return getTengKoModels().size();
    }

    public ArrayList<TengKoModel> getTengKoModels() {
        return tengKoModels;
    }

    public void setTengKoModels(ArrayList<TengKoModel> tengKoModels) {
        this.tengKoModels = tengKoModels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namapelanggaran, point;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            namapelanggaran = itemView.findViewById(R.id.tv_Tankko);
            point = itemView.findViewById(R.id.tv_Point);
        }
    }
}
