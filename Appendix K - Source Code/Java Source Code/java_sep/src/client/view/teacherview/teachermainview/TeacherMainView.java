package client.view.teacherview.teachermainview;

import client.viewmodel.teacherviewmodel.teachermainviewmodel.TeacherMainViewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


/**
 * Controller for TeacherMainView.fxml
 * @author group6
 * @version 1.0.0
 */
public class TeacherMainView
{
    @FXML
    Label userName, accountNumber;

    private TeacherMainViewModel teacherMainViewModel;

    /**
     * Initiator of the class
     * @param teacherMainViewModel
     */
    public void initiate(TeacherMainViewModel teacherMainViewModel)
    {
        this.teacherMainViewModel = teacherMainViewModel;

        this.userName.textProperty().bindBidirectional(teacherMainViewModel.userNameProperty());
        this.accountNumber.textProperty().bindBidirectional(teacherMainViewModel.accountNumberProperty());
    }

    /**
     * Controls the functionality of 'Session' button
     * @param actionEvent
     */
    public void onSessionButton(ActionEvent actionEvent)
    {
        teacherMainViewModel.openSession(actionEvent);
    }

    /**
     * Controls the functionality of 'History' button
     * @param actionEvent
     */
    public void onHistoryButton(ActionEvent actionEvent)
    {
        teacherMainViewModel.openHistory();
    }

    /**
     * Controls the functionality of 'Classroom' button
     * @param actionEvent
     */
    public void onClassroomButton(ActionEvent actionEvent)
    {
        teacherMainViewModel.openClassroom(actionEvent);
    }

    /**
     * Controls the functionality of 'Topic' button
     * @param actionEvent
     */
    public void onTopicButton(ActionEvent actionEvent)
    {
        teacherMainViewModel.openTopic(actionEvent);
    }

    /**
     * Controls the functionality of 'LogOut' button
     * @param actionEvent
     */
    public void onLogOutButton(ActionEvent actionEvent)
    {
        teacherMainViewModel.logOut(actionEvent);
    }

    /**
     * Controls the functionality of 'UserSettings' button
     * @param actionEvent
     */
    public void onUserSetttingsButton(ActionEvent actionEvent)
    {
        teacherMainViewModel.settings();
    }
}
