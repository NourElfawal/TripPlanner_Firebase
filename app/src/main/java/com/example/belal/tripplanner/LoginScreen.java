package com.example.belal.tripplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginScreen extends AppCompatActivity {



public  static String userName;
    Button loginBtn,registerBtn;
    EditText userNameEdit,passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        RegisterScreen.sharedPreferences=getSharedPreferences("myTxt",0);
       // Toast.makeText(this, ""+sharedPreferences.getString("username",""), Toast.LENGTH_SHORT).show();
        if(RegisterScreen.sharedPreferences.getString("username","").equals("")){
            onStart();
            onResume();
        }else {
            startActivity(new Intent(LoginScreen.this,ProfileNavigationDrawer.class));
            userName=RegisterScreen.sharedPreferences.getString("username","");
        }



        loginBtn= (Button) findViewById(R.id.loginBtn);
        registerBtn= (Button) findViewById(R.id.registerBtn);
        userNameEdit= (EditText) findViewById(R.id.usernameEdit);
        passwordEdit= (EditText) findViewById(R.id.passwordEdit);


        //Toast.makeText(this, ""+getSharedPreferences("myTxt",0).getString("username","***"), Toast.LENGTH_SHORT).show();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               startActivity(new Intent(LoginScreen.this,RegisterScreen.class));

            }
        });



     loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name=userNameEdit.getText().toString();
                String password=passwordEdit.getText().toString();


                RegisterScreen.sharedPreferences=getSharedPreferences("myTxt",0);
                if(RegisterScreen.sharedPreferences.getString("username","*****").equals(name)||
                        RegisterScreen.sharedPreferences.getString("password","*****").equals(password)){

                    Toast.makeText(LoginScreen.this, "Ok User", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginScreen.this,ProfileNavigationDrawer.class));

                }else{

                    Toast.makeText(LoginScreen.this, "Not Valid User", Toast.LENGTH_SHORT).show();

                }

//                DBAdapter db =new DBAdapter();
//                db.open(LoginScreen.this);
//                String x=  db.loginUserInfo(name, password);
                //Toast.makeText(LoginScreen.this, " x is  "+x, Toast.LENGTH_SHORT).show();

//                if(x.equals("ok")) {
//
//                  // Toast.makeText(LoginScreen.this, "Ok User", Toast.LENGTH_SHORT).show();
//                   sharedPreferences=getSharedPreferences("myTxt",0);
//                   SharedPreferences.Editor editor=sharedPreferences.edit();
//                   editor.putString("username",name);
//                   editor.putString("password",password);
//                   editor.commit();
//
//                   startActivity(new Intent(LoginScreen.this,ProfileNavigationDrawer.class));
//
//               }else {
//                   Toast.makeText(LoginScreen.this, "not Valid User", Toast.LENGTH_SHORT).show();
//
//               }



           //

        }
        });





    }



}
