package info.androidhive.project.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by VietAnh on 5/16/2016.
 */
public class User implements Parcelable {
    private String idUser;
    private String nameUser;
    private String lastName;
    private String firstName;
    private String fullName;
    private String birthday;
    private String address;
    private String email;
    private String phoneNumber;
    private Image image;

    public String getAddress() {
        return address;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getIdUser() {
        return idUser;
    }

    public Image getImage() {
        return image;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNameUser() {
        return nameUser;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthday(String bday) {
        this.birthday = bday;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String toString(){
        return "{\n" +
                "\t\"idUser\":\"" + idUser + "\",\n" +
                "\t\"nameUser\":\"" + nameUser +"\",\n" +
                "\t\"lastName\": \"" + lastName + "\",\n" +
                "\t\"firstName\": \"" + firstName + "\",\n" +
                "\t\"fullName\": \"" + fullName + "\",\n" +
                "\t\"birthday\": \"" + birthday + "\",\n" +
                "\t\"address\": \"" + address + "\",\n" +
                "\t\"email\": \"" +  email + "\",\n" +
                "\t\"phoneNumber\": \"" + phoneNumber + "\",\n" +
                "\t\"Image\":\n" + image.toString() +
                "}";
                /*"\t\t{\n" +
                "\t\t\t\"nameImg\": \"" + image.getNameImg() + "\",\n" +
                "\t\t\t\"height\": \"" + image.getHeight() +  "\",\n" +
                "\t\t\t\"weight\": \"" + image.getWeight() + "\",\n" +
                "\t\t\t\"src\": \"" + image.getSrc() + "\"\n" +
                "\t\t}\n" +
                "}";*/
    }

    public String getNameObject(){
        return "com.Project.POJO.User";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idUser);
        dest.writeString(this.nameUser);
        dest.writeString(this.lastName);
        dest.writeString(this.firstName);
        dest.writeString(this.fullName);
        dest.writeString(this.birthday);
        dest.writeString(this.address);
        dest.writeString(this.email);
        dest.writeString(this.phoneNumber);
        dest.writeParcelable(this.image, flags);
    }

    public User() {
    }

    protected User(Parcel in) {
        this.idUser = in.readString();
        this.nameUser = in.readString();
        this.lastName = in.readString();
        this.firstName = in.readString();
        this.fullName = in.readString();
        this.birthday = in.readString();
        this.address = in.readString();
        this.email = in.readString();
        this.phoneNumber = in.readString();
        this.image = in.readParcelable(Image.class.getClassLoader());
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
