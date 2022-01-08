package chernook.fit.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import chernook.fit.lab3.data.Education;
import chernook.fit.lab3.data.User;

public class EducationActivity extends AppCompatActivity {

    private TextView surnameTextView;
    private TextView nameTextView;
    private TextView educationTextView;
    private TextView positionTextView;
    private TextView workExperienceTextView;
    private User user;
    private Education education;

    private EditText universityEditText;
    private EditText facultyEditText;
    private EditText specialityEditText;
    private EditText dateFromEditText;
    private EditText dateToEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        surnameTextView = findViewById(R.id.surnameTextView3);
        nameTextView = findViewById(R.id.nameTextView3);
        educationTextView = findViewById(R.id.educationTextView3);
        positionTextView = findViewById(R.id.positionTextView3);
        workExperienceTextView = findViewById(R.id.workExperienceTextView4);

        universityEditText = findViewById(R.id.universityEditText);
        facultyEditText = findViewById(R.id.companyEditText);
        specialityEditText = findViewById(R.id.positionEditText2);
        dateFromEditText = findViewById(R.id.dateEditText3);
        dateToEditText = findViewById(R.id.dateEditText4);

        Bundle arguments = getIntent().getExtras();

        if(arguments!=null) {
            user = (User) arguments.getSerializable(User.class.getSimpleName());
            surnameTextView.setText("Фамилия: " + user.getSurname());
            nameTextView.setText("Имя: " + user.getName());
            educationTextView.setText("Образование: " + user.getEducationDegree().getText());
            positionTextView.setText("Должность: " + user.getPosition());
            workExperienceTextView.setText("Есть опыт работы: " + (user.isWorkExperience() ? "Да" : "Нет"));
            if (user.getEducation() != null ) {
                universityEditText.setText(user.getEducation().getUniversity());
                facultyEditText.setText(user.getEducation().getFaculty());
                specialityEditText.setText(user.getEducation().getSpeciality());
                dateFromEditText.setText(user.getEducation().getDateFrom());
                dateToEditText.setText(user.getEducation().getDateTo());
            }
        }
    }

    public void onGoToPrevActivityClick(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(User.class.getSimpleName(), user);
        startActivity(intent);
    }

    public void onGoToNextActivityClick(View v) {
        Intent intent;
        education = new Education(universityEditText.getText().toString(), facultyEditText.getText().toString(),
            specialityEditText.getText().toString(), dateFromEditText.getText().toString(), dateToEditText.getText().toString());
        user.setEducation(education);
        if (user.isWorkExperience())
            intent = new Intent(this, WorkActivity.class);
        else intent = new Intent(this, ContactActivity.class);
        intent.putExtra(User.class.getSimpleName(), user);
        startActivity(intent);
        overridePendingTransition(R.anim.diagonaltranslate,R.anim.alpha);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("EducationActivity","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("EducationActivity", "onStop");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}