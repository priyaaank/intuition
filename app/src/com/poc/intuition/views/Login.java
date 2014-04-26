package com.poc.intuition.views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.poc.intuition.R;
import com.poc.intuition.service.IListener;
import com.poc.intuition.service.LoginService;
import com.poc.intuition.service.UserSessionService;

public class Login extends Activity implements IListener<Boolean> {

  private LoginService loginService;
  private UserSessionService userSessionService;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login);

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
        String username = ((EditText) Login.this.findViewById(R.id.user_name_value)).getText().toString();
        loginService.loginForUserWithName(username);
      }
    };
  }

  @Override
  public void serviceResponse(Boolean isLoginSuccessful) {
    if(isLoginSuccessful) {
      userSessionService.loginUser(((EditText) Login.this.findViewById(R.id.user_name_value)).getText().toString());
      showHomeScreen();
    } else {
      showLoginError();
    }
  }

  private void showHomeScreen() {
    Intent homeActivityIntent = new Intent (this, TransactionListing.class);
    this.startActivity(homeActivityIntent);
  }

  private void showLoginError() {
    Toast.makeText(this.getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
  }
}
