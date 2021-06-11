package com.yin.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonParseException;
import com.yin.myapplication.databinding.ActivityMenuBinding;
import com.yin.myapplication.db.UserDBManager;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {
    ActivityMenuBinding binding;
    MenuViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the ViewModel.
        model = new ViewModelProvider(this).get(MenuViewModel.class);

        // Create the observer which updates the UI.
        final Observer<String> nameObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String newName) {
                // Update the UI, in this case, a TextView.


                if (newName==null) return;
                String mYnewName = newName
                        .replace("\"volume\":", "")
                        .replace("\"discharge_date\":", "")
                        .replace("\"", "")
                        .replace("[", "")
                        .replace("]", "")
                        .replace("{", "")
                        .replace("}", "");

                String[] arr = mYnewName.split(",");
                ArrayList<MyRecyclerItem> list = new ArrayList<MyRecyclerItem>();
                for (int i=0;i<arr.length;i+=2){
                    try{
                        Log.d("로그", arr[i]+"-------------------->"+arr[i+1]);
                        Integer vol = Integer.parseInt(arr[i]);
                        list.add(new MyRecyclerItem(vol*500, arr[i+1], vol));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                init_view(list);
                //binding.userNum.setText(mYnewName);
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        model.getCurrentName().observe(this, nameObserver);

        // 뷰 바인딩
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);
        binding.setLifecycleOwner(this);

        String mUrl = "http://10.0.2.2:3000/api/searchrecordbyuser/"+
                UserDBManager.getUser_id();
        try {
            new MenuActivity.JSONTask().execute(mUrl);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void init_view(ArrayList<MyRecyclerItem> list){
        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        Integer sum_reward = 0;
        for (int i=0; i<list.size(); i++) {
            sum_reward += list.get(i).getIntReward();
        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.recycler1) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext())) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        recordAdapter adapter = new recordAdapter(list) ;
        recyclerView.setAdapter(adapter) ;

        // init view
        String user_id = UserDBManager.getUser_id();
        binding.userNum.setText("안녕하세요! "+ user_id==null?"":user_id +" 회원님");
        binding.totalMoney.setText("현재 적립금은 "+sum_reward+"P 입니다!");
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
            model.getCurrentName().setValue(result.toString());
        }

    }
}