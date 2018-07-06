package com.ravindra.siit.chinkara.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ravindra.siit.chinkara.DataObj.DataModel;
import com.ravindra.siit.chinkara.PhoneActivity;
import com.ravindra.siit.chinkara.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SyllabusAdapter extends BaseAdapter {
    Context context;
    ArrayList<DataModel> list;
    LayoutInflater inflater;
    PhoneActivity phoneActivity;

    public SyllabusAdapter(Context context, ArrayList<DataModel> list) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
        phoneActivity = new PhoneActivity();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.syllabus_item, null);
        }
        ImageView imageView = convertView.findViewById(R.id.imageView7);
        TextView textView = convertView.findViewById(R.id.textView10);
        String url = list.get(position).getPhone();
        Picasso.with(context).load(url).into(imageView);
//        textView.setTextColor(context.getResources().getColor(R.color.editcolor));
        textView.setText(list.get(position).getName());
        return convertView;
    }
}
