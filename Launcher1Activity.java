package com.google.devrel.vrviewapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Launcher1Activity extends Activity {
    private EditText Input;
    Double x;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher1);
        Button button;

        Input =(EditText) findViewById(R.id.editText2);
        Input.setText("");

        String s= Input.getText().toString();
         if(s.length()!=0)
             x=Double.valueOf(s);
         else
             x=null;


             button = (Button) findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent= new Intent(Launcher1Activity.this,Main2Activity.class);
                startActivity(myIntent);
            }
        });
    }
}
