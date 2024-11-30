package com.example.gwabo.ui.orders;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gwabo.R;
import com.example.gwabo.adapter.CartAdapter;
import com.example.gwabo.model.CartManager;
import com.example.gwabo.databinding.FragmentOrdersBinding;

import java.util.Objects;

public class OrdersFragment extends Fragment {

    private FragmentOrdersBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOrdersBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView cartRecyclerView = binding.cartRecyclerView;
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Get cart items from CartManager and set the adapter
        CartAdapter cartAdapter = new CartAdapter(CartManager.getCart(), getContext());
        cartRecyclerView.setAdapter(cartAdapter);

        // Set button listener
        binding.btnPlaceOrder.setOnClickListener(v -> showPaymentDetails());

        return root;
    }

    private void showPaymentDetails() {
        // Inflate the payment details view
        @SuppressLint("InflateParams") View paymentView = LayoutInflater.from(getContext()).inflate(R.layout.payment_details_page, null);

        // Add the payment view to a FrameLayout or container
        FrameLayout rootLayout = requireActivity().findViewById(android.R.id.content);
        rootLayout.addView(paymentView);

        // Apply the slide-up animation
        Animation slideUp = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);
        paymentView.startAnimation(slideUp);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
