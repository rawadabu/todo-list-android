package com.example.to_dolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText RegistrationEmail,RegistrationPwd;
    private Button RegistrationBtn;
    private TextView RegistrationQn;
    private FirebaseAuth mAuth;
    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);

        RegistrationEmail = findViewById(R.id.RegistrationEmail);
        RegistrationPwd = findViewById(R.id.RegistrationPassword);
        RegistrationBtn = findViewById(R.id.RegistrationButton);
        RegistrationQn = findViewById(R.id.RegistrationPageQuestion);

        RegistrationQn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        RegistrationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = RegistrationEmail.getText().toString().trim();
                String password = RegistrationPwd.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    RegistrationEmail.setError("Required input!");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    RegistrationPwd.setError("Required input!");
                    return;
                }else{
                    loader.setMessage("Registration in progress!");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(RegistrationActivity.this,HomeActivity.class);
                                startActivity(intent);
                                finish();
                                loader.dismiss();
                            }else {
                                String error = task.getException().toString();
                                Toast.makeText(RegistrationActivity.this,"Registration has been failed" + error,Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }
                        }
                    });
                }


            }
        });
    }
}