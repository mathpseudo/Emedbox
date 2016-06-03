package com.finalyearproject.myapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.finalyearproject.myapplication.alarm.AlarmReceiver;
import com.finalyearproject.myapplication.data.DatabaseAdapter;

import java.util.Calendar;


public class MainActivity extends Activity {
    private Button entryButton;
    private Button viewButton;
    private static MainActivity inst;
    private Button updateButton;
    private Button ledButton;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    private DatabaseAdapter dbAdapter;

    public static MainActivity instance(){
        return inst;
    }

    @Override
    public void onStart(){
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entryButton = (Button) findViewById(R.id.dataEntryButton);
        viewButton = (Button) findViewById(R.id.dataViewButton);
        updateButton = (Button) findViewById(R.id.dataUpdateButton);
        ledButton = (Button) findViewById(R.id.ledButton);

        entryButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MedicinalDataEntry.class);
                startActivity(i);
                finish();
            }
        });

        viewButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(getApplicationContext(),MedicinalDataView.class);
                startActivity(i);
                finish();
            }
        });

        updateButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(getApplicationContext(),MedicinalDataUpdate.class);
                startActivity(i);
                finish();
            }
        });

        ledButton.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent i = new Intent(getApplicationContext(),ledControl.class);
                startActivity(i);
                finish();
            }
        });

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE,0);
        Intent myIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, myIntent, 0);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void setAlarmText(){
        dbAdapter = new DatabaseAdapter(this);
        dbAdapter.open();
        try{
            Cursor c = dbAdapter.getDataGivenTimeFood("morning","after");
            if(c.getCount()>0){
                String msg = "Take medicines : ";
                Log.d("Entered SetAlarmText","heyhey");
                while(c.moveToNext()){
                    String medName = c.getString(c.getColumnIndex("medName"));
                    msg += medName + " ";
                }
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        dbAdapter.close();
    }
}
