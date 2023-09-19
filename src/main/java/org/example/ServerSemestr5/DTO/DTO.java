package org.example.ServerSemestr5.DTO;

import org.example.ServerSemestr5.Enum.RequestType;
import org.example.ServerSemestr5.Enum.ResponseType;

import java.util.ArrayList;
import java.util.List;

public interface DTO<T> {

    String createResponse(T entity, ResponseType responseType);

    T getRequestEntity(String result);

    RequestType getRequestType(String result);

    String createResponse(List<T> entities, ResponseType responseType);

}
