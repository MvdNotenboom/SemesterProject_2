package client.view.registerview;

import client.viewmodel.registerviewmodel.RegisterViewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;


/**
 * Controller for the RegisterView.fxml
 * @author group6
 * @version 1.2.1
 */
public class RegisterView
{
    @FXML
    private TextField firstNameInput, lastNameInput, emailInput, accountNumberInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Toggle isTeacher;

    @FXML
    private Label userNotRegistered;

    private RegisterViewModel registerViewModel;

    /**
     * Initiator of the class
     * @param registerViewModel
     */
    public void initiate(RegisterViewModel registerViewModel)
    {
        this.registerViewModel = registerViewModel;

        //Binding different components from the fxml file with corresponding properties in the RegisterViewModel
        firstNameInput.textProperty().bindBidirectional(registerViewModel.firstNameProperty());
        lastNameInput.textProperty().bindBidirectional(registerViewModel.lastNameProperty());
        emailInput.textProperty().bindBidirectional(registerViewModel.emailProperty());
        accountNumberInput.textProperty().bindBidirectional(registerViewModel.accountNumberProperty());
        passwordInput.textProperty().bindBidirectional(registerViewModel.passwordProperty());
        isTeacher.selectedProperty().bindBidirectional(registerViewModel.isTeacherProperty());
        userNotRegistered.visibleProperty().bind(registerViewModel.userNotRegisteredMessageProperty());
    }

    /**
     * Controls the functionality of the 'Register' button
     * @param actionEvent
     */
    public void onRegisterButton(ActionEvent actionEvent)
    {
       registerViewModel.registerUser(actionEvent);
    }

    /**
     * Controls the functionality of the 'Cancel' button
     * @param actionEvent
     */
    public void onCancelButton(ActionEvent actionEvent)
    {
        registerViewModel.exit(actionEvent);
    }
}
