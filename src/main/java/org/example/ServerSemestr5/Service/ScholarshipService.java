package org.example.ServerSemestr5.Service;


import org.example.ServerSemestr5.DAO.DAOimpl.ScholarshipDaoImpl;
import org.example.ServerSemestr5.Model.entity.Scholarship;

import java.util.List;

public class ScholarshipService {

    private ScholarshipDaoImpl scholarshipDAO;

    public ScholarshipService() {
        this.scholarshipDAO = new ScholarshipDaoImpl();
    }

    public List<Scholarship> getAllEntities() {
        return scholarshipDAO.findAllEntity();
    }

    public void setScholarshipAmount(double amount){
        scholarshipDAO.setAmount(amount);
    }

}
