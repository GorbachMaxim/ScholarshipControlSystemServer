package org.example.ServerSemestr5.DTO.DTOImpl;

import com.google.gson.Gson;
import org.example.ServerSemestr5.DTO.DTO;
import org.example.ServerSemestr5.Enum.RequestType;
import org.example.ServerSemestr5.Enum.ResponseType;
import org.example.ServerSemestr5.Model.entity.Faculty;
import org.example.ServerSemestr5.Model.tcp.Request;
import org.example.ServerSemestr5.Model.tcp.Response;

import java.util.ArrayList;
import java.util.List;

public class FacultyDtoImpl implements DTO<Faculty> {
    private Request request;

    @Override
    public String createResponse(Faculty entity, ResponseType responseType) {
        String result = "";
        Faculty faculty = entity.clone();
        Response response = new Response(responseType, new Gson().toJson(faculty));
        result = new Gson().toJson(response);
        return result;
    }

    @Override
    public Faculty getRequestEntity(String result) {
        Faculty faculty;
        request = new Gson().fromJson(result, Request.class);
        faculty = new Gson().fromJson(request.getRequestMessage(), Faculty.class);
        return faculty;
    }

    @Override
    public RequestType getRequestType(String result) {
        request = new Gson().fromJson(result, Request.class);
        return request.getRequestType();
    }

    @Override
    public String createResponse(List<Faculty> entities, ResponseType responseType) {
        String result = "";
        List<Faculty> facultyList = new ArrayList<>();
        for (int i=0;i<entities.size();i++) {
            facultyList.add(entities.get(i).clone());
        }
        Response response = new Response(responseType, new Gson().toJson(facultyList));
        result = new Gson().toJson(response);
        return result;
    }

}
