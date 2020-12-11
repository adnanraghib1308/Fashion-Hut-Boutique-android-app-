package com.example.fashionhutboutique;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class checkOrder extends AppCompatActivity {

    EditText check;
    Button buttoncheck;
    Button buttonaddOrder;
    TextView t1,t2,t3,t4, t5;
    Order orderFind = new Order();
    CardView cv;

    DatabaseReference databaseOrder, databaseUserOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_order);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        check = findViewById(R.id.findBillNumber);
        buttoncheck = findViewById(R.id.buttonCheck);
        buttonaddOrder = findViewById(R.id.buttonAdd);
        t1 = findViewById(R.id.billNumberText);
        t2 = findViewById(R.id.amountText);
        t3 = findViewById(R.id.bookText);
        t4 = findViewById(R.id.delText);
        t5 = findViewById(R.id.statusText);
        cv = findViewById(R.id.l13);
        databaseOrder = FirebaseDatabase.getInstance().getReference("orders");
        databaseUserOrder = FirebaseDatabase.getInstance().getReference("userorders");

        buttonaddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userid = user.getUid();
                String id = databaseUserOrder.child(userid).push().getKey();
                databaseUserOrder.child(userid).child(id).setValue(orderFind);
                Toast.makeText(checkOrder.this, "Order added Successfully", Toast.LENGTH_SHORT).show();
            }
        });
        buttoncheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean notFound = true;
                        String toCheck = check.getText().toString().trim();
                        for(DataSnapshot x: dataSnapshot.getChildren()){
                            Order order = x.getValue(Order.class);
                            if(toCheck.equals(order.billNumber)){
                                t1.setText(order.billNumber);
                                t2.setText(order.amount);
                                t3.setText(order.bookingDate);
                                t4.setText(order.deliveryDate);
                                t5.setText(order.orderStatus);
                                buttonaddOrder.setVisibility(View.VISIBLE);
                                cv.setVisibility(View.VISIBLE);
                               orderFind = order;
                                notFound = false;
                            }
                        }
                        if(notFound){
                            Toast.makeText(checkOrder.this, "No Such order found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                       // Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                        // ...
                    }
                };
                databaseOrder.addValueEventListener(postListener);
            }
        });
    }
}