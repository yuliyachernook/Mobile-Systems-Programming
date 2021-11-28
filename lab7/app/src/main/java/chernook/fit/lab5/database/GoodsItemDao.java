package chernook.fit.lab5.database;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.List;
import java.util.UUID;

import chernook.fit.lab5.entity.GoodsItem;

@Dao
public interface GoodsItemDao {

    @Query("SELECT * FROM GOODSITEM")
    List<GoodsItem> getAll();

    @Query("SELECT * FROM GOODSITEM ORDER BY NAME")
    List<GoodsItem> getAllSortedByName();

    @Query("SELECT * FROM GOODSITEM ORDER BY FINDDATE")
    List<GoodsItem> getAllSortedByFindDate();

    @Query("SELECT * FROM GOODSITEM WHERE NAME LIKE '%'||:pattern||'%'")
    List<GoodsItem> getAllWithPattern(String pattern);

    @Query("SELECT * FROM GOODSITEM WHERE NAME LIKE '%'||:pattern||'%' ORDER BY NAME")
    List<GoodsItem> getAllSortedByNameWithPattern(String pattern);

    @Query("SELECT * FROM GOODSITEM WHERE NAME LIKE '%'||:pattern||'%' ORDER BY FINDDATE")
    List<GoodsItem> getAllSortedByFindDateWithPattern(String pattern);

    //------------------------As Cursor--------------------------------

    @Query("SELECT id as _id, name, description, findPlace," +
            " image, findDate, finder, receiptPlace, isFavorite" +
            " FROM GOODSITEM")
    Cursor getAllAsCursor();

    @Query("SELECT id as _id, name, description, findPlace," +
            " image, findDate, finder, receiptPlace, isFavorite" +
            " FROM GOODSITEM ORDER BY NAME")
    Cursor getAllSortedByNameAsCursor();

    @Query("SELECT id as _id, name, description, findPlace," +
            " image, findDate, finder, receiptPlace, isFavorite" +
            " FROM GOODSITEM ORDER BY FINDDATE")
    Cursor getAllSortedByFindDateAsCursor();

    @Query("SELECT id as _id, name, description, findPlace," +
            " image, findDate, finder, receiptPlace, isFavorite" +
            " FROM GOODSITEM WHERE isFavorite IN (:favoriteStatements)" +
            " AND NAME LIKE '%'||:pattern||'%'")
    Cursor getAllWithPatternAsCursor(int[] favoriteStatements, String pattern);

    @Query("SELECT id as _id, name, description, findPlace," +
            " image, findDate, finder, receiptPlace, isFavorite" +
            " FROM GOODSITEM WHERE isFavorite IN (:favoriteStatements) AND" +
            " NAME LIKE '%'||:pattern||'%' ORDER BY NAME")
    Cursor getAllSortedByNameWithPatternAsCursor(int[] favoriteStatements, String pattern);

    @Query("SELECT id as _id, name, description, findPlace," +
            " image, findDate, finder, receiptPlace, isFavorite" +
            " FROM GOODSITEM WHERE isFavorite IN (:favoriteStatements) AND" +
            " NAME LIKE '%'||:pattern||'%' ORDER BY FINDDATE")
    Cursor getAllSortedByFindDateWithPatternAsCursor(int[] favoriteStatements, String pattern);

    //-------------------------------------------------------------

    @Query("SELECT * FROM GOODSITEM WHERE ID = :id")
    GoodsItem getById(@TypeConverters(UUIDTypeConverter.class) UUID id);

    @Query("SELECT COUNT(*) FROM GOODSITEM WHERE ID = :id")
    boolean contains(@TypeConverters(UUIDTypeConverter.class) UUID id);

    @Insert
    void insert(GoodsItem goodItem);

    @Update
    void update(GoodsItem goodItem);

    @Delete
    void delete(GoodsItem goodItem);

    @Query("DELETE FROM GOODSITEM WHERE ID = :id")
    void delete(@TypeConverters(UUIDTypeConverter.class) UUID id);
}
