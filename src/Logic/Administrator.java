package Logic;
import Structs.ServerInfo;

import java.util.ArrayList;
import java.util.TreeSet;



public class Administrator
{
    static private ArrayList<ServerInfo> list = new ArrayList<>();

    static public boolean createServer(ServerInfo server)
    {
        synchronized (list)
        {
            return list.add(server);
        }
    }

    static public ArrayList<ServerInfo> getServers()
    {
        return list;
    }
}
