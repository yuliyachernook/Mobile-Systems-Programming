package chernook.fit.lab5.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import chernook.fit.lab5.entity.GoodsItem;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseContract.DatabaseEntry.TABLE_NAME + " (" +
                    DatabaseContract.DatabaseEntry.COLUMN_NAME_ID + " TEXT" + "," +
                    DatabaseContract.DatabaseEntry.COLUMN_NAME_NAME +" TEXT" + "," +
                    DatabaseContract.DatabaseEntry.COLUMN_NAME_DESCRIPTION +" TEXT" + "," +
                    DatabaseContract.DatabaseEntry.COLUMN_NAME_IMAGE +" TEXT" + "," +
                    DatabaseContract.DatabaseEntry.COLUMN_NAME_PLACE +" TEXT" + "," +
                    DatabaseContract.DatabaseEntry.COLUMN_NAME_DATE +" DATE" + "," +
                    DatabaseContract.DatabaseEntry.COLUMN_NAME_FINDER +" TEXT" + "," +
                    DatabaseContract.DatabaseEntry.COLUMN_NAME_RECEIPT_PLACE +" TEXT" + "," +
                    DatabaseContract.DatabaseEntry.COLUMN_NAME_IS_FAVORITE + " TEXT)";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseContract.DatabaseEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "goods.db";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}


//@Database(entities = {GoodsItem.class}, version = 1)
//public abstract class DatabaseHelper extends RoomDatabase {
//    public abstract GoodsItemDao goodItemDao();
//}
