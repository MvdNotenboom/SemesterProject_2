package server;


import shared.IServer;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Launcher for the server and RMI connection
 * @author group6
 * @version 1.0.0
 */
public class ServerStarter
{
    public static void main(String[] args)
    {
        try
        {
            Registry registry = LocateRegistry.createRegistry(2091);
            IServer server = new Server();
            registry.bind("sprogcenter", server);

            System.out.println("Main Server started...");
        }
        catch (RemoteException | AlreadyBoundException e)
        {
            e.printStackTrace();
        }
    }
}
