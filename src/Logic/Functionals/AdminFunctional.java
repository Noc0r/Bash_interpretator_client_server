package Logic.Functionals;

import Structs.ServerInfo;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface AdminFunctional extends Remote
{
    boolean createServer(ServerInfo server) throws RemoteException;
    boolean removeServer(ServerInfo server) throws RemoteException;
    public int getFreeID() throws RemoteException;
    ArrayList<ServerInfo> getList() throws RemoteException;
}
