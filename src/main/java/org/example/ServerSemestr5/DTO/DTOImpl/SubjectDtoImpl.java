package org.example.ServerSemestr5.DTO.DTOImpl;

import com.google.gson.Gson;
import org.example.ServerSemestr5.DTO.DTO;
import org.example.ServerSemestr5.Enum.RequestType;
import org.example.ServerSemestr5.Enum.ResponseType;
import org.example.ServerSemestr5.Model.entity.Faculty;
import org.example.ServerSemestr5.Model.entity.Subject;
import org.example.ServerSemestr5.Model.tcp.Request;
import org.example.ServerSemestr5.Model.tcp.Response;

import java.util.ArrayList;
import java.util.List;

public class SubjectDtoImpl implements DTO<Subject> {
    private Request request;

    @Override
    public String createResponse(Subject entity, ResponseType responseType) {
        String result = "";
        Subject subject = entity.clone();
        Response response = new Response(responseType, new Gson().toJson(subject));
        result = new Gson().toJson(response);
        return result;
    }

    @Override
    public Subject getRequestEntity(String result) {
        Subject subject;
        request = new Gson().fromJson(result, Request.class);
        subject = new Gson().fromJson(request.getRequestMessage(), Subject.class);
        return subject;
    }

    @Override
    public RequestType getRequestType(String result) {
        request = new Gson().fromJson(result, Request.class);
        return request.getRequestType();
    }

    @Override
    public String createResponse(List<Subject> entities, ResponseType responseType) {
        String result = "";
        List<Subject> subjectList = new ArrayList<>();
        for (int i=0;i<entities.size();i++) {
            subjectList.add(entities.get(i).clone());
        }
        Response response = new Response(responseType, new Gson().toJson(subjectList));
        result = new Gson().toJson(response);
        return result;
    }

}
