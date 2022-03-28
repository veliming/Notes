package com.pikachu.record;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.pikachu.record.activity.home.HomeActivity;
import com.pikachu.record.sql.data.InitialSql;
import com.pikachu.record.tool.ToolPublic;
import com.pikachu.record.tool.ToolState;

import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

//import android.support.multidex.MultiDex;


public class MainActivity extends AppCompatActivity {



    /*
     //腹泻  突破dex 64k方法限制
     @Override
     protected void attachBaseContext(Context base) {
         super.attachBaseContext(base);
         MultiDex.install(this);
     }

     //*/


	//mood 文字+颜色
	private String[] moodStr;
	private	int[] moodColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToolState.setFullScreen(this, true, true);//全屏
        setContentView(R.layout.main_activity);

        AppCenter.start(getApplication(), "158983b3-3119-4c06-95e2-66372b400582", Analytics.class, Crashes.class);
        initApp();

        new Handler().postDelayed(new Runnable(){
				@Override
				public void run() {
					Intent intent = new Intent(MainActivity.this, HomeActivity.class);
					startActivity(intent);
					finish();
				}
			}, 600);
    }






    private void initApp() {
		//mood 文字+颜色
		moodStr = getResources().getStringArray(R.array.mood_str);
		moodColor = getResources().getIntArray(R.array.mood_color);

        for (int i=0;i < moodStr.length; i++) {
            ToolPublic.MOOD_STR_TO_COLOR.add(new ToolPublic.MoodStrColor(moodStr[i], moodColor[i]));
        }
        new InitialSql(this).startInitialSql();
    }


}
