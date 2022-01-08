package chernook.fit.lab5.database;

import androidx.room.TypeConverter;

import java.util.UUID;

public class UUIDTypeConverter {
    @TypeConverter
    public static String fromUUID(UUID id) {
        return id.toString();
    }

    @TypeConverter
    public static UUID toUUID(String stringId) {
        return UUID.fromString(stringId);
    }
}