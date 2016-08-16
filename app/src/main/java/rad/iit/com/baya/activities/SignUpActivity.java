package rad.iit.com.baya.activities;

import android.widget.Button;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.TemplateActivity;

public class SignUpActivity extends TemplateActivity {

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

}
