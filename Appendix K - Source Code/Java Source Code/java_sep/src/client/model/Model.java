package client.model;


import shared.IServer;
import shared.callback.CallBack;
import shared.callback.ICallBack;
import shared.resources.Classroom;
import shared.resources.Question;
import shared.resources.User;
import shared.session.ISessionServer;
import shared.resources.ServerInfo;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Business logic implementation
 *
 * @author group6
 * @version 4.8.6
 */
public class Model implements IModel
{
    private IServer mainServer;
    private ISessionServer sessionServer;
    private User currentUser;
    private String accessCode;
    private PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    private ICallBack callbackObj;

    //===============================================================COMMON PURPOSE===============================================================//

    /**
     * Sets up the connection with the mainServer
     */
    public Model()
    {
        Registry registry = null;

        try
        {
            //Locates the registry
            registry = LocateRegistry.getRegistry("127.0.0.1", 2091);
            mainServer = (IServer) registry.lookup("sprogcenter");
        }
        catch (NotBoundException | RemoteException e)
        {
            System.out.println("Server not started, please make sure the server is on.");
            exit();
        }

    }

    /**
     * Asks mainServer to validate the credentials
     *
     * @param accountNumber
     * @param password
     * @return boolean
     */
    @Override
    public boolean getCredentials(String accountNumber, String password)
    {
        currentUser = null;

        try
        {
            currentUser = mainServer.checkCredentials(accountNumber, password);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

        //If null no record found
        if (currentUser.getAccountNumber() == null || currentUser.getAccountNumber().equals(""))
        {
            return false;
        } else
        {
            return true;
        }
    }

    /**
     * Asks the mainServer to register a new user
     *
     * @param accountNumber
     * @param password
     * @return boolean
     */
    @Override
    public boolean registerUser(String firstName, String lastName, String eMail, String accountNumber, String password, boolean isTeacher)
    {
        boolean registeredState = false;

        try
        {
            User user = new User(firstName, lastName, eMail, accountNumber, password, isTeacher);
            registeredState = mainServer.registerUser(user);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

        return registeredState;
    }

    /**
     * Returns the currently logged in user
     *
     * @return User
     */
    @Override
    public User getUser()
    {
        return currentUser;
    }

    /**
     * Exists the system
     */
    @Override
    public void exit()
    {
        System.exit(0);
    }


    //===============================================================SERVER SESSION RELATED===============================================================//

    /**
     * Sends a request to generate a server for a session, returns access code
     * @return String
     */
    @Override
    public String generateServer(String topic, ArrayList<String> words, String classroom)
    {
        try
        {
            accessCode = mainServer.generateCode(currentUser.getFirstName(), topic, words, classroom).getAccessCode();

            //After generating the server, connects to it
            connectToSession(accessCode);

            return accessCode;

        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

        //If server was not successfully created
        return "Server Not Created";
    }

    /**
     * Connects to the created server for the session and sends an instance of the callback and
     * a request to be registered for callbacks if the user is a teacher
     *
     * @param accessCode
     * @return
     */
    @Override
    public String connectToSession(String accessCode)
    {
        //Null server
        ServerInfo serverInfo = new ServerInfo(null, null, 0000, null, null);

        if (accessCode == null || accessCode.equals("") || accessCode.equals(" "))
        {
            return serverInfo.getAccessCode();
        }
        else
        {
            this.accessCode = accessCode;

            try
            {
                //Requests server information based on the accessCode from the database
                serverInfo = mainServer.getSessionServer(accessCode);
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
            }

            //Notifies if the server is not existing or nor reachable
            if (serverInfo.getAccessCode() == null || serverInfo.getAccessCode().equals(null) || serverInfo.getPort() == 0000)
            {
                return Integer.toString(serverInfo.getPort());
            }
            else
            {
                Registry registry = null;

                try
                {
                    //Connects to server with the retrieved information
                    registry = LocateRegistry.getRegistry(serverInfo.getIpAddress(), serverInfo.getPort());
                    sessionServer = (ISessionServer) registry.lookup(serverInfo.getAccessCode());

                    //Checks if user is a teacher and sends the callback instance
                    if (currentUser.isTeacher())
                    {
                        callbackObj = new CallBack(this);

                        sessionServer.registerForCallback(callbackObj);
                        System.out.println("Registered teacher for notification.");
                        try
                        {
                            Thread.sleep(1000);
                        }
                        catch (InterruptedException ex)
                        { // sleep over

                        }
                    } else if (!currentUser.isTeacher())
                    {
                        callbackObj = new CallBack(this);

                        sessionServer.registerStudentForCallback(callbackObj);
                        System.out.println("Registered student for notification.");
                        try
                        {
                            Thread.sleep(1000);
                        }
                        catch (InterruptedException ex)
                        { // sleep over

                        }
                    }


                }
                catch (NotBoundException | RemoteException e)
                {
                    e.printStackTrace();
                }
            }


            propertyChangeSupport.firePropertyChange("AccessCode", null, accessCode);
            //Returns the server's access code
            return Integer.toString(serverInfo.getPort());
        }
    }

    /**
     * Returns information about server in form of a ServerInfo Object that contains
     * access code, ip address, port number and operator of the session server
     *
     * @return ServerInfo
     */
    @Override
    public ServerInfo getSessionServerInfo()
    {
        ServerInfo serverInfo = new ServerInfo("Not found", null, 0000, null, null);

        try
        {
            return mainServer.getSessionServer(accessCode);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

        return serverInfo;
    }

    /**
     * Submits a string for a question to the main server
     *
     * @param question
     */
    @Override
    public void submitQuestion(Question question)
    {
        try
        {
            sessionServer.submitQuestion(question);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Looks up for new questions and fires an event to notify the InSessionViewModel
     */
    @Override
    public void checkNewQuestion()
    {
        ArrayList<Question> questions = new ArrayList();

        try
        {
            questions = sessionServer.retrieveQuestion();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

        propertyChangeSupport.firePropertyChange("NewQuestion", null, questions);
    }

    /**
     * Asks server to return the current list of words in the session
     *
     * @return
     */
    @Override
    public ArrayList<String> getSessionWords()
    {

        try
        {
            return sessionServer.getWords();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Returns the topic of the session that the model is currently connected to
     *
     * @return String
     */
    @Override
    public String getSessionTopic()
    {
        try
        {
            return sessionServer.getTopic();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Sets the current selected word by a students, as well as deselects the previously selected one
     *
     * @param oldWord
     * @param newWord
     */
    @Override
    public void selectWord(String oldWord, String newWord)
    {
        try
        {
            sessionServer.setSelectedWord(oldWord, newWord);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Returns a list of submitted words that were selected in a question
     *
     * @return ArrayList<String>
     */
    @Override
    public ArrayList<String> getSubmittedWords()
    {
        try
        {
            return sessionServer.getSubmittedWords();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Asks session server to check for selected words and fires a property change with the new list of selected words
     */
    @Override
    public void checkSelectedWords()
    {
        ArrayList<String> selectedWords = new ArrayList();

        try
        {
            selectedWords = sessionServer.getSelectedWords();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

        propertyChangeSupport.firePropertyChange("SelectedWords", null, selectedWords);
    }


    //===============================================================TOPIC RELATED===============================================================//

    /**
     * Asks server to register a topic, is checking for null and empty name, returns a true in case of success
     *
     * @param topic
     * @return Boolean
     */
    @Override
    public boolean createTopic(String topic)
    {
        boolean flag = false;

        if (topic == null || topic.equals("") || topic.equals(" "))
        {
            return flag;
        } else
        {
            try
            {
                return mainServer.createTopic(topic);
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
            }

            return !flag;
        }
    }

    /**
     * Asks server to edit an existing topic, checks for null and empty name in the new topic, returns true for success
     *
     * @param newTopic
     * @param oldTopic
     * @return Boolean
     */
    @Override
    public boolean editTopic(String newTopic, String oldTopic)
    {
        if (newTopic == null || newTopic.equals("") || newTopic.equals(" "))
        {
            return false;
        } else
        {
            try
            {
                return mainServer.editTopic(newTopic, oldTopic);
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
            }

            return false;
        }
    }

    /**
     * Asks server to delete an existing topic
     *
     * @param topic
     */
    @Override
    public void deleteTopic(String topic)
    {
        try
        {
            mainServer.deleteTopic(topic);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Asks server to update the current list of topics registered in the database
     *
     * @return ArrayList<String>
     */
    @Override
    public ArrayList<String> fetchTopics()
    {
        try
        {
            return mainServer.fetchTopics();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    //===============================================================WORD RELATED===============================================================//

    /**
     * Asks server to add a word to the list of words, checks for null and empty names
     *
     * @param word
     * @param topic
     */
    @Override
    public void addWord(String word, String topic)
    {
        try
        {
            mainServer.addWord(word, topic);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Asks server to edit an existing word, checks for null and empty name in the new word
     *
     * @param newWord
     * @param selectedWord
     */
    @Override
    public void editWord(String newWord, String selectedWord)
    {
        if (newWord == null || newWord.equals("") || newWord.equals(" "))
        {

        } else
        {
            try
            {
                mainServer.editWord(newWord, selectedWord);
            }
            catch (RemoteException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Asks the server to delete and existing word
     *
     * @param selectedWord
     */
    @Override
    public void deleteWord(String selectedWord)
    {
        try
        {
            mainServer.deleteWord(selectedWord);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Asks the server to update the current list of words based on a topic
     *
     * @param topic
     * @return ArrayList<String>
     */
    @Override
    public ArrayList<String> fetchWord(String topic)
    {
        try
        {
            return mainServer.fetchWord(topic);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Checks the list of currently connected students to a session
     * and fires a property change event informing about the change
     */
    @Override
    public void checkConnectedStudent()
    {
        ArrayList<User> connectedStudent = new ArrayList<User>();
        Vector callBackObj = new Vector();

        try
        {
            callBackObj = sessionServer.connectedStudents();

            for (int i = 0; i < callBackObj.size(); i++)
            {
                connectedStudent.add(((ICallBack) callBackObj.get(i)).getStudent());
            }
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

        propertyChangeSupport.firePropertyChange("ConnectedStudent", null, connectedStudent);
    }

    /**
     * Asks the session server to delete a student from the list of currently
     * connected students
     */
    @Override
    public void notifyStudentOut()
    {
        try
        {
            sessionServer.removeFromStudentList(callbackObj);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    //===============================================================CLASSROOM RELATED===============================================================//

    /**
     * Asks main server to register a classroom with the given information
     * @param classroomId
     * @param classroomName
     */
    @Override
    public void createClassroom(String classroomId, String classroomName)
    {
        try
        {
            mainServer.createClassroom(classroomId, classroomName);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Asks main server to retrieve the current list of classrooms
     * @return ArrayList<Classroom>
     */
    @Override
    public ArrayList<Classroom> fetchClassrooms()
    {
        try
        {
            return mainServer.fetchClassrooms();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Asks main server to delete a classroom with the given id
     * @param id
     */
    @Override
    public void deleteClassroom(String id)
    {
        try
        {
            mainServer.deleteClassroom(id);
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Returns the session's current classroom's id
     * @return String
     */
    @Override
    public String getClassroom()
    {
        try
        {
            return sessionServer.getClassroom();
        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    //===============================================================CHANGE LISTENER RELATED===============================================================//

    /**
     * Adds property listener without name
     *
     * @param listener
     */
    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    /**
     * Adds property listener with name
     *
     * @param name
     * @param listener
     */
    @Override
    public void addPropertyChangeListener(String name, PropertyChangeListener listener)
    {
        propertyChangeSupport.addPropertyChangeListener(name, listener);
    }

    /**
     * Removes property listener without name
     *
     * @param listener
     */
    @Override
    public void removePropertyChangeListener(PropertyChangeListener listener)
    {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    /**
     * Removes property listener with name
     *
     * @param name
     * @param listener
     */
    @Override
    public void removePropertyChangeListener(String name, PropertyChangeListener listener)
    {
        propertyChangeSupport.removePropertyChangeListener(name, listener);
    }
}
