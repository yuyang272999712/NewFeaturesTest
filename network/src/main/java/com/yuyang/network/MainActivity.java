package com.yuyang.network;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.yuyang.network.volley.VolleyTrainFragment1;
import com.yuyang.network.volley.VolleyTrainFragment2;
import com.yuyang.network.volley.VolleyTrainFragment3;
import com.yuyang.network.volley.VolleyTrainFragment4;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViews();

        fragmentManager = getSupportFragmentManager();
    }

    private void findViews(){

    }

    public void onClickListener(View view){
        int id = view.getId();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (id == R.id.volley_train_1){
            transaction.add(R.id.fragment, VolleyTrainFragment1.getInstance());
            transaction.addToBackStack(null);
        }
        if (id == R.id.volley_train_2){
            transaction.add(R.id.fragment, VolleyTrainFragment2.getInstance());
            transaction.addToBackStack(null);
        }
        if (id == R.id.volley_train_3){
            transaction.add(R.id.fragment, VolleyTrainFragment3.getInstance());
            transaction.addToBackStack(null);
        }
        if (id == R.id.volley_train_4){
            transaction.add(R.id.fragment, VolleyTrainFragment4.getInstance());
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
