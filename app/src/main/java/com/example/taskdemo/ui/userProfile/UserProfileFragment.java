package com.example.taskdemo.ui.userProfile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.taskdemo.R;
import com.example.taskdemo.databinding.FragmentUserProfileBinding;
import com.example.taskdemo.model.userProfile.UserProfile;
import com.example.taskdemo.utils.ApplicationSharedPreferences;
import com.example.taskdemo.utils.BitmapUtils;
import com.example.taskdemo.utils.Constants;
import com.example.taskdemo.utils.HelperMethod;
import com.example.taskdemo.utils.PermissionHandler;
import com.theartofdev.edmodo.cropper.CropImage;

public class UserProfileFragment extends Fragment {

    private FragmentUserProfileBinding binding;
    private ApplicationSharedPreferences sharedPreferences;
    private boolean isBackgroundImage = false;
    private boolean isEditProfile = false;
    private Context mContext;
    PermissionHandler mPermissionHandler;

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mContext = getContext();
        sharedPreferences = new ApplicationSharedPreferences(requireContext());

        // Setup toolbar
        ((AppCompatActivity) requireActivity()).setSupportActionBar(binding.toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle(R.string.user_profile);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        binding.toolbar.setTitleTextColor(Color.WHITE);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white);

        // Initialize PermissionHandler
        mPermissionHandler = new PermissionHandler(requireActivity());

        Bundle bundle = getArguments();
        if (bundle != null) {
            isEditProfile = bundle.getBoolean(Constants.IS_EDIT_PROFILE, false);

            if (isEditProfile) {
                binding.btnSubmit.setText(R.string.save_changes);
                loadUserProfile();
            } else {
                clearProfileData();
                binding.btnSubmit.setText(R.string.submit);
            }
        }

        // Set click listeners for profile and background image
        binding.ivProfileImageClick.setOnClickListener(v -> {
            isBackgroundImage = false;
            showImagePickerDialog(isBackgroundImage);
        });

        binding.btnCancel.setOnClickListener(v -> {
            Navigation.findNavController(requireView()).navigate(R.id.navigation_Setting);
        });
        binding.ivBackgroundImageClick.setOnClickListener(v -> {
            isBackgroundImage = true;
            showImagePickerDialog(isBackgroundImage);
        });

        // Set click listener for submit button
        binding.btnSubmit.setOnClickListener(v -> {
            String name = binding.edtUserName.getText().toString();
            String email = binding.edtUserEmail.getText().toString();
            String address = binding.edtUserAddress.getText().toString();
            String phoneNo = binding.edtPhoneNo.getText().toString();
            String bio = binding.edtUserBio.getText().toString();
            String profileImageUri = (String) binding.ivUserProfileImage.getTag();
            String backgroundImageUri = (String) binding.ivBackgroundImage.getTag();

            // Validate input fields
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
                HelperMethod.showToast(getString(R.string.select_profile_image_message), mContext);
                return;
            }
            if (backgroundImageUri == null || backgroundImageUri.isEmpty()) {
                HelperMethod.showToast(getString(R.string.select_background_image_message), mContext);
                return;
            }

            // Save profile data
            sharedPreferences.saveUserProfile(name, email, address, profileImageUri, backgroundImageUri, phoneNo, bio);

            if (isEditProfile) {
                HelperMethod.showToast(getString(R.string.profile_updated_successfully), mContext);
            } else {
                HelperMethod.showToast(getString(R.string.profile_created_successfully), mContext);
            }
            Navigation.findNavController(requireView()).navigate(R.id.navigation_user_detail);
        });

        // TextWatcher for bio character limit
        binding.edtUserBio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = s.length();
                binding.tvBioCharLimit.setText(length + "/300");

                if (length > 300) {
                    binding.tvBioCharLimit.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                    HelperMethod.showToast(getString(R.string.bio_length_exceeded_message), mContext);
                    binding.edtUserBio.setText(s.subSequence(0, 300));
                    binding.edtUserBio.setSelection(300);
                } else if (length == 300) {
                    binding.tvBioCharLimit.setTextColor(ContextCompat.getColor(mContext, R.color.red));
                } else {
                    binding.tvBioCharLimit.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return root;
    }

    // Show dialog to choose between gallery and camera
    private void showImagePickerDialog(boolean isBackgroundImage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.choose_an_option);
        builder.setItems(new CharSequence[]{getString(R.string.gallery), getString(R.string.camera)}, (dialog, which) -> {
            if (which == 0) {
                openGallery();
            } else {
                openCamera();
            }
        });
        builder.show();
    }

    // Launch camera to capture image
    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == AppCompatActivity.RESULT_OK && result.getData() != null) {
                    Bundle extras = result.getData().getExtras();
                    if (extras != null) {
                        Bitmap imageBitmap = (Bitmap) extras.get("data");
                        Uri imageUri = BitmapUtils.saveImageToInternalStorage(requireContext(), imageBitmap);
                        startCropActivity(imageUri);
                    }
                }
            }
    );

    // Launch gallery to select image
    private final ActivityResultLauncher<String> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            uri -> {
                if (uri != null) {
                    startCropActivity(uri);
                }
            }
    );

    // Start crop activity for selected image
    private void startCropActivity(Uri uri) {
        CropImage.activity(uri)
                .setAspectRatio(1, 1)
                .start(requireContext(), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == AppCompatActivity.RESULT_OK) {
                Uri resultUri = result.getUri();
                if (isBackgroundImage) {
                    binding.ivBackgroundImage.setImageURI(resultUri);
                    binding.ivBackgroundImage.setTag(resultUri.toString());
                } else {
                    binding.ivUserProfileImage.setImageURI(resultUri);
                    binding.ivUserProfileImage.setTag(resultUri.toString());
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                error.printStackTrace();
                HelperMethod.showToast(getString(R.string.select_profile_image_message), mContext);
            }
        }
    }

    // Open gallery to select image
    private void openGallery() {
        if (mPermissionHandler.checkStoragePermission()) {
            galleryLauncher.launch("image/*");
        } else {
            mPermissionHandler.requestPermissions();
        }
    }

    // Open camera to capture image
    private void openCamera() {
        if (mPermissionHandler.checkStoragePermission()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(intent);
        } else {
            mPermissionHandler.requestPermissions();
        }
    }

    // Load user profile data from shared preferences
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
            Glide.with(this).load(R.drawable.image).into(binding.ivUserProfileImage);
        }

        if (userProfile.getBackgroundImage() != null && !userProfile.getBackgroundImage().isEmpty()) {
            Uri backgroundUri = Uri.parse(userProfile.getBackgroundImage());
            Glide.with(this).load(backgroundUri).into(binding.ivBackgroundImage);
            binding.ivBackgroundImage.setTag(userProfile.getBackgroundImage());
        } else {
            Glide.with(this).load(R.drawable.background).into(binding.ivBackgroundImage);
        }
    }

    // Clear profile data fields
    private void clearProfileData() {
        binding.edtUserName.setText("");
        binding.edtUserEmail.setText("");
        binding.edtUserAddress.setText("");
        binding.edtPhoneNo.setText("");
        binding.edtUserBio.setText("");
        binding.ivUserProfileImage.setImageDrawable(null);
        binding.ivBackgroundImage.setImageDrawable(null);
        binding.ivUserProfileImage.setTag(null);
        binding.ivBackgroundImage.setTag(null);
    }
}