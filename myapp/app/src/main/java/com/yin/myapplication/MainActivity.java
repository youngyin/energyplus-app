package com.yin.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.yin.myapplication.databinding.ActivityMainBinding;
import com.yin.myapplication.db.BaseUrl;
import com.yin.myapplication.db.UserDBManager;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    static Context context;
    private NameViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the ViewModel.
        model = new ViewModelProvider(this).get(NameViewModel.class);

        // Create the observer which updates the UI.
        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String newName) {
                // Update the UI, in this case, a TextView.
                // check login
                if (newName.contains("1") && newName.contains("count(*)")){
                    s_login();
                } else {
                    f_login();
                }

                //binding.connect.setText(newName);
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        model.getCurrentName().observe(this, nameObserver);

        // SharedPreferences - user
        context = getApplicationContext();
        UserDBManager userDBManager = new UserDBManager(context);

        // ??? ?????????
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);

        binding.buttonLogin.setOnClickListener(v->{
            String mUrl = BaseUrl.getBaseUrl() +  "/api/login/"+
                    binding.userId.getText().toString()+"/" +
                    binding.userPw.getText().toString() + "/" +
                    binding.userPhone.getText().toString();
            try {
                new JSONTask().execute(mUrl);

            } catch (Exception e){

            }
        });

        // init view
        binding.userId.setText(UserDBManager.getUser_id());
        binding.userPw.setText(UserDBManager.getUser_pw());
        binding.userPhone.setText(UserDBManager.getPhone());
    }

    private void s_login(){
        // ????????? ?????? ???
        UserDBManager.setUser_id(binding.userId.getText().toString());
        UserDBManager.setUser_pw(binding.userPw.getText().toString());
        UserDBManager.setPhone(binding.userPhone.getText().toString());
        Intent intent = new Intent(MainActivity.context, MenuActivity.class);
        startActivity(intent);
    }

    private void f_login(){
        // ????????? ?????? ???
        Toast.makeText(this, "???????????? ?????? ???????????????.", Toast.LENGTH_LONG).show();
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override

        protected String doInBackground(String... urls) {

            try {
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{

                    URL url = new URL(urls[0]);//url??? ????????????.
                    con = (HttpURLConnection) url.openConnection();
                    con.connect();//?????? ??????
                    //?????? ????????? ??????

                    InputStream stream = con.getInputStream();
                    //????????? ??????????????? ????????? ????????? ?????? ????????? ????????????.
                    reader = new BufferedReader(new InputStreamReader(stream));

                    //?????? ???????????? ?????????
                    StringBuffer buffer = new StringBuffer();

                    //line??? ???????????? ?????? ?????? temp ??????
                    String line = "";

                    //??????????????? ?????? reader?????? ???????????? ???????????? ????????????. ??? node.js??????????????? ???????????? ????????????.
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    //??? ???????????? String ???????????? ????????????. ????????? protected String doInBackground(String??? urls) ??????
                    return buffer.toString();

                    //????????? ???????????? ????????????.

                } catch (MalformedURLException e){
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();

                } finally {

                    //????????? ?????? disconnect???????????? ????????????.
                    if(con != null){
                        con.disconnect();
                    }

                    try {
                        //????????? ????????????.
                        if(reader != null){
                            reader.close();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();

                    }

                }//finally ??????
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        //doInBackground???????????? ????????? ????????? ?????? ??????????????? ?????? ????????????.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            model.getCurrentName().setValue(result);
        }

    }
}