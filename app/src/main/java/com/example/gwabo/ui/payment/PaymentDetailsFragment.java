package com.example.gwabo.ui.payment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.gwabo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PaymentDetailsFragment extends DialogFragment {

    private EditText streetEditText, appNEditText, cityEditText, postalCodeEditText;
    private EditText phoneEditText, emailEditText, cardNumberEditText, expDateEditText, cvvEditText;
    private RadioGroup paymentMethodRadioGroup;

    public PaymentDetailsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.payment_details_page, container, false);

        // Initialize views
        streetEditText = view.findViewById(R.id.streetEditText);
        appNEditText = view.findViewById(R.id.appNEditText);
        cityEditText = view.findViewById(R.id.cityEditText);
        postalCodeEditText = view.findViewById(R.id.postalCodeEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        cardNumberEditText = view.findViewById(R.id.cardNumberEditText);
        expDateEditText = view.findViewById(R.id.expDateEditText);
        cvvEditText = view.findViewById(R.id.cvvEditText);
        paymentMethodRadioGroup = view.findViewById(R.id.paymentMethodRadioGroup);

        Button continueButton = view.findViewById(R.id.btnContinue);
        ImageView closeButton = view.findViewById(R.id.btnClose);

        // Set click listener for continue button
        continueButton.setOnClickListener(v -> handlePaymentSubmission());

        // Set click listener for close button
        closeButton.setOnClickListener(v -> {
            // Load the slide-down animation
            Animation slideDown = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_down);

            // Apply the animation to the root view
            View rootView = getView();
            if (rootView != null) {
                rootView.startAnimation(slideDown);

                // Add a listener to dismiss the fragment after the animation ends
                slideDown.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // No action needed here
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // Dismiss the fragment after the animation completes
                        requireActivity().getSupportFragmentManager().beginTransaction().remove(PaymentDetailsFragment.this).commit();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // No action needed here
                    }
                });
            }
        });


        return view;
    }

    private void handlePaymentSubmission() {
        String street = streetEditText.getText().toString();
        String appN = appNEditText.getText().toString();
        String city = cityEditText.getText().toString();
        String postalCode = postalCodeEditText.getText().toString();
        String phone = phoneEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String cardNumber = cardNumberEditText.getText().toString();
        String expDate = expDateEditText.getText().toString();
        String cvv = cvvEditText.getText().toString();

        int selectedPaymentMethodId = paymentMethodRadioGroup.getCheckedRadioButtonId();
        RadioButton selectedPaymentMethod = getView().findViewById(selectedPaymentMethodId);

        if (TextUtils.isEmpty(street) || TextUtils.isEmpty(city) || TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(email) || TextUtils.isEmpty(cardNumber) || TextUtils.isEmpty(expDate) ||
                TextUtils.isEmpty(cvv) || selectedPaymentMethod == null) {
            Toast.makeText(getContext(), "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Send data to backend/database (placeholder function for now)
        savePaymentDetailsToDatabase(street, appN, city, postalCode, phone, email,
                selectedPaymentMethod.getText().toString(), cardNumber, expDate, cvv);
    }

    private void savePaymentDetailsToDatabase(String street, String appN, String city, String postalCode,
                                              String phone, String email, String paymentMethod,
                                              String cardNumber, String expDate, String cvv) {
        OkHttpClient client = new OkHttpClient();

        // Create request body
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("street", street);
            jsonObject.put("appN", appN);
            jsonObject.put("city", city);
            jsonObject.put("postalCode", postalCode);
            jsonObject.put("phone", phone);
            jsonObject.put("email", email);
            jsonObject.put("paymentMethod", paymentMethod);
            jsonObject.put("cardNumber", cardNumber);
            jsonObject.put("expDate", expDate);
            jsonObject.put("cvv", cvv);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"),
                jsonObject.toString()
        );

        Request request = new Request.Builder()
                .url("https://your-hostinger-api-url.com/save-payment")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                getActivity().runOnUiThread(() ->
                        Toast.makeText(getContext(), "Failed to save payment details!", Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                if (response.isSuccessful()) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Payment Details Saved!", Toast.LENGTH_SHORT).show();
                        dismiss();
                    });
                }
            }
        });
    }

}
