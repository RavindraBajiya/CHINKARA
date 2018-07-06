package com.ravindra.siit.chinkara.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ravindra.siit.chinkara.DataObj.DataModel;
import com.ravindra.siit.chinkara.DataObj.NotificationData;
import com.ravindra.siit.chinkara.PhoneActivity;
import com.ravindra.siit.chinkara.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class NotificationAdapter extends BaseAdapter {
    Context context;
    ArrayList<NotificationData> list;
    PhoneActivity phoneActivity;
    LayoutInflater inflater;
    Typeface tf;
    AVLoadingIndicatorView avi;

    public NotificationAdapter(Context context, ArrayList<NotificationData> list) {
        this.context = context;
        this.list = list;
        tf = Typeface.createFromAsset( context.getAssets(), "fonts/fontawesome.ttf" );
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
        phoneActivity = new PhoneActivity();
        if (convertView==null){
            inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.noti_list_row,null);
        }
        TextView title = convertView.findViewById(R.id.notiViewTitle);
        TextView body = convertView.findViewById(R.id.notiViewBody);
        avi = convertView.findViewById(R.id.avi);
        avi.hide();
        phoneActivity.setCustomFontTV(context,title);
        phoneActivity.setCustomFontTV(context,body);
        body.setText(list.get(position).getBody());
        title.setText(list.get(position).getTitle());
        Button notiBtn = convertView.findViewById(R.id.notiViewBtn);
        notiBtn.setTypeface(tf);
        ImageView imageView =convertView.findViewById(R.id.notiViewImage);
       if (!list.get(position).getUrl().equals("null")){
           avi.show();
           Picasso.with(context)
                   .load(list.get(position).getUrl())
                   .into(imageView, new Callback() {
                       @Override
                       public void onSuccess() {
                           avi.hide();
                       }

                       @Override
                       public void onError() {

                       }
                   });
           imageView.setVisibility(View.VISIBLE);
       }
        return convertView;
    }
}
