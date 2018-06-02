package com.example.asus.panic;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    Button submit;
    EditText email,password,passwordagain;
    private FirebaseAuth Auths;
    private ProgressDialog mProgressBar;
    private android.support.v7.widget.Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mToolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        Auths = FirebaseAuth.getInstance();
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mProgressBar = new ProgressDialog(this);
        email = (EditText)findViewById(R.id.emailme);
        password =(EditText) findViewById(R.id.password);
        passwordagain =(EditText) findViewById(R.id.passwordagain);
        submit = (Button)findViewById(R.id.btn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myemail = email.getText().toString();
                String passwords = password.getText().toString();
                String passwordagains = passwordagain.getText().toString();
                Log.d("mm","menu" + myemail);
                Log.d("mm","menu" + passwordagains);
                Log.d("mm","menu" + passwords);
                if(new String(passwords).equals(passwordagains)){
                    mProgressBar.setTitle("Registering User");
                    mProgressBar.setMessage("Please Wait !");
                    mProgressBar.setCanceledOnTouchOutside(false);
                    mProgressBar.show();
                    Auths.createUserWithEmailAndPassword(myemail,passwordagains).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                mProgressBar.dismiss();
                                Intent mainIntent = new Intent(RegisterActivity.this,MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();

                            }else{
                                mProgressBar.hide();
                                Toast.makeText(RegisterActivity.this, "You Got Some Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(RegisterActivity.this, "Password Dont Match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
