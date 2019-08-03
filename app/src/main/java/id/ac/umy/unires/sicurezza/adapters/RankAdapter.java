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
import id.ac.umy.unires.sicurezza.models.RankModel;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder>  {

    private Context context;
    private ArrayList<RankModel> rankModels;

    public RankAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_rank, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.nama.setText(getRankAdapter().get(i).getNama());
        viewHolder.point.setText(getRankAdapter().get(i).getPoin());
        viewHolder.usroh.setText(getRankAdapter().get(i).getUsroh());
    }

    @Override
    public int getItemCount() {
        return getRankAdapter().size();
    }

    private ArrayList<RankModel> getRankAdapter() {
        return rankModels;
    }

    public void setRankAdapter(ArrayList<RankModel> rankModels) {
        this.rankModels = rankModels;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nama, usroh, point;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tv_RankNama);
            usroh = itemView.findViewById(R.id.tv_RankUsroh);
            point = itemView.findViewById(R.id.tv_RankPoin);
        }
    }
}
