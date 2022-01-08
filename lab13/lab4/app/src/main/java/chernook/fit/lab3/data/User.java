package chernook.fit.lab3.data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class User implements Serializable {
    private String surname;
    private String name;
    private EducationDegree educationDegree;
    private String position;
    private boolean isWorkExperience;
    private Education education;
    private Work work;
    private byte[] image;
    private String phone;
    private String email;
    private String socialNetwork;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Bitmap getImageAsBitmap() {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public void setImageFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        image = stream.toByteArray();
    }
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(String socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public User() {

    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EducationDegree getEducationDegree() {
        return educationDegree;
    }

    public void setEducationDegree(EducationDegree educationDegree) {
        this.educationDegree = educationDegree;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isWorkExperience() {
        return isWorkExperience;
    }

    public void setWorkExperience(boolean workExperience) {
        isWorkExperience = workExperience;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public User(String surname, String name, EducationDegree educationDegree, String position, boolean isWorkExperience) {
        this.surname = surname;
        this.name = name;
        this.educationDegree = educationDegree;
        this.position = position;
        this.isWorkExperience = isWorkExperience;
    }

    @Override
    public String toString() {
        String str =  "Фамилия: " + surname + "\nИмя: " + name + "\nОбразование: "
                + educationDegree.getText() + "\nДолжность: " + position + "\nОпыт работы: "
                + (isWorkExperience ? "Да" : "Нет") + "\nМесто учебы: " + education.getUniversity() + " " +
                education.getFaculty() + " " + education.getSpeciality() + " " +
                education.getDateFrom() + "-" + education.getDateTo();
        if(isWorkExperience) str += "\nМесто работы: " + work.getCompany() +
                " " + work.getPosition() + " " + work.getDateFrom() + "-" + work.getDateTo();
        str += "Телефон: " + phone + "\nE-mail: " + email + "\nСоц.сеть: " + socialNetwork;
        return str;

    }
}
