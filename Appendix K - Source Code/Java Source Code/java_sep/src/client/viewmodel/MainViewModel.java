package client.viewmodel;
import client.model.IModel;
import client.viewmodel.loginviewmodel.LoginViewModel;
import client.viewmodel.registerviewmodel.RegisterViewModel;
import client.viewmodel.studentviewmodel.sessionviewmodel.StudentSessionViewModel;
import client.viewmodel.studentviewmodel.studentmainviewmodel.StudentMainViewModel;
import client.viewmodel.teacherviewmodel.classroomviewmodel.ClassroomViewModel;
import client.viewmodel.teacherviewmodel.insessionviewmodel.TeacherInSessionViewModel;
import client.viewmodel.teacherviewmodel.sessionviewmodel.TeacherSessionViewModel;
import client.viewmodel.teacherviewmodel.teachermainviewmodel.TeacherMainViewModel;
import client.viewmodel.teacherviewmodel.topicviewmodel.TopicViewModel;

/**
 * Main class for the ViewModels
 * @author group6
 * @version 1.0.0
 */
public class MainViewModel
{
    private IModel model;
    private LoginViewModel loginViewModel;
    private RegisterViewModel registerViewModel;
    private TeacherMainViewModel teacherMainViewModel;
    private TeacherSessionViewModel teacherSessionViewModel;
    private StudentMainViewModel studentMainViewModel;
    private TeacherInSessionViewModel teacherInSessionViewModel;
    private StudentSessionViewModel studentSessionViewModel;
    private ClassroomViewModel classroomViewModel;
    private TopicViewModel topicViewModel;
    private static MainViewModel instance;

    /**
     * @param model
     */
    private MainViewModel(IModel model)
    {
        this.model = model;
        loginViewModel = new LoginViewModel(model);
        registerViewModel = new RegisterViewModel(model);
        teacherMainViewModel = new TeacherMainViewModel(model);
        teacherSessionViewModel = new TeacherSessionViewModel(model);
        studentMainViewModel = new StudentMainViewModel(model);
        teacherInSessionViewModel = new TeacherInSessionViewModel(model);
        studentSessionViewModel = new StudentSessionViewModel(model);
        topicViewModel = new TopicViewModel(model);
        classroomViewModel = new ClassroomViewModel(model);
    }

    /**
     * Singleton for the class
     * @param model
     * @return MainViewModel
     */
    public static MainViewModel getInstance(IModel model)
    {
        //Lazy instantiation
        if(instance == null)
        {
            instance = new MainViewModel(model);
        }
            return instance;
    }

    /**
     * @return LoginViewModel
     */
    public LoginViewModel getLoginViewModel()
    {
        return loginViewModel;
    }

    /**
     * @return RegisterViewModel
     */
    public RegisterViewModel getRegisterViewModel()
    {
        return registerViewModel;
    }

    /**
     * @return TeacherViewModel
     */
    public TeacherMainViewModel getTeacherMainViewModel()
    {
        return teacherMainViewModel;
    }

    /**
     * @return TeacherSessionViewModel
     */
    public TeacherSessionViewModel getTeacherSessionViewModel()
    {
        return teacherSessionViewModel;
    }

    /**
     * @return StudentMainViewModel
     */
    public StudentMainViewModel getStudentMainViewModel()
    {
        return studentMainViewModel;
    }

    /**
     * @return TeacherInSessionViewModel
     */
    public TeacherInSessionViewModel getTeacherInSessionViewModel()
    {
        return teacherInSessionViewModel;
    }

    /**
     * @return StudentSessionViewModel
     */
    public StudentSessionViewModel getStudentSessionViewModel()
    {
        return studentSessionViewModel;
    }

    /**
     * @return TopicViewModel
     */
    public TopicViewModel getTopicViewModel()
    {
        return topicViewModel;
    }

    /**
     * @return ClassroomViewModel
     */
    public ClassroomViewModel getClassroomViewModel()
    {
        return classroomViewModel;
    }

    /**
     * Asks the model to notify about a student leaving
     */
    public void notifyStudentOut()
    {
        model.notifyStudentOut();
    }
}
