package com.neatroots.logintest.LoginPage;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.neatroots.logintest.Configuration.HTTPRequest;
import com.neatroots.logintest.Configuration.HTTPService;
import com.neatroots.logintest.Container.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginViewModel {
    private String TAG = "LoginViewModel";
    private MutableLiveData<Login> loginWithPhoneResponse;
    private MutableLiveData<Boolean> animation;

    public LoginViewModel() {
        loginWithPhoneResponse = new MutableLiveData<>();
        animation = new MutableLiveData<>();
    }

    /*************** GETTER ******************/
    public MutableLiveData<Login> getLoginWithPhoneResponse() {
        if( loginWithPhoneResponse == null ){
            loginWithPhoneResponse = new MutableLiveData<>();
        }
        return loginWithPhoneResponse;
    }

    public MutableLiveData<Boolean> getAnimation() {
        if( animation == null ){
            animation = new MutableLiveData<>();
        }
        return animation;
    }

    /**********************FUNCTION**********************/
    public void loginWithPhone(String phone, String password) {
        animation.setValue(true);
        animation.setValue(true);

        /*Step 1*/
        Retrofit service = HTTPService.getInstance();
        HTTPRequest api = service.create(HTTPRequest.class);

        /*Step 2*/
        Call<Login> container = api.login(phone, password, "patient");

        /*Step 3*/
        container.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> result) {
                animation.setValue(false);

                Log.d("result", result.message());
                // Log the response details
                if (result.isSuccessful()) {
                    Login content = result.body();
                    assert content != null;

                    // Log the successful response data
                    Log.d(TAG, "onResponse: Login successful. Content: " + content.toString());

                    loginWithPhoneResponse.setValue(content);
                } else {
                    // Log the error details when the response is not successful
                    Log.d(TAG, "onResponse: Error - " + result.code() + ": " + result.message());
                    loginWithPhoneResponse.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {
                animation.setValue(false);

                // Log the failure details
                Log.d(TAG, "onFailure: Error - " + t.getMessage());

                loginWithPhoneResponse.setValue(null);
            }
        });
    }

}
