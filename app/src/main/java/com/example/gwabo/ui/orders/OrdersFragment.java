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
import com.example.gwabo.ui.payment.PaymentDetailsFragment;

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
        // Navigate to the PaymentDetailsFragment
        PaymentDetailsFragment paymentDetailsFragment = new PaymentDetailsFragment();

        // Add custom animations for the fragment transition
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_up,     // Enter animation
                        R.anim.fade_out,     // Exit animation
                        R.anim.fade_in,      // Pop enter animation (when coming back)
                        R.anim.slide_down    // Pop exit animation (when going back)
                )
                .replace(R.id.fragment_container, paymentDetailsFragment) // Ensure the container ID matches your activity's layout
                .addToBackStack(null) // Add to back stack to allow back navigation
                .commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
