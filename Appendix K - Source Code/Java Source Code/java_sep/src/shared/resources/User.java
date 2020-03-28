package shared.resources;

import java.io.Serializable;

/**
 * Provides a framework for a user
 */
public class User implements Serializable
{
    private String firstName, lastName, eMail, accountNumber, password;
    private boolean isTeacher;

    /**
     * @param firstName
     * @param lastName
     * @param eMail
     * @param accountNumber
     * @param password
     * @param isTeacher
     */
    public User(String firstName, String lastName, String eMail, String accountNumber, String password, boolean isTeacher)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.accountNumber = accountNumber;
        this.password = password;
        this.isTeacher = isTeacher;
    }

    /**
     * Gets first name
     * @return String
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Gets last name
     * @return String
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Gets eMail
     * @return String
     */
    public String geteMail()
    {
        return eMail;
    }

    /**
     * Gets account number
     * @return String
     */
    public String getAccountNumber()
    {
        return accountNumber;
    }

    /**
     * Gets password
     * @return String
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Returns true if the user is a teacher and false if the user is a student
     * @return boolean
     */
    public boolean isTeacher()
    {
        return isTeacher;
    }

    /**
     * Sets first name
     * @param firstName
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * Sets last name
     * @param lastName
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * Sets eMail
     * @param eMail
     */
    public void setEMail(String eMail)
    {
        this.eMail = eMail;
    }

    /**
     * Sets account number
     * @param accountNumber
     */
    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    /**
     * Sets password
     * @param password
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Sets whether it is a student(false) or a teacher(true)
     * @param teacher
     */
    public void setTeacher(boolean teacher)
    {
        isTeacher = teacher;
    }

    /**
     * Prints information about the user
     * @return String
     */
    @Override
    public String toString()
    {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", eMail='" + eMail + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", password='" + password + '\'' +
                ", isTeacher=" + isTeacher +
                '}';
    }
}
