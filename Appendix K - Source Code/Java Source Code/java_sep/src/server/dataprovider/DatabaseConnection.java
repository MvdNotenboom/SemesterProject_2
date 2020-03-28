package server.dataprovider;

import shared.resources.Classroom;
import shared.resources.Question;
import shared.resources.ServerInfo;
import shared.resources.User;

import java.sql.*;
import java.util.ArrayList;

/**
 * Responsible with creating the connection with the database and various functionalities
 * @author group6
 * @version 1.0.0
 */
public class DatabaseConnection implements IDataManager
{
    private Connection connection;
    private static DatabaseConnection instance;


    //===============================================================COMMON PURPOSE===============================================================//

    /**
     * Connection with database
     */
    private DatabaseConnection()
    {
        String driver = "org.postgresql.Driver";

        String url = "jdbc:postgresql://2.105.99.100:2612/postgres";
        String user = "postgres";
        String pw = "temppassword";

        connection = null;

        try
        {
            Class.forName(driver);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        try
        {
            connection = DriverManager.getConnection(url, user, pw);
        }
        catch (SQLException e)
        {
            System.out.println("Could not connect to the database, please make sure it is on");
            System.exit(0);
        }
    }

    /**
     * Singleton implementation
     * @return DatabaseConnection
     */
    public static DatabaseConnection getInstance()
    {
        if(instance == null)
        {
            instance = new DatabaseConnection();
        }

        return instance;
    }

    /**
     * Retrieving user information based on the accountNumber information
     * @param accountNumber
     * @return User
     */
    @Override
    public User getCredentials(String accountNumber)
    {
        String firstName = null;
        String lastName = null;
        String eMail = null;
        String password = null;
        boolean isTeacher = false;

        try
        {
            String preparedSql = "SELECT * FROM sep.credentials " +
                    "WHERE credentials.accountNumber = '" + accountNumber + "';";

            PreparedStatement queryLogin = connection.prepareStatement(preparedSql);
            ResultSet resultSet = queryLogin.executeQuery();

            while(resultSet.next())
            {
                firstName = (String)resultSet.getObject(1);
                lastName = (String)resultSet.getObject(2);
                eMail = (String)resultSet.getObject(3);
                password = (String)resultSet.getObject(5);
                isTeacher = (boolean)resultSet.getObject(6);
            }

            resultSet.close();
            queryLogin.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        //Creates a user with the given information from the database
        return new User(firstName, lastName, eMail, accountNumber, password, isTeacher);
    }

    /**
     * Inserting new user into the database
     * @param user
     * @return boolean
     */
    @Override
    public boolean registerUser(User user)
    {
        boolean registeredState = false;

        try
        {
            String preparedSql = "INSERT INTO sep.credentials " +
                    "VALUES('" + user.getFirstName() + "', '" + user.getLastName() + "', '" + user.geteMail() + "', '" + user.getAccountNumber() + "', '" + user.getPassword() + "', '" + user.isTeacher()+ "');";

            PreparedStatement queryLogin = connection.prepareStatement(preparedSql);
            ResultSet resultSet = queryLogin.executeQuery();
        }

        catch (SQLException e)
        {
            //Database outputs '02000' for successful registration
            if(e.getSQLState().equals("02000"))
            {
                registeredState = true;
            }
        }

        return registeredState;
    }

    //===============================================================SERVER SESSION RELATED===============================================================//

    /**
     * Creates a registration of the session server for future use
     * @param accessCode
     * @param ipAddress
     * @param port
     * @param teacher
     * @return
     */
    @Override
    public boolean makeSessionServerRegistration(String accessCode, String ipAddress, String port, String teacher, String classroom)
    {
        boolean registryState = false;
        try
        {
            String preparedSql = "INSERT INTO sep.sessionserver " +
                    "VALUES('" + accessCode + "', '" + ipAddress + "', '" + port + "', '" + teacher + "', '" + classroom + "');";

            PreparedStatement querySessionServer = connection.prepareStatement(preparedSql);
            ResultSet resultSet = querySessionServer.executeQuery();
        }
        catch (SQLException e)
        {
            if(e.getSQLState().equals("02000"))
            {
                registryState = true;
            }
        }

        return registryState;
    }

    /**
     * Retrieves a session registration based on the access code
     * @param accessCode
     * @return ServerInfo
     */
    @Override
    public ServerInfo getSessionServer(String accessCode)
    {
        ServerInfo serverInfo = null;

        String ipAddress = null;
        String operator = null;
        String stringPort = null;
        String classroom = null;
        int port = 0000;

        try
        {
            String preparedSql = "SELECT * FROM sep.sessionserver " +
                    "WHERE sessionserver.accesscode = '" + accessCode + "';";

            PreparedStatement queryServerInfo = connection.prepareStatement(preparedSql);
            ResultSet resultSet = queryServerInfo.executeQuery();

            while(resultSet.next())
            {
                ipAddress = (String)resultSet.getObject(2);
                stringPort = (String)resultSet.getObject(3);
                operator = (String)resultSet.getObject(4);
                classroom = (String)resultSet.getObject(5);
            }

            resultSet.close();
            queryServerInfo.close();

            if(stringPort == null || stringPort.equals(null))
            {

            }
            else
            {
                port = Integer.parseInt(stringPort);
            }

            serverInfo = new ServerInfo(accessCode, ipAddress, port, operator, classroom);
        }
        catch (SQLException e)
        {
            if(e.getSQLState().equals("02000"))
            {

            }
            else
            {
                e.printStackTrace();
            }
        }

        return serverInfo;
    }

    /**
     * Registers a question to the database
     * @param question
     */
    @Override
    public void submitQuestion(Question question)
    {
        try
        {
            String preparedSql = "INSERT INTO sep.question " +
                    "VALUES('" + question.getWord() + "', '" + question.getType() +"', '" + question.getQuestion() + "', '" + question.getAnswer1() + "', '"
                    + question.getAnswer2() + "', '" + question.getAnswer3() + "', '" + question.getAnswer4() + "', '" + question.getCorrectAnswer() + "', '"
                    + question.getStudentNumber() +"');";

            PreparedStatement querySubmitQuestion = connection.prepareStatement(preparedSql);
            ResultSet resultSet = querySubmitQuestion.executeQuery();

            resultSet.close();
            querySubmitQuestion.close();
        }
        catch (SQLException e)
        {
            if(e.getSQLState().equals("02000"))
            {

            }
            else
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Retrieves all the questions from the database
     * @return ArrayList<Question>
     */
    @Override
    public ArrayList<Question> retrieveQuestion()
    {
        ArrayList<Question> questions = new ArrayList();

        try
        {
            String preparedSql = "SELECT * FROM sep.question;";

            PreparedStatement queryRetrieveQuestion = connection.prepareStatement(preparedSql);
            ResultSet resultSet = queryRetrieveQuestion.executeQuery();

            while(resultSet.next())
            {
                questions.add(new Question((String)resultSet.getObject(1), (String)resultSet.getObject(2), (String)resultSet.getObject(3), (String)resultSet.getObject(4), (String)resultSet.getObject(5),
                (String)resultSet.getObject(6), (String)resultSet.getObject(7), (String)resultSet.getObject(8), (String)resultSet.getObject(9)));
            }

            resultSet.close();
            queryRetrieveQuestion.close();
        }
        catch (SQLException e)
        {
            if(e.getSQLState().equals("02000"))
            {

            }
            else
            {
                e.printStackTrace();
            }
        }

        return questions;
    }

    //===============================================================TOPIC RELATED===============================================================//

    /**
     * Registers a topic to the database
     * @param topic
     */
    @Override
    public boolean createTopic(String topic)
    {
        String preparedSql = "INSERT INTO sep.topics VALUES('" + topic + "');";

        try
        {
            PreparedStatement queryNewTopic = connection.prepareStatement(preparedSql);
            ResultSet resultSet = queryNewTopic.executeQuery();

            resultSet.close();
            queryNewTopic.close();
        }
        catch (SQLException e)
        {
            if(e.getSQLState().equals("02000"))
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        return false;
    }

    /**
     * Returns the current list of topics
     * @return ArrayList<String>
     */
    @Override
    public ArrayList<String> fetchTopics()
    {
        ArrayList<String> topics = new ArrayList();

        try
        {
            String preparedSql = "SELECT * FROM sep.topics;";

            PreparedStatement queryFetchTopic = connection.prepareStatement(preparedSql);
            ResultSet resultSet = queryFetchTopic.executeQuery();

            while(resultSet.next())
            {
                topics.add((String)resultSet.getObject(1));
            }

            resultSet.close();
            queryFetchTopic.close();
        }
        catch (SQLException e)
        {
            if(e.getSQLState().equals("02000"))
            {

            }
            else
            {
                e.printStackTrace();
            }
        }

        return topics;
    }

    /**
     * Updates a topic in the database
     * @param newTopic
     * @param oldTopic
     * @return Boolean
     */
    @Override
    public boolean updateTopic(String newTopic, String oldTopic)
    {
        String preparedSql = "UPDATE sep.topics SET topics = '" + newTopic + "' WHERE topics = '" + oldTopic + "';";

        try
        {
            PreparedStatement queryUpdateTopic = connection.prepareStatement(preparedSql);
            ResultSet resultSet = queryUpdateTopic.executeQuery();

            resultSet.close();
            queryUpdateTopic.close();
        }
        catch (SQLException e)
        {
            if(e.getSQLState().equals("02000"))
            {
                return true;
            }
            else
            {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * Deletes a topic from the database
     * @param topic
     */
    @Override
    public void deleteTopic(String topic)
    {
        String preparedSql = "DELETE FROM sep.topics WHERE topics = '" + topic + "';";

        try
        {
            PreparedStatement queryDeleteTopic = connection.prepareStatement(preparedSql);
            ResultSet resultSet = queryDeleteTopic.executeQuery();

            resultSet.close();
            queryDeleteTopic.close();
        }
        catch (SQLException e)
        {
            if(e.getSQLState().equals("02000"))
            {

            }
            else
            {
                e.printStackTrace();
            }
        }
    }

    //===============================================================WORD RELATED===============================================================//

    /**
     * Returns the current list of words in the database
     * @param topic
     * @return ArrayLisT<String>
     */
    @Override
    public ArrayList<String> fetchWords(String topic)
    {
        ArrayList<String> words = new ArrayList();

        try
        {
            String preparedSql = "SELECT * FROM sep.words WHERE topic = '" + topic + "';";

            PreparedStatement queryFetchWords = connection.prepareStatement(preparedSql);
            ResultSet resultSet = queryFetchWords.executeQuery();

            while(resultSet.next())
            {
                words.add((String)resultSet.getObject(1));
            }

            resultSet.close();
            queryFetchWords.close();
        }
        catch (SQLException e)
        {
            if(e.getSQLState().equals("02000"))
            {

            }
            else
            {
                e.printStackTrace();
            }
        }
        return words;
    }

    /**
     * Adds a word to the database
     * @param word
     * @param topic
     */
    @Override
    public void addWord(String word, String topic)
    {
        String preparedSql = "INSERT INTO sep.words VALUES('" + word + "', '" + topic + "');";

        try
        {
            PreparedStatement queryNewWord = connection.prepareStatement(preparedSql);
            ResultSet resultSet = queryNewWord.executeQuery();

            resultSet.close();
            queryNewWord.close();
        }
        catch (SQLException e)
        {
            if(e.getSQLState().equals("02000"))
            {

            }
            else
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Edits a word in the database
     * @param newWord
     * @param selectedWord
     */
    @Override
    public void editWord(String newWord, String selectedWord)
    {
        String preparedSql = "UPDATE sep.words SET word = '" + newWord + "' WHERE word = '" + selectedWord + "';";

        try
        {
            PreparedStatement queryUpdateWord= connection.prepareStatement(preparedSql);
            ResultSet resultSet = queryUpdateWord.executeQuery();

            resultSet.close();
            queryUpdateWord.close();
        }
        catch (SQLException e)
        {
            if(e.getSQLState().equals("02000"))
            {

            }
            else
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deletes a word from the database
     * @param selectedWord
     */
    @Override
    public void deleteWord(String selectedWord)
    {
        String preparedSql = "DELETE FROM sep.words WHERE word = '" + selectedWord + "';";

        try
        {
            PreparedStatement queryDeleteWord = connection.prepareStatement(preparedSql);
            ResultSet resultSet = queryDeleteWord.executeQuery();

            resultSet.close();
            queryDeleteWord.close();
        }
        catch (SQLException e)
        {
            if(e.getSQLState().equals("02000"))
            {

            }
            else
            {
                e.printStackTrace();
            }
        }
    }

    //===============================================================CLASSROOM RELATED===============================================================//

    /**
     * Adds a classroom to the database with the given information
     * @param classroomId
     * @param classroomName
     */
    @Override
    public void createClassroom(String classroomId, String classroomName)
    {
        String preparedSql = "INSERT INTO sep.classrooms VALUES('" + classroomId + "','" + classroomName + "');";

        try
        {
            PreparedStatement queryCreateClassroom = connection.prepareStatement(preparedSql);
            ResultSet resultSet = queryCreateClassroom.executeQuery();

            resultSet.close();
            queryCreateClassroom.close();
        }
        catch (SQLException e)
        {
            if(e.getSQLState().equals("02000"))
            {

            }
            else
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Returns the current list of classrooms
     * @return ArrayList<Classroom>
     */
    @Override
    public ArrayList<Classroom> fetchClassrooms()
    {
        ArrayList<Classroom> classrooms = new ArrayList();

        String preparedSql = "SELECT * FROM sep.classrooms;";

        try
        {
            PreparedStatement queryFetchClassrooms = connection.prepareStatement(preparedSql);
            ResultSet resultSet = queryFetchClassrooms.executeQuery();

            while(resultSet.next())
            {
                classrooms.add(new Classroom((String) resultSet.getObject(1), (String) resultSet.getObject(2)));
            }

            resultSet.close();
            queryFetchClassrooms.close();
        }
        catch (SQLException e)
        {
            if(e.getSQLState().equals("02000"))
            {

            }
            else
            {
                e.printStackTrace();
            }
        }

        return classrooms;
    }

    /**
     * Deletes a classroom from the database with the given id
     * @param id
     */
    @Override
    public void deleteClassroom(String id)
    {
        String preparedSql = "DELETE FROM sep.classrooms WHERE classroomid = '" + id + "';";

        try
        {
            PreparedStatement queryDeleteClassroom = connection.prepareStatement(preparedSql);
            ResultSet resultSet = queryDeleteClassroom.executeQuery();

            resultSet.close();
            queryDeleteClassroom.close();
        }
        catch (SQLException e)
        {
            if(e.getSQLState().equals("02000"))
            {

            }
            else
            {
                e.printStackTrace();
            }
        }
    }

}
