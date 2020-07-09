package org.reallume.domain;


import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Label {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User author;

    @ManyToMany()
    @JoinTable(
            name = "label_note",
            joinColumns = { @JoinColumn(name = "label_id")},
            inverseJoinColumns = { @JoinColumn(name = "note_id")}
    )
    private List<Note> notes;

    public Label() {
    }

    public Label(String name, User author) {
        this.name = name;
        this.author = author;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public User getAuthor() { return author; }

    public void setAuthor(User author) { this.author = author; }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}