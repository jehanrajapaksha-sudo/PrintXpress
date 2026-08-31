package com.example.PrintXpress;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.printxpress.R;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<OrderEntity> orders;
    private AppDatabase db;

    public OrderAdapter(List<OrderEntity> orders, AppDatabase db) {
        this.orders = orders;
        this.db = db;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderEntity order = orders.get(position);
        holder.orderId.setText("Order #" + order.getOrderNumber());
        holder.productName.setText(order.getProductName() + " (Qty: " + order.getQuantity() + ")");
        holder.status.setText("Status: " + order.getStatus());
        holder.dateText.setText("Placed on: " + order.getDate());

        if ("Processing".equals(order.getStatus())) {
            holder.btnCancel.setVisibility(View.VISIBLE);
        } else {
            holder.btnCancel.setVisibility(View.GONE);
        }

        holder.btnCancel.setOnClickListener(v -> {
            db.orderDao().delete(order);
            Toast.makeText(v.getContext(), "Order " + order.getOrderNumber() + " cancelled.", Toast.LENGTH_SHORT).show();
            orders.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, orders.size());
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderId;
        TextView productName;
        TextView status;
        TextView dateText;
        Button btnCancel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.order_id);
            productName = itemView.findViewById(R.id.order_product);
            status = itemView.findViewById(R.id.order_status);
            dateText = itemView.findViewById(R.id.order_date);
            btnCancel = itemView.findViewById(R.id.btn_cancel_order);
        }
    }
}