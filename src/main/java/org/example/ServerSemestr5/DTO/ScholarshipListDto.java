package org.example.ServerSemestr5.DTO;

import com.google.gson.Gson;
import org.example.ServerSemestr5.Enum.ResponseType;
import org.example.ServerSemestr5.Model.entity.Scholarship;
import org.example.ServerSemestr5.Model.tcp.Response;

import java.util.List;

public class ScholarshipListDto{


    public String createResponse(List<Scholarship> entity, ResponseType responseType) {
        String result = "";
        Response response = new Response(responseType, new Gson().toJson(entity));
        result = new Gson().toJson(response);
        return result;
    }



}
