import org.example.ServerSemestr5.Model.entity.*;
import org.example.ServerSemestr5.Service.serviceImpl.*;
import org.junit.jupiter.api.RepeatedTest;


public class TestHibernate {

    private UserServiceImpl userService = new UserServiceImpl();
    private FacultyServiceImpl facultyService = new FacultyServiceImpl();
    private SubjectServiceImpl subjectService = new SubjectServiceImpl();
    private GroupServiceImpl groupService = new GroupServiceImpl();
    private StudentServiceImpl studentService = new StudentServiceImpl();

    @RepeatedTest(30)//repeated because we can get unclosed sessions and because of that pull_size error
    void twoSidedUserRelationsTest() throws InterruptedException {
        for(User u:userService.getAllEntities()){
            u.clone();
        }
    }

    @RepeatedTest(30)
    void twoSidedFacultyRelationsTest(){
        for(Faculty f:facultyService.getAllEntities()){
            f.clone();
        }
    }

    @RepeatedTest(30)
    void twoSidedSubjectRelationsTest(){
        for(Subject s:subjectService.getAllEntities()){
            s.clone();
        }
    }

    @RepeatedTest(30)
    void twoSidedGroupRelationsTest(){
        for(Group g:groupService.getAllEntities()){
            g.clone();
        }
    }

    @RepeatedTest(30)
    void twoStudentGroupRelationsTest(){
        for(Student st:studentService.getAllEntities()){
            st.clone();
        }
    }
}
