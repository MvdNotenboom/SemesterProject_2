package client.view;


import client.view.registerview.RegisterView;
import client.view.studentview.studentmainview.StudentMainView;
import client.view.studentview.studentsessionview.StudentSessionView;
import client.view.teacherview.classroomview.ClassroomView;
import client.view.teacherview.teacherinsessionview.TeacherInSessionView;
import client.view.teacherview.teachermainview.TeacherMainView;
import client.view.teacherview.teachersessionview.TeacherSessionView;
import client.view.teacherview.topicview.TopicView;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import client.view.loginview.LoginView;
import client.viewmodel.MainViewModel;

import java.io.IOException;

/**
 * Initiates the graphical user interface when the software is launched
 * @author group6
 * @version 2.3.4
 */
public class MainView
{
    private MainViewModel mainViewModel;
    private static MainView instance;

    /**
     * @param mainViewModel
     */
    private MainView(MainViewModel mainViewModel)
    {
        this.mainViewModel = mainViewModel;
    }

    /**
     * @throws Exception
     */
    public void start() throws Exception
    {
        openView();
    }

    /**
     * Singleton implementation
     * @param mainViewModel
     * @return
     */
    public static MainView getInstance(MainViewModel mainViewModel)
    {
        if(instance == null)
        {
            instance = new MainView(mainViewModel);
        }

        return instance;
    }

    /**
     * Sets up the scene and the stage of the graphical user interface
     * @throws Exception
     */
    private void openView() throws Exception
    {
        Scene scene = null;
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = null;

        Stage localStage = new Stage();

        //Loading fxml resources
        fxmlLoader.setLocation(getClass().getResource("loginview/LoginView.fxml"));
        root = fxmlLoader.load();
        scene = new Scene(root);

        //Controller initiation
        LoginView loginView = fxmlLoader.getController();
        loginView.initiate(mainViewModel.getLoginViewModel());

        localStage.setTitle("Nucleus Software: Learning Language Platform");
        localStage.setScene(scene);
        localStage.show();
    }

