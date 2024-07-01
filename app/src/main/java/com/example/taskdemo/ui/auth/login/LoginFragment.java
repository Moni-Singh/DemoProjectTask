package com.example.taskdemo.ui.auth.login;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
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
        Window window = getActivity().getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(getContext(),R.color.red));

        final TextView edtUsername = binding.loginUsername;
        final TextView edtPassword = binding.loginPassword;

        requireActivity().getOnBackPressedDispatcher().addCallback(requireActivity(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                showExitConfirmationDialog();
            }
        });
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
            if (HelperMethod.isNetworkAvailable(mContext)){
                LoginRequest loginRequest = new LoginRequest(username, password);
                mViewModel.performLogin(loginRequest);
            }else{
                HelperMethod.showGeneralNICToast(mContext);
            }
        });

        mViewModel.getLoginResponseObserver().observe(getViewLifecycleOwner(), new Observer<LoginResponse>() {
            @Override
            public void onChanged(LoginResponse loginResponse) {
                if (loginResponse != null) {
                    String token = loginResponse.getToken();
                    // Save token
                    ApplicationSharedPreferences sharedPreferences = new ApplicationSharedPreferences(requireContext());
                    sharedPreferences.saveToken(token);
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

    private void showExitConfirmationDialog() {
        new AlertDialog.Builder(mContext)
                .setTitle(R.string.confirm_exit_title)
                .setMessage(R.string.confirm_exit_message)
                .setPositiveButton(R.string.yes, (dialog, which) -> {
                    // Close the app
                    requireActivity().finish();
                })
                .setNegativeButton(R.string.no, (dialog, which) -> {
                    // Dismiss the dialog
                    dialog.dismiss();
                })
                .show();
    }
}