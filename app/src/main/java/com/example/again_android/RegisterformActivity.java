package com.example.again_android;


import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;

public class RegisterformActivity extends AppCompatActivity {
    EditText location, description, contact;
    TextView fromdate,todate,coidr;
    Button selectfrom,selectto,submit;
    RadioButton plumbing, mech, electrical, carpentary;
    Calendar c;
    String emailfrom;

    int arrayl;
    DatePickerDialog d;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_registerform);


                    Intent intent = getIntent();
                    String emailx=intent.getStringExtra(DashboardActivity.EXTRA_NEW);
                    emailfrom=emailx;
                    arrayl=0;


                    location=(EditText) findViewById(R.id.location);
                    description=(EditText)findViewById(R.id.description);
                    selectfrom=(Button)findViewById(R.id.fromdate);
                    selectto=(Button)findViewById(R.id.todate);
                    fromdate=(TextView)findViewById(R.id.textView2);
                    todate=(TextView)findViewById(R.id.textView);
                    submit=(Button)findViewById(R.id.submit);
                    contact=(EditText)findViewById(R.id.phone);
                    plumbing=(RadioButton)findViewById(R.id.radio2);
                    carpentary=(RadioButton)findViewById(R.id.radio1);
                    electrical=(RadioButton)findViewById(R.id.radio3);
                    mech=(RadioButton)findViewById(R.id.radio4);


                    /*getItems();
                    arrayl=registerlist.size();
                    String y= " "+arrayl;
                    todate.setText(y);
                        a= registerlist.get(arrayl);
                     String x=a.get("id");
                     coidr.setText("/////");*/

                    submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(v==submit)

                            addItemToSheet();
                        }
                     });
                //--------------------------------------date---------------------------------------------
                selectfrom.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int day,month,year;
                        c=Calendar.getInstance();
                        day=c.get(Calendar.DAY_OF_MONTH);
                        month=c.get(Calendar.MONTH)+1;
                        year=c.get(Calendar.YEAR);
                        d= new DatePickerDialog(RegisterformActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                fromdate.setText(dayOfMonth+"/"+ month +"/"+ year);
                            }
                        },day,month,year);
                        d.show();

                    }
                });
                selectto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int day,month,year;
                        c=Calendar.getInstance();
                        day=c.get(Calendar.DAY_OF_MONTH);
                        month=c.get(Calendar.MONTH);
                        year=c.get(Calendar.YEAR);
                        d=new DatePickerDialog(RegisterformActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                                todate.setText(dayOfMonth+"/"+month+"/"+year);
                            }
                        },day,month,year);
                        d.show();
                    }
                    });

    }
    //--------------------------------------------Sending Data--------------------------------------
    private void   addItemToSheet() {

        final ProgressDialog loading = ProgressDialog.show(this, "Registering Complaint", "Please wait");
        final String a_from = fromdate.getText().toString().trim();
        final String a_to = todate.getText().toString().trim();
        final String phoneno = contact.getText().toString().trim();
        final String loc = location.getText().toString().trim();
        final String desc = description.getText().toString().trim();
        final String emailid=emailfrom;
        String Type = "";
        /*Date calendar;
        calendar = Calendar.getInstance().getTime();
        SimpleDateFormat date = new SimpleDateFormat("dd/MM+/yyyy");
        final String cdate = date.format(calendar);*/

        if (plumbing.isChecked())
            Type = Type + "Plumbing ";
        if (carpentary.isChecked())
            Type = Type + "Carpentry ";
        if (electrical.isChecked())
            Type = Type + "Electrical ";
        if (mech.isChecked())
            Type = Type + "Masonry";
        final String Typ1 = Type;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycbx6hq8obB-Fn_XXSVIC5nrM8bHgm_sMAHnePvnGdWae2RI1AH0/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        loading.dismiss();
                        Toast.makeText(RegisterformActivity.this, response, Toast.LENGTH_LONG).show();
                        Intent nintent = new Intent(getApplicationContext(), DashboardActivity.class);
                        startActivity(nintent);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parmas = new HashMap<>();


                parmas.put("action", "addItem");
                parmas.put("type", Typ1);
                parmas.put("location", loc);
                parmas.put("description", desc);
                parmas.put("contact", phoneno);
                parmas.put("available_from",a_from);
                parmas.put("available_to", a_to);
                parmas.put("emailid",emailid);


                return parmas;
            }
        };

        int socketTimeOut = 60000;// 60 seconds

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }
    //-----------------------------------------------------------------------------------------------------------

    //-----------------------------------------------------------------------------------------------------------

}


