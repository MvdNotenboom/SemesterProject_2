package client.viewmodel.loginviewmodel;

import client.model.IModel;
import client.view.MainView;
import client.viewmodel.MainViewModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;


/**
 * Links the controller of the 'Login' GUI with the business logic
 *
 * @author group6
 * @version 1.5.3
 */
public class LoginViewModel
{
    private IModel model;
    private StringProperty login, password;
    private BooleanProperty wrongCredentialsState, isTeacher;
    private boolean logedIn = false;

    /**
     * @param model
     */
    public LoginViewModel(IModel model)
    {
        this.model = model;
        //Initiating the properties
        login = new SimpleStringProperty();
        password = new SimpleStringProperty();
        isTeacher = new SimpleBooleanProperty();

        wrongCredentialsState = new SimpleBooleanProperty(false);
    }

    /**
     * Links the 'Login' button with the business logic
     */
    public void getCredentials(ActionEvent actionEvent)
    {
        //Basic validation
        if (login.getValue() == null || login.getValue().equals("") || login.getValue().equals(" ") || login.getValue().length() < 4)
        {
            wrongCredentialsState.setValue(true);
        } else
        {
            logedIn = model.getCredentials(login.getValue(), password.getValue());

            //Clears the field after pressing the button
            loginProperty().setValue("");
            passwordProperty().setValue("");

            //Fires up the error message in case of wrong credentials
            wrongCredentialsState.setValue(!logedIn);

            //Loads up the next scene
            if (logedIn)
            {
                logIn(actionEvent);
            }
        }
    }

    /**
     * Sets the scene for the 'TeacherMainView' or 'StudentMainView' GUI depending on the user type
     *
     * @param actionEvent
     */
    private void logIn(ActionEvent actionEvent)
    {
        //Clears the field after pressing the button
        loginProperty().setValue("");
        passwordProperty().setValue("");
        wrongCredentialsState.setValue(false);

        if (model.getUser().isTeacher())
        {
            MainView.getInstance(MainViewModel.getInstance(model)).teacherMainView(actionEvent);

            //Sets the name and accountNumber
            MainViewModel.getInstance(model).getTeacherMainViewModel().setCredentials(model.getUser().getAccountNumber(), model.getUser().getFirstName());
        }
        else
        {
            //Switching to RegisterView
            MainView.getInstance(MainViewModel.getInstance(model)).studentMainView(actionEvent);

            //Sets the name and accountNumber
            MainViewModel.getInstance(model).getStudentMainViewModel().setCredentials(model.getUser().getAccountNumber(), model.getUser().getFirstName());
        }
    }

    /**
     * Sets up the scene for the 'Register' GUI
     *
     * @param actionEvent
     */
    public void changeToRegister(ActionEvent actionEvent)
    {
        //Clears the field after pressing the button
        loginProperty().setValue("");
        passwordProperty().setValue("");
        wrongCredentialsState.setValue(false);

        MainView.getInstance(MainViewModel.getInstance(model)).registerView(actionEvent);
    }

    /**
     * Links the 'Cancel' button with the business logic
     */
    public void exit()
    {
        model.exit();
    }

    /**
     * Getter of the property responsible for the Student/Teacher toggle
     *
     * @return BooleanProperty
     */
    public BooleanProperty isTeacherProperty()
    {
        return isTeacher;
    }

    /**
     * Getter of the property responsible for the login input field
     *
     * @return StringProperty
     */
    public StringProperty loginProperty()
    {
        return login;
    }

    /**
     * Getter of the property responsible for the error message of a wrong password/login
     *
     * @return BooleanProperty
     */
    public BooleanProperty wrongCredentialsStateProperty()
    {
        return wrongCredentialsState;
    }

    /**
     * Getter of the property responsible for the password input field
     *
     * @return StringProperty
     */
    public StringProperty passwordProperty()
    {
        return password;
    }
}


