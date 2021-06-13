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

        // 뷰 바인딩
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
        // 로그인 성공 시
        UserDBManager.setUser_id(binding.userId.getText().toString());
        UserDBManager.setUser_pw(binding.userPw.getText().toString());
        UserDBManager.setPhone(binding.userPhone.getText().toString());
        Intent intent = new Intent(MainActivity.context, MenuActivity.class);
        startActivity(intent);
    }

    private void f_login(){
        // 로그인 실패 시
        Toast.makeText(this, "존재하지 않는 계정입니다.", Toast.LENGTH_LONG).show();
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override

        protected String doInBackground(String... urls) {

            try {
                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{

                    URL url = new URL(urls[0]);//url을 가져온다.
                    con = (HttpURLConnection) url.openConnection();
                    con.connect();//연결 수행
                    //입력 스트림 생성

                    InputStream stream = con.getInputStream();
                    //속도를 향상시키고 부하를 줄이기 위한 버퍼를 선언한다.
                    reader = new BufferedReader(new InputStreamReader(stream));

                    //실제 데이터를 받는곳
                    StringBuffer buffer = new StringBuffer();

                    //line별 스트링을 받기 위한 temp 변수
                    String line = "";

                    //아래라인은 실제 reader에서 데이터를 가져오는 부분이다. 즉 node.js서버로부터 데이터를 가져온다.
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }

                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String… urls) 니까
                    return buffer.toString();

                    //아래는 예외처리 부분이다.

                } catch (MalformedURLException e){
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();

                } finally {

                    //종료가 되면 disconnect메소드를 호출한다.
                    if(con != null){
                        con.disconnect();
                    }

                    try {
                        //버퍼를 닫아준다.
                        if(reader != null){
                            reader.close();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();

                    }

                }//finally 부분
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            model.getCurrentName().setValue(result);
        }

    }
}