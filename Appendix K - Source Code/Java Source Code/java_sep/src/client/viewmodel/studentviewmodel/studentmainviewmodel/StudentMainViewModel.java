package client.viewmodel.studentviewmodel.studentmainviewmodel;

import client.model.IModel;
import client.view.MainView;
import client.viewmodel.MainViewModel;

import javafx.beans.property.*;
import javafx.event.ActionEvent;


/**
 * Links the controller of the 'StudentMainView' GUI with the business logic
 * @author group6
 * @version 1.0.0
 */
public class StudentMainViewModel
{
    private IModel model;
    private StringProperty userName, accountNumber;
    private BooleanProperty errorLabel;

    /**
     * @param model
     */
    public StudentMainViewModel(IModel model)
    {
        this.model = model;

        //Initiating the properties
        this.userName = new SimpleStringProperty();
        this.accountNumber = new SimpleStringProperty();
        this.errorLabel = new SimpleBooleanProperty(false);
    }

    /**
     * @return String
     */
    public StringProperty userNameProperty()
    {
        return userName;
    }

    /**
     * @return String
     */
    public StringProperty accountNumberProperty()
    {
        return accountNumber;
    }

    /**
     * Sets the account number and user name
     * @param accountNumber
     * @param firstName
     */
    public void setCredentials(String accountNumber, String firstName)
    {
        this.accountNumber.setValue(accountNumber);
        this.userName.setValue(firstName);
    }

    /**
     * Sets up the scene for the 'LoginView' GUI
     * @param actionEvent
     */
    public void logOut(ActionEvent actionEvent)
    {
        //Switching to LoginView
        MainView.getInstance(MainViewModel.getInstance(model)).loginView(actionEvent);
    }

    /**
     * Checks if the access code id valid and if so connects to the session
     * @param accessCode
     * @param actionEvent
     */
    public void onSessionJoinButton(String accessCode, ActionEvent actionEvent)
    {
        Boolean joinedSession = false;
        String connection = model.connectToSession(accessCode);

        if( connection == null || connection.equals(null) || connection.equals("0"))
        {
        }
        else
        {
            joinedSession = true;
        }


        if(joinedSession)
        {
            errorLabel.setValue(joinedSession);

            //Switching to StudentSessionView
            MainView.getInstance(MainViewModel.getInstance(model)).studentSessionView(actionEvent);
        }

        errorLabel.setValue(!joinedSession);
    }

    /**
     * @return BooleanProperty
     */
    public BooleanProperty errorLabelProperty()
    {
        return errorLabel;
    }
}
