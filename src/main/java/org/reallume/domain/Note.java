package org.reallume.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String text;
    private Date time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Label> labels;

    public Note() {
    }

    public Note(String name, String text, User user) {
        this.name = name;
        this.text = text;
        this.author = user;
        this.time = new Date();
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTime() {

        SimpleDateFormat dateFormat = null;

        dateFormat = new SimpleDateFormat("dd MMM yyyy");

        return dateFormat.format(this.time);
    }

    public void setTime(){ this.time = new Date(); }

    //по Groovy можно обращаться к полю метода, который не существует. Например, getAuthorName -> поле AuthorName
    public String getAuthorName(){
        return author != null ? author.getUsername() : "<none>";
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }
}