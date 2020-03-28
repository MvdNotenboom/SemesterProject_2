package client.viewmodel.studentviewmodel.sessionviewmodel;

import client.model.IModel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import shared.resources.Question;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Links the controller of the 'StudentSessionView' GUI with the business logic
 * @author group6
 * @version 1.0.2
 */
public class StudentSessionViewModel
{
    private StringProperty questionInput, teacher, topic, student, classroom, time, oldWord,
            questionType, answer1, answer2, answer3, answer4, summaryWord, summaryType,
            summaryQuestion, summaryAnswers, summaryCorrect;

    private BooleanProperty radio1, radio2, radio3, radio4, errorMessage;

    private ObservableList<String> disabledWordsList;

    private IModel model;

    /**
     * @param model
     */
    public StudentSessionViewModel(IModel model)
    {
        this.model = model;
        String nowTime = Calendar.getInstance().get(Calendar.HOUR) + ":" + Calendar.getInstance().get(Calendar.MINUTE) + ":" + Calendar.getInstance().get(Calendar.SECOND);

        //===============================================================QUESTION RELATED PROPERTIES===============================================================//
        questionType = new SimpleStringProperty();
        answer1 = new SimpleStringProperty();
        answer2 = new SimpleStringProperty();
        answer3 = new SimpleStringProperty();
        answer4 = new SimpleStringProperty();
        radio1 = new SimpleBooleanProperty();
        radio2 = new SimpleBooleanProperty();
        radio3 = new SimpleBooleanProperty();
        radio4 = new SimpleBooleanProperty();
        questionInput = new SimpleStringProperty();

        //===============================================================SESSION RELATED PROPERTIES==============================================================//
        teacher = new SimpleStringProperty();
        topic = new SimpleStringProperty();
        student = new SimpleStringProperty();
        classroom = new SimpleStringProperty();
        oldWord = new SimpleStringProperty();
        time = new SimpleStringProperty(nowTime);

        errorMessage = new SimpleBooleanProperty(false);

        disabledWordsList = FXCollections.observableArrayList();

        summaryAnswers = new SimpleStringProperty();
        summaryCorrect = new SimpleStringProperty();
        summaryQuestion = new SimpleStringProperty();
        summaryType = new SimpleStringProperty();
        summaryWord = new SimpleStringProperty();

        //Listens for the events fired by the model when the list of disabled words is modified
        model.addPropertyChangeListener("SelectedWords", evt -> disabledWords(evt));
    }

    /**
     * Links the 'Submit' button with the business logic
     */
    public void submitQuestion()
    {
        if(oldWord.getValue() == null || oldWord.getValue().equals("") || questionType.getValue() == null || answer1.getValue() == null || answer2.getValue() == null ||
                answer3.getValue() == null || answer4.getValue() == null || questionInput.getValue() == null)
        {
            errorMessage.setValue(true);
        }
        else
        {
            errorMessage.setValue(false);
            if(radio1.getValue())
            {
                Question question = new Question(oldWord.getValue(), questionType.getValue(), questionInput.getValue(), answer1.getValue(), answer2.getValue(), answer3.getValue(), answer4.getValue(), answer1.getValue(), model.getUser().getAccountNumber());
                model.submitQuestion(question);
            }
            else if(radio2.getValue())
            {
                Question question = new Question(oldWord.getValue(), questionType.getValue(), questionInput.getValue(), answer1.getValue(), answer2.getValue(), answer3.getValue(), answer4.getValue(), answer2.getValue(), model.getUser().getAccountNumber());
                model.submitQuestion(question);
            }
            else if(radio3.getValue())
            {
                Question question = new Question(oldWord.getValue(), questionType.getValue(), questionInput.getValue(), answer1.getValue(), answer2.getValue(), answer3.getValue(), answer4.getValue(), answer3.getValue(), model.getUser().getAccountNumber());
                model.submitQuestion(question);
            }
            else if(radio4.getValue())
            {
                Question question = new Question(oldWord.getValue(), questionType.getValue(), questionInput.getValue(), answer1.getValue(), answer2.getValue(), answer3.getValue(), answer4.getValue(), answer4.getValue(), model.getUser().getAccountNumber());
                model.submitQuestion(question);
            }

            //Resetting all the fields
            oldWord.setValue("");

            summaryWord.setValue("");
            summaryCorrect.setValue("");

            answer4.setValue("");
            answer3.setValue("");
            answer2.setValue("");
            answer1.setValue("");

            questionInput.setValue("");
            questionType.setValue("");

            radio4.setValue(false);
            radio3.setValue(false);
            radio2.setValue(false);
            radio1.setValue(false);
        }
    }

