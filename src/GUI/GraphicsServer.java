package GUI;

import GUI.Styles.Style;
import Structs.ServerInfo;

import javax.swing.*;

public class GraphicsServer
{
    private JFrame frame;

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(JFrame frame) {
        this.frame = frame;
    }

    public GraphicsServer()
    {
        init();
        events();
    }

    private  void events()
    {

    }

    private void init()
    {
        frame = new JFrame("Create host");
        frame.setBackground(Style.background);
        frame.setBounds(50,50,400,300);
        frame.setResizable(false);
    }
}
