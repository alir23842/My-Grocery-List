package com.example.grocerywithjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class Main_Activity extends AppCompatActivity implements View.OnClickListener {


    Button mSyncDaata, mViewData, mDeleteData;
    ProgressBar mSyncProgress;
    FirebaseDatabase mDataBase;
    DatabaseReference mDataBaseRef;
    DatabaseHelper mDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        mDataBase = FirebaseDatabase.getInstance();
        mDataBaseRef = mDataBase.getReference();
        mDataBaseHelper = DatabaseHelper.getInstance(Main_Activity.this);

    }

    private void initViews() {

        mSyncDaata = findViewById(R.id.AM_sync_btn);
        mViewData = findViewById(R.id.AM_view_btn);
        mDeleteData = findViewById(R.id.AM_delete_btn);
        mSyncProgress = findViewById(R.id.AM_sync_pro_bar);
        mSyncDaata.setOnClickListener(Main_Activity.this);
        mViewData.setOnClickListener(Main_Activity.this);
        mDeleteData.setOnClickListener(Main_Activity.this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.AM_sync_btn:
                syncData();
                break;
            case R.id.AM_view_btn:
                viewData();
                break;
            case R.id.AM_delete_btn:
                deleteData();
                break;

        }
    }


    private void syncData() {


        if (isNetworkConnected()) {

            proBarVisible(true);

            mDataBaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    ArrayList<Grocery_Class> list = new ArrayList<>();
                    for (DataSnapshot item : dataSnapshot.getChildren()) {
                        list.add(item.getValue(Grocery_Class.class));
                    }
                    mDataBaseHelper.insertAll(list);
                    proBarVisible(false);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    proBarVisible(false);
                }
            });


        } else {
            Toast.makeText(this, "Please Connect With Network!", Toast.LENGTH_SHORT).show();
        }


    }

    private void viewData() {
        if (mDataBaseHelper.getData().isEmpty()) {
            Toast.makeText(this, "Data Fetch Needed", Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(this, List_Activity.class));
        }
    }

    private void deleteData() {
        mDataBaseHelper.deleteAll();
        Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show();

    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return Objects.requireNonNull(cm).getActiveNetworkInfo() != null && Objects.requireNonNull(cm.getActiveNetworkInfo()).isConnected();
    }

    private void proBarVisible(Boolean boolen) {

        if (boolen) {
            mSyncDaata.setVisibility(View.INVISIBLE);
            mSyncProgress.setVisibility(View.VISIBLE);
        } else {
            mSyncDaata.setVisibility(View.VISIBLE);
            mSyncProgress.setVisibility(View.INVISIBLE);
        }

    }


}
