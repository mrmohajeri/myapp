package com.example.nefrin.newprojectstartup;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Nefrin on 7/11/2017.
 */
public class QuesionsAdapter extends RecyclerView.Adapter<QuesionsAdapter.QuestionHolder> {
    Context context;
    List<myquestionstbl> mDataset;

    public QuesionsAdapter(Context context, List<myquestionstbl> mDataset) {
        this.context = context;
        this.mDataset = mDataset;
    }


    @Override
    public QuestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_1, parent, false);
        QuestionHolder dataObjectHolder = new QuestionHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(QuestionHolder holder, int position) {
        holder.fpart.setText(mDataset.get(position).firsPart);
        holder.spart.setText(mDataset.get(position).secondPart);

    }

    public void addItem(myquestionstbl dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void update(List<myquestionstbl> list) {
        mDataset = list;
        notifyDataSetChanged();
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class QuestionHolder extends RecyclerView.ViewHolder {

        TextView fpart, spart;

        public QuestionHolder(View itemView) {
            super(itemView);
            fpart = (TextView) itemView.findViewById(R.id.qfp);
            spart = (TextView) itemView.findViewById(R.id.qsp);
        }

    }
}