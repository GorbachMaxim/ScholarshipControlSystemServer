package org.example.ServerSemestr5.Model.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private int idUser;

    @Column(name = "login")
    private String login;

    @Column(name = "password")
    private int password;

    @Column(name = "isAdmin")
    private boolean isAdmin;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @Builder
    public User(int idUser, String login, int password, boolean isAdmin, Person person, Faculty faculty) {
        this.idUser = idUser;
        this.login = login;
        this.password = password;
        this.isAdmin = isAdmin;
        this.person = person;
        this.faculty = faculty;
    }

    public User() {

    }


    public User clone(){
        Person personClone = new Person();
        if(this.person != null) {
            personClone.setFirstname(this.getPerson().getFirstname());
            personClone.setLastname(this.getPerson().getLastname());
            personClone.setPatronymic(this.getPerson().getPatronymic());
            personClone.setIdPerson(this.getPerson().getIdPerson());
        }

        Faculty cloneFaculty = new Faculty();
        if(this.faculty != null) {
            cloneFaculty.setFacultyName(this.getFaculty().getFacultyName());
            cloneFaculty.setIdFaculty(this.getFaculty().getIdFaculty());
        }

        User clone = User.builder()
                .idUser(this.idUser)
                .login(this.login)
                .password(this.password)
                .isAdmin(this.isAdmin)
                .faculty(cloneFaculty)
                .person(personClone)
                        .build();
        return clone;
    }
}
