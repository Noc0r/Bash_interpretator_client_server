package Logic.Functionals;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerFunctional extends Remote {
    String send(String clientID,String commands) throws RemoteException;
}
