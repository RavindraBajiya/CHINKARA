package com.ravindra.siit.chinkara.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ravindra.siit.chinkara.DataObj.DataModel;
import com.ravindra.siit.chinkara.DataObj.MenuListData;
import com.ravindra.siit.chinkara.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class ResultListAdapter extends BaseAdapter {
    ArrayList<DataModel> list;
    Context context;

    public ResultListAdapter(ArrayList<DataModel> list, Context context) {
        this.list = list;
        this.context = context;
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
        LayoutInflater inflater;
        if (convertView==null){
          inflater = LayoutInflater.from(context);
          convertView = inflater.inflate(R.layout.result_row_2,null);
        }
        LinearLayout linearLayout = convertView.findViewById(R.id.resultRowLinear);
        TextView textView = convertView.findViewById(R.id.textViewResultRow2);
        TextView textView1 = convertView.findViewById(R.id.textViewResultRow2Second);
        if (position==0||position%2==0){
            linearLayout.setBackgroundColor(context.getResources().getColor(R.color.result_row_color));
        }
        else
        {
            linearLayout.setBackgroundColor(context.getResources().getColor(R.color.result_row_color2));

        }
        textView.setText(list.get(position).getName());
        textView1.setText(list.get(position).getPhone());
        return convertView;
    }
}
