package com.capstone.loginactivity;

import android.content.Context;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MedicineAdapter extends BaseAdapter {
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
            convertView = inflater.inflate(R.layout.medicine_item_list, parent, false);
            TextView eName = convertView.findViewById(R.id.premedicine_name);
            TextView textView1 = convertView.findViewById(R.id.met1);
            TextView textView2 = convertView.findViewById(R.id.met2);
            TextView textView3 = convertView.findViewById(R.id.met3);
            TextView textView4 = convertView.findViewById(R.id.met4);
            ListItem item = listitems.get(position);
            eName.setText("약이름 : "+ item.getMedicine());
            textView1.setText(item.getDayDosage());
            textView2.setText(item.getTimeDosage());
            textView3.setText(item.getFreq());
            textView4.setText(item.getSum());
        }

        return convertView;
    }

    public void addItem(String medicineList) {
        String[] medicineInfo = medicineList.split("_");
        ListItem item = new ListItem();
        item.setMedicine(medicineInfo[0]);
        item.setDayDosage(medicineInfo[1]);
        item.setTimeDosage(medicineInfo[2]);
        item.setFreq(medicineInfo[3]);
        item.setSum(medicineInfo[4]);
        listitems.add(item);

    }


}
