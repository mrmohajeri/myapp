package com.example.nefrin.newprojectstartup;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class MyQuestionsFragment extends Fragment {
    private static SuperRecyclerView recyclerView;
    private static QuesionsAdapter adapter;
    private static List<myquestionstbl> notes = new ArrayList<>();

    private static long initialCount;
    private static View view;
    int modifyPos = -1;
    public MyQuestionsFragment() {
    }

    public static void in(Context context) {
        List<myquestionstbl> q = myquestionstbl.listAll(myquestionstbl.class);
        recyclerView = (SuperRecyclerView) view.findViewById(R.id.q_rv);
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        gridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

        recyclerView.setLayoutManager(gridLayoutManager);
        initialCount = myquestionstbl.count(myquestionstbl.class);
        if (initialCount >= 0) {

            notes = myquestionstbl.listAll(myquestionstbl.class);

            adapter = new QuesionsAdapter(context, notes);
            recyclerView.setAdapter(adapter);

            if (notes.isEmpty())
                Snackbar.make(recyclerView, "No notes added.", Snackbar.LENGTH_LONG).show();

        }
    }

    public static void notifi() {
        adapter.notifyDataSetChanged();
        recyclerView.invalidate();
        adapter.notifyItemInserted(notes.size() + 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my_questions, container, false);
        in(getActivity());
        adapter.SetOnItemClickListener(new QuesionsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "item clicked " + String.valueOf(notes.get(position).getKey()), Toast.LENGTH_SHORT).show();
                QuestionsFragment.showq(notes.get(position).getKey());
                MainActivity.svp(0);

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}