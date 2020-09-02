package com.capstone.loginactivity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    LayoutInflater inflater = null;
    private ArrayList<UserItem> m_oData = new ArrayList<>();
    private int nListCnt = 0;

    public ListViewAdapter(ArrayList<UserItem> _oData)
    {
        m_oData = _oData;

    }



    @Override
    public int getCount()
    {
        Log.i("TAG", "getCount");
        return m_oData.size();
    }

    @Override
    public Object getItem(int position)
    {
        return null;
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.user_item_list, parent, false);
        }

        TextView tName = (TextView) convertView.findViewById(R.id.name);
        TextView tPhone = (TextView) convertView.findViewById(R.id.HP);

        tName.setText(m_oData.get(position).name);
        tPhone.setText(m_oData.get(position).hp);
        return convertView;
    }

    public void addItem(String name, String phone, String uid){
        UserItem item = new UserItem();
        item.SetName(name);
        item.SetHp(phone);
        item.SetUid(uid);
    }
}


