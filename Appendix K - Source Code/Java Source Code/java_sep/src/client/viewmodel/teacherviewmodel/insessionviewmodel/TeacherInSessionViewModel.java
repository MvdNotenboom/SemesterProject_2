package client.viewmodel.teacherviewmodel.insessionviewmodel;

import client.model.IModel;
import client.view.MainView;
import client.viewmodel.MainViewModel;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import shared.resources.Question;
import shared.resources.User;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;


/**
 * Links the controller of the 'TeacherInSessionView' GUI with the business logic
 */
public class TeacherInSessionViewModel
{
    private IModel model;

    private StringProperty studentLabel, accessCodeLabel;
    private BooleanProperty questionAccept;
    private ObservableList<Question> submittedQuestion;
    private ObservableList<User> connectedStudent;

    /**
     * @param model
     */
    public TeacherInSessionViewModel(IModel model)
    {
        this.model = model;

        //Initiate the properties
        studentLabel = new SimpleStringProperty();
        accessCodeLabel = new SimpleStringProperty();
        questionAccept = new SimpleBooleanProperty(true);
        submittedQuestion = FXCollections.observableArrayList();
        connectedStudent = FXCollections.observableArrayList();

        //Listens for the events fired by the model when the list of submitted questions is updated
        model.addPropertyChangeListener("NewQuestion", evt -> newQuestion(evt));

        //Listens for the session access code
        model.addPropertyChangeListener("AccessCode", evt -> setAccessCode(evt));

        //Listens for the connected students list
        model.addPropertyChangeListener("ConnectedStudent", evt -> connectedStudent(evt));
    }

    /**
     * Sets the value of the access code
     * @param evt
     */
    private void setAccessCode(PropertyChangeEvent evt)
    {
        accessCodeLabel.setValue((String) evt.getNewValue());
    }


    /**
     * Is called when an event 'ConnectedStudent' is fired, retrieves the list of connected students
     * and adds it to an observable list
     * @param evt
     */
    private void connectedStudent(PropertyChangeEvent evt)
    {
        Platform.runLater(() ->
        {
            connectedStudent.clear();

            connectedStudent.addAll((ArrayList<User>) evt.getNewValue());
        });
    }

    /**
     * Getter for the list of connected students
     * @return ObservableList<User>
     */
    public ObservableList<User> getConnectedStudent()
    {
        return connectedStudent;
    }

    /**
     * Getter for the list of submitted questions
     * @return ObservableList<String>
     */
    public ObservableList<Question> getSubmittedQuestion()
    {
        return submittedQuestion;
    }

    /**
     * Is called when an event with the 'NewQuestion' name is fired, retrieves the
     * questions list and assigns it to the responsible properties
     * @param evt
     */
    private void newQuestion(PropertyChangeEvent evt)
    {
        Platform.runLater(() ->
        {
            //Clears the list before updating
            submittedQuestion.clear();

            //Updates the list from the change listener
            submittedQuestion.addAll((ArrayList<Question>) evt.getNewValue());
        });
    }

    /**
     * Getter for the studentLabelProeprty
     * @return StringProperty
     */
    public StringProperty studentLabelProperty()
    {
        return studentLabel;
    }

    /**
     * Getter for the accessCodeLabelProperty
     * @return StringProperty
     */
    public StringProperty accessCodeLabelProperty()
    {
        return accessCodeLabel;
    }

    /**
     * Getter for the questionAcceptProperty
     * @return BooleanProperty
     */
    public BooleanProperty questionAcceptProperty()
    {
        return questionAccept;
    }

    /**
     * Sets up the scene of the main menu
     * @param actionEvent
     */
    public void mainMenu(ActionEvent actionEvent)
    {
        //Swithcing to TeacherMainView
        MainView.getInstance(MainViewModel.getInstance(model)).teacherMainView(actionEvent);
    }


}
