package client.model;


import shared.resources.Classroom;
import shared.resources.Question;
import shared.resources.ServerInfo;
import shared.resources.User;

import java.util.ArrayList;

/**
 * Business logic of the software
 * @author group6
 * @version 3.4.3
 */
public interface IModel extends IPropertyChangeSubject
{
    //===============================================================COMMON PURPOSE===============================================================//
    boolean registerUser(String firstName, String lastName, String eMail, String accountNumber, String password, boolean isTeacher);
    boolean getCredentials(String accountNumber, String password);
    User getUser();
    void exit();

    //===============================================================SERVER SESSION RELATED===============================================================//
    String generateServer(String topic, ArrayList<String> selectedWords, String classroom);
    String connectToSession(String accessCode);
    ServerInfo getSessionServerInfo();
    void submitQuestion(Question question);
    void checkNewQuestion();
    ArrayList<String> getSessionWords();
    String getSessionTopic();
    void selectWord(String oldWord, String newWord);
    ArrayList<String> getSubmittedWords();
    void checkConnectedStudent();
    void notifyStudentOut();

    //===============================================================TOPIC RELATED===============================================================//
    boolean createTopic(String value);
    boolean editTopic(String newTopic, String oldTopic);
    void deleteTopic(String value);
    ArrayList<String> fetchTopics();

    //===============================================================WORD RELATED===============================================================//
    void addWord(String word, String topic);
    void editWord(String newWord, String selectedWord);
    void deleteWord(String selectedWord);
    void checkSelectedWords();
    ArrayList<String> fetchWord(String topic);

    //===============================================================WORD RELATED===============================================================//
    void createClassroom(String classroomId, String classroomName);
    ArrayList<Classroom> fetchClassrooms();
    void deleteClassroom(String id);
    String getClassroom();
}
