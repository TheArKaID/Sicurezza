package id.ac.umy.unires.sicurezza;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

class ItemClick {

    private final RecyclerView recyclerView;
    private OnItemClickListener mOnItemClickListener;

    private ItemClick(RecyclerView rview) {
        this.recyclerView = rview;
        this.recyclerView.setTag(R.id.click, this);
        RecyclerView.OnChildAttachStateChangeListener stateChangeListener = new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                if (mOnItemClickListener != null)
                    view.setOnClickListener(onClickListener);
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {

            }
        };
        this.recyclerView.addOnChildAttachStateChangeListener(stateChangeListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null){
                RecyclerView.ViewHolder holder = recyclerView.getChildViewHolder(v);
                mOnItemClickListener.onItemClicked(recyclerView, holder.getAdapterPosition(), v);
            }
        }
    };

    static ItemClick addTo(RecyclerView view){
        ItemClick clicked = (ItemClick) view.getTag(R.id.click);
        if(clicked == null){
            clicked = new ItemClick(view);
        }
        return clicked;
    }

    void setmOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClicked(RecyclerView recyclerView, int position, View v);
    }
}
