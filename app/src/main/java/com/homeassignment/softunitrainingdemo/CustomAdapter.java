package com.homeassignment.softunitrainingdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.homeassignment.softunitrainingdemo.Models.University;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bon on 5/20/2015.
 */
public class CustomAdapter extends BaseAdapter {

    List<University> universities = new ArrayList<University>();
    Context context;

    public CustomAdapter(Context context) {
        this.context = context;
        seedUnis();
    }

    private void seedUnis() {
        int i = 1;
        this.universities.add(new University(i,"Soft Uni","Tintqva 50"));

        while(i<100) {

            this.universities.add(new University(i,"Soft Uni" + i,"Tintqva 50" + i %2));
            i++;
        }

    }

    @Override
    public int getCount() {
        return this.universities.size();
    }

    @Override
    public University getItem(int position) {
        return this.universities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView name;
        TextView address;

        if (convertView==null){
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.university_row,parent,false);
            name = (TextView) convertView.findViewById(R.id.lbUniName);
            address = (TextView) convertView.findViewById(R.id.lbUniAddress);
            convertView.setTag(R.id.lbUniName,name);
            convertView.setTag(R.id.lbUniAddress,address);


        }else{
            name = (TextView) convertView.findViewById(R.id.lbUniName);
            address = (TextView) convertView.findViewById(R.id.lbUniAddress);

        }

        University uni = getItem(position);
        name.setText(uni.getName());
        address.setText(uni.getAddress());

        return  convertView;
    }
}
