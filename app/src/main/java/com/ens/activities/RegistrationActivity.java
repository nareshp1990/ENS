package com.ens.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ens.R;
import com.ens.bus.UserCreatedEvent;
import com.ens.model.user.UserRequest;
import com.ens.service.UserService;
import com.ens.utils.NetworkState;
import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;

import androidx.appcompat.app.AppCompatActivity;
import de.greenrobot.event.EventBus;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = RegistrationActivity.class.getCanonicalName();

    private EventBus eventBus = EventBus.getDefault();

    private UserService userService;

    @BindView(R.id.txtUserName)
    private EditText txtUserName;

    @BindView(R.id.txtEmail)
    private EditText txtEmail;

    @BindView(R.id.txtMobileNumber)
    private EditText txtMobileNumber;

    @BindView(R.id.txtPassword)
    private EditText txtPassword;

    @BindView(R.id.btnRegister)
    private Button btnRegister;

    @BindView(R.id.lnkLogin)
    private TextView lnkLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ButterKnifeLite.bind(this);

        userService = new UserService(this);

        btnRegister.setOnClickListener(this);
        lnkLogin.setOnClickListener(this);


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

                String userName = txtUserName.getText().toString();
                String email = txtEmail.getText().toString();
                String mobileNumber = txtMobileNumber.getText().toString();
                String password = txtPassword.getText().toString();

                UserRequest userRequest = new UserRequest();

                userRequest.setMobileNumber(mobileNumber);
                userRequest.setPassword(password);
                userRequest.setEmail(email);
                userRequest.setUserName(userName);

                userService.createUser(userRequest);

            }
            break;

            case R.id.lnkLogin: {
                redirectToLogin();
            }
            break;
        }

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


}
