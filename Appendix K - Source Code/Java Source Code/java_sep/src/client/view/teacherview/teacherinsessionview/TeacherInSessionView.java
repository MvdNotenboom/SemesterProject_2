package client.view.teacherview.teacherinsessionview;

import client.viewmodel.teacherviewmodel.insessionviewmodel.TeacherInSessionViewModel;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import shared.resources.Question;
import shared.resources.User;

import java.util.ArrayList;

/**
 * Controller for the TeacherInSessionView.fxml
 */
public class TeacherInSessionView
{
    @FXML
    private GridPane questionPreviewGridPane, questionReviewGridPane, studentGridPane;

    @FXML
    private AnchorPane studentAnchorPane, questionPreviewAnchorPane;

    @FXML
    private ToggleButton questionAccept;

    @FXML
    private Label studentLabel, accessCodeLabel;

    private ToggleGroup toggleGroup;
    private ArrayList<Question> submittedQuestion;
    private ArrayList<User>  connectedStudent;
    private TeacherInSessionViewModel teacherInSessionViewModel;

    /**
     * Initiates the class
     * @param teacherInSessionViewModel
     */
    public void initiate(TeacherInSessionViewModel teacherInSessionViewModel)
    {
        this.teacherInSessionViewModel = teacherInSessionViewModel;
        studentLabel.textProperty().bindBidirectional(teacherInSessionViewModel.studentLabelProperty());
        accessCodeLabel.textProperty().bindBidirectional(teacherInSessionViewModel.accessCodeLabelProperty());
        questionAccept.selectedProperty().bindBidirectional(teacherInSessionViewModel.questionAcceptProperty());
        toggleGroup = new ToggleGroup();

        teacherInSessionViewModel.getSubmittedQuestion().addListener((ListChangeListener<Question>) change -> setQuestionPreview((change.getList())));
        teacherInSessionViewModel.getConnectedStudent().addListener((ListChangeListener<User>) change -> setConnectedStudentList(change.getList()));

        addRadioEvent();
    }

    /**
     * Sets the current questions in the preview mode
     * @param evt
     */
    public void setQuestionPreview(ObservableList<? extends Question> evt)
    {
        //Retrieving the updated list of questions
        submittedQuestion = new ArrayList<>(evt);

        int row = 1; 
        
        //Sets up the layout
        for (int i = 0; i < submittedQuestion.size(); i++)
        {
            Label accountNumber, type, word;
            RadioButton radioButton;

            accountNumber = new Label(submittedQuestion.get(i).getStudentNumber());
            type = new Label(submittedQuestion.get(i).getType());
            word = new Label(submittedQuestion.get(i).getWord());
            radioButton = new RadioButton();

            questionPreviewGridPane.add(accountNumber, 0, row);
            questionPreviewGridPane.add(type, 1, row);
            questionPreviewGridPane.add(word, 2, row);
            questionPreviewGridPane.add(radioButton, 3, row);

            radioButton.setToggleGroup(toggleGroup);

            questionPreviewAnchorPane.setMinHeight(row * 30);;
            questionPreviewGridPane.setMinHeight(row * 30);
            questionPreviewGridPane.getRowConstraints().add(new RowConstraints(25));

            questionPreviewGridPane.setHalignment(accountNumber, HPos.CENTER);
            questionPreviewGridPane.setHalignment(type, HPos.CENTER);
            questionPreviewGridPane.setHalignment(word, HPos.CENTER);
            questionPreviewGridPane.setHalignment(radioButton, HPos.CENTER);

            row++;
        }
    }

    public void setConnectedStudentList(ObservableList<? extends User> evt)
    {
        connectedStudent = new ArrayList<>(evt);

        studentGridPane.getChildren().clear();

        Label accountNumber, name;
        int row = 0;

        for (int i = 0; i < connectedStudent.size(); i++)
        {
            accountNumber = new Label(connectedStudent.get(i).getAccountNumber());
            name = new Label(connectedStudent.get(i).getFirstName() + " " + connectedStudent.get(i).getLastName());

            int col = 0;
            studentGridPane.add(accountNumber, col, row);
            col++;
            studentGridPane.add(name, col, row);

            studentAnchorPane.setMinHeight(row * 30);;
            studentGridPane.setMinHeight(row * 30);
            studentGridPane.getRowConstraints().add(new RowConstraints(25));

            studentGridPane.setHalignment(accountNumber, HPos.CENTER);
            studentGridPane.setHalignment(name, HPos.CENTER);

            row++;
        }
    }

    /**
     * Controls the functionality of the 'Submit Review' button
     * @param actionEvent
     */
    public void onSubmitReviewButton(ActionEvent actionEvent)
    {
    }

    /**
     * Controls the functionality of the 'Main Menu' button
     * @param actionEvent
     */
    public void onMainMenuButton(ActionEvent actionEvent)
    {
        teacherInSessionViewModel.mainMenu(actionEvent);
    }

    public void addRadioEvent()
    {
        toggleGroup.selectedToggleProperty().addListener((observableValue, toggle, t1) ->
        {
            setQuestionReview(submittedQuestion.get((questionPreviewGridPane.getRowIndex((Node) t1) - 1)));
        });
    }

    public void setQuestionReview(Question question)
    {
        studentLabel.setText(question.getStudentNumber());

        questionReviewGridPane.getChildren().retainAll(questionReviewGridPane.getChildren().get(0), questionReviewGridPane.getChildren().get(1),
                questionReviewGridPane.getChildren().get(2), questionReviewGridPane.getChildren().get(3), questionReviewGridPane.getChildren().get(4));

        Label word, type, question_, answers, correctAnswer;

        word = new Label(question.getWord());
        type = new Label(question.getType());
        answers = new Label(question.getAnswer1() + ", " +
                question.getAnswer2() + ", " + question.getAnswer3() + ", " +
                question.getAnswer4());
        question_ = new Label(question.getQuestion());
        correctAnswer = new Label(question.getCorrectAnswer());

        question_.setWrapText(true);
        question_.setMinHeight(50);

        questionReviewGridPane.add(word, 1, 0);
        questionReviewGridPane.add(type, 1, 1);
        questionReviewGridPane.add(question_, 1, 2);
        questionReviewGridPane.add(answers, 1, 3);
        questionReviewGridPane.add(correctAnswer, 1, 4);

        questionReviewGridPane.setHgap(15);
    }
}
