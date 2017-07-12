package com.example.nefrin.newprojectstartup;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class QuestionsFragment extends Fragment {
    private static TextView first_part, second_part, votes;
    private static Button push, not_push, next, report;
    LinearLayoutManager linearLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    AVLoadingIndicatorView avLoadingIndicatorView;
    String str;
    int q_id;
    int q_id_ = -1;
    private RecyclerView.LayoutManager layoutManager;
    public QuestionsFragment() {
    }

    public static void setFtext(String ftext) {
        first_part.setText(ftext);
    }

    public static void setSText(String sText) {
        second_part.setText(sText);
    }

    public static void setVotes(String vText) {
        votes.setText(vText);
    }

    public static void update_q(int q_id) {
        push.setEnabled(false);
        not_push.setEnabled(false);
        RequestParams params = new RequestParams();
        params.add("id", Integer.toString(q_id));
        NetUtils.refresh("app/refresh.php", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                try {
                    votes.setText("posetives: " + String.valueOf(response.getJSONObject(0).getInt("posetive")) + " negative: " + String.valueOf(response.getJSONObject(0).getInt("negative")));
                    votes.setVisibility(View.VISIBLE);
                    next.setVisibility(View.VISIBLE);
                    next.setEnabled(true);
                    report.setEnabled(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
                Log.d("Failed: ", "" + statusCode);
                Log.d("Error : ", "" + throwable);
                Log.d("response : ", "" + responseString);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_questions, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        report = (Button) view.findViewById(R.id.report);
        report.setEnabled(false);
        push = (Button) view.findViewById(R.id.push);
        not_push = (Button) view.findViewById(R.id.not_push);
        next = (Button) view.findViewById(R.id.next);
        next.setVisibility(View.INVISIBLE);
        next.setEnabled(false);
        avLoadingIndicatorView = (AVLoadingIndicatorView) view.findViewById(R.id.avi);
        avLoadingIndicatorView.smoothToShow();
        first_part = (TextView) view.findViewById(R.id.first_part);
        second_part = (TextView) view.findViewById(R.id.second_part);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadQuestion();
            }
        });
        votes = (TextView) view.findViewById(R.id.votes);
        loadQuestion();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadQuestion();
                first_part.setText("");
                second_part.setText("");
                votes.setText("");
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams params = new RequestParams();
                params.add("id", String.valueOf(q_id));
                NetUtils.post("app/update_posetive.php", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        update_q(q_id);


                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }
        });
        not_push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestParams params = new RequestParams();
                params.add("id", String.valueOf(q_id));
                NetUtils.post("app/update_negative.php", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        update_q(q_id);
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }
        });
        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialDialog.Builder(getActivity())
                        .title("Warning")
                        .content("Are you sure")
                        .canceledOnTouchOutside(true)
                        .positiveText("Yeah")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                reportq();
                            }
                        })
                        .negativeText("Nope")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                                dialog.cancel();
                            }
                        })
                        .show();

            }
        });
        return view;
    }

    private void reportq() {
        final MaterialDialog materialDialog;
        materialDialog = new MaterialDialog.Builder(getActivity())
                .title("Plz wait")
                .content("reporting question")
                .canceledOnTouchOutside(false)
                .progress(true, 0)
                .show();
        RequestParams params = new RequestParams();
        params.add("qid", String.valueOf(q_id));
        NetUtils.post("/app/report.php", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                materialDialog.dismiss();
                materialDialog.cancel();
                Toast.makeText(getActivity(), "Question Reported", Toast.LENGTH_SHORT).show();
                loadQuestion();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                materialDialog.dismiss();
                materialDialog.cancel();
                Log.d("Failed: ", "" + statusCode);
                Log.d("Error : ", "" + error);
                Log.d("response : ", "" + new String(responseBody));
            }
        });
    }

    private void loadQuestion() {
        NetUtils.get("app/", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONArray response) {
                if((avLoadingIndicatorView.isShown()))
                {
                    avLoadingIndicatorView.smoothToHide();
                }
                try {
                    push.setEnabled(true);
                    next.setVisibility(View.INVISIBLE);
                    next.setEnabled(false);
                    not_push.setEnabled(true);
                    votes.setVisibility(View.INVISIBLE);
                    q_id_ = response.getJSONObject(0).getInt("ID");
                    if (q_id != q_id_) {
                        first_part.setText(response.getJSONObject(0).getString("first_part"));
                        second_part.setText(response.getJSONObject(0).getString("second_part"));
                        votes.setText("posetives: " + String.valueOf(response.getJSONObject(0).getInt("posetive")) + " negative: " + String.valueOf(response.getJSONObject(0).getInt("negative")));
                        q_id = q_id_;
                        Log.e("load q_id: ", String.valueOf(q_id));
                        Log.e("load q_id_: ", String.valueOf(q_id_));
                    } else {
                        loadQuestion();
                    }

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
                alertDialog.show();
            }

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