package Logic;

import java.io.*;

public class BashExecutor 
{
    File commands;
    public void executeCommands() throws IOException,InterruptedException {
        try {
            commands = createTempScript();
            ProcessBuilder pb = new ProcessBuilder("bash", commands.toString());
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
        } finally {
            commands.delete();
        }
    }

    public File createTempScript() throws IOException {
        File commands = File.createTempFile("cmds", null);
        Writer streamWriter = new OutputStreamWriter(
                            new FileOutputStream(commands));
        PrintWriter printWriter = new PrintWriter(streamWriter);
        printWriter.println("#!/bin/bash");
        printWriter.println("cd bin");
        printWriter.println("ls");
        printWriter.close();
        return commands;
    }
}
