package shared;

import shared.resources.Classroom;
import shared.resources.User;
import shared.resources.ServerInfo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Server side of the software
 * @author group6
 * @version 3.4.5
 */
public interface IServer extends Remote
{

    //===============================================================COMMON PURPOSE===============================================================//
    User checkCredentials(String accountNumber, String password) throws RemoteException;
    boolean registerUser(User user) throws RemoteException;

    //===============================================================SESSION SERVER===============================================================//
    ServerInfo generateCode(String teacher, String topic, ArrayList<String> words, String classroom) throws RemoteException;
    ServerInfo getSessionServer(String accessCode) throws RemoteException;

    //===============================================================TOPIC RELATED===============================================================//
    boolean createTopic(String topic) throws RemoteException;
    ArrayList<String> fetchTopics() throws  RemoteException;
    boolean editTopic(String newTopic, String oldTopic) throws RemoteException;
    void deleteTopic(String topic) throws RemoteException;

    //===============================================================WORD RELATED===============================================================//
    ArrayList<String> fetchWord(String topic) throws RemoteException;
    void addWord(String word, String topic) throws  RemoteException;
    void editWord(String newWord, String selectedWord) throws RemoteException;
    void deleteWord(String selectedWord) throws RemoteException;

    //===============================================================CLASSROOM RELATED===============================================================//
    void createClassroom(String classroomId, String classroomName) throws RemoteException;
    ArrayList<Classroom> fetchClassrooms() throws RemoteException;
    void deleteClassroom(String id) throws RemoteException;
}
