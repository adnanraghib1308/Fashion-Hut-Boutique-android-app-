package com.example.fashionhutboutique;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyOrderViewHolder> {

    ArrayList<Order> order = new ArrayList<Order>();
    public  MyOrderAdapter(){

    }
    public MyOrderAdapter(Order order){
        this.order.add(order);
    }

    @NonNull
    @Override
    public MyOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
         View view = inflater.inflate(R.layout.order_items, parent, false);

        return new MyOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderViewHolder holder, int position) {
        Order data = this.order.get(position);
        holder.textViewBill.setText(data.billNumber);
        holder.textViewAmt.setText(data.amount);
        holder.textViewbook.setText(data.bookingDate);
        holder.textViewDel.setText(data.deliveryDate);
        holder.textViewStatus.setText(data.orderStatus);
    }

    @Override
    public int getItemCount() {
        return this.order.size();
    }

    public class MyOrderViewHolder extends RecyclerView.ViewHolder{
         TextView textViewBill, textViewAmt, textViewbook, textViewDel, textViewStatus;
         public MyOrderViewHolder(@NonNull View itemView) {
             super(itemView);
             textViewBill = itemView.findViewById(R.id.billNumberText);
             textViewAmt = itemView.findViewById(R.id.amountText);
             textViewbook = itemView.findViewById(R.id.bookText);
             textViewDel = itemView.findViewById(R.id.delText);
             textViewStatus = itemView.findViewById(R.id.statusText);
         }
     }
}
