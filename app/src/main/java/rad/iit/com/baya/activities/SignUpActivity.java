package rad.iit.com.baya.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.TemplateActivity;

public class SignUpActivity extends TemplateActivity implements View.OnClickListener {

    EditText userNameEditText;
    EditText passwordEditText;
    Button signUpButton;

    Button hitButton;

    @Override
    public void initView() {
        setContentView(R.layout.activity_sign_up);
        hitButton = (Button) findViewById(R.id.button_hit);
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
        signUpButton=(Button) findViewById(R.id.btn_sign_up);

        signUpButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btn_sign_up:
                break;
        }
    }
}
