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
import chernook.fit.lab3.data.Work;

public class WorkActivity extends AppCompatActivity {

    private TextView surnameTextView;
    private TextView nameTextView;
    private TextView educationTextView;
    private TextView positionTextView;
    private TextView workExperienceTextView;
    private TextView educationTextView2;
    private User user;
    private Education education;
    private Work work;

    private EditText companyEditText;
    private EditText positionEditText;
    private EditText dateFromEditText;
    private EditText dateToEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        surnameTextView = findViewById(R.id.surnameTextView3);
        nameTextView = findViewById(R.id.nameTextView3);
        educationTextView = findViewById(R.id.educationTextView3);
        positionTextView = findViewById(R.id.positionTextView3);
        workExperienceTextView = findViewById(R.id.workExperienceTextView4);
        educationTextView2 = findViewById(R.id.educationTextView4);

        companyEditText = findViewById(R.id.companyEditText);
        positionEditText = findViewById(R.id.positionEditText2);
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
            educationTextView2.setText("Место учебы: " + user.getEducation().getUniversity() + " " +
                    user.getEducation().getFaculty() + " " + user.getEducation().getSpeciality() + " " +
                    user.getEducation().getDateFrom() + " " + user.getEducation().getDateTo());
            if (user.getWork() != null) {
                companyEditText.setText(user.getWork().getCompany());
                positionEditText.setText(user.getWork().getPosition());
                dateFromEditText.setText(user.getWork().getDateFrom());
                dateToEditText.setText(user.getWork().getDateTo());
            }
        }
    }

    public void onGoToPrevActivityClick(View v) {
        Intent intent = new Intent(this, EducationActivity.class);
        intent.putExtra(User.class.getSimpleName(), user);
        startActivity(intent);
    }

    public void onGoToNextActivityClick(View v) {
        work = new Work(companyEditText.getText().toString(), positionEditText.getText().toString(),
                dateFromEditText.getText().toString(), dateToEditText.getText().toString());
        user.setWork(work);
        Intent intent = new Intent(this, ContactActivity.class);
        intent.putExtra(User.class.getSimpleName(), user);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("WorkActivity","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("WorkActivity", "onStop");
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