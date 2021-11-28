package chernook.fit.lab5.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import chernook.fit.lab5.entity.GoodsItem;

@Database(entities = {GoodsItem.class}, version = 1)
public abstract class DatabaseHelper extends RoomDatabase {
    public abstract GoodsItemDao goodItemDao();
}
