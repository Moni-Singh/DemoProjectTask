package com.example.taskdemo.ui.auth.login;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.taskdemo.R;
import com.example.taskdemo.databinding.FragmentLoginBinding;
import com.example.taskdemo.model.request.LoginRequest;
import com.example.taskdemo.model.response.LoginResponse;
import com.example.taskdemo.utils.ApplicationSharedPreferences;
import com.example.taskdemo.utils.HelperMethod;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private Context mContext;
    private LoginViewModel mViewModel;
    private View progressLayout;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mContext = getContext();
        progressLayout = binding.progressLayout.getRoot();
        mViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        final TextView edtUsername = binding.loginUsername;
        final TextView edtPassword = binding.loginPassword;
        binding.btnLogin.setOnClickListener(view -> {
//            String username = edtUsername.getText().toString();
//            String password = edtPassword.getText().toString();
            String username = "mor_2314";
            String password = "83r5^_";

            if (username.isEmpty() && password.isEmpty()) {
                // Show error message for username and password
                HelperMethod.showToast(getString(R.string.username_password_required), mContext);
                return;
            }
            if (username.isEmpty()) {
                // Show error message for empty username field
                HelperMethod.showToast(getString(R.string.username_required), mContext);
                return;
            }
            if (password.isEmpty()) {
                // Show error message for empty username field
                HelperMethod.showToast(getString(R.string.password_required), mContext);
                return;
            }
            if (password.length() < 5) {
                HelperMethod.showToast(getString(R.string.password_requirement_not_met), mContext);
                return;
            }

            progressLayout.setVisibility(View.VISIBLE);
            LoginRequest loginRequest = new LoginRequest(username, password);
            mViewModel.performLogin(loginRequest);

        });

        mViewModel.getLoginResponseObserver().observe(getViewLifecycleOwner(), new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (loginResponse != null) {

                    String token = loginResponse.getToken();
                    // Save token
                    ApplicationSharedPreferences sharedPreferences = new ApplicationSharedPreferences(requireContext());
                    sharedPreferences.saveToken(token);
                    // Navigate to main tab after successful login
                    progressLayout.setVisibility(View.GONE);
                    HelperMethod.showToast(getString(R.string.successfull_login), mContext);
                    Navigation.findNavController(requireView()).navigate(R.id.navigation_mainTab);
                } else {
                    progressLayout.setVisibility(View.GONE);
                    HelperMethod.showToast(getString(R.string.something_went_wrong), mContext);
                }
            }
        });
        return root;
    }
}