    /**
     * Sets up the scene and the stage for register view
     * @param actionEvent
     */
    public void registerView(ActionEvent actionEvent)
    {
        Parent registerViewParent = null;
        FXMLLoader fxmlLoader = new FXMLLoader();
        try
        {
            //Loads the fxml resources
            fxmlLoader.setLocation(getClass().getResource("registerview/RegisterView.fxml"));
            registerViewParent = fxmlLoader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Scene registerViewScene = new Scene(registerViewParent);

        //Gets the current stage
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        //Initiates the controller of the 'Register'
        RegisterView registerView = fxmlLoader.getController();
        registerView.initiate(mainViewModel.getRegisterViewModel());

        stage.setScene(registerViewScene);
        stage.show();
    }

    /**
     * Sets up the scene and the stage for login view
     * @param actionEvent
     */
    public void loginView(ActionEvent actionEvent)
    {
        Parent loginViewParent = null;
        FXMLLoader fxmlLoader = new FXMLLoader();

        try
        {
            //Loads the fxml resources
            fxmlLoader.setLocation(getClass().getResource("loginview/LoginView.fxml"));
            loginViewParent = fxmlLoader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Scene registerViewScene = new Scene(loginViewParent);

        //Gets the current stage
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        //Initiates the controller of the 'Login'
        LoginView loginView = fxmlLoader.getController();
        loginView.initiate(mainViewModel.getLoginViewModel());

        stage.setScene(registerViewScene);
        stage.show();
    }

    /**
     * Sets up the scene and the stage for teacher's main view
     * @param actionEvent
     */
    public void teacherMainView(ActionEvent actionEvent)
    {
        Parent teacherMainViewParent = null;
        FXMLLoader fxmlLoader = new FXMLLoader();

        try
        {
            //Loads the fxml resources
            fxmlLoader.setLocation(getClass().getResource("teacherview/teachermainview/TeacherMainView.fxml"));
            teacherMainViewParent = fxmlLoader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //Initiates the controller of the 'TeacherMainView'
        TeacherMainView teacherView = fxmlLoader.getController();
        teacherView.initiate(mainViewModel.getTeacherMainViewModel());

        Scene teacherMainViewScene = new Scene(teacherMainViewParent);

        //Gets the current stage
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        stage.setScene(teacherMainViewScene);
        stage.show();
    }

    /**
     * Sets up the scene and the stage for the student's main view
     * @param actionEvent
     */
    public void studentMainView(ActionEvent actionEvent)
    {
        Parent studentMainViewParent = null;
        FXMLLoader fxmlLoader = new FXMLLoader();

        try
        {
            //Loads the fxml resources
            fxmlLoader.setLocation(getClass().getResource("studentview/studentmainview/StudentMainView.fxml"));
            studentMainViewParent = fxmlLoader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //Initiates the controller of the 'StudentMainView'
        StudentMainView studentView = fxmlLoader.getController();
        studentView.initiate(mainViewModel.getStudentMainViewModel());

        Scene studentMainViewScene = new Scene(studentMainViewParent);

        //Gets the current stage
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        stage.setScene(studentMainViewScene);
        stage.show();
    }

    /**
     * Sets up the scene and the stage for the student's session view
     * @param actionEvent
     */
    public void studentSessionView(ActionEvent actionEvent)
    {
        Parent sessionViewParent = null;
        FXMLLoader fxmlLoader = new FXMLLoader();

        try
        {
            //Loads the fxml resources
            fxmlLoader.setLocation(getClass().getResource("studentview/studentsessionview/StudentSessionView.fxml"));
            sessionViewParent = fxmlLoader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Scene sessionViewScene = new Scene(sessionViewParent);

        //Gets the current stage
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        //Initiates the controller of the 'StudentSessionView'
        StudentSessionView sessionView = fxmlLoader.getController();
        sessionView.initiate(mainViewModel.getStudentSessionViewModel());

        stage.setScene(sessionViewScene);
        stage.show();

        stage.setOnCloseRequest(windowEvent ->
        {
            mainViewModel.notifyStudentOut();
            System.exit(0);
        });
    }

    /**
     * Sets up the scene and the stage for the teacher's classroom view
     * @param actionEvent
     */
    public void classroomView(ActionEvent actionEvent)
    {

        Parent classroomViewParent = null;
        FXMLLoader fxmlLoader = new FXMLLoader();

        try
        {
            //Loads the fxml
            fxmlLoader.setLocation(getClass().getResource("teacherview/classroomview/ClassroomView.fxml"));
            classroomViewParent = fxmlLoader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //Gets the current stage
        Scene classroomViewScene = new Scene(classroomViewParent);

        //Gets the current stage
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        //Initiates the controller of the 'Classroom'
        ClassroomView classroomView = fxmlLoader.getController();
        classroomView.initiate(mainViewModel.getClassroomViewModel());

        stage.setScene(classroomViewScene);
        stage.show();
    }

    /**
     * Sets up the scene and the stage for the teacher's session view
     * @param actionEvent
     */
    public void sessionView(ActionEvent actionEvent)
    {
        Parent sessionViewParent = null;
        FXMLLoader fxmlLoader = new FXMLLoader();

        try
        {
            //Loads the fxml
            fxmlLoader.setLocation(getClass().getResource("teacherview/teachersessionview/TeacherSessionView.fxml"));
            sessionViewParent = fxmlLoader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //Gets the current stage
        Scene sessionViewScene = new Scene(sessionViewParent);

        //Gets the current stage
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        //Initiates the controller of the 'Session'
        TeacherSessionView teacherSessionView = fxmlLoader.getController();
        teacherSessionView.initiate(mainViewModel.getTeacherSessionViewModel());

        stage.setScene(sessionViewScene);
        stage.show();
    }

    /**
     * Sets up the scene and the stage for the teacher's topic view
     * @param actionEvent
     */
    public void topicView(ActionEvent actionEvent)
    {
        Parent topicViewParent = null;
        FXMLLoader fxmlLoader = new FXMLLoader();

        try
        {
            //Loads the fxml
            fxmlLoader.setLocation(getClass().getResource("teacherview/topicview/TopicView.fxml"));
            topicViewParent = fxmlLoader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        //Gets the current stage
        Scene topicViewScene = new Scene(topicViewParent);

        //Gets the current stage
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        //Initiates the controller of the 'Session'
        TopicView topicView = fxmlLoader.getController();
        topicView.initiate(mainViewModel.getTopicViewModel());

        stage.setScene(topicViewScene);
        stage.show();
    }

    /**
     * Sets up the scene and the stage for the teacher's inSession view
     * @param actionEvent
     */
    public void inSessionView(ActionEvent actionEvent)
    {
        Parent sessionViewParent = null;
        FXMLLoader fxmlLoader = new FXMLLoader();

        try
        {
            //Loads the fxml resources
            fxmlLoader.setLocation(getClass().getResource("teacherview/teacherinsessionview/TeacherInSessionView.fxml"));
            sessionViewParent = fxmlLoader.load();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        Scene sessionViewScene = new Scene(sessionViewParent);

        //Gets the current stage
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        //Initiates the controller of the 'TeacherInSessionView'
        TeacherInSessionView teacherInSessionView = fxmlLoader.getController();
        teacherInSessionView.initiate(mainViewModel.getTeacherInSessionViewModel());

        stage.setScene(sessionViewScene);
        stage.show();
    }
}
