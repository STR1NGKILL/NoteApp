package org.reallume.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity()
@Table(name = "usr")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @Transient
    private String confirmPassword;

    @NotNull
    private String email;

    @NotNull
    private Boolean activeStatus;

    private String token;

    @NotNull
    private String role;

    private Date lastLogin;

    @NotNull
    private Date registDate;

    @Lob
    private byte[] image;
    @Transient
    private String imageString;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Category> categories;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Label> labels;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Note> notes;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="userStat_id")
    private UserStat userStat;


    public User(){
    }

    public User(String username, String password, String confirmPassword, String email, String name, Boolean activeStatus, String token) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.email = email;
        this.activeStatus = activeStatus;
        this.token = token;
        this.registDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageString() {
        return imageString;
    }

    public void setImageString(String imageString) {
        this.imageString = imageString;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setCategory(Category category) {
        this.categories.add(category);
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public void setLabel(Label label) {
        this.labels.add(label);
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getLastLogin() {
        if(this.lastLogin != null) {
            SimpleDateFormat dateFormat = null;

            dateFormat = new SimpleDateFormat("d MMMM yyyy H:m:s");

            return dateFormat.format(this.lastLogin);
        }else
            return "Не входил";
    }

    public void setLastLogin() {
        this.lastLogin = new Date();
    }

    public String getRegistDate() {
        if(this.registDate != null) {
            SimpleDateFormat dateFormat = null;

            dateFormat = new SimpleDateFormat("d MMMM yyyy ");

            return dateFormat.format(this.registDate);
        }else
            return "Не регистрировался";
    }

    public Date getRegistDateOrigin(){
        return registDate;
    }

    public void setRegistDate(Date registDate) {
        this.registDate = registDate;
    }

    public UserStat getUserStat() {
        return userStat;
    }

    public void setUserStat(UserStat userStat) {
        this.userStat = userStat;
    }
}
