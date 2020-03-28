package client.viewmodel.teacherviewmodel.topicviewmodel;

import client.model.IModel;
import client.view.MainView;
import client.viewmodel.MainViewModel;

import javafx.beans.property.*;
import javafx.event.ActionEvent;

import java.util.ArrayList;

/**
 * Links the controller of the 'Topic' GUI with the business logic
 * @author group6
 * @version 1.0.0
 */
public class TopicViewModel
{
    private IModel model;
    private StringProperty newTopic, newWord, editTopic;
    private BooleanProperty noTopicSelected, noTopicName, noWordSelected, noWordName;

    /**
     * Constructor
     * @param model
     */
    public TopicViewModel(IModel model)
    {
        this.model = model;

        //Initiation of the properties
        this.noWordSelected = new SimpleBooleanProperty(false);
        this.noWordName = new SimpleBooleanProperty(false);

        this.noTopicName = new SimpleBooleanProperty(false);
        this.noTopicSelected = new SimpleBooleanProperty(false);

        this.newTopic = new SimpleStringProperty();
        this.newWord = new SimpleStringProperty();
        this.editTopic = new SimpleStringProperty();
    }

    /**
     * Links the 'Create Topic' button with the business logic
     * @return ArrayList<String>
     */
    public ArrayList<String> createTopic()
    {
        boolean flag = model.createTopic(newTopic.getValue());
        noTopicName.setValue(!flag);
        noWordSelected.setValue(false);
        noTopicSelected.setValue(false);
        noWordName.setValue(false);
        newTopic.setValue("");

        return fetchTopics();
    }

    /**
     * Links the 'Edit Topic' button with the business logic
     * @return ArrayList<String>
     */
    public ArrayList<String> editTopic()
    {
        if(editTopic == null || editTopic.getValue() == null)
        {
            noTopicName.setValue(false);
            noWordSelected.setValue(false);
            noWordName.setValue(false);
            noTopicSelected.setValue(true);
        }
        else
        {
            boolean flag = model.editTopic(newTopic.getValue(), editTopic.getValue());
            noTopicSelected.setValue(false);
            noTopicName.setValue(!flag);

            newTopic.setValue("");
            editTopic.setValue(null);
        }

        return fetchTopics();
    }

    /**
     * Links the 'Delete Topic' button with the business logic
     * @return ArrayList<String>
     */
    public ArrayList<String> deleteTopic()
    {
        if(editTopic == null || editTopic.getValue() == null)
        {
            noTopicName.setValue(false);
            noWordSelected.setValue(false);
            noWordName.setValue(false);
            noTopicSelected.setValue(true);
        }
        else
        {
            model.deleteTopic(editTopic.getValue());

            noTopicSelected.setValue(false);
            editTopic.setValue(null);
        }

        return fetchTopics();
    }

    /**
     * Links the 'Add Word' button with the business logic
     */
    public void addWord()
    {
        if (newWord.getValue() == null || newWord.getValue().equals("") || newWord.getValue().equals(" ") ||
        editTopic.getValue() == null || editTopic.getValue().equals("") || editTopic.getValue().equals(" "))
        {
            noTopicName.setValue(false);
            noTopicSelected.setValue(false);
            noWordSelected.setValue(false);
            noWordName.setValue(true);
        }
        else
        {
            noTopicName.setValue(false);
            noTopicSelected.setValue(false);
            noWordSelected.setValue(false);
            noWordName.setValue(false);
            model.addWord(newWord.getValue(), editTopic.getValue());
            newWord.setValue("");
        }
    }

    /**
     * Links the 'Edit Word' button with the business logic
     * @param selectedWord
     */
    public void editWord(String selectedWord)
    {
        if(newWord.getValue() == null || newWord.getValue().equals("") || newWord.getValue().equals(" "))
        {
            noWordName.setValue(true);
            noTopicName.setValue(false);
            noTopicSelected.setValue(false);
            noWordSelected.setValue(false);
        }
        else if(selectedWord == null || selectedWord.equals("") || selectedWord.equals(" "))
        {
            noWordSelected.setValue(true);
            noWordName.setValue(false);
            noTopicName.setValue(false);
            noTopicSelected.setValue(false);
        }
        else
        {
            noTopicSelected.setValue(false);
            noTopicName.setValue(false);
            noWordName.setValue(false);
            noWordSelected.setValue(false);
            model.editWord(newWord.getValue(), selectedWord);
            newWord.setValue("");
        }
    }

    /**
     * Links the 'Delete Word' button with the business logic
     * @param selectedWord
     */
    public void deleteWord(String selectedWord)
    {
        if(selectedWord == null || selectedWord.equals("") || selectedWord.equals(" "))
        {
            noTopicSelected.setValue(false);
            noTopicName.setValue(false);
            noWordName.setValue(false);
            noWordSelected.setValue(true);
        }
        else
        {
            noTopicSelected.setValue(false);
            noTopicName.setValue(false);
            noWordName.setValue(false);
            noWordSelected.setValue(false);
            model.deleteWord(selectedWord);
        }

    }

    /**
     * Links the 'Main Menu' button with the business logic
     * @param actionEvent
     */
    public void mainMenu(ActionEvent actionEvent)
    {
        //Switching to TeacherMainView
        MainView.getInstance(MainViewModel.getInstance(model)).teacherMainView(actionEvent);
    }

    /**
     * Asks model to return the current list of words based on a topic
     * @return ArrayList<String>
     */
    public ArrayList<String> fetchWord()
    {
        return model.fetchWord(editTopic.getValue());
    }

    /**
     * Asks model to return the current list of topics
     * @return ArrayList<String>
     */
    public ArrayList<String> fetchTopics()
    {
        return model.fetchTopics();
    }

    /**
     * @return StringProperty
     */
    public StringProperty newTopicProperty()
    {
        return newTopic;
    }

    /**
     * @return StringProperty
     */
    public StringProperty newWordProperty()
    {
        return newWord;
    }

    /**
     * @return StringProperty
     */
    public StringProperty editTopicProperty()
    {
        return editTopic;
    }

    /**
     * @return BooleanProperty
     */
    public BooleanProperty noTopicSelectedProperty()
    {
        return noTopicSelected;
    }

    /**
     * @return BooleanProperty
     */
    public BooleanProperty noTopicNameProperty()
    {
        return noTopicName;
    }

    /**
     * @return BooleanProperty
     */
    public BooleanProperty noWordNameProperty()
    {
        return noWordName;
    }

    /**
     * @return BooleanProperty
     */
    public BooleanProperty noWordSelectedProperty()
    {
        return noWordSelected;
    }
}
