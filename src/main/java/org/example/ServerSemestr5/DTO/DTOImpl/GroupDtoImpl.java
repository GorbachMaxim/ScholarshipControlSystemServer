package org.example.ServerSemestr5.DTO.DTOImpl;

import com.google.gson.Gson;
import org.example.ServerSemestr5.DTO.DTO;
import org.example.ServerSemestr5.Enum.RequestType;
import org.example.ServerSemestr5.Enum.ResponseType;
import org.example.ServerSemestr5.Model.entity.Group;
import org.example.ServerSemestr5.Model.tcp.Request;
import org.example.ServerSemestr5.Model.tcp.Response;

import java.util.ArrayList;
import java.util.List;

public class GroupDtoImpl implements DTO<Group> {
    private Request request;

    @Override
    public String createResponse(Group entity, ResponseType responseType) {
        String result = "";
        Group group = entity.clone();
        Response response = new Response(responseType, new Gson().toJson(group));
        result = new Gson().toJson(response);
        return result;
    }

    @Override
    public Group getRequestEntity(String result) {
        Group group;
        request = new Gson().fromJson(result, Request.class);
        group = new Gson().fromJson(request.getRequestMessage(), Group.class);
        return group;
    }

    @Override
    public RequestType getRequestType(String result) {
        request = new Gson().fromJson(result, Request.class);
        return request.getRequestType();
    }

    @Override
    public String createResponse(List<Group> entities, ResponseType responseType) {
        String result = "";
        List<Group> groupList = new ArrayList<>();
        for (int i=0;i<entities.size();i++) {
            groupList.add(entities.get(i).clone());
        }
        Response response = new Response(responseType, new Gson().toJson(groupList));
        result = new Gson().toJson(response);
        return result;
    }

}
