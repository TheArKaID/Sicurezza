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
import id.ac.umy.unires.sicurezza.models.DetailPoinModel;

public class PoinDetailAdapter extends RecyclerView.Adapter<PoinDetailAdapter.ViewHolder>  {

    private Context context;
    private ArrayList<DetailPoinModel> detailPoinModels;

    public PoinDetailAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.penjelasan.setText(getPoinDetailAdapter().get(i).getPenjelasan());
        viewHolder.tanggal.setText(getPoinDetailAdapter().get(i).getTanggal());
        viewHolder.poin.setText(getPoinDetailAdapter().get(i).getPoin());
    }

    @Override
    public int getItemCount() {
        return getPoinDetailAdapter().size();
    }

    private ArrayList<DetailPoinModel> getPoinDetailAdapter() {
        return detailPoinModels;
    }

    public void setPoinDetailAdapter(ArrayList<DetailPoinModel> detailPoinModels) {
        this.detailPoinModels = detailPoinModels;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView penjelasan, tanggal, poin;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            penjelasan = itemView.findViewById(R.id.tv_DetailPoinpenjelasan);
            tanggal = itemView.findViewById(R.id.tv_DetailPointanggal);
            poin = itemView.findViewById(R.id.tv_DetailPoinpoin);
        }
    }
}
