package com.example.nefrin.newprojectstartup;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import org.json.JSONArray;

import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class AddQuestionFragment extends Fragment {
    TextView first__part, second__part;
    Button send;
    String ftxt, stxt;
    int key;
    MaterialDialog materialDialog;
    public AddQuestionFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_question, container, false);
        send = (Button) view.findViewById(R.id.send_q);
        first__part = (TextView) view.findViewById(R.id.first__part);
        second__part = (TextView) view.findViewById(R.id.second__part);
        int min = 0;
        int max = 100;
        Random r = new Random();
        final int i1 = r.nextInt(max - min + 1) + min;
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestParams params = new RequestParams();
                ftxt = first__part.getText().toString();
                stxt = second__part.getText().toString();
                params.add("f_p", ftxt);
                params.add("s_p", stxt);
                if (valid()) {
                    materialDialog = new MaterialDialog.Builder(getActivity())
                            .title("Plz wait")
                            .content("sending question")
                            .canceledOnTouchOutside(false)
                            .progress(true, i1)
                            .show();
                    NetUtils.post("/app/add.php", params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            Log.e("Repo ", new String(responseBody));
                            String repo = new String(responseBody);
                            try {
                                JSONArray rep = new JSONArray(repo);
                                key = rep.getJSONObject(0).getInt("ID");
                                Toast.makeText(getActivity(), new String(responseBody), Toast.LENGTH_LONG).show();
                                materialDialog.dismiss();
                                materialDialog.cancel();
                                QuestionsFragment.setFtext(ftxt);
                                QuestionsFragment.setSText(stxt);
                                QuestionsFragment.setVotes("Set votes text");
                                myquestionstbl myquestionstbl = new myquestionstbl(ftxt, stxt, key);
                                myquestionstbl.save();
                                Log.i("database ", String.valueOf(myquestionstbl.save()));
                                QuestionsFragment.showq(key);
                                MainActivity.svp(0);
                                MyQuestionsFragment.up();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            materialDialog.dismiss();
                            materialDialog.cancel();
                            Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                            Log.d("Failed: ", "" + statusCode);
                            Log.d("Error : ", "" + error);

                        }
                    });
                } else {
                    Log.e("Return ", "false");
                    new MaterialDialog.Builder(getActivity())
                            .title("Error")
                            .content("At least 15 chars")
                            .canceledOnTouchOutside(false)
                            .positiveText("0k")
                            .show();
                }
            }
        });
        return view;
    }

    public boolean valid() {
        int flength = first__part.getText().length();
        int slength = second__part.getText().length();
        Log.i("length f ", String.valueOf(flength));
        Log.i("length s ", String.valueOf(slength));
        if ((flength < 15) || (slength < 15)) {
            return false;
        } else {
            return true;
        }

    }

}