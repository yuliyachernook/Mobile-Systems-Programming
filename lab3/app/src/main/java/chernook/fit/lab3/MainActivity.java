package chernook.fit.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private CheckBox workCheckBox;
    private EditText surnameEditText;
    private EditText nameEditText;
    private EditText positionEditText;
    private Spinner educationSpinner;
    private boolean isWorkExperience;
    private User user = new User();

    final static String userVariableKey = "USER_VARIABLE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surnameEditText = findViewById(R.id.surnameEditText);
        nameEditText = findViewById(R.id.nameEditText);
        positionEditText = findViewById(R.id.positionEditText);
        educationSpinner = findViewById(R.id.educationSpinner);
        workCheckBox = findViewById(R.id.workExperienceCheckBox);

        Bundle arguments = getIntent().getExtras();

        if (arguments != null) {
            user = (User) arguments.getSerializable(User.class.getSimpleName());
            surnameEditText.setText(user.getSurname());
            nameEditText.setText(user.getName());
            positionEditText.setText(user.getPosition());
            educationSpinner.setSelection(EducationDegree.fromString(user.getEducationDegree().getText()));
            if (user.isWorkExperience()) workCheckBox.setChecked(true);
        }
    }

    public EducationDegree getEducation() {
        int selected = educationSpinner.getSelectedItemPosition();
        switch (selected) {
            case 0: return EducationDegree.POSTGRADUATE;
            case 1: return EducationDegree.HIGHER;
            case 2: return EducationDegree.SPECIAL;
            case 3: return EducationDegree.TECHNICAL;
            case 4: return EducationDegree.GENERAL;
        }
        return EducationDegree.POSTGRADUATE;
    }

    public void onCheckboxClicked(View view) {
        if (workCheckBox.isChecked())
            isWorkExperience = true;
        else isWorkExperience = false;
    }

    public void onGoToNextActivityClick(View v) {
        int selectedEducation = educationSpinner.getSelectedItemPosition();
        Intent intent;
        if (selectedEducation == 4 && workCheckBox.isChecked()) {
            intent = new Intent(this, WorkActivity.class);
        }
        else intent = new Intent(this, EducationActivity.class);
        User user = new User(surnameEditText.getText().toString(), nameEditText.getText().toString(),
                getEducation(), positionEditText.getText().toString(), isWorkExperience);
        intent.putExtra(User.class.getSimpleName(), user);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("MainActivity","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("MainActivity", "onStop");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putSerializable(userVariableKey, user);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        user = (User)savedInstanceState.getSerializable(userVariableKey);
        surnameEditText.setText(user.getSurname());
        nameEditText.setText(user.getName());
        positionEditText.setText(user.getPosition());
        if (user.isWorkExperience()) workCheckBox.setChecked(true);
    }
}