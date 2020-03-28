package shared.callback;

import shared.resources.User;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * Interface for a callback functionality
 * @author group6
 * @version 1.0.0
 * @inspiredby Professor M. L. Liu
 */
public interface ICallBack extends Remote
{
    void newQuestion() throws RemoteException;
    void selectedWord() throws RemoteException;
    void newStudent() throws RemoteException;
    User getStudent() throws RemoteException;
}
