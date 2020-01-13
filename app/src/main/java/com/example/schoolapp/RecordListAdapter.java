package com.example.schoolapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class RecordListAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<School> recordList;

    public RecordListAdapter(Context context, int layout, ArrayList<School> recordList) {
        this.context = context;
        this.layout = layout;
        this.recordList = recordList;
    }

    @Override
    public int getCount() {
        return recordList.size();
    }

    @Override
    public Object getItem(int position) {
        return recordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imageView;
        TextView txtName,txtAdd,txtForm,txtSpe;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row=convertView;
        ViewHolder holder = new ViewHolder();

        if (row == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=inflater.inflate(layout,null);
            holder.txtName=row.findViewById(R.id.txtName);
            holder.txtAdd=row.findViewById(R.id.txtAdd);
            holder.txtForm=row.findViewById(R.id.txtForm);
            holder.txtSpe=row.findViewById(R.id.txtSpe);
            holder.imageView=row.findViewById(R.id.imgIcon);
            row.setTag(holder);
        }else {
            holder = (ViewHolder) row.getTag();
        }
        School school=recordList.get(position);

        holder.txtName.setText(school.getSchoolname());
        holder.txtAdd.setText(school.getSchoolAddress());
        holder.txtSpe.setText(school.getSchoolSpec());
        byte[] recordImage=school.getSchoolLogo();
        Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage,0,recordImage.length);
        holder.imageView.setImageBitmap(bitmap);
        return row;
    }
}
