package shared.callback;

import client.model.IModel;
import shared.resources.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Implementation for a callback functionality
 * @author group6
 * @version 1.0.0
 * @inspiredby Professor M. L. Liu
 */
public class CallBack extends UnicastRemoteObject implements ICallBack
{
    IModel model;

    /**
     * @param model
     * @throws RemoteException
     */
    public CallBack(IModel model) throws RemoteException
    {
        super();
        this.model = model;
    }

    /**
     * Notifies model to check for new questions
     * @throws RemoteException
     */
    @Override
    public void newQuestion() throws RemoteException
    {
        model.checkNewQuestion();
    }

    /**
     * Notifies model to check for new words
     * @throws RemoteException
     */
    @Override
    public void selectedWord() throws RemoteException
    {
        model.checkSelectedWords();
    }

    /**
     * Notifies model to check for the newly connected students
     * @throws RemoteException
     */
    @Override
    public void newStudent() throws RemoteException
    {
        model.checkConnectedStudent();
    }

    /**
     * Returns the user that have sent the callBack obj
     * @return User
     * @throws RemoteException
     */
    @Override
    public User getStudent() throws RemoteException
    {
        return model.getUser();
    }


}
