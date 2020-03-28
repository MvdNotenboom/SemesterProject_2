package shared.resources;

import java.io.Serializable;

/**
 * Class for holding information about a server
 * @author group6
 * @version 1.0.0
 */
public class ServerInfo implements Serializable
{
    private String ipAddress, accessCode, operator, classroom;
    private int port;

    /**
     * @param accessCode
     * @param ipAddress
     * @param port
     * @param operator
     */
    public ServerInfo(String accessCode, String ipAddress, int port, String operator, String classroom)
    {
        this.ipAddress = ipAddress;
        this.accessCode = accessCode;
        this.operator = operator;
        this.port = port;
        this.classroom = classroom;
    }

    /**
     * Setter for ipAddress
     * @param ipAddress
     */
    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    /**
     * Setter for accessCode
     * @param accessCode
     */
    public void setAccessCode(String accessCode)
    {
        this.accessCode = accessCode;
    }

    /**
     * Setter for Operator
     * @param operator
     */
    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    /**
     * Setter for port
     * @param port
     */
    public void setPort(int port)
    {
        this.port = port;
    }

    /**
     * Getter for ip address
     * @return String
     */
    public String getIpAddress()
    {
        return ipAddress;
    }

    /**
     * Getter for accessCode
     * @return String
     */
    public String getAccessCode()
    {
        return accessCode;
    }

    /**
     * Getter for operator
     * @return String
     */
    public String getOperator()
    {
        return operator;
    }

    /**
     * Getter for port
     * @return int
     */
    public int getPort()
    {
        return port;
    }

    /**
     * Sets the classroom of a session
     * @param classroom
     */
    public void setClassroom(String classroom)
    {
        this.classroom = classroom;
    }

    /**
     * Gets the current classroom of a session
     * @return String
     */
    public String getClassroom()
    {
        return classroom;
    }

    /**
     * toString
     * @return String
     */
    @Override
    public String toString()
    {
        return "ServerInfo{" +
                "ipAddress='" + ipAddress + '\'' +
                ", accessCode='" + accessCode + '\'' +
                ", operator='" + operator + '\'' +
                ", port=" + port +
                '}';
    }
}
