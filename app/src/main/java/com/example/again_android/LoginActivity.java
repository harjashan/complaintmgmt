package com.example.again_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
      Button btnsignup,btnlogin;
        EditText txtemail,txtpassword;
    private FirebaseAuth firebaseAuth;

    public static final String EXTRA_USER="com.example.again_android.EXTRA_USER";
    public static final String EXTRA_HANDSHAKE="com.example.again_android.EXTRA_HANDSHAKE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivity);
        btnsignup=findViewById(R.id.signup);
        btnlogin=findViewById(R.id.login);
        txtemail=findViewById(R.id.editText);
        txtpassword=findViewById(R.id.editText2);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,SignupActivity.class);
                startActivity(intent);
            }
        });

        firebaseAuth=FirebaseAuth.getInstance();
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=txtemail.getText().toString().trim();
                String password=txtpassword.getText().toString().trim();
                boolean admin= txtemail.getText().toString().equalsIgnoreCase("admin@gmail.com");
                if (admin==true){
                    startActivity(new Intent(getApplicationContext(),admin.class));
                }



                if (TextUtils.isEmpty(email)){
                    txtemail.setError("Please enter email");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    txtpassword.setError("Please enter password");
                    return;
                }
                firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){

                                    startActivity(new Intent(getApplicationContext(),DashboardActivity.class));
                                }    else{
                                    Toast.makeText(LoginActivity.this,"Login failed ",Toast.LENGTH_LONG).show();
                                }


                            }
                        });
                sendtodash();
                sendtoregister();

            }
        });

    }
    public void sendtodash() {
        String email = txtemail.getText().toString();
        String handshake="fromLogin";
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra(EXTRA_USER, email);
        intent.putExtra(EXTRA_HANDSHAKE,handshake);
        startActivity(intent);
    }
        public void sendtoregister(){
            String email=txtemail.getText().toString();
            String handshake="fromLogin";

            Intent intent=new Intent(this,DashboardActivity.class);
            intent.putExtra(EXTRA_USER,email);
            intent.putExtra(EXTRA_HANDSHAKE,handshake);

            startActivity(intent);

    }
}

