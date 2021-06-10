package com.yin.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yin.myapplication.databinding.ActivityMenuBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {
    ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 뷰 바인딩
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);
        binding.setLifecycleOwner(this);

        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
        String format_time1 = format1.format (System.currentTimeMillis());
        binding.lastDay.setText(format_time1);

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        ArrayList<MyRecyclerItem> list = new ArrayList<>();
        for (int i=0; i<3; i++) {
            list.add(new MyRecyclerItem("reward: 100", "2020-06-10 11:00:00", "30 mL")) ;
        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = findViewById(R.id.recycler1) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        recordAdapter adapter = new recordAdapter(list) ;
        recyclerView.setAdapter(adapter) ;

    }
}