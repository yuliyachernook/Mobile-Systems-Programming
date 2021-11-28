package chernook.fit.lab9.modelview;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import chernook.fit.lab9.database.repository.ContactRepository;
import chernook.fit.lab9.model.Contact;
import chernook.fit.lab9.model.SortType;

public class ContactModelView extends AndroidViewModel {

    private ContactRepository repository;
    private LiveData<List<Contact>> contacts;
    private SortType sortType;

    public ContactModelView(@NonNull Application application) {
        super(application);
        repository = new ContactRepository(application);
        sortType = SortType.NO_SORT;
        contacts = repository.getContacts(sortType);
    }

    public void setSortType(SortType sortType) {
        contacts = repository.getContacts(sortType);
        this.sortType = sortType;
    }

    public SortType getSortType() {
        return sortType;
    }

    public LiveData<List<Contact>> getContacts() {
        contacts = repository.getContacts(sortType);
        return contacts;
    }

    public void insertRecipe(Contact contact) {
        repository.insertContact(contact);
    }

    public void removeRecipe(Contact contact) {
        repository.removeContact(contact);
    }

    public void removeRecipe(UUID id) {
        repository.removeContact(id);
    }

    public void updateRecipe(Contact newContact) {
        repository.updateContact(newContact);
    }

}