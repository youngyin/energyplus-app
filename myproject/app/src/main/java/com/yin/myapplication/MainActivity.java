package com.yin.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
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
            // 로그인 성공 시
            UserDBManager.setUser_id(binding.userId.getText().toString());
            UserDBManager.setUser_pw(binding.userPw.getText().toString());
            UserDBManager.setPhone(binding.userPhone.getText().toString());
            Intent intent = new Intent(this, MenuActivity.class);
            startActivity(intent);

            // 로그인 실패 시
            AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
            dlg.setTitle("로그인 실패"); //제목
            dlg.setMessage("존재하지 않는 회원 정보입니다. 회원가입하시겠습니까?"); // 메시지
            dlg.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int which) {
                    binding.buttonSignup.setVisibility(View.VISIBLE);
                    binding.buttonLogin.setVisibility(View.GONE);
                }
            });
            dlg.show();
        });

        binding.buttonSignup.setOnClickListener(v -> {
            UserDBManager.setUser_id(binding.userId.getText().toString());
            UserDBManager.setUser_pw(binding.userPw.getText().toString());
            UserDBManager.setPhone(binding.userPhone.getText().toString());

            binding.buttonSignup.setVisibility(View.GONE);
            binding.buttonLogin.setVisibility(View.VISIBLE);
        });

        // init view
        binding.userId.setText(UserDBManager.getUser_id());
        binding.userPw.setText(UserDBManager.getUser_pw());
        binding.userPhone.setText(UserDBManager.getPhone());
    }
}