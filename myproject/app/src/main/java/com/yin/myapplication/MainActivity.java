package com.yin.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yin.myapplication.databinding.ActivityMainBinding;
import com.yin.myapplication.db.UserDBManager;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SharedPreferences - user
        UserDBManager userDBManager = new UserDBManager(getApplicationContext());

        // 뷰 바인딩
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setLifecycleOwner(this);

        binding.buttonLogin.setOnClickListener(v->{

            UserDBManager.setUser_id(binding.userId.getText().toString());
            UserDBManager.setUser_pw(binding.userPw.getText().toString());
            UserDBManager.setPhone(binding.userPhone.getText().toString());
            //Intent intent = new Intent(this, MenuActivity.class);
            //startActivity(intent);
        });

        // init view
        binding.userId.setText(UserDBManager.getUser_id());
        binding.userPw.setText(UserDBManager.getUser_pw());
        binding.userPhone.setText(UserDBManager.getPhone());
    }
}