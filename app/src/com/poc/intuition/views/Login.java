package com.poc.intuition.views;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.poc.intuition.R;
import com.poc.intuition.service.IListener;
import com.poc.intuition.service.LoginService;
import com.poc.intuition.service.PurchaseCategoryService;
import com.poc.intuition.service.UserSessionService;

public class Login extends Activity implements IListener<Boolean> {

    private LoginService loginService;
    private UserSessionService userSessionService;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
//        getActionBar().hide();

        loginService = new LoginService(this.getApplicationContext(), this);
        userSessionService = new UserSessionService(getApplicationContext());
        wireUiEvents();
    }

    private void wireUiEvents() {
        this.findViewById(R.id.login_button).setOnClickListener(loginButtonClickListener());
    }

    private View.OnClickListener loginButtonClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog = ProgressDialog.show(Login.this, "Logging in", "You are being logged in", true);
                String username = ((EditText) Login.this.findViewById(R.id.user_name_value)).getText().toString();
                loginService.loginForUserWithName(username);
            }
        };
    }

    @Override
    public void serviceResponse(Boolean isLoginSuccessful) {
        if (isLoginSuccessful) {
            userSessionService.loginUser(((EditText) Login.this.findViewById(R.id.user_name_value)).getText().toString());
            PurchaseCategoryService.singleInstance(this.getApplicationContext()).fetchCategoriesForUser();
            progressDialog.dismiss();
            if (userSessionService.isUserOnboardingComplete()) {
//                showHomeScreen();
                startUserOnboarding();
            } else {
                startUserOnboarding();
            }
        } else {
            showLoginError();
        }
    }

    private void startUserOnboarding() {
        Intent setupWizard = new Intent(this, SetupWizard.class);
        this.startActivity(setupWizard);
    }

    private void showHomeScreen() {
        Intent setupWizard = new Intent(this, Dashboard.class);
        this.startActivity(setupWizard);
    }

    private void showLoginError() {
        if(progressDialog != null  && progressDialog.isShowing()) progressDialog.dismiss();
        Toast.makeText(this.getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
    }
}
