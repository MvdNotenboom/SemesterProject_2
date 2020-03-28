package client.viewmodel.teacherviewmodel.sessionviewmodel;

import client.model.IModel;
import client.view.MainView;
import client.viewmodel.MainViewModel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import shared.resources.Classroom;

import java.util.ArrayList;

/**
 * Links the controller of the 'Session' GUI with the business logic
 * @author group6
 * @version 1.0.0
 */
public class TeacherSessionViewModel
{

    private IModel model;
    private StringProperty selectedTopic;
    private BooleanProperty sessionNotCreatedMessage;
    private ObservableList<Classroom> classrooms;

    /**
     * Constructor of the class
     * @param model
     */
    public TeacherSessionViewModel(IModel model)
    {
        this.model = model;

        //Sets up the properties
        sessionNotCreatedMessage = new SimpleBooleanProperty(false);
        selectedTopic = new SimpleStringProperty();

        classrooms = FXCollections.observableArrayList();
    }

    /**
     * Links the 'Create Session' button with the business logic
     */
    public void createSession(ActionEvent actionEvent, ArrayList<String> selectedWords, String classroom)
    {
        String accessCode = "Server Not Created";

        if(selectedTopic.getValue() == null || classroom == null || selectedWords.isEmpty())
        {
            sessionNotCreatedMessage.setValue(true);
        }
        else
        {
            accessCode = model.generateServer(selectedTopic.getValue(), selectedWords, classroom);

            //Checks if server is not created
            if(accessCode.equals("Server Not Created"))
            {
                sessionNotCreatedMessage.setValue(true);
            }
            else
            {
                MainView.getInstance(MainViewModel.getInstance(model)).inSessionView(actionEvent);
            }
        }
    }

    /**
     * Links the 'Main Menu' button with the business logic
     * Sets the scene for the 'TeacherMainView' GUI
     * @param actionEvent
     */
    public void mainMenu(ActionEvent actionEvent)
    {
        //Switching to TeacherMainView
        MainView.getInstance(MainViewModel.getInstance(model)).teacherMainView(actionEvent);
    }

    /**
     * Updates the current list of words
     * @return ArrayList<String>
     */
    public ArrayList<String> fetchWord()
    {
        return model.fetchWord(selectedTopic.getValue());
    }

    /**
     * Updates the current list of topics
     * @return ArrayList<String>
     */
    public ArrayList<String> fetchTopics()
    {
        return model.fetchTopics();
    }

    /**
     * Getter for the property responsible for display error message when the server was not created
     * @return BooleanProperty
     */
    public BooleanProperty sessionNotCreatedProperty()
    {
        return sessionNotCreatedMessage;
    }

    /**
     * Getter for the editTopicProperty
     * @return StringProperty
     */
    public StringProperty editTopicProperty()
    {
        return selectedTopic;
    }

    /**
     * Returns the current list of classroom, updating it before returning
     * @return ObservableList<Classroom>
     */
    public ObservableList<Classroom> getClassrooms()
    {
        fetchClassrooms();
        return classrooms;
    }

    /**
     * Asks the model the update the current list of classrooms
     */
    private void fetchClassrooms()
    {
        classrooms.clear();

        classrooms.addAll(model.fetchClassrooms());
    }
}
