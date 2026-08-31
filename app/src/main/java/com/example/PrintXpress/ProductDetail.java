package com.example.PrintXpress;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.printxpress.R;
import com.google.android.material.textfield.TextInputEditText;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class ProductDetail extends Fragment {

    private String productName;
    private String productDescription;
    private int productImage;
    private AppDatabase db;
    private SessionManager sessionManager;
    private TextInputEditText editQuantity, editCustomText;
    private AutoCompleteTextView paperSpinner, deliverySpinner;
    private Uri selectedFileUri;

    private final ActivityResultLauncher<Intent> filePickerLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    selectedFileUri = result.getData().getData();
                    Toast.makeText(requireContext(), "File selected: " + selectedFileUri.getLastPathSegment(), Toast.LENGTH_SHORT).show();
                }
            }
    );

    public static ProductDetail newInstance(Product product) {
        ProductDetail fragment = new ProductDetail();
        Bundle args = new Bundle();
        args.putString("name", product.getName());
        args.putString("description", product.getDescription());
        args.putInt("image", product.getImageResourceId());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            productName = getArguments().getString("name");
            productDescription = getArguments().getString("description");
            productImage = getArguments().getInt("image");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_detail, container, false);

        db = AppDatabase.getInstance(requireContext());
        sessionManager = new SessionManager(requireContext());

        ImageView imageView = view.findViewById(R.id.detail_image);
        TextView nameTextView = view.findViewById(R.id.detail_name);
        TextView descTextView = view.findViewById(R.id.detail_description);
        paperSpinner = view.findViewById(R.id.spinner_paper_type);
        deliverySpinner = view.findViewById(R.id.spinner_delivery);
        editQuantity = view.findViewById(R.id.edit_quantity);
        editCustomText = view.findViewById(R.id.edit_custom_text);
        Button uploadButton = view.findViewById(R.id.btn_upload_design);
        Button orderButton = view.findViewById(R.id.btn_place_order);

        nameTextView.setText(productName);
        descTextView.setText(productDescription);
        imageView.setImageResource(productImage);

        String[] paperTypes = {"Standard (250gsm)", "Premium (350gsm)", "Matte Finish", "Glossy Finish"};
        ArrayAdapter<String> paperAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, paperTypes);
        paperSpinner.setAdapter(paperAdapter);
        paperSpinner.setText(paperTypes[0], false);

        String[] deliveryOptions = {"Pickup", "Home Delivery"};
        ArrayAdapter<String> deliveryAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, deliveryOptions);
        deliverySpinner.setAdapter(deliveryAdapter);
        deliverySpinner.setText(deliveryOptions[0], false);

        uploadButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            String[] mimeTypes = {"image/*", "application/pdf"};
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            filePickerLauncher.launch(intent);
        });

        orderButton.setOnClickListener(v -> handlePlaceOrder());

        return view;
    }

    private void handlePlaceOrder() {
        if (!sessionManager.isLoggedIn()) {
            Toast.makeText(requireContext(), "Please login to place an order", Toast.LENGTH_LONG).show();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Login())
                    .addToBackStack(null)
                    .commit();
            return;
        }

        if (editQuantity.getText() == null) return;
        String qtyStr = editQuantity.getText().toString().trim();
        if (TextUtils.isEmpty(qtyStr)) {
            editQuantity.setError("Quantity is required");
            return;
        }

        int quantity;
        try {
            quantity = Integer.parseInt(qtyStr);
        } catch (NumberFormatException e) {
            editQuantity.setError("Invalid quantity");
            return;
        }

        if (quantity <= 0) {
            editQuantity.setError("Quantity must be greater than 0");
            return;
        }

        String paperType = paperSpinner.getText().toString();
        String deliveryOption = deliverySpinner.getText().toString();
        String customText = editCustomText.getText() != null ? editCustomText.getText().toString().trim() : "";
        String orderNumber = "PX-" + (1000 + new Random().nextInt(9000));
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        OrderEntity order = new OrderEntity(0, orderNumber, productName, "Processing", date, quantity, paperType, customText, deliveryOption, sessionManager.getUserId());
        long result = db.orderDao().insert(order);

        if (result != -1) {
            Toast.makeText(requireContext(), "Order " + orderNumber + " placed successfully!", Toast.LENGTH_LONG).show();
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Orders())
                    .commit();
        } else {
            Toast.makeText(requireContext(), "Failed to place order.", Toast.LENGTH_SHORT).show();
        }
    }
}