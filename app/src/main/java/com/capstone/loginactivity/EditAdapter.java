package com.capstone.loginactivity;

import android.content.Context;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EditAdapter extends BaseAdapter{

    int resourceId ;
    private ArrayList<ListItem> listitems = new ArrayList<>();

    @Override
    public int getCount() {
        return listitems.size();
    }

    @Override
    public ListItem getItem(int position) {
        return listitems.get(position);

    }
    public TextWatcher getTextWatcher(ListItem item){
        ListItem mItem = item;
        return mItem.textWatcher;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RecyclerView.ViewHolder holder;
        final Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_item_list, parent, false);
        }

        EditText editText = convertView.findViewById(R.id.medicine_name);
        listitems.get(position).setMedicine(editText.getText().toString());

        Button deleteButton = convertView.findViewById(R.id.delBtn);

        TextView textView1 = convertView.findViewById(R.id.ftv1);
        TextView textView2 = convertView.findViewById(R.id.ftv2);
        TextView textView3 = convertView.findViewById(R.id.ftv3);
        TextView textView4 = convertView.findViewById(R.id.ftv4);

        EditText editText1 = convertView.findViewById(R.id.fet1);
        EditText editText2 = convertView.findViewById(R.id.fet2);
        EditText editText3 = convertView.findViewById(R.id.fet3);
        EditText editText4 = convertView.findViewById(R.id.fet4);

        deleteButton.setOnClickListener(v -> {
            listitems.remove(position);
            notifyDataSetChanged();
        });



        return convertView;
    }

    public void addItem() {
        ListItem item = new ListItem();
        listitems.add(item);
    }


}