package mx.com.aries.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mx.com.aries.R;
import mx.com.aries.utils.SharedPreferenceUtil;

public class WelcomeActivity extends AppCompatActivity {

    Button start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        start = (Button) findViewById(R.id.ac_welcome_start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getApplicationContext();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                SharedPreferenceUtil mSharedPreferences = SharedPreferenceUtil.getInstance(context);
                mSharedPreferences.setValue("IsFirtsTime", false);
                finish();
            }
        });
    }
}
