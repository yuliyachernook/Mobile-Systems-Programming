package chernook.fit.lab3;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import chernook.fit.lab3.data.User;
import chernook.fit.lab3.utils.Helper;

public class ContactActivity extends AppCompatActivity {

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
            intent = new Intent(this, WorkActivity.class);
        else intent = new Intent(this, EducationActivity.class);
        intent.putExtra(User.class.getSimpleName(), user);
        startActivity(intent);
    }

    public void onGoToNextActivityClick(View v) {
        user.setPhone(phoneEditText.getText().toString());
        user.setEmail(mailEditText.getText().toString());
        user.setSocialNetwork(socialEditText.getText().toString());
        Bitmap bmp = ((BitmapDrawable)image.getDrawable()).getBitmap();;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        user.setImage(byteArray);
        Intent intent = new Intent(this, UserActivity.class);
        intent.putExtra(User.class.getSimpleName(), user);
        startActivity(intent);
    }

    public void onPhotoClick(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1: {
                if (data == null)
                    break;
                try {
                    Uri selectedImageUri = data.getData();
                    Bitmap bitmap = null;
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                    image.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

        switch (resultCode){
            case 2: {
                setResult(2, data);
                finish();
                break;
            }
        }
    }



}
