package client.viewmodel.registerviewmodel;

import client.model.IModel;
import client.view.MainView;
import client.viewmodel.MainViewModel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;


/**
 * Links the controller of the 'Register' GUI with the business logic
 * @author group6
 * @version 1.2.0
 */
public class RegisterViewModel
{
    private IModel model;
    private StringProperty firstName, lastName, email, accountNumber, password;
    private BooleanProperty userNotRegisteredMessage, isTeacher;
    private boolean registerState;

    /**
     * @param model
     */
    public RegisterViewModel(IModel model)
    {
        this.model = model;

        //Initiating the properties
        firstName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();
        email = new SimpleStringProperty();
        accountNumber = new SimpleStringProperty();
        password = new SimpleStringProperty();
        isTeacher = new SimpleBooleanProperty();

        userNotRegisteredMessage = new SimpleBooleanProperty(false);
    }

    /**
     * Links the 'Register' button with the business logic provides basic level of validation
     * @param event
     */
    public void registerUser(ActionEvent event)
    {
        if(firstName.getValue() == null || firstName.getValue().equals("") || firstName.getValue().equals(" ") || lastName.getValue() == null ||
        lastName.getValue().equals("") || lastName.getValue().equals(" ") || email.getValue() == null || email.getValue().equals("") ||
        email.getValue().equals(" ") || accountNumber.getValue() == null || accountNumber.getValue().equals("") || accountNumber.getValue().equals(" ") ||
        accountNumber.getValue().length() < 4 || password.getValue() == null || password.getValue().equals("") || password.getValue().equals(" ") ||
        password.getValue().length() < 6)
        {
            userNotRegisteredMessage.setValue(true);
        }
        else
        {
            registerState =  model.registerUser(firstName.getValue(), lastName.getValue(), email.getValue(), accountNumber.getValue(), password.getValue(), isTeacher.getValue());
        }

        if(registerState)
        {
            //In case of successful register process clears all the fields
            firstNameProperty().setValue("");
            lastNameProperty().setValue("");
            emailProperty().setValue("");
            accountNumberProperty().setValue("");
            passwordProperty().setValue("");
        }
        else
        {
            /*
            In case of a not successful register process clears only the account number and password field
            as those need validation
             */
            accountNumberProperty().setValue("");
            passwordProperty().setValue("");
        }

        //Fires up the not registered error message
        userNotRegisteredMessage.setValue(!registerState);

        //In case of a successful register process returns to the 'Login' GUI
        if(registerState)
        {
            exit(event);
        }
    }

    /**
     * Sets up the scene for the 'Login' GUI
     * @param actionEvent
     */
    public void exit(ActionEvent actionEvent)
    {
        //Clears all the fields
        firstNameProperty().setValue("");
        lastNameProperty().setValue("");
        emailProperty().setValue("");
        accountNumberProperty().setValue("");
        passwordProperty().setValue("");
        userNotRegisteredMessage.setValue(false);

        //Switching to Login View
        MainView.getInstance(MainViewModel.getInstance(model)).loginView(actionEvent);

    }

    /**
     * Getter of the property responsible for the Student/Teacher toggle
     * @return BooleanProperty
     */
    public BooleanProperty isTeacherProperty()
    {
        return isTeacher;
    }

    /**
     * Getter of the property responsible for the first name input field
     * @return StringProperty
     */
    public StringProperty firstNameProperty()
    {
        return firstName;
    }

    /**
     * Getter of the property responsible for the last name input field
     * @return StringProperty
     */
    public StringProperty lastNameProperty()
    {
        return lastName;
    }

    /**
     * Getter of the property responsible for the email input field
     * @return StringProperty
     */
    public StringProperty emailProperty()
    {
        return email;
    }

    /**
     * Getter of the property responsible for the account number input field
     * @return StringProperty
     */
    public StringProperty accountNumberProperty()
    {
        return accountNumber;
    }

    /**
     * Getter of the property responsible for the password input field
     * @return StringProperty
     */
    public StringProperty passwordProperty()
    {
        return password;
    }

    /**
     * Getter of the property responsible for the user not registered error message
     * @return BooleanProperty
     */
    public BooleanProperty userNotRegisteredMessageProperty()
    {
        return userNotRegisteredMessage;
    }
}
