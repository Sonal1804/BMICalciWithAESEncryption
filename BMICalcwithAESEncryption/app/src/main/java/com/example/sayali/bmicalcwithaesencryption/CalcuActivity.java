package com.example.sayali.bmicalcwithaesencryption;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CalcuActivity extends AppCompatActivity {

    TextView tvWelcome,tvFeet,tvInch, tvWeight, tvHeight;
    Spinner spnFeet, spnInch;
    EditText etWeight;
    Button btnCalculate, btnViewHistory;

    SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcu);

        tvWelcome=findViewById(R.id.tvWelcome);
        tvWeight=findViewById(R.id.tvWeight);
        tvFeet=findViewById(R.id.tvFeet);
        spnFeet=findViewById(R.id.spnFeet);
        tvInch=findViewById(R.id.tvInch);
        spnInch=findViewById(R.id.spnInch);
        tvHeight=findViewById(R.id.tvHeight);
        etWeight=findViewById(R.id.etWeight);
        btnCalculate=findViewById(R.id.btnCalculate);
        btnViewHistory=findViewById(R.id.btnViewHistory);


        sp=getSharedPreferences("myp3",MODE_PRIVATE);
        String n=sp.getString("name","");
        tvWelcome.setText("Welcome "+n);

        final ArrayList<String> feet=new ArrayList<>();
        feet.add("1");
        feet.add("2");
        feet.add("3");
        feet.add("4");
        feet.add("5");
        feet.add("6");
        feet.add("7");
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,feet);
        spnFeet.setAdapter(arrayAdapter);

        final ArrayList<String> inch=new ArrayList<>();
        inch.add("0");
        inch.add("1");
        inch.add("2");
        inch.add("3");
        inch.add("4");
        inch.add("5");
        inch.add("6");
        inch.add("7");
        inch.add("8");
        inch.add("9");
        inch.add("8");
        inch.add("9");
        inch.add("10");
        inch.add("11");
        ArrayAdapter arrayAdapter1=new ArrayAdapter(this,android.R.layout.simple_spinner_item,inch);
        spnInch.setAdapter(arrayAdapter1);

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int s1,s2;
                double s3, bmi;
                int p1=spnFeet.getSelectedItemPosition();
                String pfeet=feet.get(p1);
                int f=Integer.parseInt(pfeet);

                int p2=spnInch.getSelectedItemPosition();
                String pinch=inch.get(p2);
                int i=Integer.parseInt(pinch);

                s1=i/12;
                s2=f+s1;
                s3=s2/3.28;

                String k=etWeight.getText().toString();
                if (k.length()==0)
                {
                    etWeight.setError("Weight field is Empty");
                    etWeight.requestFocus();
                    return;
                }
                int kg=Integer.parseInt(k);

                bmi=kg/(s3*s3);
                double x=bmi;
                String BMI1=Double.toString(x);
                String BMI=String.format("%.2f",bmi);

                Intent a= new Intent(CalcuActivity.this,ResultActivity.class);
                a.putExtra("BMI",BMI);
                startActivity(a);




            }
        });

        /*Let's say that we want to convert our height to meters, but we're not 6 feet tall this time. Instead, we're 5 feet 10 inches. We would solve as follows:
        10 / 12 = .84
        5 + .84 = 5.84 feet total
        5.84 / 3.28 = 1.78 meters*/

        btnViewHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent a= new Intent(CalcuActivity.this,HistoryActivity.class);
                startActivity(a);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(R.id.aboutus==item.getItemId())
        {
            Toast.makeText(this, "App is made by Sonal", Toast.LENGTH_SHORT).show();
        }
        if(R.id.website==item.getItemId())
        {
            Intent a=new Intent(Intent.ACTION_VIEW);
            a.setData(Uri.parse("http://"+"www.google.com"));
            startActivity(a);
        }
        return super.onOptionsItemSelected(item);
    }
}
