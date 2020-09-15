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
        Context context = parent.getContext();
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_item_list, parent, false);
        }

        EditText editText = (EditText) convertView.findViewById(R.id.ValueEditText);
        listitems.get(position).setMedicine(editText.getText().toString());

        Button deleteButton = (Button) convertView.findViewById(R.id.delBtn);

        TextView textView1 = (TextView) convertView.findViewById(R.id.ftv1);
        TextView textView2 = (TextView) convertView.findViewById(R.id.ftv2);
        TextView textView3 = (TextView) convertView.findViewById(R.id.ftv3);
        TextView textView4 = (TextView) convertView.findViewById(R.id.ftv4);

        EditText editText1 = (EditText) convertView.findViewById(R.id.fet1);
        listitems.get(position).setDayDosage(editText1.getText().toString());
        EditText editText2 = (EditText) convertView.findViewById(R.id.fet2);
        listitems.get(position).setTimeDosage(editText2.getText().toString());
        EditText editText3 = (EditText) convertView.findViewById(R.id.fet3);
        listitems.get(position).setFreq(editText3.getText().toString());
        EditText editText4 = (EditText) convertView.findViewById(R.id.fet4);
        listitems.get(position).setSum(editText4.getText().toString());

        deleteButton.setOnClickListener(v -> {
            listitems.remove(position);
            notifyDataSetChanged();
        });
        ListItem item = getItem(position);


        clearTextChangedListener(editText);
        editText.setText(item.mValue);
        editText.addTextChangedListener(item.textWatcher);
        clearTextChangedListener(editText1);
        editText1.setText(item.mValue);
        editText1.addTextChangedListener(item.textWatcher);
        clearTextChangedListener(editText2);
        editText2.setText(item.mValue);
        editText2.addTextChangedListener(item.textWatcher);
        clearTextChangedListener(editText3);
        editText3.setText(item.mValue);
        editText3.addTextChangedListener(item.textWatcher);
        clearTextChangedListener(editText4);
        editText4.setText(item.mValue);
        editText4.addTextChangedListener(item.textWatcher);
        return convertView;
    }

    public void addItem() {
        ListItem item = new ListItem();
        listitems.add(item);
    }
    private void clearTextChangedListener(EditText editText) {
        //리스트 목록의 모든 리스너를 대상으로 검사하여 삭제해 준다
        int count = getCount();
        for (int i = 0 ; i < count ; i++)
            editText.removeTextChangedListener(getTextWatcher(getItem(i)));
    }

}