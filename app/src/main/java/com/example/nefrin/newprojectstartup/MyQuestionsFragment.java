package com.example.nefrin.newprojectstartup;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_my_questions, container, false);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        list = myquestionstbl.listAll(myquestionstbl.class); // انتخاب تمامی داده ها از دیتابیس با استفاده از SugarORM
        adapter = new QuesionsAdapter(getActivity(), list);
        recyclerView = (SuperRecyclerView) view.findViewById(R.id.q_rv);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {

            GestureDetector gestureDetector = new GestureDetector(getActivity(), new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent motionEvent) {
                    return true;
                }

            });
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                childview = rv.findChildViewUnder(e.getX(), e.getY());
                int temp = (list.get(rvpos).key);
                if (childview != null && gestureDetector.onTouchEvent(e)) {

                    rvpos = rv.getChildAdapterPosition(childview);

                    Toast.makeText(getActivity(), String.valueOf(list.get(rvpos).key), Toast.LENGTH_LONG).show();
                    QuestionsFragment.showq(temp);
                    MainActivity.svp(0);

                }

                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

}