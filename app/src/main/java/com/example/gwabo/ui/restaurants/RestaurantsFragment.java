package com.example.gwabo.ui.restaurants;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gwabo.databinding.FragmentRestaurantsBinding;

public class RestaurantsFragment extends Fragment {

    private FragmentRestaurantsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RestaurantsViewModel restaurantsViewModel =
                new ViewModelProvider(this).get(RestaurantsViewModel.class);

        binding = FragmentRestaurantsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // final TextView textView = binding.textNotifications;
        // notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}