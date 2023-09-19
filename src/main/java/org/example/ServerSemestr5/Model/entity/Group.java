package org.example.ServerSemestr5.Model.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_group")
    private int idGroup;

    @Column(name = "group_name")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Student> students;

    @ManyToMany
    @JoinTable (name="groups_subjects",
            joinColumns=@JoinColumn (name="group_id"),
            inverseJoinColumns=@JoinColumn(name="subject_id"))
    private List<Subject> subjects;

    public Group clone(){
        Group clone = new Group();
        clone.setIdGroup(this.idGroup);
        clone.setName(this.name);

        Faculty cloneFaculty = new Faculty();
        cloneFaculty.setFacultyName(this.getFaculty().getFacultyName());
        cloneFaculty.setIdFaculty(this.getFaculty().getIdFaculty());

        clone.setFaculty(cloneFaculty);

        ArrayList<Student> studentsClone = new ArrayList<>();
        for (Student st : this.students){
            Student student = new Student();
            student.setIdStudent(st.getIdStudent());
            student.setFirstname(st.getFirstname());
            student.setLastname(st.getLastname());
            student.setMarks(st.getMarks());
            studentsClone.add(student);
        }
        clone.setStudents(studentsClone);

        ArrayList<Subject> subjectsClone= new ArrayList<>();
        for (Subject sub : this.subjects){
            Subject subject = new Subject();
            subject.setIdSubject(sub.getIdSubject());
            subject.setName(sub.getName());
            subject.setHoursNumber(sub.getHoursNumber());
            subjectsClone.add(subject);
        }
        clone.setSubjects(subjectsClone);
        return clone;
    }
}