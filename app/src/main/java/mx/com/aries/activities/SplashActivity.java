package mx.com.aries.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mx.com.aries.R;
import mx.com.aries.utils.SharedPreferenceUtil;

public class SplashActivity extends AppCompatActivity {
    SharedPreferenceUtil mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = SharedPreferenceUtil.getInstance(this);
        if (mSharedPreferences.getBoolanValue("IsFirtsTime", true)) {
            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
        }
        else
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        finish();
    }
}
