package client.view.studentview.studentsessionview;

import client.viewmodel.studentviewmodel.sessionviewmodel.StudentSessionViewModel;

import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.util.ArrayList;

/**
 * Controller for the StudentSessionView.fxml
 * @author group6
 * @version 1.0.0
 */
public class StudentSessionView
{
    @FXML
    private TextArea questionInput;

    @FXML
    private TextField answer1, answer2, answer3, answer4;

    @FXML
    private RadioButton radio1, radio2, radio3, radio4;

    @FXML
    private Label teacherLabel, topicLabel, classroomLabel, studentLabel, wordLabel, timeLabel,
            summaryWord, summaryQuestion, summaryType, summaryAnswers, summaryCorrect, errorLabel;

    @FXML
    GridPane gridPane;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private ComboBox<String> questionType;

    @FXML
    private ToggleGroup toggleGroupCorrect;

    private ToggleGroup toggleGroup;
    private StudentSessionViewModel studentSessionViewModel;
    private JFXRadioButton selectedWord;

    /**
     * Initiator of the class
     * @param studentSessionViewModel
     */
    public void initiate(StudentSessionViewModel studentSessionViewModel)
    {
        this.studentSessionViewModel = studentSessionViewModel;
        toggleGroup = new ToggleGroup();

        //===============================================================QUESTION RELATED PROPERTIES===============================================================//
        radio1.selectedProperty().bindBidirectional(studentSessionViewModel.radio1Property());
        radio2.selectedProperty().bindBidirectional(studentSessionViewModel.radio2Property());
        radio3.selectedProperty().bindBidirectional(studentSessionViewModel.radio3Property());
        radio4.selectedProperty().bindBidirectional(studentSessionViewModel.radio4Property());

        answer1.textProperty().bindBidirectional(studentSessionViewModel.answer1Property());
        answer2.textProperty().bindBidirectional(studentSessionViewModel.answer2Property());
        answer3.textProperty().bindBidirectional(studentSessionViewModel.answer3Property());
        answer4.textProperty().bindBidirectional(studentSessionViewModel.answer4Property());

        questionInput.textProperty().bindBidirectional(studentSessionViewModel.questionInputProperty());

        questionType.getItems().setAll(FXCollections.observableList(studentSessionViewModel.getQuestionType()));
        questionType.valueProperty().bindBidirectional(studentSessionViewModel.questionTypeProperty());

        summaryAnswers.textProperty().bindBidirectional(studentSessionViewModel.summaryAnswersProperty());
        summaryCorrect.textProperty().bindBidirectional(studentSessionViewModel.summaryCorrectProperty());
        summaryQuestion.textProperty().bindBidirectional(studentSessionViewModel.summaryQuestionProperty());
        summaryType.textProperty().bindBidirectional(studentSessionViewModel.summaryTypeProperty());
        summaryWord.textProperty().bindBidirectional(studentSessionViewModel.summaryWordProperty());

        //===============================================================SESSION RELATED PROPERTIES==============================================================//
        teacherLabel.textProperty().bindBidirectional(studentSessionViewModel.teacherProperty());
        topicLabel.textProperty().bindBidirectional(studentSessionViewModel.sessionProperty());
        classroomLabel.textProperty().bindBidirectional(studentSessionViewModel.classroomProperty());
        studentLabel.textProperty().bindBidirectional(studentSessionViewModel.studentProperty());
        timeLabel.textProperty().bindBidirectional(studentSessionViewModel.timeProperty());
        wordLabel.textProperty().bindBidirectional(studentSessionViewModel.selectedProperty());
        errorLabel.visibleProperty().bindBidirectional(studentSessionViewModel.errorLabelProperty());
        //Connecting to the session server
        studentSessionViewModel.whenConnected();

        //Fetching words from the database by the specified topic
        importWords();

        //Listening to the list of disabled words in the view model
        studentSessionViewModel.getDisabledWordsList().addListener((ListChangeListener<String>) change -> disableWords(change.getList()));

        //Initial check for the disabled words
        studentSessionViewModel.checkSelectedWords();

        // Event listeners for different GUI parts to provide "summary" functionality
        addComboBoxEvent();
        addTextAreaEvent();
        addAnswersEvent();
        addRadioEvent();
    }

    /**
     * Controller for the 'Submit' button
     * @param actionEvent
     */
    public void onSubmitButton(ActionEvent actionEvent)
    {
        if(toggleGroupCorrect.getSelectedToggle() == null)
        {
            errorLabel.setVisible(true);
        }
        else
        {
            studentSessionViewModel.submitQuestion();
            errorLabel.setVisible(false);
        }

    }

    /**
     * Controls for the 'SelectWord' button
     * @param actionEvent
     */
    public void onSelectWord(ActionEvent actionEvent)
    {
        selectedWord = (JFXRadioButton) toggleGroup.getSelectedToggle();
        if(toggleGroup.getSelectedToggle() == null)
        {

        }
        else
        {
            studentSessionViewModel.selectWord(selectedWord.getText());
        }
    }

