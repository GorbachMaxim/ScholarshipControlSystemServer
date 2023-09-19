package org.example.ServerSemestr5.DTO.DTOImpl;


import com.google.gson.Gson;
import org.example.ServerSemestr5.DTO.DTO;
import org.example.ServerSemestr5.Enum.RequestType;
import org.example.ServerSemestr5.Enum.ResponseType;
import org.example.ServerSemestr5.Model.entity.User;
import org.example.ServerSemestr5.Model.tcp.Request;
import org.example.ServerSemestr5.Model.tcp.Response;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import java.util.ArrayList;
import java.util.List;

public class UserDtoImpl implements DTO<User> {
    private Request request;

    @Override
    public String createResponse(User entity, ResponseType responseType) {
        String result = "";
        User user = entity.clone();
        Response response = new Response(responseType, new Gson().toJson(user));
        result = new Gson().toJson(response);
        return result;
    }

    @Override
    public User getRequestEntity(String result) {
        User user;
        request = new Gson().fromJson(result, Request.class);
        user = new Gson().fromJson(request.getRequestMessage(), User.class);
        return user;
    }

    @Override
    public RequestType getRequestType(String result) {
        request = new Gson().fromJson(result, Request.class);
        return request.getRequestType();
    }

    @Override
    public String createResponse(List<User> entities, ResponseType responseType) {
        String result = "";
        List<User> userList = new ArrayList<>();
        for (int i=0;i<entities.size();i++) {
            userList.add(entities.get(i).clone());
        }
        Response response = new Response(responseType, new Gson().toJson(userList));
        result = new Gson().toJson(response);
        return result;
    }
}
