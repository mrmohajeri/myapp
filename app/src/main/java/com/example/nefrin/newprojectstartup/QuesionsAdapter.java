package com.example.nefrin.newprojectstartup;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nefrin on 7/11/2017.
 */
public class QuesionsAdapter extends RecyclerView.Adapter<QuesionsAdapter.NoteVH> {
    Context context;
    List<myquestionstbl> notes;

    public QuesionsAdapter(Context context, List<myquestionstbl> notes) {
        this.context = context;
        this.notes = notes;

    }


    @Override
    public NoteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_1, parent, false);
        NoteVH viewHolder = new NoteVH(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NoteVH holder, int position) {

        holder.fp.setText(notes.get(position).getTitle());
        holder.sp.setText(notes.get(position).getNote());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView fp, sp;

        public NoteVH(View itemView) {
            super(itemView);

            fp = (TextView) itemView.findViewById(R.id.qfp);
            sp = (TextView) itemView.findViewById(R.id.qsp);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
