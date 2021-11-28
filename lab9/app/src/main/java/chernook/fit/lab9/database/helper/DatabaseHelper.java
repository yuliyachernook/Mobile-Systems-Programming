package chernook.fit.lab9.database.helper;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import chernook.fit.lab9.database.dao.ContactDao;
import chernook.fit.lab9.model.Contact;

@Database(entities = {Contact.class}, version = 1)
public abstract class DatabaseHelper extends RoomDatabase {
    private static DatabaseHelper instance;
    private static Context context;
    public abstract ContactDao contactDao();

    public static synchronized DatabaseHelper getInstance(Context context) {
        instance.context = context;
        if (instance == null) {
            instance = Room.databaseBuilder(context, DatabaseHelper.class,
                    "database").fallbackToDestructiveMigration()
                    .addCallback(callback).build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InitialDataAsyncTask(instance).execute();
        }
    };

    private static class InitialDataAsyncTask extends AsyncTask<Void, Void, Void> {
        private ContactDao contactDao;
        public InitialDataAsyncTask(DatabaseHelper database) {
            contactDao = database.contactDao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }

}
