package org.example.ServerSemestr5.Model.tcp;




import lombok.Data;
import org.example.ServerSemestr5.Enum.RequestType;

@Data
public class Request {

    private RequestType requestType;

    private String requestMessage;

    public Request(RequestType requestType, String requestMessage) {
        this.requestType = requestType;
        this.requestMessage = requestMessage;
    }
}
