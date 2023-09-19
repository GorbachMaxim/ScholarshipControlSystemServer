package org.example.ServerSemestr5.Model.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subject")
    private int idSubject;

    @Column(name = "subject_name")
    private String name;

    @Column(name = "hours_number")
    private int hoursNumber;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable (name="groups_subjects",
            joinColumns=@JoinColumn (name="subject_id"),
            inverseJoinColumns=@JoinColumn(name="group_id"))
    private List<Group> groups;

    public Subject clone(){
        Subject clone = new Subject();
        clone.setIdSubject(this.idSubject);
        clone.setName(this.name);
        clone.setHoursNumber(this.hoursNumber);

        List<Group> groupsClone = new ArrayList<>();
        for(Group gr : this.groups){
            Group newGroup = gr.clone();
            groupsClone.add(newGroup);
        }
        clone.setGroups(groupsClone);
        return clone;
    }
}
