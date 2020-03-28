package client.viewmodel.teacherviewmodel.classroomviewmodel;

import client.model.IModel;
import client.view.MainView;
import client.viewmodel.MainViewModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import shared.resources.Classroom;


/**
 * Links the controller of the 'Classroom' GUI with the business logic
 * @author group6
 * @version 1.2.5.
 */
public class ClassroomViewModel
{
    private IModel model;
    private StringProperty classroomId, classroomName;
    private ObservableList<Classroom> classrooms;


    /**
     * Constructor of the class
     * @param model
     */
    public ClassroomViewModel(IModel model)
    {
        this.model = model;

        //Sets up the properties
        classroomId = new SimpleStringProperty();
        classroomName = new SimpleStringProperty();

        classrooms = FXCollections.observableArrayList();
    }

    /**
     * Links the 'Create' button witht he business logic, including some validation
     */
    public void createClassroom()
    {
        if(classroomName == null || classroomName.getValue() == null || classroomName.getValue().equals("")  ||
                classroomName.getValue().equals(" ") || classroomId == null || classroomId.getValue() == null ||
        classroomId.getValue().equals("") || classroomId.getValue().equals(" ") || classroomId.getValue().length() < 5)
        {
            //needs informing user about wrong inputs
        }
        else
        {
            model.createClassroom(classroomId.getValue(), classroomName.getValue());

            //resets the values after submitting
            classroomName.setValue("");
            classroomId.setValue("");
        }

        //updates the current list of classrooms
        fetchClassrooms();
    }

    /**
     * Asks the model to update the current list of classrooms
     */
    private void fetchClassrooms()
    {
        //clears before adding
        classrooms.clear();

        classrooms.addAll(model.fetchClassrooms());
    }

    /**
     * Links the 'Delete' button with the business logic
     * @param id
     */
    public void deleteClassroom(String id)
    {
        model.deleteClassroom(id);

        fetchClassrooms();
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
     * Getter for the classroomId property
     * @return StringProperty
     */
    public StringProperty classroomIdProperty()
    {
        return classroomId;
    }

    /**
     * Getter for the classroom name property
     * @return StringProperty
     */
    public StringProperty classroomNameProperty()
    {
        return classroomName;
    }

    /**
     * Returns the current list of classrooms, updating it before returning
     * @return ObservableList<Classroom>
     */
    public ObservableList<Classroom> getClassrooms()
    {
        fetchClassrooms();
        return classrooms;
    }
}
