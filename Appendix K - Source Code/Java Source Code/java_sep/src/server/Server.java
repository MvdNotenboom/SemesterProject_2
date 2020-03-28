package server;

import shared.*;
import server.dataprovider.IDataManager;
import server.dataprovider.DatabaseConnection;
import shared.resources.Classroom;
import shared.resources.User;
import shared.resources.ServerInfo;
import server.session.SessionServer;
import shared.session.ISessionServer;

import java.net.Inet4Address;
import java.net.UnknownHostException;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;

/**
 * Server side implementation
 * @author group6
 * @version 1.2.5
 */
public class Server implements IServer
{
    private IDataManager dataManager;
    private ISessionServer sessionServer;
    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    //===============================================================COMMON PURPOSE===============================================================//
    /**
     * Constructor
     * @throws RemoteException
     */
    public Server() throws RemoteException
    {
        UnicastRemoteObject.exportObject(this, 0);
        dataManager = DatabaseConnection.getInstance();
    }

    /**
     * Validates the credentials
     * @param accountNumber
     * @param password
     * @return boolean
     * @throws RemoteException
     */
    @Override
    public User checkCredentials(String accountNumber, String password) throws RemoteException
    {
        User user = dataManager.getCredentials(accountNumber);

        //if returned null - non-existing registration
        if (user.getPassword() == null)
        {
            //Creates a 'null' user to signal that the user with given account number and password is not existing
            return new User(null, null, null, null, null, false);
        }
        else if(user.getPassword().equals(password))
        {
            return user;
        }
        else
        {
            //Creates a 'null' user to signal that the user with given account number and password is not existing
            return new User(null, null, null, null, null, false);
        }
    }

    /**
     * Forewords data for registration to the database for registration
     * @param user
     * @return boolean
     * @throws RemoteException
     */
    @Override
    public boolean registerUser(User user) throws RemoteException
    {
        return dataManager.registerUser(user);
    }

    /**
     * Random code generator
     * @param charNumber
     * @return String
     */
    private static String accessCodeGenerator(int charNumber)
    {
        StringBuilder builder = new StringBuilder();
        while (charNumber-- != 0)
        {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    //===============================================================SERVER SESSION RELATED===============================================================//
    /**
     * Creates a server for the session and passes the server information
     * @param teacher
     * @return ServerInfo
     * @throws RemoteException
     */
    @Override
    public ServerInfo generateCode(String teacher, String topic, ArrayList<String> words, String classroom) throws RemoteException
    {
        String accessCode = accessCodeGenerator(5);
        String ipAddress = null;

        //Retrieves the server's ip address
        try
        {
           ipAddress = Inet4Address.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }

        boolean goodPort = true;

        //Initial port
        int port = 2091;

        //Tries to create a registry on the provided port if not increments it
        while(goodPort)
        {
            try
            {
                Registry registry = LocateRegistry.createRegistry(port);
                System.out.println("Available port found " + port);
                goodPort = false;
                sessionServer = new SessionServer(topic, words, classroom);
                registry.bind(accessCode, sessionServer);

                System.out.println("Session Server started with the access code " + accessCode);
            }
            catch (AlreadyBoundException e)
            {
                e.printStackTrace();
            }
            catch (ExportException e)
            {
                System.out.println("Port " + port + " occupied. Looking for next available port.");
                port++;
            }
        }

        //Registers the server in the database
        boolean registryState = dataManager.makeSessionServerRegistration(accessCode, ipAddress, (port + ""), teacher, classroom);

        if(registryState)
        {
            return new ServerInfo(accessCode, ipAddress, port, teacher, classroom);
        }
        else
        {
            return new ServerInfo(null, null, 0000, null, null);
        }
    }

    /**
     * Retrieves servers information from the database based on the access code
     * @param accessCode
     * @return ServerInfo
     * @throws RemoteException
     */
    @Override
    public ServerInfo getSessionServer(String accessCode) throws RemoteException
    {
        ServerInfo serverInfo = dataManager.getSessionServer(accessCode);

        if (serverInfo == null)
        {
            return new ServerInfo(null, null, 0000, null, null);
        }
        else
        {
            return serverInfo;
        }
    }

    //===============================================================TOPIC RELATED===============================================================//
    /**
     * Asks datamanger to register a new topic
     * @param topic
     */
    @Override
    public boolean createTopic(String topic)
    {
        return dataManager.createTopic(topic);
    }

    /**
     * Returns the current list of topics
     * @return ArrayList<String>
     * @throws RemoteException
     */
    @Override
    public ArrayList<String> fetchTopics() throws RemoteException
    {
        return dataManager.fetchTopics();
    }

    /**
     * Updates an existing topic
     * @param newTopic
     * @param oldTopic
     * @return
     * @throws RemoteException
     */
    @Override
    public boolean editTopic(String newTopic, String oldTopic) throws RemoteException
    {
        return dataManager.updateTopic(newTopic, oldTopic);
    }

    /**
     * Deletes an existing topic
     * @param topic
     */
    @Override
    public void deleteTopic(String topic)
    {
        dataManager.deleteTopic(topic);
    }

    //===============================================================WORD RELATED===============================================================//

    /**
     * Returns the current list of words
     * @param topic
     * @return ArrayList<String>
     */
    @Override
    public ArrayList<String> fetchWord(String topic)
    {
        return dataManager.fetchWords(topic);
    }

    /**
     * Adds a word to the list
     * @param word
     * @param topic
     * @throws RemoteException
     */
    @Override
    public void addWord(String word, String topic) throws RemoteException
    {
        dataManager.addWord(word, topic);
    }

    /**
     * Updates an existing word
     * @param newWord
     * @param selectedWord
     * @throws RemoteException
     */
    @Override
    public void editWord(String newWord, String selectedWord) throws RemoteException
    {
        dataManager.editWord(newWord, selectedWord);
    }

    /**
     * Deletes an existing word
     * @param selectedWord
     * @throws RemoteException
     */
    @Override
    public void deleteWord(String selectedWord) throws RemoteException
    {
        dataManager.deleteWord(selectedWord);
    }

    //===============================================================CLASSROOM RELATED===============================================================//

    /**
     * Asks the datamanger to register a classroom
     * @param classroomId
     * @param classroomName
     */
    @Override
    public void createClassroom(String classroomId, String classroomName)
    {
        dataManager.createClassroom(classroomId, classroomName);
    }

    /**
     * Updates the current list of classrooms
     * @return ArrayList<Classroom>
     * @throws RemoteException
     */
    @Override
    public ArrayList<Classroom> fetchClassrooms() throws RemoteException
    {
        return dataManager.fetchClassrooms();
    }

    /**
     * Asks datamanger to delete a classroom
     * @param id
     * @throws RemoteException
     */
    @Override
    public void deleteClassroom(String id) throws RemoteException
    {
        dataManager.deleteClassroom(id);
    }
}
