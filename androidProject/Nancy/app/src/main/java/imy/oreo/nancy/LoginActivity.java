package imy.oreo.nancy;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.parse.*;


public class LoginActivity extends ActionBarActivity {

    private TextInputLayout emailWrapper;
    private TextInputLayout passwordWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        InitParse.initParse(this);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        emailWrapper = (TextInputLayout)  findViewById(R.id.loginEmailWrapper);
        passwordWrapper = (TextInputLayout) findViewById(R.id.loginPasswordWrapper);

        emailWrapper.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                emailWrapper.getEditText().setError(null);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordWrapper.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                passwordWrapper.getEditText().setError(null);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void goToRegister(View view) {

        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }

    public void onLoginButtonClicked(View view) {

        String email = emailWrapper.getEditText().getText().toString();
        String password = passwordWrapper.getEditText().getText().toString();

        if (Utility.isEmpty(email) || Utility.isEmpty(password)) {

            if(Utility.isEmpty(email)) {

                emailWrapper.getEditText().setError(LoginActivity.this.getString(R.string.empty_input_error_message));
                emailWrapper.getEditText().requestFocus();

            }else if(Utility.isEmpty(password)) {


                passwordWrapper.getEditText().setError(LoginActivity.this.getString(R.string.empty_input_error_message));
                passwordWrapper.getEditText().requestFocus();
            }

        }else {

            if(!Utility.validateEmail(email)) {

                emailWrapper.getEditText().setError("Invalid email address");
                emailWrapper.getEditText().requestFocus();

            }else {

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                //TODO authenticate user here
                ParseUser.logInInBackground(email, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        setProgressBarIndeterminateVisibility(false);

                        if (e == null) {
                            // User Login is successful
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        } else {
                            // User Login Failed
                            Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                //Toast.makeText(getApplicationContext(), email + " " + password, Toast.LENGTH_LONG).show();
            }
        }
    }

}
