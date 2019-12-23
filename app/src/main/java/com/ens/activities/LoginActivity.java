package com.ens.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ens.MainActivity;
import com.ens.R;
import com.ens.bus.UserLoggedInEvent;
import com.ens.config.ENSApplication;
import com.ens.model.user.User;
import com.ens.service.AuthService;
import com.ens.utils.NetworkState;
import com.ens.utils.SharedPrefsUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mindorks.butterknifelite.ButterKnifeLite;
import com.mindorks.butterknifelite.annotations.BindView;

import androidx.appcompat.app.AppCompatActivity;
import de.greenrobot.event.EventBus;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = LoginActivity.class.getCanonicalName();

    @BindView(R.id.editTxtMobile)
    private EditText editTxtMobile;

    @BindView(R.id.editTxtPwd)
    private EditText editTxtPwd;

    @BindView(R.id.btnLogin)
    private Button btnLogin;

    @BindView(R.id.lnkRegister)
    private TextView lnkRegister;

    private AuthService authService;

    private EventBus eventBus = EventBus.getDefault();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnifeLite.bind(this);

        btnLogin.setOnClickListener(this);
        lnkRegister.setOnClickListener(this);
        lnkRegister.setMovementMethod(LinkMovementMethod.getInstance());

        authService = new AuthService(this);

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

            case R.id.btnLogin: {

                if (!NetworkState.isInternetAvailable(this)) {
                    Toast.makeText(this, "Please make sure internet is available", Toast.LENGTH_SHORT).show();
                    return;
                }

                String mobileNumber = editTxtMobile.getText().toString();
                String password = editTxtPwd.getText().toString();

                authService.login(mobileNumber, password);

            }
            break;

            case R.id.lnkRegister: {
                Intent intent = new Intent(this, RegistrationActivity.class);
                startActivity(intent);
                finish();
            }
            break;
        }

    }

    public void onEvent(UserLoggedInEvent userLoggedInEvent) {

        User user = userLoggedInEvent.getUser();

        String userResponse = "";

        try {

            userResponse = ENSApplication.getObjectMapper().writeValueAsString(user);
            Log.i(TAG, "### User Login Response : " + userResponse);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
            Log.e(TAG, "### Error while logging : " + e);
        }

        if (!userResponse.isEmpty()) {

            ENSApplication.saveLoggedInUser(userResponse);
            ENSApplication.saveLoggedInUserId(user.getId());
            SharedPrefsUtils.setBooleanPreference(ENSApplication.getAppContext(), "skipLogin", true);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        } else {

            // Show logging error message to user
            Log.d(TAG, "### Error while user logging in");

        }

    }
}
