package client.view.teacherview.topicview;

import client.viewmodel.teacherviewmodel.topicviewmodel.TopicViewModel;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

/**
 * Controller for the TopicView.fxml
 */
public class TopicView
{
    @FXML
    private JFXComboBox<String> topicComboBox;

    @FXML
    private TextField newTopicField, newWordField;

    @FXML
    private GridPane gridPane;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label noTopicName, noTopicSelected, noWordName, noWordSelected;

    private ToggleGroup toggleGroup;
    private JFXRadioButton selectedRadioButton;
    private TopicViewModel topicViewModel;

    /**
     * Initiates the class
     * @param topicViewModel
     */
    public void initiate(TopicViewModel topicViewModel)
    {
        //Initiating the properties
        this.topicViewModel = topicViewModel;
        toggleGroup = new ToggleGroup();

        topicComboBox.getItems().setAll(FXCollections.observableList(topicViewModel.fetchTopics()));
        topicComboBox.valueProperty().bindBidirectional(topicViewModel.editTopicProperty());

        noTopicName.visibleProperty().bindBidirectional(topicViewModel.noTopicNameProperty());
        noTopicSelected.visibleProperty().bindBidirectional(topicViewModel.noTopicSelectedProperty());

        noWordName.visibleProperty().bindBidirectional(topicViewModel.noWordNameProperty());
        noWordSelected.visibleProperty().bindBidirectional(topicViewModel.noWordSelectedProperty());

        newTopicField.textProperty().bindBidirectional(topicViewModel.newTopicProperty());
        newWordField.textProperty().bindBidirectional(topicViewModel.newWordProperty());

        //Adding event for the combobox
        addComboBoxEvent();
    }

    /**
     * Controller for the 'CreateTopic' button
     * @param actionEvent
     */
    public void onCreateTopicButton(ActionEvent actionEvent)
    {
        topicViewModel.createTopic();
        topicComboBox.getItems().setAll(FXCollections.observableList(topicViewModel.fetchTopics()));
    }

    /**
     * Controller for the'EditTopic' button
     * @param actionEvent
     */
    public void onEditTopicButton(ActionEvent actionEvent)
    {
        topicViewModel.editTopic();
        topicComboBox.getItems().setAll(FXCollections.observableList(topicViewModel.fetchTopics()));
    }

    /**
     * Controller for the'DeleteTopic' button
     * @param actionEvent
     */
    public void onDeleteTopicButton(ActionEvent actionEvent)
    {
        topicViewModel.deleteTopic();
        topicComboBox.getItems().setAll(FXCollections.observableList(topicViewModel.fetchTopics()));
    }

    /**
     * Controller for the 'EditTopic' button
     * @param actionEvent
     */
    public void onMainMenuButton(ActionEvent actionEvent)
    {
        topicViewModel.mainMenu(actionEvent);
    }

    /**
     * Controller for the 'AddWord' button
     * @param actionEvent
     */
    public void onAddWordButton(ActionEvent actionEvent)
    {
        topicViewModel.addWord();
        importWords();
    }

    /**
     * Controller for the 'EditWord' button
     * @param actionEvent
     */
    public void onEditWordButton(ActionEvent actionEvent)
    {
        selectedRadioButton = (JFXRadioButton) toggleGroup.getSelectedToggle();

        if(selectedRadioButton == null || selectedRadioButton.getText() == "" || selectedRadioButton.getText() == " ")
        {
            topicViewModel.editWord(" ");
        }
        else
        {
            topicViewModel.editWord(selectedRadioButton.getText());
            importWords();
            toggleGroup.getSelectedToggle().setSelected(false);
        }


    }

    /**
     * Controller for the 'DeleteWord' button
     * @param actionEvent
     */
    public void onDeleteWordButton(ActionEvent actionEvent)
    {
        selectedRadioButton = (JFXRadioButton) toggleGroup.getSelectedToggle();

        if(selectedRadioButton == null)
        {
            topicViewModel.deleteWord(" ");
        }
        else
        {
            topicViewModel.deleteWord(selectedRadioButton.getText());
            importWords();
        }
    }

    /**
     * Takes care for importing the words of a specific topic
     */
    private void importWords()
    {
        gridPane.getChildren().clear();

        //Asks view model to fetch the wordlist
        ArrayList<String> words = topicViewModel.fetchWord();

        int col = 0;
        int row = 0;

        //Sets up the grid pane with radiobuttons
        for (int i = 0; i < words.size(); i++)
        {
            JFXRadioButton radioButton = new JFXRadioButton(words.get(i));

            radioButton.setToggleGroup(toggleGroup);

            gridPane.add(radioButton, col, row);

            if(col <= 2)
            {
                col++;
            }
            else
            {
                row++;
                anchorPane.setMinHeight(row * 30);
                gridPane.setMinHeight(row * 25);
                gridPane.setVgap(10);
                col = 0;
            }

        }
    }

    /**
     * Adding event listener for loading the words based on the combobox choice
     */
    private void addComboBoxEvent()
    {
        topicComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> importWords());
    }
}
