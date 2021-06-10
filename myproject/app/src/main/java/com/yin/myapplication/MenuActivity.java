package com.yin.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yin.myapplication.databinding.ActivityMenuBinding;
import com.yin.myapplication.db.UserDBManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MenuActivity extends AppCompatActivity {
    ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 뷰 바인딩
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);
        binding.setLifecycleOwner(this);


        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        ArrayList<MyRecyclerItem> list = new ArrayList<>();
        Integer sum_reward = 0;
        for (int i=0; i<3; i++) {
            sum_reward += 100;
            list.add(new MyRecyclerItem(100, "2020-06-10 11:00:00", 30)) ;
        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.recycler1) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        recordAdapter adapter = new recordAdapter(list) ;
        recyclerView.setAdapter(adapter) ;

        // init view
        String user_id = UserDBManager.getUser_id();
        binding.userNum.setText("안녕하세요! "+ user_id==null?"":user_id +" 회원님");
        binding.totalMoney.setText("현재 적립금은 "+sum_reward+"원 입니다!");

    }
}