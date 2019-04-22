package GUI;

import GUI.Styles.Style;
import Structs.ServerInfo;

import javax.swing.*;

public class GraphicsClient
{
    private JFrame frame;
    private JList<ServerInfo> servers;

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public GraphicsClient()
    {
        init();
        events();
    }

    private  void events()
    {

    }

    private void init()
    {
        frame = new JFrame("Enabled servers");
        frame.setBackground(Style.background);
        frame.setBounds(50,50,400,300);
        frame.setResizable(false);
    }

}
