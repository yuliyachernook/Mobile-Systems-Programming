package chernook.fit.lab5.utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import chernook.fit.lab5.database.DatabaseContract;
import chernook.fit.lab5.database.DatabaseHelper;
import chernook.fit.lab5.database.DatabaseHelperSingleton;
import chernook.fit.lab5.database.DateTypeConverter;
import chernook.fit.lab5.database.UUIDTypeConverter;
import chernook.fit.lab5.entity.GoodsItem;

public class DatabaseGoodsItemsContainer {

    private static GoodItemsSortType sortType = GoodItemsSortType.NO_SORT;
    private static GoodsItemsFavoriteSelection favoriteSelection = GoodsItemsFavoriteSelection.ALL;
    private static String pattern = "";
    private static DatabaseHelper mDbHelper;


    public static void addGoodItem(Context context, GoodsItem goodItem) {
        mDbHelper = new DatabaseHelper(context);
//        if (goodItem.getId() == null || db.goodItemDao().contains(goodItem.getId())) {
//            UUID uuid = UUID.randomUUID();
//            goodItem.setId(uuid);
//        }
        goodItem.setId(UUID.randomUUID());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DatabaseEntry.COLUMN_NAME_ID, goodItem.getId().toString());
        values.put(DatabaseContract.DatabaseEntry.COLUMN_NAME_NAME, goodItem.getName());
        values.put(DatabaseContract.DatabaseEntry.COLUMN_NAME_DESCRIPTION, goodItem.getDescription());
        values.put(DatabaseContract.DatabaseEntry.COLUMN_NAME_IMAGE, goodItem.getImage());
        values.put(DatabaseContract.DatabaseEntry.COLUMN_NAME_PLACE, goodItem.getFindPlace());
        System.out.println(goodItem.getFindDate());
        values.put(DatabaseContract.DatabaseEntry.COLUMN_NAME_DATE, String.valueOf(goodItem.getFindDate()));
        values.put(DatabaseContract.DatabaseEntry.COLUMN_NAME_FINDER, goodItem.getFinder());
        values.put(DatabaseContract.DatabaseEntry.COLUMN_NAME_RECEIPT_PLACE, goodItem.getReceiptPlace());
        values.put(DatabaseContract.DatabaseEntry.COLUMN_NAME_IS_FAVORITE, goodItem.isFavorite());
        long newRowId;
        newRowId = db.insert(
                DatabaseContract.DatabaseEntry.TABLE_NAME,
                null,
                values);
        db.close();
        System.out.println(newRowId);
        //db.goodItemDao().insert(goodItem);
    }

    public static void updateGoodItem(Context context, GoodsItem goodItem) {
//        DatabaseHelper db = DatabaseHelperSingleton.getDatabaseHelper(context);
//        if (newGoodItem.getId() != null && db.goodItemDao().contains(newGoodItem.getId())) {
//            db.goodItemDao().update(newGoodItem);
//        }
        mDbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(DatabaseContract.DatabaseEntry.COLUMN_NAME_NAME, goodItem.getName());
        values.put(DatabaseContract.DatabaseEntry.COLUMN_NAME_DESCRIPTION, goodItem.getDescription());
        values.put(DatabaseContract.DatabaseEntry.COLUMN_NAME_IMAGE, goodItem.getImage());
        values.put(DatabaseContract.DatabaseEntry.COLUMN_NAME_PLACE, goodItem.getFindPlace());
        values.put(DatabaseContract.DatabaseEntry.COLUMN_NAME_DATE, goodItem.getFindDate().toString());
        values.put(DatabaseContract.DatabaseEntry.COLUMN_NAME_FINDER, goodItem.getFinder());
        values.put(DatabaseContract.DatabaseEntry.COLUMN_NAME_RECEIPT_PLACE, goodItem.getReceiptPlace());
        values.put(DatabaseContract.DatabaseEntry.COLUMN_NAME_IS_FAVORITE, goodItem.isFavorite());
        long newRowId;
        newRowId =
            db.update(DatabaseContract.DatabaseEntry.TABLE_NAME, values, DatabaseContract.DatabaseEntry.COLUMN_NAME_ID
                    + "='" + goodItem.getId().toString() + "'", null);

        db.close();
        System.out.println(newRowId);
    }

    public static void removeGoodItem(Context context, UUID id) {
        mDbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
//        DatabaseHelper db = DatabaseHelperSingleton.getDatabaseHelper(context);
//        //goodItems.removeIf(t -> t.getId() == null);
//        if (id != null)
//            db.goodItemDao().delete(id);
        db.delete(DatabaseContract.DatabaseEntry.TABLE_NAME, "_id = ?", new String[]{String.valueOf(id)});
    }




    public static List<GoodsItem> getGoodItemsListByPattern(Context context) {
   //     DatabaseHelper db = DatabaseHelperSingleton.getDatabaseHelper(context);
        String filter = "(.*)" + pattern.toLowerCase() + "(.*)";
        List<GoodsItem> result = new ArrayList<>();
        Cursor c = null;
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        c = db.rawQuery("SELECT * FROM " + DatabaseContract.DatabaseEntry.TABLE_NAME, null);
        for (boolean hasStart = c.moveToFirst(); hasStart && !c.isAfterLast(); c.moveToNext()) {
            int nameIndex = c.getColumnIndex("name");
            String name = c.getString(nameIndex);
            int favoriteIndex = c.getColumnIndex("isFavorite");
            boolean favorite = c.getInt(favoriteIndex) == 1;
            if (name.matches(filter) && favoriteSelection.matches(favorite)) {
                GoodsItem goodItem = new GoodsItem(
                        UUIDTypeConverter.toUUID( c.getString(0)),
                        c.getString(1), c.getString(2),
                        c.getBlob(3), c.getString(4), DateTypeConverter.toDate(c.getLong(5)),
                        c.getString(6), c.getString(7), c.getInt(8) == 1);
                result.add(goodItem);
            }
        }
        return result;
    }

    public static Cursor getGoodItemsListByPatternAsCursor(Context context) {
//        DatabaseHelper db = DatabaseHelperSingleton.getDatabaseHelper(context);
       Cursor cursor = null;
        mDbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        switch (sortType) {
            case SORT_BY_NAME:
                cursor = db.rawQuery("SELECT * FROM " + DatabaseContract.DatabaseEntry.TABLE_NAME  + " ORDER BY NAME", null);

                break;
            case SORT_BY_FIND_DATE:
                cursor = db.rawQuery("SELECT * FROM " + DatabaseContract.DatabaseEntry.TABLE_NAME + " ORDER BY FINDDATE", null);
                break;
            default:
            cursor = db.rawQuery("SELECT * FROM " + DatabaseContract.DatabaseEntry.TABLE_NAME, null);
        }
        return cursor;
    }

    public static GoodItemsSortType getSortType() {
        return sortType;
    }

    public static void setSortType(GoodItemsSortType newSortType) {
        sortType = newSortType;
    }

    public static GoodsItemsFavoriteSelection getFavoriteSelection() {
        return favoriteSelection;
    }

    public static void setFavoriteSelection(GoodsItemsFavoriteSelection favoriteSelection) {
        DatabaseGoodsItemsContainer.favoriteSelection = favoriteSelection;
    }

    public static String getPattern() {
        return pattern;
    }

    public static void setPattern(String newPattern) {
        pattern = newPattern;
    }
}
