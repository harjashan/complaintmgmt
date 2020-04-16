package com.example.again_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends AppCompatActivity {
     Button reg,history,rejected,pending;
    TextView emailfetch;
    String email,x;
    public static final String EXTRA_NEW="com.example.again_android.EXTRA_NEW";
    public static final String EXTRA_PEN="com.example.again_android.EXTRA_PEN";
    public static final String EXTRA_REJ="com.example.again_android.EXTRA_REJ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Intent intent=getIntent();
        String handL=intent.getStringExtra(LoginActivity.EXTRA_HANDSHAKE);
        String handS=intent.getStringExtra(SignupActivity.EXTRA_HANDSHAKE);
        String luser=intent.getStringExtra(LoginActivity.EXTRA_USER);
        String suser=intent.getStringExtra(SignupActivity.EXTRA_EMAIL);


        if(handL.equals("fromLogin"))
            email=luser;
        if(handS.equals("fromSign"))
            email=suser;

        emailfetch=findViewById(R.id.emailfetch);
        //handL=handS=luser=suser=null;
        reg=findViewById(R.id.register);
        history=findViewById(R.id.history);
        rejected=findViewById(R.id.rejectc);
        pending=findViewById(R.id.pendreq);


        emailfetch.setText(email);
        x=emailfetch.getText().toString();
        sendtoreg();

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this,RegisterformActivity.class);
                startActivity(intent);

            }
        });
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this,HistoryActivity.class);
                startActivity(intent);

            }
        });
        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this,RegisterformActivity.class);
                startActivity(intent);
                sendtohistpend();
            }
        });
        rejected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DashboardActivity.this,RegisterformActivity.class);
                startActivity(intent);
                sendtohistreject();
            }
        });
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        finish();
    }
    public void sendtoreg()
    {
        String fwd=emailfetch.getText().toString();
        Intent intent= new Intent(this, RegisterformActivity.class);
        intent.putExtra(EXTRA_NEW,fwd);
        startActivity(intent);
    }
    public void sendtohistpend(){
        String fwd="pendingc";
        Intent intent= new Intent(this, HistoryActivity.class);
        intent.putExtra(EXTRA_PEN,fwd);
        startActivity(intent);
    }
        public void sendtohistreject(){
            String fwd="rejectedc";
            Intent intent= new Intent(this, HistoryActivity.class);
            intent.putExtra(EXTRA_REJ,fwd);
            startActivity(intent);
        }
}
