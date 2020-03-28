package server.dataprovider;

import shared.resources.Classroom;
import shared.resources.Question;
import shared.resources.ServerInfo;
import shared.resources.User;

import java.util.ArrayList;

/**
 * Responsible for in and out data
 * @author group6
 * @version 1.0.0
 */
public interface IDataManager
{
    //===============================================================COMMON PURPOSE===============================================================//
    boolean registerUser(User user);
    User getCredentials(String accountNumber);

    //===============================================================SERVER SESSION RELATED===============================================================//
    boolean makeSessionServerRegistration(String accessCode, String ipAddress, String port, String teacher, String classroom);
    ServerInfo getSessionServer(String accessCode);
    void submitQuestion(Question question);
    ArrayList<Question> retrieveQuestion();

    //===============================================================TOPIC RELATED===============================================================//
    boolean createTopic(String topic);
    ArrayList<String> fetchTopics();
    boolean updateTopic(String newTopic, String oldTopic);
    void deleteTopic(String topic);

    //===============================================================WORD RELATED===============================================================//
    ArrayList<String> fetchWords(String topic);
    void addWord(String word, String topic);
    void editWord(String newWord, String selectedWord);
    void deleteWord(String selectedWord);

    //===============================================================CLASSROOM RELATED===============================================================//
    void createClassroom(String classroomId, String classroomName);
    ArrayList<Classroom> fetchClassrooms();
    void deleteClassroom(String id);
}
