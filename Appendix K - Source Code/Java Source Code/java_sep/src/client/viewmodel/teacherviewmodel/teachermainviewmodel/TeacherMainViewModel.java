package client.viewmodel.teacherviewmodel.teachermainviewmodel;

import client.model.IModel;
import client.view.MainView;
import client.viewmodel.MainViewModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;

/**
 * Links the controller of the 'TeacherMain' GUI with the business logic
 * @author group6
 * @version 1.0.0
 */
public class TeacherMainViewModel
{
    private IModel model;
    private StringProperty userName, accountNumber;

    /**
     * Constructor
     * @param model
     */
    public TeacherMainViewModel(IModel model)
    {
        this.model = model;

        //Initiating the properties
        this.userName = new SimpleStringProperty();
        this.accountNumber = new SimpleStringProperty();
    }

    /**
     * Links the 'Session' button with the business logic
     * Sets the scene for the 'Session' GUI
     */
    public void openSession(ActionEvent actionEvent)
    {
        //Switching to TeacherSessionView
        MainView.getInstance(MainViewModel.getInstance(model)).sessionView(actionEvent);
    }

    /**
     * Links the 'History' button with the business logic
     */
    public void openHistory()
    {
        //Switching to HistoryView
    }

    /**
     * Links the 'Classroom' button with the business logic
     */
    public void openClassroom(ActionEvent actionEvent)
    {
        //Switching to ClassroomView
        MainView.getInstance(MainViewModel.getInstance(model)).classroomView(actionEvent);
    }

    /**
     * Links the 'Topic' button with the business logic
     */
    public void openTopic(ActionEvent actionEvent)
    {
        //Switching to TopicView
        MainView.getInstance(MainViewModel.getInstance(model)).topicView(actionEvent);
    }

    /**
     * Sets the user name and account number of the currently logged in user
     * @param accountNumber
     * @param firstName
     */
    public void setCredentials(String accountNumber, String firstName)
    {
        this.accountNumber.setValue(accountNumber);
        this.userName.setValue(firstName);
    }

    /**
     * Sets up the scene for the 'Login' GUI
     * @param actionEvent
     */
    public void logOut(ActionEvent actionEvent)
    {
        //Switching to LoginView
        MainView.getInstance(MainViewModel.getInstance(model)).loginView(actionEvent);
    }

    /**
     * Links the 'User Settings' button with the business logic
     */
    public void settings()
    {
        //Switching to UserSettingsView
    }

    /**
     * Getter of the property responsible for the user name label
     * @return StringProperty
     */
    public StringProperty userNameProperty()
    {
        return userName;
    }

    /**
     * Getter of the property responsible for the account number label
     * @return StringProperty
     */
    public StringProperty accountNumberProperty()
    {
        return accountNumber;
    }
}
