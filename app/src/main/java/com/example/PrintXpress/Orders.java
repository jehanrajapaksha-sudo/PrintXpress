package com.example.PrintXpress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.printxpress.R;
import java.util.List;

public class Orders extends Fragment {

    private AppDatabase db;
    private SessionManager sessionManager;
    private RecyclerView recyclerView;
    private TextView emptyText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orders, container, false);

        db = AppDatabase.getInstance(requireContext());
        sessionManager = new SessionManager(requireContext());
        recyclerView = view.findViewById(R.id.recycler_orders);
        emptyText = view.findViewById(R.id.text_no_orders);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        if (sessionManager.isLoggedIn()) {
            loadOrders();
        } else {
            showEmpty("Please login to view your orders.");
        }

        return view;
    }

    private void loadOrders() {
        List<OrderEntity> orderList = db.orderDao().getOrdersByUserId(sessionManager.getUserId());

        if (!orderList.isEmpty()) {
            OrderAdapter adapter = new OrderAdapter(orderList, db);
            recyclerView.setAdapter(adapter);
            recyclerView.setVisibility(View.VISIBLE);
            emptyText.setVisibility(View.GONE);
        } else {
            showEmpty("You have no active orders.");
        }
    }

    private void showEmpty(String message) {
        recyclerView.setVisibility(View.GONE);
        emptyText.setVisibility(View.VISIBLE);
        emptyText.setText(message);
    }
}