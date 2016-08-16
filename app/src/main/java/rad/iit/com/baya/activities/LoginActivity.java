package rad.iit.com.baya.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.TemplateActivity;

/**
 * Created by Zayed on 16-Aug-16.
 */
public class LoginActivity extends TemplateActivity implements View.OnClickListener {

    EditText userNameEditText;
    EditText passwordEditText;
    Button logInButton;

    @Override
    public void initView() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void initializeViewByData() {

    }

    @Override
    public void listenView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userNameEditText=(EditText) findViewById(R.id.et_user_name);
        passwordEditText=(EditText) findViewById(R.id.et_password);
        logInButton=(Button) findViewById(R.id.btn_login);

        logInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_login:
                break;
        }
    }
}
