package tw.com.feast_test0301;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class Load extends AppCompatActivity {
    private static int LOAD_TIMEOUT = 2000; //2秒

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setSize
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_load);
        //setDelayed
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Intent
                startActivity(new Intent(Load.this,MainActivity.class));
                finish();
                //destroyPage
            }
        },LOAD_TIMEOUT);


    }
}