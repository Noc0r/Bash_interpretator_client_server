package Logic;

import java.io.*;

public class BashExecutor 
{
    File commands;
    //TO DO: need redirect output to the client object-reciever
    public void executeCommands(String command) throws IOException,InterruptedException {
        try {
            commands = createTempScript(command);
            ProcessBuilder pb = new ProcessBuilder("bash", commands.toString());
            pb.inheritIO();//!!!
            Process process = pb.start();
            process.waitFor();
        } finally {
            commands.delete();
        }
    }

    public File createTempScript(String command) throws IOException {
        File commands = File.createTempFile("cmds", null);
        Writer streamWriter = new OutputStreamWriter(
                            new FileOutputStream(commands));
        PrintWriter printWriter = new PrintWriter(streamWriter);
        printWriter.println(command);
        printWriter.close();
        return commands;
    }
    public static void main(String[] args)
    {
        BashExecutor exec = new BashExecutor();
        try
        {
            exec.executeCommands("cd /home/noc0r\nls");
        }
        catch (IOException|InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
