package chernook.fit.lab9;

import static chernook.fit.lab9.utils.Literals.TAKE_PHOTO;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import chernook.fit.lab9.model.Contact;
import chernook.fit.lab9.utils.ImageManager;
import chernook.fit.lab9.utils.Literals;

public class CreateContactActivity extends AppCompatActivity {

    private Contact contact = new Contact();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        Spinner typeField = findViewById(R.id.sphere);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Contact.getSpheres());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeField.setAdapter(adapter);
    }

    public void done(View view) {
        EditText surname = findViewById(R.id.surname);
        EditText name = findViewById(R.id.name);
        EditText patronymic = findViewById(R.id.patronymic);
        EditText phone = findViewById(R.id.phone);
        EditText email = findViewById(R.id.email);
        Spinner sphere = findViewById(R.id.sphere);

        contact.surname = surname.getText().toString();
        contact.name = name.getText().toString();
        contact.patronymic = patronymic.getText().toString();
        contact.phone = phone.getText().toString();
        contact.email = email.getText().toString();
        contact.workSphere = sphere.getSelectedItem().toString();

        if (contact.imageUri == null) {
            Drawable defaultDrawable = getResources().getDrawable(R.drawable.person);
            Bitmap defaultBitmap = ((BitmapDrawable)defaultDrawable).getBitmap();
            contact.imageUri = ImageManager.saveToInternalStorage(this, defaultBitmap);
        }

        //DatabaseHelperSingleton.getDatabaseHelper(this).recipeDao().insert(recipe);
        Intent intent = new Intent();
        intent.putExtra(Literals.CONTACT, contact);
        setResult(Literals.ADD_CONTACT, intent);
        finish();
    }

    public void takePhoto(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), TAKE_PHOTO);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case TAKE_PHOTO: {
                if (data == null) break;
                Uri selectedImageUri = data.getData();
                String internalUriString = ImageManager.saveToInternalStorage(this, selectedImageUri);
                contact.imageUri = internalUriString;
                ImageView imageField = findViewById(R.id.imageField);
                imageField.setImageURI(Uri.parse(internalUriString));
                break;
            }
        }
    }
}