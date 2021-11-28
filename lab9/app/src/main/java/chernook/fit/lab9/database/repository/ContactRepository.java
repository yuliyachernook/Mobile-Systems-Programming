package chernook.fit.lab9.database.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.UUID;

import chernook.fit.lab9.database.dao.ContactDao;
import chernook.fit.lab9.database.helper.DatabaseHelper;
import chernook.fit.lab9.model.Contact;
import chernook.fit.lab9.model.SortType;

public class ContactRepository {

    private ContactDao contactDao;

    public ContactRepository(Application application) {
        DatabaseHelper database = DatabaseHelper.getInstance(application);
        contactDao = database.contactDao();
    }

    public LiveData<List<Contact>> getContacts(SortType sortType) {
        switch (sortType) {
            case NAME_ASC: return contactDao.getAllSortedByNameAsc();
            case NAME_DESC: return contactDao.getAllSortedByNameDesc();
            default: return contactDao.getAll();
        }
    }

    public void insertContact(Contact contact) {
        new InsertContactAsyncTask(contactDao).execute(contact);
    }

    public void removeContact(Contact contact) {
        new RemoveContactAsyncTask(contactDao).execute(contact);
    }

    public void removeContact(UUID id) {
        new RemoveRecipeAsyncTaskUUID(contactDao).execute(id);
    }

    public void updateContact(Contact newContact) {
        new UpdateContactAsyncTask(contactDao).execute(newContact);
    }

    private static class InsertContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao contactDao;
        public InsertContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }
        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.insert(contacts[0]);
            return null;
        }
    }

    private static class RemoveContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao contactDao;
        public RemoveContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }
        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.delete(contacts[0]);
            return null;
        }
    }

    private static class RemoveRecipeAsyncTaskUUID extends AsyncTask<UUID, Void, Void> {
        private ContactDao contactDao;
        public RemoveRecipeAsyncTaskUUID(ContactDao contactDao) {
            this.contactDao = contactDao;
        }
        @Override
        protected Void doInBackground(UUID... uuids) {
            contactDao.delete(uuids[0]);
            return null;
        }
    }

    private static class UpdateContactAsyncTask extends AsyncTask<Contact, Void, Void> {
        private ContactDao contactDao;
        public UpdateContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }
        @Override
        protected Void doInBackground(Contact... recipes) {
            contactDao.update(recipes[0]);
            return null;
        }
    }
}
