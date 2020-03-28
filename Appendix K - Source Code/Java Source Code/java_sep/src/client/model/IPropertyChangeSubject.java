package client.model;

import java.beans.PropertyChangeListener;

/**
 * Interface for a property change subject
 * @version 1.0.0
 * @author group6
 */
public interface IPropertyChangeSubject
{
    //===============================================================ADDING PROPERTY CHANGES===============================================================//
    void addPropertyChangeListener(PropertyChangeListener listener);
    void addPropertyChangeListener(String name, PropertyChangeListener listener);

    //===============================================================REMOVING PROPERTY CHANGES===============================================================//
    void removePropertyChangeListener(PropertyChangeListener listener);
    void removePropertyChangeListener(String name, PropertyChangeListener listener);
}
