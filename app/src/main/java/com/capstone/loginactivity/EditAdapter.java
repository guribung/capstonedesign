package com.capstone.loginactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class EditAdapter extends BaseAdapter {
    private ArrayList<ListItem> listitems = new ArrayList<>();

    @Override
    public int getCount() {
        return listitems.size();
    }

    @Override
    public Object getItem(int position) {
        return listitems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_item_list, parent, false);
        }
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
        EditText editText = (EditText) convertView.findViewById(R.id.ValueEditText);
        TextView textView1 = (TextView) convertView.findViewById(R.id.ftv1);
        TextView textView2 = (TextView) convertView.findViewById(R.id.ftv2);
        TextView textView3 = (TextView) convertView.findViewById(R.id.ftv3);
        TextView textView4 = (TextView) convertView.findViewById(R.id.ftv4);
        EditText editText1 = (EditText) convertView.findViewById(R.id.fet1);
        EditText editText2 = (EditText) convertView.findViewById(R.id.fet2);
        EditText editText3 = (EditText) convertView.findViewById(R.id.fet3);
        EditText editText4 = (EditText) convertView.findViewById(R.id.fet4);

        return convertView;
    }

    public void addItem() {
        ListItem item = new ListItem();
        listitems.add(item);
    }


}