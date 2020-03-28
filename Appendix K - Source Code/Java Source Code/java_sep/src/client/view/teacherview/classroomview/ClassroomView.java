package client.view.teacherview.classroomview;

import client.viewmodel.teacherviewmodel.classroomviewmodel.ClassroomViewModel;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import shared.resources.Classroom;

/**
 * Controller for ClassroomView.fxml
 * @author group6
 * @version 1.4.3.
 */
public class ClassroomView
{
    @FXML
    private TextField classroomId, classroomName;

    @FXML
    private ComboBox<Classroom> classroomComboBox;

    @FXML
    private Label selectedClassroom;

    private ClassroomViewModel classroomViewModel;

    /**
     * Initiator of the class
     * @param classroomViewModel
     */
    public void initiate(ClassroomViewModel classroomViewModel)
    {
        this.classroomViewModel = classroomViewModel;

        //Initiation of the components and binding
        classroomId.textProperty().bindBidirectional(classroomViewModel.classroomIdProperty());
        classroomName.textProperty().bindBidirectional(classroomViewModel.classroomNameProperty());
        classroomComboBox.getItems().setAll(FXCollections.observableList(classroomViewModel.getClassrooms()));

        //Update of the combo box
        classroomViewModel.getClassrooms().addListener((ListChangeListener<Classroom>)change -> classroomComboBox.getItems().setAll(FXCollections.observableList(change.getList())));

        //Update of the selected classroom
        addComboBoxEvent();
    }

    /**
     * Controls the functionality of the 'Create' button
     * @param actionEvent
     */
    public void onCreateButton(ActionEvent actionEvent)
    {
        classroomViewModel.createClassroom();
    }

    /**
     * Controls the functionality of the 'Delete' button
     * @param actionEvent
     */
    public void onDeleteButton(ActionEvent actionEvent)
    {
        if(classroomComboBox.getSelectionModel().getSelectedItem() == null)
        {
            //needs informing for the user about not selecting any topic
        }
        else
        {
            classroomViewModel.deleteClassroom(classroomComboBox.getSelectionModel().getSelectedItem().getId());
        }
    }

    /**
     * Controls the functionality of the 'Main Menu' button
     * @param actionEvent
     */
    public void onMainMenuButton(ActionEvent actionEvent)
    {
        classroomViewModel.mainMenu(actionEvent);
    }

    /**
     * Adds event on when the selection of the combobox is changed to update the selectedClassroom Label accordingly
     */
    private void addComboBoxEvent()
    {
            classroomComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) ->
            {
                if(newValue == null)
                {
                    selectedClassroom.setText("");
                }
                else
                {
                    selectedClassroom.setText(newValue.getName());
                }
            });
    }
}
