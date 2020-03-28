package server.session;

import shared.resources.Question;
import shared.callback.ICallBack;
import server.dataprovider.DatabaseConnection;
import server.dataprovider.IDataManager;
import shared.session.ISessionServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Implementation for a session server
 * @author group6
 * @version 2.8.3
 */
public class SessionServer implements ISessionServer
{
    private IDataManager databaseConnection;
    private String topic, classroom;
    private ArrayList<String> words, selectedWords, submittedWords;
    private Vector teacherList, studentList;

    //===============================================================COMMON PURPOSE===============================================================//

    /**
     * @throws RemoteException
     */
    public SessionServer(String topic, ArrayList<String> words, String classroom) throws RemoteException
    {
        UnicastRemoteObject.exportObject(this, 0);
        databaseConnection = DatabaseConnection.getInstance();

        selectedWords = new ArrayList<String>();
        submittedWords = new ArrayList<String>();
        teacherList = new Vector();
        studentList = new Vector();
        this.topic = topic;
        this.words = words;
        this.classroom = classroom;
    }

    /**
     * Returns a list of the currently connected students
     * @return Vector
     */
    @Override
    public Vector connectedStudents()
    {
        return studentList;
    }

    /**
     * Removes a student from the list of currently connected
     * @param callbackObj
     * @throws RemoteException
     */
    @Override
    public void removeFromStudentList(ICallBack callbackObj) throws RemoteException
    {
        studentList.remove(callbackObj);

        doCallbacksNewStudent();
    }

    /**
     * Registers a teacher that will need to get callbacks
     * @param callbackClientObject
     * @throws RemoteException
     */
    @Override
    public synchronized void registerForCallback(ICallBack callbackClientObject) throws RemoteException
    {
        if(!(teacherList.contains(callbackClientObject))) {
            teacherList.addElement(callbackClientObject);
            System.out.println("Registered new teacher");
        }
    }

    /**
     * Registers a student that will need to get callbacks
     * @param callBackStudentObject
     * @throws RemoteException
     */
    @Override
    public void registerStudentForCallback(ICallBack callBackStudentObject) throws RemoteException
    {
        if(!(studentList.contains(callBackStudentObject))) {
            studentList.addElement(callBackStudentObject);
            System.out.println("Registered new student");
        }

        doCallbacksNewStudent();
    }

    /**
     * Notifies registered users that a new question was submited
     * @throws java.rmi.RemoteException
     */
    private synchronized void doCallbacksNewQuestion( ) throws RemoteException{
        // make callback to each registered client
        System.out.println(
                "**************************************\n"
                        + "Callbacks new question initiated ---");
        for (int i = 0; i < teacherList.size(); i++){
            System.out.println("doing "+ i +"-th callback\n");
            // convert the vector object to a callback object
            ICallBack nextClient =
                    (ICallBack) teacherList.elementAt(i);
            // invoke the callback method
            nextClient.newQuestion();
        }// end for
        System.out.println("********************************\n" +
                "Server completed callbacks ---");
    }

    /**
     * Does callbacks to the registered students
     * @throws java.rmi.RemoteException
     */
    private synchronized void doCallbacksStudent( ) throws RemoteException{
        // make callback to each registered client
        System.out.println(
                "**************************************\n"
                        + "Callbacks new selected word initiated ---");
        for (int i = 0; i < studentList.size(); i++){
            System.out.println("doing "+ i +"-th callback\n");
            // convert the vector object to a callback object
            ICallBack nextClient =
                    (ICallBack) studentList.elementAt(i);
            // invoke the callback method
            nextClient.selectedWord();
        }// end for
        System.out.println("********************************\n" +
                "Server completed callbacks ---");
    }

    /**
     * Does Callbacks to the teachers about a new student connected to the session
     * @throws RemoteException
     */
    private synchronized void doCallbacksNewStudent( ) throws RemoteException{
        // make callback to each registered client
        System.out.println(
                "**************************************\n"
                        + "Callbacks new student initiated ---");
        for (int i = 0; i < teacherList.size(); i++){
            System.out.println("doing "+ i +"-th callback\n");
            // convert the vector object to a callback object
            ICallBack nextClient =
                    (ICallBack) teacherList.elementAt(i);
            // invoke the callback method
            nextClient.newStudent();
        }// end for
        System.out.println("********************************\n" +
                "Server completed callbacks ---");
    }


    //===============================================================TOPIC RELATED===============================================================//

    /**
     * Returns the topic of the session
     * @return String
     */
    @Override
    public String getTopic()
    {
        return topic;
    }

    //===============================================================WORD RELATED===============================================================//

    /**
     * Returns the word list of the session
     * @return ArrayList<String>
     */
    @Override
    public ArrayList<String> getWords()
    {
        return words;
    }

    /**
     * Sets a list of selected words
     * @param oldWord
     * @param selectedWord
     * @throws RemoteException
     */
    @Override
    public void setSelectedWord(String oldWord, String selectedWord) throws RemoteException
    {
        selectedWords.remove(oldWord);
        selectedWords.add(selectedWord);
        doCallbacksStudent();
    }

    /*
     * Returns a list of submitted words
     * @return ArrayList<String>
     * @throws RemoteException
     */
    @Override
    public ArrayList<String> getSubmittedWords() throws RemoteException
    {
        return submittedWords;
    }

    /**
     * Returns a list of selected words
     * @return ArrayList<String>
     * @throws RemoteException
     */
    @Override
    public ArrayList<String> getSelectedWords() throws RemoteException
    {
        return selectedWords;
    }

    //===============================================================QUESTION RELATED===============================================================//

    /**
     * Sends a request to the data manager to register a question
     * @param question
     * @throws RemoteException
     */
    @Override
    public void submitQuestion(Question question) throws RemoteException
    {
        databaseConnection.submitQuestion(question);

        submittedWords.add(question.getWord());
        //Calls callback to notify the teacher about the new question
        doCallbacksNewQuestion();
    }

    /**
     * Retrieves questions from the data manager
     * @return ArrayList<Question>
     * @throws RemoteException
     */
    @Override
    public ArrayList<Question> retrieveQuestion() throws RemoteException
    {
        return databaseConnection.retrieveQuestion();
    }

    //===============================================================CLASSROOM RELATED===============================================================//

    /**
     * Returns the current classroom of the session
     * @return String
     */
    @Override
    public String getClassroom()
    {
        return classroom;
    }



}

