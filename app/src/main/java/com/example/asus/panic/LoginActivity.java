package com.example.asus.panic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
    Button submit;
    EditText email,password;
    private FirebaseAuth Auths;
    private ProgressDialog mProgressBar;
    private android.support.v7.widget.Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mToolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        Auths = FirebaseAuth.getInstance();
        getSupportActionBar().setTitle("Have An Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mProgressBar = new ProgressDialog(this);
        email = (EditText)findViewById(R.id.emailyo);
        password =(EditText) findViewById(R.id.passwordyo);
        submit = (Button)findViewById(R.id.btnyo);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Emaila = email.getText().toString();
                String Passworda = password.getText().toString();
                if(! TextUtils.isEmpty(Emaila) || !TextUtils.isEmpty(Passworda)){
                    mProgressBar.setTitle("Login");
                    mProgressBar.setMessage("Please Wait Checking Credentials");
                    mProgressBar.setCanceledOnTouchOutside(false);
                    mProgressBar.show();
                    Auths.signInWithEmailAndPassword(Emaila,Passworda).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                mProgressBar.dismiss();
                                Intent mainIntent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(mainIntent);
                                finish();
                            }else{
                                mProgressBar.hide();
                                Toast.makeText(LoginActivity.this,"can not sign in",Toast.LENGTH_LONG);
                            }
                        }
                    });

                }else{
                    Toast.makeText(LoginActivity.this,"check your input",Toast.LENGTH_LONG);
                }
            }
        });
    }
}
