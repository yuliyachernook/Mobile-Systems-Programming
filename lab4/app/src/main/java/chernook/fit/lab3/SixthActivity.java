package chernook.fit.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import chernook.fit.lab3.data.User;

public class SixthActivity extends AppCompatActivity {
    private TextView surnameTextView;
    private TextView nameTextView;
    private TextView educationTextView;
    private TextView positionTextView;
    private TextView workExperienceTextView;
    private TextView educationTextView2;
    private User user;
    private TextView workTextView;
    private TextView phoneTextView;
    private TextView mailTextView;
    private TextView socialTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        surnameTextView = findViewById(R.id.surnameTextView3);
        nameTextView = findViewById(R.id.nameTextView3);
        educationTextView = findViewById(R.id.educationTextView3);
        positionTextView = findViewById(R.id.positionTextView3);
        workExperienceTextView = findViewById(R.id.workExperienceTextView4);
        educationTextView2 = findViewById(R.id.educationTextView4);
        workTextView = findViewById(R.id.workTextView);
        phoneTextView = findViewById(R.id.phoneTextView);
        mailTextView = findViewById(R.id.emailTextView);
        socialTextView = findViewById(R.id.socialNetworksTextView);

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
            if (user.isWorkExperience()) workTextView.setText("\nМесто работы: " +
                    user.getWork().getCompany() + " " + user.getWork().getPosition() + " " + user.getWork().getDateFrom() + "-" +
                    user.getWork().getDateTo());
            if (user.getPhone() != null) phoneTextView.setText(user.getPhone());
            if (user.getEmail() != null) mailTextView.setText(user.getEmail());
            if (user.getSocialNetwork() != null) socialTextView.setText(user.getSocialNetwork());
        }
    }

    public void onGoToPrevActivityClick(View v) {
        Intent intent = new Intent(this, FifthActivity.class);
        intent.putExtra(User.class.getSimpleName(), user);
        startActivity(intent);
    }

    public void onOpenPhone(View view) {
        Intent phoneIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneTextView.getText().toString(), null));
        startActivity(phoneIntent);
    }

    public void onOpenEmail(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("mailto", phoneTextView.getText().toString(), null));
        startActivity(emailIntent);
    }

    public void onOpenSocialNetwork(View view) {
        Intent socialNetworkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(((TextView) view).getText().toString()));
        startActivity(socialNetworkIntent);
    }
}