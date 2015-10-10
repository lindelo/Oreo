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


public class RegisterActivity extends ActionBarActivity {

    private TextInputLayout emailWrapper;
    private TextInputLayout passwordWrapper;
    private TextInputLayout c_passwordWrapper;
    private InitParse connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        InitParse.initParse(this);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        emailWrapper = (TextInputLayout)  findViewById(R.id.registerEmailWrapper);
        passwordWrapper = (TextInputLayout) findViewById(R.id.registerPasswordWrapper);
        c_passwordWrapper = (TextInputLayout) findViewById(R.id.c_registerPasswordWrapper);


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

        c_passwordWrapper.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                c_passwordWrapper.getEditText().setError(null);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void goToLogin(View view) {

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
    public void onRegisterButtonClicked(View view) {

        String email = emailWrapper.getEditText().getText().toString();
        String password = passwordWrapper.getEditText().getText().toString();
        String c_password = c_passwordWrapper.getEditText().getText().toString();

        if (Utility.isEmpty(email) || Utility.isEmpty(password) || Utility.isEmpty(c_password)) {

            if(Utility.isEmpty(email)) {

                emailWrapper.getEditText().setError(RegisterActivity.this.getString(R.string.empty_input_error_message));
                emailWrapper.getEditText().requestFocus();

            }else if(Utility.isEmpty(password)) {


                passwordWrapper.getEditText().setError(RegisterActivity.this.getString(R.string.empty_input_error_message));
                passwordWrapper.getEditText().requestFocus();

            }else if(Utility.isEmpty(c_password)){

                c_passwordWrapper.getEditText().setError(RegisterActivity.this.getString(R.string.empty_input_error_message));
                c_passwordWrapper.getEditText().requestFocus();

            }

        }else {

            if(!Utility.validateEmail(email)) {

                emailWrapper.getEditText().setError("Invalid email address");
                emailWrapper.getEditText().requestFocus();

            }else if(password.length() >= 6) {
                if(c_password.equals(password))
                {
                    //Action
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                    //TODO register the user, creating user account

                    ParseUser User = new ParseUser();
                    User.setUsername(email);
                    User.setEmail(email);
                    User.setPassword(password);
                    User.signUpInBackground();
                    Toast.makeText(getApplicationContext(), email + " " + password, Toast.LENGTH_LONG).show();
                }
                else{
                    c_passwordWrapper.getEditText().setError("Password does not match");
                    c_passwordWrapper.getEditText().requestFocus();
                }

            }else{
                passwordWrapper.getEditText().setError(RegisterActivity.this.getString(R.string.pass_hint));
                passwordWrapper.getEditText().requestFocus();
            }
        }
    }

}
