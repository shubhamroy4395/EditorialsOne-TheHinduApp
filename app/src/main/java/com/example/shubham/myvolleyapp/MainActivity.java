package com.example.shubham.myvolleyapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
CardView cv1,cv2;
    TextView tvtitle1,tvtitle2,tvcontent1,tvcontent2;
    ProgressDialog pDialog;
    Button refresh;
    final int tabColor = Color.parseColor("#0F344F");
    String url = "https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Fwww.thehindu.com%2Fopinion%2Feditorial%2Ffeeder%2Fdefault.rss";
    String newurl = "http://www.thehindu.com/opinion/editorial/";
    String titleJson[]=new String[10];
    String contentJson[]=new String[10];
    String urlJson[]=new String[10];
    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvtitle1=(TextView)findViewById(R.id.texttitle1);
        tvtitle2=(TextView)findViewById(R.id.texttitle2);
        tvcontent1=(TextView)findViewById(R.id.textcontent1);
        tvcontent2=(TextView)findViewById(R.id.textcontent2);
        cv1=(CardView)findViewById(R.id.cardView1);
        cv2=(CardView)findViewById(R.id.cardView2);
        refresh= (Button)findViewById(R.id.refresh);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        makeJsonObjectRequest();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("A Reminder for Lazyass Baby a.k.a Nidhu AGG");
                alert.setMessage("\n\nThis app is created for Nidhu Agg so that she could shrug away her laziness and read worthwhile materials on the internet. She is a high maintainence GF and needs constant pampering. I hope she enjoys this app \n\n");
                alert.setPositiveButton("OK", null);
                alert.show();

            }
        });

        cv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(tabColor);
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(MainActivity.this, Uri.parse(urlJson[8]));
            }
        });

        cv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                builder.setToolbarColor(tabColor);
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(MainActivity.this, Uri.parse(urlJson[9]));
            }
        });



    }

    public void makeJsonObjectRequest() {
        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray news = response.getJSONArray("items");
                    for(int i=0;i<news.length();i++) {
                        JSONObject first = news.getJSONObject(i);
                        Log.e("myapp", "onResponse: "+first.getString("title"));
                            titleJson[i] = first.getString("title");
                            contentJson[i] = first.getString("description");
                            urlJson[i] = first.getString("url");

                    }
                    tvtitle1.setText(titleJson[8]);
                    tvtitle2.setText(titleJson[9]);
                    tvcontent1.setText(contentJson[8]);
                    tvcontent2.setText(contentJson[9]);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
        return;
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
