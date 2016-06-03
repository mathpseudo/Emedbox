package com.finalyearproject.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.finalyearproject.myapplication.data.DatabaseAdapter;


public class MedicinalDataEntry extends ActionBarActivity {
    private Button submitButton;
    private RadioGroup timeGroup;
    private RadioGroup foodGroup;
    private EditText medName;
    private DatabaseAdapter dbAdapter;
    String aMedName;
    String aTime;
    String aFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicinal_data_entry);
        dbAdapter = new DatabaseAdapter(this);
        dbAdapter.open();
        submitButton = (Button) findViewById(R.id.submitbutton);
        submitButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){
                medName = (EditText) findViewById(R.id.medName);
                timeGroup = (RadioGroup) findViewById(R.id.timeGroup);
                foodGroup = (RadioGroup) findViewById(R.id.foodGroup);

                aMedName = medName.getText().toString();
                aTime = "";
                aFood = "";

                if(timeGroup.getCheckedRadioButtonId() != -1){
                    int id1 = timeGroup.getCheckedRadioButtonId();
                    switch(id1){
                        case R.id.morning:
                            aTime = "Morning";
                            break;
                        case R.id.noon:
                            aTime = "Noon";
                            break;
                        case R.id.night:
                            aTime = "Night";
                            break;
                    }
                }

                if(foodGroup.getCheckedRadioButtonId() != -1) {
                    int id2 =foodGroup.getCheckedRadioButtonId();
                    switch(id2){
                        case R.id.beforeFood:
                            aFood = "Before";
                            break;
                        case R.id.afterFood:
                            aFood = "After";
                            break;
                    }
                }

                if(aMedName.matches("") || aTime.matches("") || aFood.matches("")){
                    Toast.makeText(getApplicationContext(),"Please Enter All Details", Toast.LENGTH_SHORT).show();
                }
                else{
                    dbAdapter.insertData(aMedName,aTime,aFood);
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    Log.d("Entered the data",aMedName);
                    startActivity(i);
                    finish();
                }
                dbAdapter.close();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_medicinal_data_entry, menu);
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
}
