package chernook.fit.lab9.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.List;
import java.util.UUID;

import chernook.fit.lab9.converter.UUIDTypeConverter;
import chernook.fit.lab9.model.Contact;

@Dao
public interface ContactDao {
    @Query("SELECT * FROM CONTACT")
    LiveData<List<Contact>> getAll();

    @Query("SELECT * FROM CONTACT ORDER BY SURNAME ASC")
    LiveData<List<Contact>> getAllSortedByNameAsc();

    @Query("SELECT * FROM CONTACT ORDER BY SURNAME DESC")
    LiveData<List<Contact>> getAllSortedByNameDesc();

    @Query("SELECT * FROM CONTACT WHERE ID = :id")
    Contact getById(@TypeConverters(UUIDTypeConverter.class) UUID id);

    @Query("SELECT COUNT(*) FROM CONTACT WHERE ID = :id")
    boolean contains(@TypeConverters(UUIDTypeConverter.class) UUID id);

    @Insert
    void insert(Contact goodItem);

    @Update
    void update(Contact goodItem);

    @Delete
    void delete(Contact goodItem);

    @Query("DELETE FROM CONTACT WHERE ID = :id")
    void delete(@TypeConverters(UUIDTypeConverter.class) UUID id);
}
