package com.ravindra.siit.chinkara.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ravindra.siit.chinkara.DataObj.MenuListData;
import com.ravindra.siit.chinkara.PhoneActivity;
import com.ravindra.siit.chinkara.R;

import java.util.ArrayList;

public class MenuListAdapter extends BaseAdapter{
    ArrayList<MenuListData> list;
    Context context;
    PhoneActivity pa;
    Typeface font;

    public MenuListAdapter(ArrayList<MenuListData> list, Context context) {
        this.list = list;
        this.context = context;
        font = Typeface.createFromAsset( context.getAssets(), "fonts/fontawesome.ttf" );
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
            convertView = inflater.inflate(R.layout.menu_list_row,null);
        }
//        ImageView imageView = convertView.findViewById(R.id.menuImage);
        Button btn = convertView.findViewById(R.id.menuImage);
        btn.setText(list.get(position).getMenu());
        btn.setTypeface(font);
        TextView textView = convertView.findViewById(R.id.textViewMenuList);

//        imageView.setImageResource(list.get(position).getImg());
        pa = new PhoneActivity();
        pa.setCustomFontTV(context,textView);
        textView.setText(list.get(position).getMenuText());
        return convertView;
    }
}