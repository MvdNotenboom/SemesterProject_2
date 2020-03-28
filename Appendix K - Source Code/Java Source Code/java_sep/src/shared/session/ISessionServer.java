package shared.session;

import shared.resources.Question;
import shared.callback.ICallBack;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Interface for a session server
 * @author group6
 * @version 1.0.0
 */
public interface ISessionServer extends Remote
{
    //===============================================================COMMON PURPOSE===============================================================//
    void registerForCallback(ICallBack callbackClientObject) throws RemoteException;
    void registerStudentForCallback(ICallBack callBackStudentObject) throws RemoteException;
    Vector connectedStudents() throws RemoteException;
    void removeFromStudentList(ICallBack callbackObj) throws RemoteException;

    //===============================================================TOPIC RELATED===============================================================//
    String getTopic() throws RemoteException;

    //===============================================================WORD RELATED===============================================================//
    ArrayList<String> getWords() throws RemoteException;
    void setSelectedWord(String oldWord, String selectedWord) throws RemoteException;
    ArrayList<String> getSubmittedWords() throws RemoteException;
    ArrayList<String> getSelectedWords() throws RemoteException;

    //===============================================================QUESTION RELATED===============================================================//
    void submitQuestion(Question question) throws RemoteException;
    ArrayList<Question> retrieveQuestion() throws RemoteException;

    //===============================================================CLASSROOM RELATED===============================================================//
    String getClassroom() throws RemoteException;
}
