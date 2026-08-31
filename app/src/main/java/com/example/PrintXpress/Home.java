package com.example.PrintXpress;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.printxpress.R;
import java.util.ArrayList;
import java.util.List;

public class Home extends Fragment implements ProductAdapter.OnProductClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);

        // Categories RecyclerView
        RecyclerView recyclerCategories = view.findViewById(R.id.recycler_categories);
        recyclerCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        List<Product> categoryList = new ArrayList<>();
        categoryList.add(new Product("1", "Business Cards", "Premium quality business cards", 500, R.drawable.business));
        categoryList.add(new Product("2", "Posters", "Vibrant A3 posters", 1200, R.drawable.posters));
        categoryList.add(new Product("3", "T-Shirts", "Custom printed cotton t-shirts", 2500, R.drawable.shirt));
        categoryList.add(new Product("4", "Mugs", "Ceramic mugs with your photo", 1500,R.drawable.mug));
        categoryList.add(new Product("5", "Banners", "Large format vinyl banners", 3000, R.drawable.banner));
        categoryList.add(new Product("6", "Flyers", "High-quality promotional flyers", 800, R.drawable.fly));
        categoryList.add(new Product("7", "Stickers", "Custom die-cut stickers", 300, R.drawable.sti));

        ProductAdapter categoryAdapter = new ProductAdapter(categoryList, this);
        recyclerCategories.setAdapter(categoryAdapter);

        // Creations RecyclerView


        return view;
    }

    @Override
    public void onProductClick(Product product) {
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, ProductDetail.newInstance(product))
                .addToBackStack(null)
                .commit();
    }
}
