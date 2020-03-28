package client.view.teacherview.teachersessionview;

import client.viewmodel.teacherviewmodel.sessionviewmodel.TeacherSessionViewModel;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import shared.resources.Classroom;

import java.util.ArrayList;

/**
 * Controller for StudentSessionView.fxml
 * @author group6
 * @version 1.0.0
 */
public class TeacherSessionView
{
    @FXML
    private Label sessionNotCreated, selectedTopicLabel, classroomLabel;

    @FXML
    private JFXComboBox<String> topicComboBox;

    @FXML
    private JFXComboBox<Classroom> classroomComboBox;

    @FXML
    private GridPane gridPane, selectedGridPane;

    @FXML
    private AnchorPane anchorPane, selectedAnchorPane;

    private ArrayList<String> selectedWords;
    private TeacherSessionViewModel teacherSessionViewModel;

    /**
     * Initiator for the class
     * @param teacherSessionViewModel
     */
    public void initiate(TeacherSessionViewModel teacherSessionViewModel)
    {
        this.teacherSessionViewModel = teacherSessionViewModel;

        //Initiation of the components and binding
        topicComboBox.getItems().setAll(FXCollections.observableList(teacherSessionViewModel.fetchTopics()));
        topicComboBox.valueProperty().bindBidirectional(teacherSessionViewModel.editTopicProperty());

        classroomComboBox.getItems().setAll(FXCollections.observableList(teacherSessionViewModel.getClassrooms()));

        selectedTopicLabel.textProperty().bindBidirectional(teacherSessionViewModel.editTopicProperty());
        sessionNotCreated.visibleProperty().bind(teacherSessionViewModel.sessionNotCreatedProperty());

        addComboBoxEvent();
        addClassroomComboBoxEvent();
    }

    /**
     * Controls the functionality of 'Create Session' button
     * @param actionEvent
     */
    public void onCreateSessionButton(ActionEvent actionEvent)
    {
        teacherSessionViewModel.createSession(actionEvent, selectedWords, classroomLabel.getText());
    }

    /**
     * Controls the functionality of 'Main Menu' button
     * @param actionEvent
     */
    public void onMainMenuButton(ActionEvent actionEvent)
    {
        teacherSessionViewModel.mainMenu(actionEvent);
    }

    /**
     * Imports the current list of words based on the selected topic
     */
    private void importWords()
    {
        //Clears the grid pane before importing
        gridPane.getChildren().clear();

        //Retrieves the list from the View Model
        ArrayList<String> words = teacherSessionViewModel.fetchWord();
        selectedWords = new ArrayList<String>();

        int col = 0;
        int row = 0;

        //Sets up the checkboxes and layout
        for (int i = 0; i < words.size(); i++)
        {
            JFXCheckBox checkBox = new JFXCheckBox(words.get(i));

            checkBox.selectedProperty().addListener((observableValue, aBoolean, t1) ->
            {
                if(observableValue.getValue() == true)
                {
                    selectedWords.add(checkBox.getText());
                }
                else if(observableValue.getValue() == false)
                {
                    selectedWords.remove(checkBox.getText());
                }

                //Updates the list of words displayed in the summary
                updateSelectedWords();
            });

            gridPane.add(checkBox, col, row);
            col++;

            if(col <= 2)
            {
                col++;
            }
            else
            {
                row++;
                anchorPane.setMinHeight(row * 35);
                gridPane.setVgap(10);
                col = 0;
            }

        }
    }

    /**
     * Sets up the list of words for the summary
     */
    private void updateSelectedWords()
    {
        selectedGridPane.getChildren().clear();

        int col = 0;
        int row = 0;

        for (int i = 0; i < selectedWords.size(); i++)
        {
            Label label = new Label(selectedWords.get(i));
            label.setMinHeight(20);

            selectedGridPane.add(label, col, row);

            if(col < 1)
            {
                col++;
            }
            else
            {
                row++;
                selectedAnchorPane.setMinHeight(row * 35);
                gridPane.setVgap(10);
                col = 0;
            }
        }
    }

    /**
     * Adds an event for when a topic is changed in the combobox for the words to be updated accordingly
     */
    private void addComboBoxEvent()
    {
        topicComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) ->
                importWords());
    }

    /**
     * Adds and event for when a classroom is changed in the combobox for the classroom id in the summary to be updated accordingly
     */
    private void addClassroomComboBoxEvent()
    {
        classroomComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) ->
        {
            if(newValue == null)
            {
                classroomLabel.setText("");
            }
            else
            {
                classroomLabel.setText(newValue.getId());
            }
        });
    }
}
