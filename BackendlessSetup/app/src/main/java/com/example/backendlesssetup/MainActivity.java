package com.example.backendlesssetup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class MainActivity extends AppCompatActivity {
Person person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        person=new Person();
        person.setName("Sayed");
        person.setSurname("Hossain");
        Backendless.Data.of(Person.class).save(person, new AsyncCallback<Person>() {
            @Override
            public void handleResponse(Person response) {

            }

            @Override
            public void handleFault(BackendlessFault fault) {

            }
        });
    }
}