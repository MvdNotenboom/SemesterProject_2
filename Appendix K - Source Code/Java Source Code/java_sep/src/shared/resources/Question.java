package shared.resources;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class for holding information about a question
 * @author group6
 * @version 2.3.2
 */
public class Question implements Serializable
{
    private String question;
    private String word;
    private String type;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String correctAnswer;
    private String studentNumber;


    /**
     * Constructor for the class
     * @param word
     * @param type
     * @param question
     * @param answer1
     * @param answer2
     * @param answer3
     * @param answer4
     * @param correctAnswer
     * @param accountNumber
     */
    public Question(String word, String type, String question, String answer1, String answer2, String answer3, String answer4, String correctAnswer, String accountNumber)
    {
        this.word = word;
        this.type = type;
        this.question = question;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.answer3 = answer3;
        this.answer4 = answer4;
        this.correctAnswer = correctAnswer;
        this.studentNumber = accountNumber;
    }

    /**
     * Getter for the student number that submitted the question
     * @return String
     */
    public String getStudentNumber()
    {
        return studentNumber;
    }

    /**
     * Getter the first answer
     * @return String
     */
    public String getAnswer1()
    {
        return answer1;
    }

    /**
     * Getter for the second answer
     * @return String
     */
    public String getAnswer2()
    {
        return answer2;
    }

    /**
     * Getter for the third answer
     * @return String
     */
    public String getAnswer3()
    {
        return answer3;
    }

    /**
     * Getter for the fourth answer
     * @return String
     */
    public String getAnswer4()
    {
        return answer4;
    }

    /**
     * Setter for the question
     * @param question
     */
    public void setQuestion(String question)
    {
        this.question = question;
    }

    /**
     * Setter for the word
     * @param word
     */
    public void setWord(String word)
    {
        this.word = word;
    }

    /**
     * Setter for the type
     * @param type
     */
    public void setType(String type)
    {
        this.type = type;
    }

    /**
     * Setter for the correct answer
     * @param correctAnswer
     */
    public void setCorrectAnswer(String correctAnswer)
    {
        this.correctAnswer = correctAnswer;
    }

    /**
     * Getter for the question text
     * @return String
     */
    public String getQuestion()
    {
        return question;
    }

    /**
     * Getter for the word
     * @return String
     */
    public String getWord()
    {
        return word;
    }

    /**
     * Getter for the type
     * @return String
     */
    public String getType()
    {
        return type;
    }

    /**
     * Getter for the correct answer
     * @return String
     */
    public String getCorrectAnswer()
    {
        return correctAnswer;
    }

    /**
     * Prints the information about the question
     * @return String
     */
    @Override
    public String toString()
    {
        return question;
    }

}
