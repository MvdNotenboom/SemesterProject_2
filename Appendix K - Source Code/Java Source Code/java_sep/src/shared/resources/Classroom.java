package shared.resources;

import java.io.Serializable;

/**
 * Utility class used to create classrooms
 * @author group6
 * @version 2.3.1
 */
public class Classroom implements Serializable
{
    private String name;
    private String id;

    /**
     * Constructor
     * @param id
     * @param name
     */
    public Classroom(String id, String name)
    {
        this.name = name;
        this.id = id;
    }

    /**
     * Getter for name
     * @return String
     */
    public String getName()
    {
        return name;
    }

    /**
     * Getter for account number
     * @return String
     */
    public String getId()
    {
        return id;
    }

    /**
     * Setter for name
     * @param name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Setter for account number
     * @param id
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * Return the account number only
     * @return String
     */
    @Override
    public String toString()
    {
        return id; 
    }
}
