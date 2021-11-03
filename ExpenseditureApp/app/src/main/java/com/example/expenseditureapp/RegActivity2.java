package com.example.expenseditureapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.Calendar;

public class RegActivity2 extends AppCompatActivity {
private static final String tag="RegActivity2" ;
private DatePickerDialog.OnDateSetListener dateSetListener;
private EditText etName,etNumber,etEmail,etPassword,etDob;
private Button btnRegister;
private TextView tvSignin;
private Calendar cal;
private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg2);
        registartion();
    }

    private void registartion() {
        etName=findViewById(R.id.etName);
        etNumber=findViewById(R.id.etPhoneNumber);
        etDob=findViewById(R.id.etDob);
        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btnRegister=findViewById(R.id.btnReg);
        tvSignin=findViewById(R.id.tvSignin);
         cal=Calendar.getInstance();
         final int year=cal.get(Calendar.YEAR);
        final int month=cal.get(Calendar.MONTH);
        final int day=cal.get(Calendar.DAY_OF_MONTH);
        firebaseAuth=FirebaseAuth.getInstance();
        etDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog=new DatePickerDialog(
                        RegActivity2.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        dateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Log.d(tag,"onDateSet: mm/dd/yyy: "+ month+"/"+day+"/"+year);
                String date=month + "/" + day+ "/" +year;
                etDob.setText(date);
            }
        };
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=etName.getText().toString();
                String number=etNumber.getText().toString();
                String email=etEmail.getText().toString();
                String password=etPassword.getText().toString();
                String dob=etDob.getText().toString();
                if(TextUtils.isEmpty(dob)){
                    etName.setError("Date of Birth is required");
                    etName.requestFocus();
                    return;
                }
                if(number.length()>10){
                    etNumber.setError("Number is not valid");
                    etNumber.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(name)){
                    etName.setError("Name is required");
                    etName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(number)){
                    etNumber.setError("Number is required");
                    etNumber.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
                {
                    etEmail.setError("Valid Email is required");
                    etEmail.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    etEmail.setError("Email is required");
                    etEmail.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    etPassword.setError("Password is required");
                    etEmail.requestFocus();
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "User Account created", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(getApplicationContext(), "User Already existes", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else
                            {
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
    }
}