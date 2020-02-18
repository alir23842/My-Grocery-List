package com.example.grocerywithjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class List_Activity extends AppCompatActivity {

    RecyclerView mRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        mRV = findViewById(R.id.AL_rv);
        mRV.setLayoutManager(new LinearLayoutManager(this));
        mRV.setAdapter(new MyAdapter(DatabaseHelper.getInstance(this).getData(), this));
    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        ArrayList<Grocery_Class> list;
        Context context;

        MyAdapter(ArrayList<Grocery_Class> list, Context context) {
            this.list = list;
            this.context = context;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            MyViewHolder(View v) {
                super(v);
                tv = v.findViewById(R.id.GIV_tostring_tv);
            }
        }

        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_item_view, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(list.get(position).toString());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

}
