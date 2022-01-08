package chernook.fit.lab5.database;

import android.provider.BaseColumns;

import androidx.room.TypeConverters;

import java.util.Date;
import java.util.UUID;

public final class DatabaseContract {
    public DatabaseContract(){ }
    public static abstract class DatabaseEntry implements BaseColumns {
        public static final String TABLE_NAME = "goods";
        public static final String COLUMN_NAME_ID = "_id";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_IMAGE = "image";
        public static final String COLUMN_NAME_PLACE = "findPlace";
        public static final String COLUMN_NAME_DATE = "findDate";
        public static final String COLUMN_NAME_FINDER = "finder";
        public static final String COLUMN_NAME_RECEIPT_PLACE = "receiptPlace";
        public static final String COLUMN_NAME_IS_FAVORITE = "isFavorite";
    }
}
