package com.example.again_android;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class HistoryActivity extends AppCompatActivity {
    EditText searchid;
    ListView listView;
    ListAdapter adapter;
    ProgressDialog loading,load1;
    String searchidval;
    Button searchbtn;
    String d="";
    ArrayList<HashMap<String, String>> list=new ArrayList<>();
    ArrayList<HashMap<String, String>> searchlist=new ArrayList<>();
    HashMap<String, String> item;
    HashMap<String, String> a;
    String x,y;
    int arrayl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        arrayl = 0;
        listView = (ListView) findViewById(R.id.lv_items);

        Intent intent=getIntent();
        String pendingvar=intent.getStringExtra(DashboardActivity.EXTRA_PEN);
        String rejectedvar=intent.getStringExtra(DashboardActivity.EXTRA_REJ);
        /*x=pendingvar;
        y=rejectedvar;
        searchid.setText(x+"////");*/

            getItems();


        searchid = (EditText) findViewById(R.id.searchid);//searchid-bind->searchiblock
        searchbtn = (Button) findViewById(R.id.searchbtn);//searchbtn-bind->sbtn

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchlist.clear();

                searchidval = searchid.getText().toString();

                search(searchidval);
                searchidval="";

            }
        });

    }
   private void search(String searchvar)
    {
        for (int i = 0; i < arrayl; i++)
        {a= new HashMap<>();
            item= list.get(i);
            String var=item.get("id");

            if(var.equals(searchvar))
                {
                    listView.setAdapter(null);
                    String sdate=item.get("date");
                    String sloc=item.get("location");
                    String sdes=item.get("description");
                    String sstat=item.get("status");

                    a = new HashMap<>();
                    a.put("date", sdate);
                    a.put("id", var);
                    a.put("location", sloc);
                    a.put("description", sdes);
                    a.put("status", sstat);
                    searchlist.add(item);
                }
            load1=ProgressDialog.show(this,"Searching","Please wait",false,true);
            adapter = new SimpleAdapter(this,searchlist,R.layout.list_item_row,
                    new String[]{"id","location","description","date","status"},new int[]{
                    R.id.coidsettext,R.id.locationsettext,R.id.descriptionsettext,
                    R.id.datesettext,R.id.statussettext});

            listView.setAdapter(adapter);
            load1.dismiss();
             }

        }


    private void getItems()
    {
        loading=ProgressDialog.show(this,"Fetching","Please wait",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.googleusercontent.com/macros/echo?user_content_key=XMy19mya7TSmTQ3yJEjdKXjFfSbdZJgBUmkWUXplGrE_NVKGY2WGjPcAMFozlxDw2DxCGaK-2pLf2LJmtQ5nj7R7jGMP5AG4m5_BxDlH2jW0nuo2oDemN9CCS2h10ox_1xSncGQajx_ryfhECjZEnM98MjUysClUshhbm1X9h_Dv1fL4HydTEgcAdQ9Cz_GyXTRdebbOCtNOebqNacHRIRcmFEa6jifgYjHAbtibbXGGkCAV3NRZPw&lib=M0FSyxh8TYux2WSZU_YgdQUZaVBX25g54",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        int socketTimeOut = 60000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }
    void parseItems(String jsonResposnce)
    {
        try{
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("items");

            arrayl=jarray.length();
            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);
                String date = jo.getString("date");
                String coid = jo.getString("id");
                String loc = jo.getString("location");
                String desp = jo.getString("description");
                String status = jo.getString("status");

                item = new HashMap<>();
                item.put("date", date);
                item.put("id", coid);
                item.put("location", loc);
                item.put("description", desp);
                item.put("status", status);

                list.add(item);


            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
//-------------------------------------------------------------------------
         adapter = new SimpleAdapter(this,list,R.layout.list_item_row,
                new String[]{"id","location","description","date","status"},new int[]{
                R.id.coidsettext,R.id.locationsettext,R.id.descriptionsettext,
                R.id.datesettext,R.id.statussettext});
        listView.setAdapter(adapter);
        loading.dismiss();
    }

    //-------------------------------------------------------
    private void getPendItems() {
        loading = ProgressDialog.show(this, "Fetching", "Please wait", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.googleusercontent.com/macros/echo?user_content_key=XMy19mya7TSmTQ3yJEjdKXjFfSbdZJgBUmkWUXplGrE_NVKGY2WGjPcAMFozlxDw2DxCGaK-2pLf2LJmtQ5nj7R7jGMP5AG4m5_BxDlH2jW0nuo2oDemN9CCS2h10ox_1xSncGQajx_ryfhECjZEnM98MjUysClUshhbm1X9h_Dv1fL4HydTEgcAdQ9Cz_GyXTRdebbOCtNOebqNacHRIRcmFEa6jifgYjHAbtibbXGGkCAV3NRZPw&lib=M0FSyxh8TYux2WSZU_YgdQUZaVBX25g54",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parsePItems(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        int socketTimeOut = 60000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
    void parsePItems(String jsonResposnce)
    {
        try{
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("items");

            arrayl=jarray.length();
            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);
                String date = jo.getString("date");
                String coid = jo.getString("id");
                String loc = jo.getString("location");
                String desp = jo.getString("description");
                String status = jo.getString("status");
                if(status.equals("pending"))
                {
                    item = new HashMap<>();
                    item.put("date", date);
                    item.put("id", coid);
                    item.put("location", loc);
                    item.put("description", desp);
                    item.put("status", status);

                    list.add(item);
                }

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
//-------------------------------------------------------------------------
        adapter = new SimpleAdapter(this,list,R.layout.list_item_row,
                new String[]{"id","location","description","date","status"},new int[]{
                R.id.coidsettext,R.id.locationsettext,R.id.descriptionsettext,
                R.id.datesettext,R.id.statussettext});
        listView.setAdapter(adapter);
        loading.dismiss();
    }
    //-------------------------------------------------------
    private void getRejItems() {
        loading = ProgressDialog.show(this, "Fetching", "Please wait", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://script.googleusercontent.com/macros/echo?user_content_key=XMy19mya7TSmTQ3yJEjdKXjFfSbdZJgBUmkWUXplGrE_NVKGY2WGjPcAMFozlxDw2DxCGaK-2pLf2LJmtQ5nj7R7jGMP5AG4m5_BxDlH2jW0nuo2oDemN9CCS2h10ox_1xSncGQajx_ryfhECjZEnM98MjUysClUshhbm1X9h_Dv1fL4HydTEgcAdQ9Cz_GyXTRdebbOCtNOebqNacHRIRcmFEa6jifgYjHAbtibbXGGkCAV3NRZPw&lib=M0FSyxh8TYux2WSZU_YgdQUZaVBX25g54",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseRItems(response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        int socketTimeOut = 60000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
    void parseRItems(String jsonResposnce)
    {
        try{
            JSONObject jobj = new JSONObject(jsonResposnce);
            JSONArray jarray = jobj.getJSONArray("items");

            arrayl=jarray.length();
            for (int i = 0; i < jarray.length(); i++) {

                JSONObject jo = jarray.getJSONObject(i);
                String date = jo.getString("date");
                String coid = jo.getString("id");
                String loc = jo.getString("location");
                String desp = jo.getString("description");
                String status = jo.getString("status");
                if(status.equals("rejected"))
                {
                    item = new HashMap<>();
                    item.put("date", date);
                    item.put("id", coid);
                    item.put("location", loc);
                    item.put("description", desp);
                    item.put("status", status);

                    list.add(item);
                }

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
//-------------------------------------------------------------------------
        adapter = new SimpleAdapter(this,list,R.layout.list_item_row,
                new String[]{"id","location","description","date","status"},new int[]{
                R.id.coidsettext,R.id.locationsettext,R.id.descriptionsettext,
                R.id.datesettext,R.id.statussettext});
        listView.setAdapter(adapter);
        loading.dismiss();
    }
    }

