package com.example.expenseditureapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {
    private static final String tag="HomeActivity" ;
    private DatePickerDialog.OnDateSetListener dateSetListener;
private EditText etDateNo,etIncome,etExpense;
private Button btnBalance;
private TextView tvResults;
FirebaseDatabase firebaseDatabase;
DatabaseReference reference;
private Calendar cal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        etDateNo=findViewById(R.id.etDateNo);
        etIncome=findViewById(R.id.etIncome);
        etExpense=findViewById(R.id.etExpense);
        btnBalance=findViewById(R.id.btnBalance);
        tvResults=findViewById(R.id.tvResult);
        cal=Calendar.getInstance();
        final int year=cal.get(Calendar.YEAR);
        final int month=cal.get(Calendar.MONTH);
        final int day=cal.get(Calendar.DAY_OF_MONTH);

        etDateNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog=new DatePickerDialog(
                        HomeActivity.this,
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
                etDateNo.setText(date);
            }
        };

btnBalance.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference("balance");

        String date=etDateNo.getText().toString().trim();
        String income=etIncome.getText().toString().trim();
        String expense=etExpense.getText().toString().trim();
        String results="Date: "+date+"\n"+"income: "+income+"\n"+"expense: "+expense;
        UserHelper helper=new UserHelper(date,income,expense);
        reference.child(date).setValue(helper);
       tvResults.setText(results);
    }
});
    }
}