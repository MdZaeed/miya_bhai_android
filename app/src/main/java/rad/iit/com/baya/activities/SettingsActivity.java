package rad.iit.com.baya.activities;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import rad.iit.com.baya.R;
import rad.iit.com.baya.activities.template.TemplateActivity;
import rad.iit.com.baya.data.constants.ApplicationConstants;

/**
 * Created by Zayed on 11-Oct-16.
 */
public class SettingsActivity extends TemplateActivity {

    RadioButton banglaRadioButton,englishRadioButton;
    @Override
    public void initView() {

        setContentView(R.layout.activity_settings);

        banglaRadioButton=(RadioButton) findViewById(R.id.rb_bangla);
        englishRadioButton=(RadioButton) findViewById(R.id.rb_english);

        String language=getLanguage();

        if(language.equals(getResources().getString(R.string.bangla_string))) {
            banglaRadioButton.setChecked(true);
        }else {
            englishRadioButton.setChecked(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if(banglaRadioButton.isChecked() && getLanguage().equals(getString(R.string.english_string)))
        {
            saveLanguageData(getResources().getString(R.string.bangla_string));
        } else if(englishRadioButton.isChecked() && getLanguage().equals(getString(R.string.bangla_string)))
        {
            saveLanguageData(getResources().getString(R.string.english_string));
        }
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