    /**
     * Asks model to mark the provided word as selected
     * @param selectedWord
     */
    public void selectWord(String selectedWord)
    {
        if(selectedWord == null)
        {

        }
        else
        {
            model.selectWord(oldWord.getValue(), selectedWord);
            oldWord.setValue(selectedWord);
            summaryWord.setValue(selectedWord);
        }
    }

    /**
     * Sets up the properties for when a user is connected to the session server
     */
    public void whenConnected()
    {
        teacher.setValue(model.getSessionServerInfo().getOperator());
        student.setValue(model.getUser().getFirstName());
        topic.setValue(model.getSessionTopic());
        classroom.setValue(model.getClassroom());
    }

    /**
     * Asks model to check the current list of selected words
     */
    public void checkSelectedWords()
    {
        model.checkSelectedWords();
    }

    /**
     * Creates an observable list and updates it based on the input from the event listener
     * @param evt
     */
    private void disabledWords(PropertyChangeEvent evt)
    {
        //Clears the list before updating
        disabledWordsList.clear();

        //Updates the list from the change listener
        disabledWordsList.addAll((ArrayList<String>) evt.getNewValue());
    }

    /**
     * Getter for the list of disabled words
     * @return ObservableList<String>
     */
    public ObservableList<String> getDisabledWordsList()
    {
        return disabledWordsList;
    }

    /**
     * Getter for the question types
     * @return ArrayList<String>
     */
    public ArrayList<String> getQuestionType()
    {
        ArrayList<String> questionType = new ArrayList<String>();

        questionType.add("Odd-one-out");
        questionType.add("Antonym");
        questionType.add("Synonym");
        questionType.add("Missing Word");
        questionType.add("Word Meaning");

        return questionType;
    }

    /**
     * Returns the current list of session words
     * @return ArrayList<String>
     */
    public ArrayList<String> getSessionWords()
    {
        return model.getSessionWords();
    }


    /**
     * Returns the current list of submitted words
     * @return ArrayList<String>
     */
    public ArrayList<String> getSubmittedWords()
    {
        return model.getSubmittedWords();
    }

    /**
     * @return StringProperty
     */
    public StringProperty teacherProperty()
    {
        return teacher;
    }

    /*
     * @return StringProperty
     */
    public StringProperty sessionProperty()
    {
        return topic;
    }

    /**
     * @return StringProperty
     */
    public StringProperty studentProperty()
    {
        return student;
    }

    /**
     * @return StringProperty
     */
    public StringProperty classroomProperty()
    {
        return classroom;
    }

    /**
     * @return StringProperty
     */
    public StringProperty timeProperty()
    {
        return time;
    }

    /**
     * @return StringProperty
     */
    public StringProperty questionInputProperty()
    {
        return questionInput;
    }

    /**
     * @return StringProperty
     */
    public StringProperty selectedProperty()
    {
        return oldWord;
    }

    /**
     * @return StringProperty
     */
    public StringProperty questionTypeProperty()
    {
        return questionType;
    }

    /**
     * @return StringProperty
     */
    public StringProperty answer1Property()
    {
        return answer1;
    }

    /**
     * @return StringProperty
     */
    public StringProperty answer2Property()
    {
        return answer2;
    }

    /**
     * @return StringProperty
     */
    public StringProperty answer3Property()
    {
        return answer3;
    }

    /**
     * @return StringProperty
     */
    public StringProperty answer4Property()
    {
        return answer4;
    }

    /**
     * @return StringProperty
     */
    public StringProperty summaryWordProperty()
    {
        return summaryWord;
    }

    /**
     * @return StringProperty
     */
    public StringProperty summaryTypeProperty()
    {
        return summaryType;
    }

    /**
     * @return StringProperty
     */
    public StringProperty summaryQuestionProperty()
    {
        return summaryQuestion;
    }

    /**
     * @return StringProperty
     */
    public StringProperty summaryAnswersProperty()
    {
        return summaryAnswers;
    }

    /**
     * @return StringProperty
     */
    public StringProperty summaryCorrectProperty()
    {
        return summaryCorrect;
    }

    /**
     * @return BooleanProperty
     */
    public BooleanProperty radio1Property()
    {
        return radio1;
    }

    /**
     * @return BooleanProperty
     */
    public BooleanProperty radio2Property()
    {
        return radio2;
    }

    /**
     * @return BooleanProperty
     */
    public BooleanProperty radio3Property()
    {
        return radio3;
    }

    /**
     * @return BooleanProperty
     */
    public BooleanProperty radio4Property()
    {
        return radio4;
    }

    /**
     * @return BooleanProperty
     */
    public BooleanProperty errorLabelProperty()
    {
        return errorMessage;
    }

}
