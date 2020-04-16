package com.example.again_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
          Button btnsubmit;
    EditText nametext,txtemail,txtpassword,phonetext;
    private FirebaseAuth firebaseAuth;
    public static final String EXTRA_EMAIL="com.example.again_android.EXTRA_EMAIL";
    public static final String EXTRA_HANDSHAKE="com.example.again_android.EXTRA_HANDSHAKE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btnsubmit=findViewById(R.id.submit);
        Spinner datespin=findViewById(R.id.date);
        Spinner monthspin=findViewById(R.id.month);
        Spinner yearspin=findViewById(R.id.year);
        Spinner genderspin=findViewById(R.id.gender);
        nametext=findViewById(R.id.name);
        txtemail=(EditText) findViewById(R.id.email);
        txtpassword=(EditText) findViewById(R.id.password);
        phonetext=findViewById(R.id.mobile);


        ArrayAdapter adapter=ArrayAdapter.createFromResource(this,R.array.datestring,android.R.layout.simple_spinner_item);
         adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
         datespin.setAdapter(adapter);

        ArrayAdapter adaptermnth=ArrayAdapter.createFromResource(this,R.array.monthstring,android.R.layout.simple_spinner_item);
        adaptermnth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthspin.setAdapter(adaptermnth);

        ArrayAdapter adapteryr=ArrayAdapter.createFromResource(this,R.array.yearstring,android.R.layout.simple_spinner_item);
        adapteryr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearspin.setAdapter(adapteryr);

        ArrayAdapter adaptergn=ArrayAdapter.createFromResource(this,R.array.genderstring,android.R.layout.simple_spinner_item);
        adaptergn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderspin.setAdapter(adaptergn);
        firebaseAuth=FirebaseAuth.getInstance();

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=txtemail.getText().toString().trim();
                String password=txtpassword.getText().toString().trim();
                String name=nametext.getText().toString().trim();
                String phone=phonetext.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    txtemail.setError("Please enter email");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    txtpassword.setError("Please enter password");
                    return;
                }
                if (txtpassword.length()<=6){
                    txtpassword.setError("Password must be >=6 characters");
                    return;
                }
                if (TextUtils.isEmpty(name)){
                    nametext.setError("Please enter your name");
                    return;
                }
                if (TextUtils.isEmpty(phone)){
                    phonetext.setError("Please enter your mobile no.");
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                            Toast.makeText(SignupActivity.this, "Registration Complete", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(SignupActivity.this,"Authentication failed",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                openActivity2();
                openRegisterActivity();

            }
        });
    }

    public void openActivity2(){

        String email=txtemail.getText().toString();
        String handshake="fromSign";
        Intent intent=new Intent(this,DashboardActivity.class);
        intent.putExtra(EXTRA_EMAIL,email);
        intent.putExtra(EXTRA_HANDSHAKE,handshake);

        startActivity(intent);

    }
    public void openRegisterActivity() {
        String email = txtemail.getText().toString();
        String handshake="fromSign";
        Intent intent=new Intent(this,RegisterformActivity.class);
        intent.putExtra(EXTRA_EMAIL,email);
        intent.putExtra(EXTRA_HANDSHAKE,handshake);
        startActivity(intent);
    }
        @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
