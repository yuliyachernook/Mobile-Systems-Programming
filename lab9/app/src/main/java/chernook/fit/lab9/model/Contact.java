package chernook.fit.lab9.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.UUID;

import chernook.fit.lab9.converter.UUIDTypeConverter;

@Entity(tableName = "CONTACT")
public class Contact implements Serializable {
    @PrimaryKey
    @NonNull
    @TypeConverters(UUIDTypeConverter.class)
    public UUID id;
    public String surname;
    public String name;
    public String patronymic;
    public String imageUri;
    public String workSphere;
    public String phone;
    public String email;

    public Contact() {
        id = UUID.randomUUID();
    }


    public static String[] getSpheres() {
        return new String[] { "IT", "Economy", "Education",
                "Medicine", "Food", "Technician"};
    }
}