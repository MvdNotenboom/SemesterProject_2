package client.view.loginview;


import client.viewmodel.loginviewmodel.LoginViewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;



/**
 * Controller for LoginView.fxml
 * @author group6
 * @version 1.2.5
 */
public class LoginView
{
    @FXML
    private TextField loginInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private Label wrongPasswordMessage;

    private LoginViewModel loginViewModel;

    /**
     * Initiator of the class
     * @param loginViewModel
     */
    public void initiate(LoginViewModel loginViewModel)
    {
        this.loginViewModel = loginViewModel;

        //Binding different components from the fxml file with corresponding properties in the LoginViewModel
        wrongPasswordMessage.visibleProperty().bind(loginViewModel.wrongCredentialsStateProperty());
        loginInput.textProperty().bindBidirectional(loginViewModel.loginProperty());
        passwordInput.textProperty().bindBidirectional(loginViewModel.passwordProperty());
    }

    /**
     * Controls the functionality of the 'Login' button
     * @param actionEvent
     */
    public void onLoginButton(ActionEvent actionEvent)
    {
        loginViewModel.getCredentials(actionEvent);
    }

    /**
     * Controls the functionality of the 'Register' button
     * @param actionEvent
     */
    public void onRegisterButton(ActionEvent actionEvent)
    {
        loginViewModel.changeToRegister(actionEvent);
    }

    /**
     * Controls the functionality of the 'Cancel' button
     * @param actionEvent
     */
    public void onCancelButton(ActionEvent actionEvent)
    {
        loginViewModel.exit();
    }
}
