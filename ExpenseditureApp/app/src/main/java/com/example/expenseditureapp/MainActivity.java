package com.example.expenseditureapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText etEmail,etPassword;
    private Button btnLogin;
    private TextView tvForgetPasword,tvSingup;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();
        loginDetails();
    }

    private void loginDetails() {
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btnLogin=findViewById(R.id.btnLogin);
        tvForgetPasword=findViewById(R.id.tvForget);
        tvSingup=findViewById(R.id.tvSingUp);
        dialog=new ProgressDialog(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=etEmail.getText().toString();
                String password=etPassword.getText().toString();

               dialog.setMessage("Processing...");
               dialog.show();

                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                              dialog.dismiss();
                                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                                Toast.makeText(getApplicationContext(),"Login Successfully",Toast.LENGTH_LONG).show();

                            }else {
                                 dialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Login Failed",Toast.LENGTH_LONG).show();
                            }
                    }
                });
            }
        });

        tvForgetPasword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText resetMail=new EditText(view.getContext());
                final AlertDialog.Builder passwordResetDailog= new AlertDialog.Builder(view.getContext());
                passwordResetDailog.setTitle("Reset Password ?");
                passwordResetDailog.setMessage("Enter your email to received reset link.");
                passwordResetDailog.setView(resetMail);

                passwordResetDailog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String mail=resetMail.getText().toString();
                        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this,"Reset link sent to your email.",Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this,"Error ! Reset link is not sent."+e.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

                passwordResetDailog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                passwordResetDailog.create().show();
            }
        });

        tvSingup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegActivity2.class));
            }
        });
    }
}