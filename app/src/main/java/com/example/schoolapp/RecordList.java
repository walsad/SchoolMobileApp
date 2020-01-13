package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecordList extends AppCompatActivity {

    ListView mListView;
    ArrayList<School> mList;
    RecordListAdapter mAdapter=null;
    ImageView imageViewIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);
        mListView = findViewById(R.id.listView);
        mList=new ArrayList<>();
        mAdapter=new RecordListAdapter(this,R.layout.row,mList);
        mListView.setAdapter(mAdapter);

        Cursor cursor =MainActivity.sqlHelper.getData("SELECT * FROM ecole");
        mList.clear();
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String address = cursor.getString(2);
            String forma = cursor.getString(3);
            String Spec = cursor.getString(4);
            byte[] image = cursor.getBlob(5);

            mList.add(new School(id,name,address,forma,Spec,image));


        }
        mAdapter.notifyDataSetChanged();

        if (mList.size()==0){
            Toast.makeText(this,"No School Found...",Toast.LENGTH_SHORT).show();
        }


        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }
}
