package com.example.nefrin.newprojectstartup;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.malinskiy.superrecyclerview.swipe.SwipeDismissRecyclerViewTouchListener;

import java.util.List;

public class MyQuestionsFragment extends Fragment {
    private static SuperRecyclerView recyclerView;
    private static QuesionsAdapter adapter;
    private static List<myquestionstbl> list;
    private static long initialCount;
    private static View view;
    LinearLayoutManager linearLayoutManager; // لنیر لیوت منیجر برای تعریف ریسایکلر ویو بصورت لیست عمودی
    int modifyPos = -1;
    View childview;
    int rvpos;
    public MyQuestionsFragment() {
    }

    public static void up() {
        adapter.update(myquestionstbl.listAll(myquestionstbl.class));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my_questions, container, false);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        list = myquestionstbl.listAll(myquestionstbl.class); // انتخاب تمامی داده ها از دیتابیس با استفاده از SugarORM
        adapter = new QuesionsAdapter(getActivity(), list);
        recyclerView = (SuperRecyclerView) view.findViewById(R.id.q_rv);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                int temp = list.get(position).key;
                QuestionsFragment.showq(temp);
                MainActivity.svp(0);
            }
        }));
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                up();
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

}