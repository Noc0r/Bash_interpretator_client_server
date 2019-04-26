package Logic;

import java.io.*;
import java.util.Scanner;

public class BashExecutor implements Serializable
{
    File commands;
    File output;
    //TO DO: need redirect output to the client object-reciever
    public String executeCommands(String command) throws IOException,InterruptedException {
        try {
            commands = createTempScript(command);
            ProcessBuilder pb = new ProcessBuilder("bash", commands.toString());
            //pb.inheritIO();
            output = File.createTempFile("output", null);
            pb.redirectOutput(output);
            Process process = pb.start();
            process.waitFor();
        } finally {
            String answer="";
            Scanner s = new Scanner(output);
            while(s.hasNext())
            {
                answer+=s.nextLine()+"\n";
            }
            output.delete();
            commands.delete();
            return answer;
        }
    }

    private File createTempScript(String command) throws IOException {
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
            System.out.println(exec.executeCommands("cd /home/noc0r\nls\npwd"));
        }
        catch (IOException|InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
