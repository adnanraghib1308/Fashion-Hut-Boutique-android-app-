package com.example.fashionhutboutique;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class AddOrder extends AppCompatActivity {

    EditText billNumber;
    EditText amount;
    EditText bookingDate;
    EditText deliveryDate;
    Button save;


    DatabaseReference databaseOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        billNumber = findViewById(R.id.billNumber);
        amount = findViewById(R.id.amount);
        bookingDate = findViewById(R.id.bookingDate);
        deliveryDate = findViewById(R.id.deliveryDate);
        save = findViewById(R.id.saveButton);

        databaseOrder = FirebaseDatabase.getInstance().getReference("orders");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOrder();
            }
        });

    }
    protected void addOrder(){

        String billno = billNumber.getText().toString().trim();
        String amt = amount.getText().toString();
        String bkndate = bookingDate.getText().toString();
        String deldate = deliveryDate.getText().toString();

        if(!TextUtils.isEmpty(billno) && !TextUtils.isEmpty(amt)){
           String id = databaseOrder.push().getKey();
            Order order = new Order(id, billno, amt, bkndate, deldate);
            databaseOrder.child(billno).setValue(order);
            Toast.makeText(this, "Order Added Successfully " + order.orderStatus, Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(this, "Please fill all the values", Toast.LENGTH_SHORT).show();
        }
        //Toast.makeText(this, "ghfhgkfkfk", Toast.LENGTH_SHORT).show();
    }
}