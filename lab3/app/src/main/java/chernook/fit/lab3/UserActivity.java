package chernook.fit.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class UserActivity extends AppCompatActivity {

    private ArrayAdapter<User> adapter;
    private List<User> users;
    ListView listView;
    private User user;
    private TextView userTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        listView = findViewById(R.id.list);
        userTextView = findViewById(R.id.userTextView);

        users = new ArrayList<User>();

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        listView.setAdapter(adapter);

        Bundle arguments = getIntent().getExtras();
        if(arguments!=null) {
            user = (User) arguments.getSerializable(User.class.getSimpleName());
            userTextView.setText("Фамилия: " + user.getSurname() + "\nИмя: " + user.getName() + "\nОбразование: "
                + user.getEducationDegree().getText() + "\nДолжность: " + user.getPosition() + "\nОпыт работы: "
                    + (user.isWorkExperience() ? "Да" : "Нет") + "\nМесто учебы: " + user.getEducation().getUniversity() + " " +
                    user.getEducation().getFaculty() + " " + user.getEducation().getSpeciality() + "" +
                    user.getEducation().getDateFrom() + "-" + user.getEducation().getDateTo());
            if (user.isWorkExperience()) userTextView.append("\nМесто работы: " +
                    user.getWork().getCompany() + " " + user.getWork().getPosition() + " " + user.getWork().getDateFrom() + "-" +
                    user.getWork().getDateTo());
        }
    }
    public void save(View view){
        users.add(user);
        boolean result = JSONHelper.exportToJSON(this, users);
        if(result){
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не удалось сохранить данные", Toast.LENGTH_LONG).show();
        }

    }
    public void open(View view){
        users = JSONHelper.importFromJSON(this);
        if(users!=null){
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
            listView.setAdapter(adapter);
            Toast.makeText(this, "Данные восстановлены", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Не удалось открыть данные", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("UserActivity","onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("UserActivity", "onStop");
    }
}