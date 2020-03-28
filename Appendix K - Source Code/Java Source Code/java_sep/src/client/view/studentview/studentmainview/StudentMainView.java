package client.view.studentview.studentmainview;

import client.viewmodel.studentviewmodel.studentmainviewmodel.StudentMainViewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller for StudentMainView.fxml
 * @author group6
 * @version 1.0.3
 */
public class StudentMainView
{
    @FXML
    private Label userName, accountNumber, errorLabel;

    @FXML
    private TextField sessionAccessCode;

    private StudentMainViewModel studentMainViewModel;

    /**
     * Initiator of the class
     * @param studentMainViewModel
     */
    public void initiate(StudentMainViewModel studentMainViewModel)
    {
        this.studentMainViewModel = studentMainViewModel;
        this.userName.textProperty().bindBidirectional(studentMainViewModel.userNameProperty());
        this.accountNumber.textProperty().bindBidirectional(studentMainViewModel.accountNumberProperty());
        this.errorLabel.visibleProperty().bindBidirectional(studentMainViewModel.errorLabelProperty());
    }

    /**
     * Controls the functionality of the 'Log Out' button
     * @param actionEvent
     */
    public void onLogOutButton(ActionEvent actionEvent)
    {
        studentMainViewModel.logOut(actionEvent);
    }

    /**
     * Controls the functionality of the 'History' button
     * @param actionEvent
     */
    public void onHistoryButton(ActionEvent actionEvent)
    {
    }

    /**
     * Controls the functionality of the 'Join Quiz' button
     * @param actionEvent
     */
    public void onJoinQuizzButton(ActionEvent actionEvent)
    {
    }

    /**
     * Controls the functionality of the 'Join Session button'
     * @param actionEvent
     */
    public void onJoinSessionButton(ActionEvent actionEvent)
    {
        studentMainViewModel.onSessionJoinButton(sessionAccessCode.textProperty().getValue(), actionEvent);
    }
}
