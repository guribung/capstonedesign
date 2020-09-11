package com.capstone.loginactivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class PrescriptionActivity extends AppCompatActivity {

    private EditText pName, pAnum, pCode;
    private Button addDrug, editEnd;
    private ListView listView;
    public ArrayList<EditText> find = new ArrayList<>();
    public ArrayList<EditAdapter.ListItem> listViewItemList = new ArrayList<EditAdapter.ListItem>(); //리스트뷰
    private ArrayList<EditAdapter.ListItem> filteredItemList = listViewItemList; //리스트뷰 임시저장소
    public ArrayList<String> find2 = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        pName = findViewById(R.id.name_edit);
        pAnum = findViewById(R.id.regnum_edit);
        pCode = findViewById(R.id.dis_code);

        addDrug = findViewById(R.id.add_drug);
        editEnd = findViewById(R.id.end_button);

        listView = findViewById(R.id.PlistView);

        addDrug.setOnClickListener(v -> {

        });

        editEnd.setOnClickListener(v -> {
            String name = pName.getText().toString().trim();
            String admNum = pAnum.getText().toString().trim();
            String code = pCode.getText().toString().trim();
        });
    }

    public class EditAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }

        public void addItem(int num) {
            ListItem item = new ListItem();
            listViewItemList.add(item);
        }

        public class ListItem {
            private int num;

            public int getNum() {
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }


        }
    }
}