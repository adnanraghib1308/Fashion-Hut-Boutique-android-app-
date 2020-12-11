package com.example.fashionhutboutique;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyOrders extends AppCompatActivity {


    MyOrderAdapter myOrderAdapter = new MyOrderAdapter();
    Order orderGet;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.pbar);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = findViewById(R.id.myorders);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (user!=null){
            loadMyOrders();
        }
        else{
            Toast.makeText(this, "You need to login first...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MyOrders.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void loadMyOrders() {
        final String userid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseUserOrder = FirebaseDatabase.getInstance().getReference("userorders/"+userid);
        databaseUserOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot x: snapshot.getChildren()){
                    Order order = x.getValue(Order.class);
                    myOrderAdapter.order.add(order);
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(myOrderAdapter);
                }

                Toast.makeText(MyOrders.this, "count", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}