package chernook.fit.lab3;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import chernook.fit.lab3.data.User;
import chernook.fit.lab3.utils.Helper;

public class FourthActivity extends AppCompatActivity {

    private TextView surnameTextView;
    private TextView nameTextView;
    private TextView educationTextView;
    private TextView positionTextView;
    private TextView workExperienceTextView;
    private TextView educationTextView2;
    private User user;
    private TextView workTextView;
    private EditText phoneEditText;
    private EditText mailEditText;
    private EditText socialEditText;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        surnameTextView = findViewById(R.id.surnameTextView3);
        nameTextView = findViewById(R.id.nameTextView3);
        educationTextView = findViewById(R.id.educationTextView3);
        positionTextView = findViewById(R.id.positionTextView3);
        workExperienceTextView = findViewById(R.id.workExperienceTextView4);
        educationTextView2 = findViewById(R.id.educationTextView4);
        workTextView = findViewById(R.id.workTextView);

        phoneEditText = findViewById(R.id.phoneEditText);
        mailEditText = findViewById(R.id.emailEditText);
        socialEditText = findViewById(R.id.socialEditText);
        image = findViewById(R.id.my_avatar_imageview);

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
            if (user.getPhone() != null) phoneEditText.setText(user.getPhone());
            if (user.getEmail() != null) mailEditText.setText(user.getEmail());
            if (user.getSocialNetwork() != null) socialEditText.setText(user.getSocialNetwork());
        }
    }

    public void onGoToPrevActivityClick(View v) {
        Intent intent;
        if (user.isWorkExperience())
            intent = new Intent(this, ThirdActivity.class);
        else intent = new Intent(this, SecondActivity.class);
        intent.putExtra(User.class.getSimpleName(), user);
        startActivity(intent);
    }

    public void onGoToNextActivityClick(View v) {
        user.setPhone(phoneEditText.getText().toString());
        user.setEmail(mailEditText.getText().toString());
        user.setSocialNetwork(socialEditText.getText().toString());
        Intent intent = new Intent(this, FifthActivity.class);
        intent.putExtra(User.class.getSimpleName(), user);
        startActivity(intent);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK && null != result.getData()) {
                        Uri selectedImage = result.getData().getData();
                        user.setImagePath(Helper.getRealPathFromURI(getBaseContext(),selectedImage));
                        image.setImageURI(selectedImage);
                    }
                }
            });

            public void onClick(View view) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                someActivityResultLauncher.launch(intent);
            }
    }
