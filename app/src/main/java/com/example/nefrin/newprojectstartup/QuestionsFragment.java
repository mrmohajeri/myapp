package com.example.nefrin.newprojectstartup;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;

public class QuestionsFragment extends Fragment {
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    AVLoadingIndicatorView avLoadingIndicatorView;
    private RecyclerView.LayoutManager layoutManager;
    TextView first_part , second_part , votes;
    public QuestionsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questions, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        avLoadingIndicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        avLoadingIndicatorView.smoothToShow();
        first_part = (TextView) view.findViewById(R.id.first_part);
        second_part = (TextView) view.findViewById(R.id.second_part);
        votes = (TextView) view.findViewById(R.id.votes);
        loadData();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                first_part.setText("");
                second_part.setText("");
                votes.setText("");
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    private void loadData() {
        NetUtils.get("app/", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                if((avLoadingIndicatorView.isShown()))
                {
                    avLoadingIndicatorView.smoothToHide();
                }
                try {
                    first_part.setText(response.getJSONObject(0).getString("first_part"));
                    second_part.setText(response.getJSONObject(0).getString("second_part"));
                    votes.setText("posetives: "+String.valueOf(response.getJSONObject(0).getInt("posetive")) + " negative: " + String.valueOf(response.getJSONObject(0).getInt("negative")));
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers,Throwable throwable , JSONObject jsonObject){
                super.onFailure(statusCode,headers,throwable,jsonObject);
                Log.d("Failed: ", ""+statusCode);
                Log.d("Error : ", "" + throwable);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Connection Problem");
                builder.setNegativeButton("0k", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        dialogInterface.cancel();
                    }
                });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Toast.makeText(getActivity(),"On canceeled called",Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                avLoadingIndicatorView.smoothToHide();
                alertDialog.show();            }

            @Override
            public void onFailure(int statusCode, Header[] headers,String responseString , Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("Failed: ", "" + statusCode);
                Log.d("Error : ", "" + throwable);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Connection Problem");
                builder.setNegativeButton("0k", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        dialogInterface.cancel();
                    }
                });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Toast.makeText(getActivity(),"On canceeled called",Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = builder.create();
                avLoadingIndicatorView.smoothToHide();
                alertDialog.show();
            }
        });
    }
}