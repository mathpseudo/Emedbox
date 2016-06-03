package com.finalyearproject.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.finalyearproject.myapplication.data.DatabaseAdapter;

import java.util.ArrayList;


public class MedicinalDataUpdate extends ActionBarActivity {
    private Cursor c;
    private DatabaseAdapter dbAdapter;
    private ListView medicines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_data_update);

        ArrayList list = new ArrayList();

        medicines = (ListView) findViewById(R.id.listView2);
        dbAdapter = new DatabaseAdapter(this);
        dbAdapter.open();
        try{
            c = dbAdapter.getData();
            if(c.getCount() > 0){
                while(c.moveToNext()){
                    String medname = c.getString(c.getColumnIndex("medName"));
                    list.add(medname);
                }
            }
            final ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
            medicines.setAdapter(adapter);
            medicines.setOnItemClickListener(myListClickListener);
        }
        catch(Exception e){

        }
    }

    private AdapterView.OnItemClickListener myListClickListener = new AdapterView.OnItemClickListener()
    {
        public void onItemClick (AdapterView<?> av, View v, int arg2, long arg3) {
            String medname = ((TextView) v).getText().toString();
            Log.d("Entered","onItemClick");
            dbAdapter.deleteMedicineName(medname);
            dbAdapter.close();
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_medicine_data_update, menu);
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
