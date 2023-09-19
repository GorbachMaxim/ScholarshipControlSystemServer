package org.example.ServerSemestr5.DTO.DTOImpl;

import com.google.gson.Gson;
import org.example.ServerSemestr5.DTO.DTO;
import org.example.ServerSemestr5.Enum.RequestType;
import org.example.ServerSemestr5.Enum.ResponseType;
import org.example.ServerSemestr5.Model.entity.Group;
import org.example.ServerSemestr5.Model.entity.Student;
import org.example.ServerSemestr5.Model.tcp.Request;
import org.example.ServerSemestr5.Model.tcp.Response;

import java.util.ArrayList;
import java.util.List;

public class StudentDtoImpl implements DTO<Student> {
    private Request request;

    @Override
    public String createResponse(Student entity, ResponseType responseType) {
        String result = "";
        Student student = entity.clone();
        Response response = new Response(responseType, new Gson().toJson(student));
        result = new Gson().toJson(response);
        return result;
    }

    @Override
    public Student getRequestEntity(String result) {
        Student student;
        request = new Gson().fromJson(result, Request.class);
        student = new Gson().fromJson(request.getRequestMessage(), Student.class);
        return student;
    }

    @Override
    public RequestType getRequestType(String result) {
        request = new Gson().fromJson(result, Request.class);
        return request.getRequestType();
    }

    @Override
    public String createResponse(List<Student> entities, ResponseType responseType) {
        String result = "";
        List<Student> studentList = new ArrayList<>();
        for (int i=0;i<entities.size();i++) {
            studentList.add(entities.get(i).clone());
        }
        Response response = new Response(responseType, new Gson().toJson(studentList));
        result = new Gson().toJson(response);
        return result;
    }
}
