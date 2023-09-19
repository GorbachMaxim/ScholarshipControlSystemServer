package org.example.ServerSemestr5.Model.entity;


import com.google.gson.Gson;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "faculties")
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_faculty")
    private int idFaculty;

    @Column(name = "faculty_name")
    private String facultyName;

    @OneToMany(mappedBy = "faculty", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Group> groups;

    @OneToMany(mappedBy = "faculty", fetch = FetchType.LAZY, cascade = CascadeType.ALL)//EAGER
    private List<User> users;


    public Faculty clone(){
        Faculty clone = new Faculty();
        clone.idFaculty = this.idFaculty;
        clone.facultyName = this.facultyName;
        return clone;
    }
}
