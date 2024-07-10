package com.example.taskdemo.ui.userProfile;

import static com.example.taskdemo.utils.HelperMethod.showToast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.taskdemo.R;
import com.example.taskdemo.databinding.FragmentUserProfileBinding;
import com.example.taskdemo.model.userProfile.UserProfile;
import com.example.taskdemo.utils.ApplicationSharedPreferences;
import com.example.taskdemo.utils.BitmapUtils;
import com.example.taskdemo.utils.HelperMethod;

import java.io.IOException;
import java.io.InputStream;

public class UserProfileFragment extends Fragment {

    private UserProfileViewModel mViewModel;
    private FragmentUserProfileBinding binding;
    private ApplicationSharedPreferences sharedPreferences;
    private boolean isBackgroundImage = false;
    private boolean isEditProfile = false;
    private  Context mContext;

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(UserProfileViewModel.class);
        View root = binding.getRoot();
        mContext = getContext();
        sharedPreferences = new ApplicationSharedPreferences(requireContext());
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(R.string.user_profile);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);
        Bundle bundle = getArguments();
        if (bundle != null) {
            isEditProfile = bundle.getBoolean("isEditProfile", false);

            if (isEditProfile) {
                binding.btnSubmit.setText("Save Changes");
                loadUserProfile();
            } else {
                clearProfileData();
                binding.btnSubmit.setText("Submit");
            }
        }

        binding.ivProfileImageClick.setOnClickListener(v -> {
            isBackgroundImage = false;
            showImagePickerDialog();
        });

        binding.ivBackgroundImageClick.setOnClickListener(v -> {
            isBackgroundImage = true;
            showImagePickerDialog();
        });

        binding.btnSubmit.setOnClickListener(v -> {
            String name = binding.edtUserName.getText().toString();
            String email = binding.edtUserEmail.getText().toString();
            String address = binding.edtUserAddress.getText().toString();
            String phoneNo = binding.edtPhoneNo.getText().toString();
            String bio = binding.edtUserBio.getText().toString();
            String profileImageUri = (String) binding.ivUserProfileImage.getTag();
            String backgroundImageUri = (String) binding.ivBackgroundImage.getTag();
            if (name.isEmpty()) {
                HelperMethod.showToast(getString(R.string.enter_name_message), mContext);
                return;
            }
            if (email.isEmpty()) {
                HelperMethod.showToast(getString(R.string.enter_email_message), mContext);
                return;
            }

            if (address.isEmpty()) {
                HelperMethod.showToast(getString(R.string.enter_address_message), mContext);
                return;
            }
            if (phoneNo.isEmpty()) {
                HelperMethod.showToast(getString(R.string.enter_phone_message), mContext);
                return;
            }
            if (phoneNo.length() != 10) {
                HelperMethod.showToast(getString(R.string.phone_number_length_message), mContext);
                return;
            }
            if (bio.isEmpty()) {
                HelperMethod.showToast(getString(R.string.enter_bio_message), mContext);
                return;
            }
            if (bio.length() > 300) {
                HelperMethod.showToast(getString(R.string.bio_length_exceeded_message), mContext);
                return;
            }
            if (profileImageUri == null || profileImageUri.isEmpty()) {
                HelperMethod.showToast(getString(R.string.select_profile_image_message),mContext);
                return;
            }
            if (backgroundImageUri == null || backgroundImageUri.isEmpty()) {
                HelperMethod.showToast(getString(R.string.select_background_image_message),mContext);
                return;
            }
            sharedPreferences.saveUserProfile(name, email, address, profileImageUri, backgroundImageUri, phoneNo, bio);
            Navigation.findNavController(requireView()).navigate(R.id.navigation_user_detail);
        });
        // Add TextWatcher for EditText to update character count and restrict input
        binding.edtUserBio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                binding.tvBioCharLimit.setText(length + "/300");

                if (length > 300) {
                    HelperMethod.showToast(getString(R.string.bio_length_exceeded_message), mContext);
                    binding.edtUserBio.setText(s.subSequence(0, 300));
                    binding.edtUserBio.setSelection(300);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        return root;
    }



    private void loadUserProfile() {
        UserProfile userProfile = sharedPreferences.getUserProfile();
        binding.edtUserName.setText(userProfile.getName());
        binding.edtUserEmail.setText(userProfile.getEmail());
        binding.edtUserAddress.setText(userProfile.getAddress());
        binding.edtPhoneNo.setText(userProfile.getPhoneNo());
        binding.edtUserBio.setText(userProfile.getBio());

        if (userProfile.getProfileImage() != null && !userProfile.getProfileImage().isEmpty()) {
            Uri profileUri = Uri.parse(userProfile.getProfileImage());
            Glide.with(this).load(profileUri).into(binding.ivUserProfileImage);
            binding.ivUserProfileImage.setTag(userProfile.getProfileImage());
        } else {
            Glide.with(this).load(R.drawable.nature).into(binding.ivUserProfileImage);
        }

        if (userProfile.getBackgroundImage() != null && !userProfile.getBackgroundImage().isEmpty()) {
            Uri backgroundUri = Uri.parse(userProfile.getBackgroundImage());
            Glide.with(this).load(backgroundUri).into(binding.ivBackgroundImage);
            binding.ivBackgroundImage.setTag(userProfile.getBackgroundImage());
        } else {
            Glide.with(this).load(R.drawable.background).into(binding.ivBackgroundImage);
        }
    }

    private void clearProfileData() {
        binding.edtUserName.setText("");
        binding.edtUserEmail.setText("");
        binding.edtUserAddress.setText("");
        binding.edtPhoneNo.setText("");
        binding.edtUserBio.setText("");
        binding.ivUserProfileImage.setImageResource(R.drawable.nature);
        binding.ivBackgroundImage.setImageResource(R.drawable.background);
        binding.ivUserProfileImage.setTag(null);
        binding.ivBackgroundImage.setTag(null);
    }

    private void showImagePickerDialog() {
        String[] options = {"Camera", "Gallery"};
        new AlertDialog.Builder(requireContext())
                .setTitle("Choose an option")
                .setItems(options, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            openCamera();
                            break;
                        case 1:
                            openGallery();
                            break;
                    }
                })
                .show();
    }

    private void openGallery() {
        galleryLauncher.launch("image/*");
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(intent);
    }

    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == AppCompatActivity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap imageBitmap = (Bitmap) extras.get("data");
                            if (imageBitmap != null) {
                                Uri imageUri = BitmapUtils.saveImageToInternalStorage(requireContext(), imageBitmap);
                                if (isBackgroundImage) {
                                    binding.ivBackgroundImage.setImageBitmap(imageBitmap);
                                    binding.ivBackgroundImage.setTag(imageUri.toString());
                                } else {
                                    binding.ivUserProfileImage.setImageBitmap(imageBitmap);
                                    binding.ivUserProfileImage.setTag(imageUri.toString());
                                }
                            }
                        }
                    }
                }
            }
    );

    private final ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    try {
                        InputStream inputStream = requireContext().getContentResolver().openInputStream(uri);
                        Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                        Uri imageUri = BitmapUtils.saveImageToInternalStorage(requireContext(), imageBitmap);

                        if (isBackgroundImage) {
                            binding.ivBackgroundImage.setImageBitmap(imageBitmap);
                            binding.ivBackgroundImage.setTag(imageUri.toString());
                        } else {
                            binding.ivUserProfileImage.setImageBitmap(imageBitmap);
                            binding.ivUserProfileImage.setTag(imageUri.toString());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    );
}
