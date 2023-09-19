package org.example.ServerSemestr5.Model.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Entity
@Table(name = "scholarship")
public class Scholarship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_scholarship")
    private int idScholarship;

    @Column(name = "mark")
    private String mark;

    @Column(name = "scholarship_amount")
    private double scholarshipAmount;

    @Column(name = "coefficient")
    private double coefficient;

    @Column(name = "from_mark")
    private int fromMark;

    @Column(name = "to_mark")
    private int toMark;


}
