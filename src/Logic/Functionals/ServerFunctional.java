package Logic.Functionals;

import Logic.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerFunctional extends Remote {
    String send(String clientID,String commands) throws RemoteException;
    boolean addClient(Client name)throws RemoteException;
    void removeClient(Client name)throws RemoteException;
    ArrayList<Client> getUsers() throws RemoteException;
}