    /**
     * Disables the selected words by other clients of the session
     * @param evt
     */
    public void disableWords(ObservableList<? extends String> evt)
    {
        //Retrieving the updated list of disabled words
        ArrayList<String> selectedWords = new ArrayList<>(evt);
        ArrayList<String> submittedWords = studentSessionViewModel.getSubmittedWords();

        //Setting up the toggle group with all radio buttons enabled
        for (int i = 0; i < toggleGroup.getToggles().size(); i++)
        {
            ((RadioButton) toggleGroup.getToggles().get(i)).setDisable(false);
        }

        //Disabling the radio buttons that are in the list of selected words based on the user input
        for (int i = 0; i < selectedWords.size(); i++)
        {
            for (int j = 0; j < toggleGroup.getToggles().size(); j++)
            {
                if(selectedWords.get(i).equals(((RadioButton) toggleGroup.getToggles().get(j)).getText()))
                {
                    ((RadioButton) toggleGroup.getToggles().get(j)).setDisable(true);
                    ((RadioButton) toggleGroup.getToggles().get(j)).setSelected(false);
                }
            }
        }

        for (int i = 0; i < submittedWords.size(); i++)
        {
            for (int j = 0; j < toggleGroup.getToggles().size(); j++)
            {
                if(submittedWords.get(i).equals(((RadioButton) toggleGroup.getToggles().get(j)).getText()))
                {
                    ((RadioButton) toggleGroup.getToggles().get(j)).setDisable(true);
                    ((RadioButton) toggleGroup.getToggles().get(j)).setSelected(false);
                }
            }
        }
    }

    /**
     * Asks the view model to import the session words
     */
    private void importWords()
    {
        //Clears the grid pane before importing
        gridPane.getChildren().clear();

        //Retrieves the array list from the view model
        ArrayList<String> words = studentSessionViewModel.getSessionWords();

        int col = 0;
        int row = 0;

        //Sets up the layout
        for (int i = 0; i < words.size(); i++)
        {
            JFXRadioButton radioButton = new JFXRadioButton(words.get(i));

            radioButton.setToggleGroup(toggleGroup);

            gridPane.add(radioButton, col, row);

            if(col < 2)
            {
                col++;
            }
            else
            {
                row++;
                anchorPane.setMinHeight(row * 30);
                gridPane.setMinHeight(row * 30);
                gridPane.setVgap(10);
                col = 0;
            }

        }
    }

    /**
     * Adds event listener to when a question type is selected from the combobox
     */
    private void addComboBoxEvent()
    {
        questionType.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) ->
                summaryType.setText(newValue));

    }

    /**
     * Adds event listener to when a question is added
     */
    private void addTextAreaEvent()
    {
        questionInput.textProperty().addListener((observableValue, s, t1) ->
                summaryQuestion.setText(t1));
    }

    /**
     * Adds event listener to when answers are inserted
     */
    private void addAnswersEvent()
    {
        //Array list of 4 answers
        ArrayList<String> answers = new ArrayList<>(4);
        answers.add(0, " ");
        answers.add(1, " ");
        answers.add(2, " ");
        answers.add(3, " ");

        //================================================Listeners to each text field==================================//
        answer1.textProperty().addListener((observableValue, s, t1) ->
        {
            answers.set(0, t1);

            appendSummaryAnswers(answers);
        });

        answer2.textProperty().addListener((observableValue, s, m1) ->
        {
            answers.set(1, m1);

            appendSummaryAnswers(answers);
        });

        answer3.textProperty().addListener((observableValue, s, n1) ->
        {
            answers.set(2, n1);

            appendSummaryAnswers(answers);
        });

        answer4.textProperty().addListener((observableValue, s, b1) ->
        {
            answers.set(3, b1);

            appendSummaryAnswers(answers);
        });
    }

    /**
     * Formats for proper displaying in the answer summary section
     * @param answers
     */
    private void appendSummaryAnswers(ArrayList<String> answers)
    {
        summaryAnswers.setText(" ");

        String str = " ";

        for(int i = 0; i < 4; i++)
        {
            str += answers.get(i) + ", ";

        }

        summaryAnswers.setText(str);
    }

    /**
     * Event listener for the selected radio buttons
     */
    private void addRadioEvent()
    {
        toggleGroupCorrect.selectedToggleProperty().addListener((observableValue, toggle, t1) ->
        {
            if(t1 == radio1)
            {
                summaryCorrect.setText(answer1.getText());
            }
            else if(t1 == radio2)
            {
                summaryCorrect.setText(answer2.getText());
            }
            else if(t1 == radio3)
            {
                summaryCorrect.setText(answer3.getText());
            }
            else if(t1 == radio4)
            {
                summaryCorrect.setText(answer4.getText());
            }
        });
    }
}
