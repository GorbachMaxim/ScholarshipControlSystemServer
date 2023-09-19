package org.example.ServerSemestr5.Model.entity;


import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

@Data
@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_student")
    private int idStudent;

    @Column(name = "student_firstname")
    private String firstname;

    @Column(name = "student_lastname")
    private String lastname;

    @Column(name = "budget")
    private boolean budget;

    @Column(name = "marks")
    private String marks;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;

    public Student clone(){
        Student clone = new Student();
        clone.setFirstname(this.firstname);
        clone.setLastname(this.lastname);
        clone.setBudget(this.budget);
        clone.setIdStudent(this.idStudent);
        clone.setMarks(this.marks);

        Group cloneGroup = new Group();
        cloneGroup.setIdGroup(this.group.getIdGroup());
        cloneGroup.setName(this.group.getName());

        ArrayList<Subject> subjectsClone= new ArrayList<>();
        for (Subject sub : this.group.getSubjects()){
            Subject subject = new Subject();
            subject.setIdSubject(sub.getIdSubject());
            subject.setName(sub.getName());
            subject.setHoursNumber(sub.getHoursNumber());
            subjectsClone.add(subject);
        }
        cloneGroup.setSubjects(subjectsClone);
        clone.setGroup(cloneGroup);
        return clone;
    }
}