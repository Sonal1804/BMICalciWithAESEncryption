package com.example.sayali.bmicalcwithaesencryption;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {

    EditText etName, etAge, etPhoneNumber;
    RadioGroup rgGender;
    Button btnRegister;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName=findViewById(R.id.etName);
        etAge=findViewById(R.id.etAge);
        etPhoneNumber=findViewById(R.id.etPhoneNumber);
        rgGender=findViewById(R.id.rgGender);
        btnRegister=findViewById(R.id.btnRegister);

        sp=getSharedPreferences("myp3",MODE_PRIVATE);

        String n=sp.getString("name","");
        if (n.length()!=0){
            Intent a= new Intent(MainActivity.this,CalcuActivity.class);
            startActivity(a);
            finish();
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=etName.getText().toString();
                if(name.length()==0)
                {
                    etName.setError("Name field is empty");
                    etName.requestFocus();
                    return;
                }
                String age=etAge.getText().toString();
                if(age.length()==0)
                {
                    etAge.setError("Age field is empty");
                    etAge.requestFocus();
                    return;
                }
                String phone=etPhoneNumber.getText().toString();
                if((phone.length()<10 || phone.length()>10))
                {
                    etPhoneNumber.setError("Check Phone Number");
                    etPhoneNumber.requestFocus();
                    return;
                }

                SharedPreferences.Editor editor=sp.edit();
                editor.putString("name",name);
                editor.putString("age",age);
                editor.putString("phone",phone);
                editor.commit();

                Intent a= new Intent(MainActivity.this,CalcuActivity.class);
                startActivity(a);
                finish();


            }
        });

    }
}

