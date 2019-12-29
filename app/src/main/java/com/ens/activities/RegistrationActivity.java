package com.ens.activities;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ens.R;
import com.ens.bus.FileUploadedEvent;
import com.ens.bus.UserCreatedEvent;
import com.ens.model.user.UserRequest;
import com.ens.model.user.UserType;
import com.ens.service.ContentService;
import com.ens.service.UserService;
import com.ens.utils.AppUtils;
import com.ens.utils.NetworkState;
import com.google.android.material.textfield.TextInputEditText;
import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;

import java.io.File;

import androidx.appcompat.app.AppCompatActivity;
import de.greenrobot.event.EventBus;

import static com.ens.utils.AppUtils.CAMERA_REQUEST_CODE;
import static com.ens.utils.AppUtils.GALLERY_REQUEST_CODE;
import static com.ens.utils.AppUtils.PERMISSION_REQUEST_CODE;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = RegistrationActivity.class.getCanonicalName();

    private EventBus eventBus = EventBus.getDefault();

    private UserService userService;

    @BindView(R.id.txtUserName)
    private TextInputEditText txtUserName;

    @BindView(R.id.txtEmail)
    private TextInputEditText txtEmail;

    @BindView(R.id.txtMobileNumber)
    private TextInputEditText txtMobileNumber;

    @BindView(R.id.txtPassword)
    private TextInputEditText txtPassword;

    @BindView(R.id.btnRegister)
    private Button btnRegister;

    @BindView(R.id.lnkLogin)
    private TextView lnkLogin;

    @BindView(R.id.profileImageView)
    private ImageView profileImageView;

    private String cameraFilePath;

    private ContentService contentService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ButterKnifeLite.bind(this);

        userService = new UserService(this);
        contentService = new ContentService(this);

        btnRegister.setOnClickListener(this);
        lnkLogin.setOnClickListener(this);
        profileImageView.setOnClickListener(this);

        if (!AppUtils.checkPermission(this)) {
            AppUtils.requestPermission(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!eventBus.isRegistered(this)) {
            eventBus.register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (eventBus.isRegistered(this)) {
            eventBus.unregister(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnRegister: {

                if (!NetworkState.isInternetAvailable(this)) {
                    Toast.makeText(this, "Please make sure internet is available", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (AppUtils.hasImage(profileImageView)) {
                    File imageFile = new File(cameraFilePath);
                    contentService.uploadFile(imageFile);
                } else {
                    submitUserRequest(null);
                }


            }
            break;

            case R.id.lnkLogin: {
                redirectToLogin();
            }
            break;

            case R.id.profileImageView: {

                this.cameraFilePath = AppUtils.selectImage(this);

            }
            break;
        }

    }

    private void submitUserRequest(String profileImageUrl) {

        String userName = txtUserName.getText().toString();
        String email = txtEmail.getText().toString();
        String mobileNumber = txtMobileNumber.getText().toString();
        String password = txtPassword.getText().toString();

        UserRequest userRequest = new UserRequest();

        userRequest.setMobileNumber(mobileNumber);
        userRequest.setPassword(password);
        userRequest.setEmail(email);
        userRequest.setUserName(userName);
        userRequest.setUserType(UserType.USER);
        userRequest.setProfileImageUrl(profileImageUrl);

        userService.createUser(userRequest);

    }

    public void onEvent(UserCreatedEvent userCreatedEvent) {

        if (userCreatedEvent.getApiResponse() != null) {

            Toast.makeText(this, "User Created Successfully, Please login", Toast.LENGTH_LONG).show();

            redirectToLogin();
        }

    }

    private void redirectToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {

            case PERMISSION_REQUEST_CODE: {

                if (grantResults.length > 0) {

                    if (!AppUtils.isPermissionsGranted(grantResults)) {

                        AppUtils.requestPermission(this);

                    }

                }

            }
            break;

        }

    }

    public void onEvent(FileUploadedEvent fileUploadedEvent) {

        if (fileUploadedEvent != null && fileUploadedEvent.getContentResponse() != null) {

            Log.d(TAG, "### Content URL : " + fileUploadedEvent.getContentResponse().getDownloadUrl());

            submitUserRequest(fileUploadedEvent.getContentResponse().getDownloadUrl());

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            switch (requestCode) {

                case CAMERA_REQUEST_CODE: {

                    profileImageView.setImageURI(Uri.parse(cameraFilePath));

                }
                break;

                case GALLERY_REQUEST_CODE: {

                    Uri selectedImage = data.getData();

                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    // Set the Image in ImageView after decoding the String
                    profileImageView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));

                    cameraFilePath = imgDecodableString;

                    //newsImageView.setImageURI(selectedImage);

                }
                break;

            }

        }
    }
}
