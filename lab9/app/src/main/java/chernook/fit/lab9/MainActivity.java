package chernook.fit.lab9;

import static chernook.fit.lab9.utils.Literals.ADD_CONTACT;
import static chernook.fit.lab9.utils.Literals.EDIT_CONTACT;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.ContextMenu;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import chernook.fit.lab9.model.Contact;
import chernook.fit.lab9.model.SortType;
import chernook.fit.lab9.modelview.ContactModelView;
import chernook.fit.lab9.utils.ImageManager;
import chernook.fit.lab9.utils.Literals;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    private ContactModelView contactModelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactModelView = new ViewModelProvider.AndroidViewModelFactory(getApplication())
                .create(ContactModelView.class);

        contactModelView.getContacts().observe(this, this::updateFragment);
    }

    public void updateFragment(List<Contact> contacts) {

        ContactsFragment cardFragment = new ContactsFragment();
        cardFragment.setList(contacts);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container_view, cardFragment).commitAllowingStateLoss();

        ImageManager.clearUnused(this, contacts.stream()
                .map(r -> r.imageUri).collect(Collectors.toList()));
    }

    public void createNew(View view) {
        Intent intent = new Intent(this, CreateContactActivity.class);
        startActivityForResult(intent, 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode) {
            case ADD_CONTACT: {
                if (data == null) break;
                Contact contact = (Contact) data.getSerializableExtra(Literals.CONTACT);
                contactModelView.insertRecipe(contact);
                break;
            }
            case EDIT_CONTACT: {
                if (data == null) break;
                Contact newContact = (Contact) data.getSerializableExtra(Literals.CONTACT);
                contactModelView.updateRecipe(newContact);
                break;
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Remove");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);

        View view = findViewById(item.getItemId());
        UUID id = (UUID) view.getTag();
        switch (item.getTitle().toString()) {
            case "Edit":
                Intent intent = new Intent(this, EditContactActivity.class);
                intent.putExtra(Literals.CONTACT_ID, id);
                startActivityForResult(intent, 0);
                break;
            case "Remove":
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(R.string.remove_recipe);
                builder.setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                    contactModelView.removeRecipe(id);
                });
                builder.setNegativeButton(R.string.no, (dialogInterface, i) -> {
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.no_sort_setting:
                Toast.makeText(this, "No sort", Toast.LENGTH_SHORT).show();
                contactModelView.setSortType(SortType.NO_SORT);
                contactModelView.getContacts().removeObservers(this);
                contactModelView.getContacts().observe(this, this::updateFragment);
                return true;
            case R.id.sort_by_name_asc_setting:
                Toast.makeText(this, "Sort by name ascending", Toast.LENGTH_SHORT).show();
                contactModelView.setSortType(SortType.NAME_ASC);
                contactModelView.getContacts().removeObservers(this);
                contactModelView.getContacts().observe(this, this::updateFragment);
                return true;
            case R.id.sort_by_name_desc_setting:
                Toast.makeText(this, "Sort by name descending", Toast.LENGTH_SHORT).show();
                contactModelView.setSortType(SortType.NAME_DESC);
                contactModelView.getContacts().removeObservers(this);
                contactModelView.getContacts().observe(this, this::updateFragment);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}