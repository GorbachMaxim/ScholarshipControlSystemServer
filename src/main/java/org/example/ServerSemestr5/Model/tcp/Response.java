package org.example.ServerSemestr5.Model.tcp;

import lombok.Data;
import org.example.ServerSemestr5.Enum.ResponseType;

@Data
public class Response {
    private ResponseType responseType;
    private String responseMessage;

    public Response(ResponseType responseType, String responseMessage) {
        this.responseType = responseType;
        this.responseMessage = responseMessage;
    }
}
