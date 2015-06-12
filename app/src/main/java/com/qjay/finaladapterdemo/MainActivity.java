package com.qjay.finaladapterdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.qjay.finaladapterdemo.adapter.AbsCommonAdapter;
import com.qjay.finaladapterdemo.adapter.AbsRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<String> items = new ArrayList<String>();
        for (int i = 0; i < 100; i++) {
            items.add("items"+i);
        }


        ListView lv = (ListView) findViewById(R.id.lv);

        lv.setAdapter(new AbsCommonAdapter<String>(this, items, android.R.layout.simple_list_item_1) {
            @Override
            public void onBindViewHolder(ViewHolder viewHolder, String item) {
                viewHolder.setTextViewText(android.R.id.text1, item);
            }
        });




        RecyclerView rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager mLayoutManager_vertical = new LinearLayoutManager(this);
        mLayoutManager_vertical.setOrientation(LinearLayoutManager.VERTICAL);//这里可以不设置,默认是垂直的
        rv.setLayoutManager(mLayoutManager_vertical);



        rv.setAdapter(new AbsRecyclerViewAdapter<String>(items,android.R.layout.simple_list_item_1) {

            @Override
            public void onBindViewHolder(ViewHolder viewHolder, String item) {
                viewHolder.setTextViewText(android.R.id.text1,item);
            }
        });
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
}
