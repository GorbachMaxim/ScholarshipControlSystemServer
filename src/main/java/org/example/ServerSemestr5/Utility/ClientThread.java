package org.example.ServerSemestr5.Utility;




import com.google.gson.Gson;
import org.example.ServerSemestr5.DAO.DAOimpl.FacultyDaoImpl;
import org.example.ServerSemestr5.DAO.DAOimpl.StudentDaoImpl;
import org.example.ServerSemestr5.DTO.DTOImpl.*;
import org.example.ServerSemestr5.DTO.ScholarshipListDto;
import org.example.ServerSemestr5.Enum.RequestType;
import org.example.ServerSemestr5.Enum.ResponseType;
import org.example.ServerSemestr5.Model.entity.*;
import org.example.ServerSemestr5.Model.tcp.Request;
import org.example.ServerSemestr5.Service.ScholarshipService;
import org.example.ServerSemestr5.Service.serviceImpl.*;
import org.hibernate.Session;

import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientThread implements Runnable {
    //---------------------------------
    private Socket clientSocket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    int clientNumber;

    //enums-----------------------------------


    private ResponseType responseType;
    private RequestType requestType;
    //service-----------------------------------
    private UserServiceImpl userService = new UserServiceImpl();
    private FacultyServiceImpl facultyService = new FacultyServiceImpl();

    private PersonServiceImpl personService = new PersonServiceImpl();

    private SubjectServiceImpl subjectService = new SubjectServiceImpl();

    private ScholarshipService scholarshipService = new ScholarshipService();

    private GroupServiceImpl groupService = new GroupServiceImpl();

    private StudentServiceImpl studentService = new StudentServiceImpl();
    //dto----------------------------------

    private UserDtoImpl userDto = new UserDtoImpl();

    private FacultyDtoImpl facultyDto = new FacultyDtoImpl();

    private SubjectDtoImpl subjectDto = new SubjectDtoImpl();

    private ScholarshipListDto scholarshipDto = new ScholarshipListDto();

    private GroupDtoImpl groupDto = new GroupDtoImpl();

    private StudentDtoImpl studentDto = new StudentDtoImpl();


    public ClientThread(Socket acceptSocket, int clientNumber) throws IOException {
        this.clientSocket = acceptSocket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(acceptSocket.getInputStream()));
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(acceptSocket.getOutputStream()));
        this.clientNumber = clientNumber;
    }

    private void sendMessage(String result) throws IOException {
        bufferedWriter.write(result);
        bufferedWriter.newLine();
        bufferedWriter.flush();
    }

    private String getMessage() throws IOException {
        String result = bufferedReader.readLine();
        return result;
    }


    @Override
    public void run() {
        String message;
        boolean cycle = true;
        try {
            while (cycle) {
                /*Session session = HibernateUtil.getSessionFactory().getCurrentSession();
                if (session.isOpen()){
                    session.close();
                }*/
                message = getMessage();
                requestType = new Gson().fromJson(message, Request.class).getRequestType();
                System.out.println("Запрос от клиента № " + clientNumber +": " + requestType);
                switch (requestType) {
                    case LOGIN: {
                        loginCase(message);
                        break;
                    }
                    case REGISTRATION: {
                        registrationCase(message);
                        break;
                    }
                    case GET_SCHOLARSHIP: {
                        getScholarshipCase(message);
                        break;
                    }
                    case SET_SCHOLARSHIP: {
                        setScholarshipCase(message);
                        break;
                    }
                    case GET_USERS: {
                        getUsersCase(message);
                        break;
                    }
                    case ADD_USER: {
                        addUserCase(message);
                        break;
                    }
                    case UPDATE_USER: {
                        updateUserCase(message);
                        break;
                    }
                    case DELETE_USER: {
                        deleteUserCase(message);
                        break;
                    }
                    case GET_FACULTIES: {
                        getFacultiesCase(message);
                        break;
                    }
                    case ADD_FACULTY: {
                        addFacultyCase(message);
                        break;
                    }
                    case UPDATE_FACULTY: {
                        updateFacultyCase(message);
                        break;
                    }
                    case DELETE_FACULTY: {
                        deleteFacultyCase(message);
                        break;
                    }
                    case GET_SUBJECTS: {
                        getSubjectsCase(message);
                        break;
                    }
                    case ADD_SUBJECT: {
                        addSubjectCase(message);
                        break;
                    }
                    case UPDATE_SUBJECT: {
                        updateSubjectCase(message);
                        break;
                    }
                    case DELETE_SUBJECT: {
                        deleteSubjectCase(message);
                        break;
                    }
                    case GET_GROUPS: {
                        getGroupsCase(message);
                        break;
                    }
                    case ADD_GROUP: {
                        addGroupCase(message);
                        break;
                    }
                    case UPDATE_GROUP: {
                        updateGroupCase(message);
                        break;
                    }
                    case DELETE_GROUP: {
                        deleteGroupCase(message);
                        break;
                    }
                    case UPDATE_EXAM: {
                        updateExamsCase(message);
                        break;
                    }
                    case GET_STUDENTS: {
                        getStudentsCase(message);
                        break;
                    }
                    case ADD_STUDENT: {
                        addStudentCase(message);
                        break;
                    }
                    case UPDATE_STUDENT: {
                        updateStudentCase(message);
                        break;
                    }
                    case DELETE_STUDENT: {
                        deleteStudentCase(message);
                        break;
                    }
                }

            }
        } catch (IOException e) {
            System.out.println("Error:" + e.getMessage());
        }
        finally {
            System.out.println("Клиент № " + this.clientNumber + " отключился! ");
        }
    }




    private void getScholarshipCase(String message) throws IOException {
        List<Scholarship> data = scholarshipService.getAllEntities();
        String result;
        result = scholarshipDto.createResponse(data, ResponseType.SUCCESS);
        System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.SUCCESS);
        sendMessage(result);
    }

    private void setScholarshipCase(String message) throws IOException {

        message = getMessage();
        double baseScholarship = new Gson().fromJson(message, Double.class);
        scholarshipService.setScholarshipAmount(baseScholarship);

        String result = new Gson().toJson(ResponseType.SUCCESS);
        System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.SUCCESS);
        sendMessage(result);
    }

    private void registrationCase(String message) throws IOException {
        String result;
        User user = userDto.getRequestEntity(message);
        Person person = user.getPerson();
        Faculty faculty = user.getFaculty();

        if(userService.searchLogin(user)){
            result = new Gson().toJson(ResponseType.FAIL);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.FAIL);
            sendMessage(result);
            return;
        }

        if(!facultyService.isFindEntity(faculty)){
            facultyService.save(faculty);
        }
        else
            faculty = facultyService.searchEntity(faculty);

        if(!personService.isFindEntity(person)){
            personService.save(person);
        }
        else
            person = personService.searchEntity(person);

        user.setFaculty(faculty);
        user.setPerson(person);
        userService.save(user);

        result = new Gson().toJson(ResponseType.SUCCESS);
        System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.SUCCESS);
        sendMessage(result);
    }

    private void getUsersCase(String message) throws IOException {
        List<User> data = userService.getAllEntities();
        String result;
        result = userDto.createResponse(data, ResponseType.SUCCESS);
        System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.SUCCESS);
        sendMessage(result);
    }

    private void addUserCase(String message) throws IOException {
        String result;
        User user = userDto.getRequestEntity(message);
        Person person = user.getPerson();
        Faculty faculty = user.getFaculty();

        if(userService.searchLogin(user)){
            result = new Gson().toJson(ResponseType.FAIL);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.FAIL);
            sendMessage(result);
            return;
        }

        if(!facultyService.isFindEntity(faculty)){
            facultyService.save(faculty);
        }
        else
            faculty = facultyService.searchEntity(faculty);

        if(!personService.isFindEntity(person)){
            personService.save(person);
        }
        else
            person = personService.searchEntity(person);

        user.setFaculty(faculty);
        user.setPerson(person);
        userService.save(user);

        result = new Gson().toJson(ResponseType.SUCCESS);
        System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.SUCCESS);
        sendMessage(result);
    }

    private void updateUserCase(String message) throws IOException {
        String result;
        User user = userDto.getRequestEntity(message);
        Person person = user.getPerson();
        Faculty faculty = user.getFaculty();

        if(!facultyService.isFindEntity(faculty)){
            facultyService.save(faculty);
        }
        else
            faculty = facultyService.searchEntity(faculty);

        if(!personService.isFindEntity(person)){
            personService.save(person);
        }
        else
            person = personService.searchEntity(person);

        user.setFaculty(faculty);
        user.setPerson(person);
        userService.update(user);

        result = new Gson().toJson(ResponseType.SUCCESS);
        System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.SUCCESS);
        sendMessage(result);
    }

    private void deleteUserCase(String message) throws IOException {
        try {
            String result;
            User user = userDto.getRequestEntity(message);
            Person person = user.getPerson();
            Faculty faculty = user.getFaculty();


            faculty = facultyService.searchEntity(faculty);

            person = personService.searchEntity(person);

            user.setFaculty(faculty);
            user.setPerson(person);
            userService.delete(user);

            result = new Gson().toJson(ResponseType.SUCCESS);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.SUCCESS);
            sendMessage(result);
        }
        catch (Exception e){
            System.out.println("Ошибка удаления пользователя: "+ e.getMessage());
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.FAIL);
        }
    }

    private void loginCase(String message) throws IOException {
        String result;
        User user = userDto.getRequestEntity(message);
        user = userService.searchEntity(user);
        if(user.getLogin().length() < 5){
            result = userDto.createResponse(new User(), ResponseType.FAIL);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.FAIL);
            sendMessage(result);
            return;
        }
        else {
            result = userDto.createResponse(user, ResponseType.SUCCESS);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.SUCCESS);

        }
        sendMessage(result);
    }

    private void getFacultiesCase(String message) throws IOException {
        List<Faculty> data = facultyService.getAllEntities();
        String result;
        result = facultyDto.createResponse(data, ResponseType.SUCCESS);
        System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.SUCCESS);
        sendMessage(result);
    }

    private void addFacultyCase(String message) throws IOException {
        String result;
        Faculty faculty = facultyDto.getRequestEntity(message);




        if(!facultyService.isFindEntity(faculty)){
            facultyService.save(faculty);
            result = new Gson().toJson(ResponseType.SUCCESS);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.SUCCESS);
            sendMessage(result);
        }
        else {
            result = new Gson().toJson(ResponseType.FAIL);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.FAIL);
            sendMessage(result);
        }
    }

    private void updateFacultyCase(String message) throws IOException {
        String result;
        Faculty faculty = facultyDto.getRequestEntity(message);

        if(facultyService.update(faculty)){
            result = new Gson().toJson(ResponseType.SUCCESS);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.SUCCESS);
            sendMessage(result);
        }
        else {
            result = new Gson().toJson(ResponseType.FAIL);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.FAIL);
            sendMessage(result);
        }


    }

    private void deleteFacultyCase(String message) throws IOException {
        String result;
        Faculty faculty = facultyDto.getRequestEntity(message);


        if(facultyService.delete(faculty)) {
            result = new Gson().toJson(ResponseType.SUCCESS);
            System.out.println("Ответ клиенту № " + clientNumber + ": " + ResponseType.SUCCESS);
            sendMessage(result);
        }
        else {
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.FAIL);
        }
    }

    private void getSubjectsCase(String message) throws IOException {
        List<Subject> data = subjectService.getAllEntities();
        String result;
        result = subjectDto.createResponse(data, ResponseType.SUCCESS);
        System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.SUCCESS);
        sendMessage(result);
    }

    private void addSubjectCase(String message) throws IOException {
        String result;
        Subject subject = subjectDto.getRequestEntity(message);

        if(!subjectService.isFindEntity(subject)){
            subjectService.save(subject);
            result = new Gson().toJson(ResponseType.SUCCESS);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.SUCCESS);
            sendMessage(result);
        }
        else {
            result = new Gson().toJson(ResponseType.FAIL);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.FAIL);
            sendMessage(result);
        }
    }

    private void updateSubjectCase(String message) throws IOException {
        String result;
        Subject subject = subjectDto.getRequestEntity(message);


        if(subjectService.update(subject)){
            result = new Gson().toJson(ResponseType.SUCCESS);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.SUCCESS);
            sendMessage(result);
        }
        else {
            result = new Gson().toJson(ResponseType.FAIL);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.FAIL);
            sendMessage(result);
        }
    }

    private void deleteSubjectCase(String message) throws IOException {
        String result;
        Subject subject = subjectDto.getRequestEntity(message);


        if(subjectService.delete(subject)) {
            result = new Gson().toJson(ResponseType.SUCCESS);
            System.out.println("Ответ клиенту № " + clientNumber + ": " + ResponseType.SUCCESS);
            sendMessage(result);
        }
        else {
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.FAIL);
        }
    }

    private void getGroupsCase(String message) throws IOException {
        List<Group> data = groupService.getAllEntities();
        String result;
        result = groupDto.createResponse(data, ResponseType.SUCCESS);
        System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.SUCCESS);
        sendMessage(result);
    }

    private void addGroupCase(String message) throws IOException {
        String result;
        Group group = groupDto.getRequestEntity(message);
        if(!groupService.isFindEntity(group)){

            Faculty faculty = group.getFaculty();
            if(!facultyService.isFindEntity(faculty)){
                facultyService.save(faculty);
            }
            else
                faculty = facultyService.searchEntity(faculty);
            group.setFaculty(faculty);

            groupService.save(group);
            result = new Gson().toJson(ResponseType.SUCCESS);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.SUCCESS);
            sendMessage(result);
        }
        else {
            result = new Gson().toJson(ResponseType.FAIL);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.FAIL);
            sendMessage(result);
        }
    }

    private void updateGroupCase(String message) throws IOException {
        String result;
        Group group = groupDto.getRequestEntity(message);
        Faculty faculty = group.getFaculty();

        if(groupService.update(group)){
            if(!facultyService.isFindEntity(faculty)){
                facultyService.save(faculty);
            }
            else
                faculty = facultyService.searchEntity(faculty);
            group.setFaculty(faculty);
            groupService.update(group);
            result = new Gson().toJson(ResponseType.SUCCESS);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.SUCCESS);
            sendMessage(result);
        }
        else {
            result = new Gson().toJson(ResponseType.FAIL);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.FAIL);
            sendMessage(result);
        }
    }

    private void deleteGroupCase(String message) throws IOException {
        String result;
        Group group = groupDto.getRequestEntity(message);


        if(groupService.delete(group)) {
            result = new Gson().toJson(ResponseType.SUCCESS);
            System.out.println("Ответ клиенту № " + clientNumber + ": " + ResponseType.SUCCESS);
            sendMessage(result);
        }
        else {
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.FAIL);
        }
    }

    private void updateExamsCase(String message) throws IOException {
        String result;
        Group group = groupDto.getRequestEntity(message);
        List<Subject> groupSubjects = group.getSubjects();
        List<Subject> allSubjects = subjectService.getAllEntities();
        for (Subject groupSubject: groupSubjects){
            for(Subject subject : allSubjects){
                if(subject.getName().equals(groupSubject.getName())){
                    groupSubject = subject;
                }
            }
        }
        group.setSubjects(groupSubjects);

        if(groupService.updateSubjects(group)){
            result = new Gson().toJson(ResponseType.SUCCESS);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.SUCCESS);
            sendMessage(result);
        }
        else {
            result = new Gson().toJson(ResponseType.FAIL);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.FAIL);
            sendMessage(result);
        }
    }

    private void getStudentsCase(String message) throws IOException {
        List<Student> data = studentService.getAllEntities();
        String result;
        result = studentDto.createResponse(data, ResponseType.SUCCESS);
        System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.SUCCESS);
        sendMessage(result);
    }

    private void addStudentCase(String message) throws IOException {
        String result;
        Student student = studentDto.getRequestEntity(message);
        Group group = student.getGroup();
        if(!groupService.isFindEntity(group)){
            result = new Gson().toJson(ResponseType.FAIL);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.FAIL);
            sendMessage(result);
            return;
        }
        else
            group = groupService.searchEntity(group);
        student.setGroup(group);
        if(!studentService.isFindEntity(student)){
            studentService.save(student);
            result = new Gson().toJson(ResponseType.SUCCESS);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.SUCCESS);
            sendMessage(result);
        }
        else {
            result = new Gson().toJson(ResponseType.FAIL);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.FAIL);
            sendMessage(result);
        }
    }

    private void updateStudentCase(String message) throws IOException {
        String result;
        Student student = studentDto.getRequestEntity(message);
        Group group = student.getGroup();
        if(!groupService.isFindEntity(group)){
            result = new Gson().toJson(ResponseType.FAIL);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.FAIL);
            sendMessage(result);
            return;
        }
        else
            group = groupService.searchEntity(group);
        student.setGroup(group);
        if(studentService.update(student)){
            result = new Gson().toJson(ResponseType.SUCCESS);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.SUCCESS);
            sendMessage(result);
        }
        else {
            result = new Gson().toJson(ResponseType.FAIL);
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.FAIL);
            sendMessage(result);
        }
    }

    private void deleteStudentCase(String message) throws IOException {
        String result;
        Student student = studentDto.getRequestEntity(message);
        if(studentService.delete(student)) {
            result = new Gson().toJson(ResponseType.SUCCESS);
            System.out.println("Ответ клиенту № " + clientNumber + ": " + ResponseType.SUCCESS);
            sendMessage(result);
        }
        else {
            System.out.println("Ответ клиенту № " + clientNumber +": " + ResponseType.FAIL);
        }
    }
}

