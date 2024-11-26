package com.neatroots.logintest.LoginPage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.neatroots.logintest.MainActivity;
import com.neatroots.logintest.Model.User;
import com.neatroots.logintest.R;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUserName;
    private EditText edtPassword;
    private Button btnLogin;

    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        setUpEvent();
        this.viewModel = new LoginViewModel();

        setUpViewModel();

    }

    private void initView() {
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    private void setUpEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edtUserName.getText().toString();
                String password = edtPassword.getText().toString();
                viewModel.loginWithPhone(phone, password);
            }
        });
    }

    private void setUpViewModel() {
        viewModel.getLoginWithPhoneResponse().observe(this, loginResponse -> {

            if (loginResponse == null) {
//                dialog.show(getString(R.string.attention),
//                        getString(R.string.oops_there_is_an_issue),
//                        R.drawable.ic_close);
                Toast.makeText(this, "Đange nhap that bai", Toast.LENGTH_SHORT).show();
                return;
            }

            int result = loginResponse.getResult();
            String message = loginResponse.getMsg();


            Log.d("result", "setUpViewModel: " + result);
            Log.d("message", "setUpViewModel: " + message);

            /*Case 1 - login successfully*/
            if (result == 1) {
                /*Lay du lieu tu API ra*/
                String token = loginResponse.getAccessToken();
                User user = loginResponse.getData();


                /*Lay du lieu vao Global Variable*/
//                globalVariable.setAccessToken( "JWT " + token );
//                globalVariable.setAuthUser(user);

                /*luu accessToken vao Shared Reference*/
//                sharedPreferences.edit().putString("accessToken", "JWT " + token.trim()).apply();

                /*hien thi thong bao la dang nhap thanh cong*/
//                Toast.makeText(this, getString(R.string.login_successfully), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "login thanh cong", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
            /*Case 2 - login failed*/
            else {
//                dialog.show(getString(R.string.attention),
//                        message,
//                        R.drawable.ic_close);
//                Toast.makeText(this, getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();

            }

        });

    }
}