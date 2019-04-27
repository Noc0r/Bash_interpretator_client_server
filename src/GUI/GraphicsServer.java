package GUI;

import GUI.Styles.Style;
import Logic.Server;
import Structs.ServerInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphicsServer
{
    private JFrame frame;
    private JButton okBut = new JButton("OK");
    private JTextArea nameArea = new JTextArea();
    private JTextArea conArea = new JTextArea();
    private JTextArea pasArea = new JTextArea();
    private Server s;

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
        okBut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ServerInfo info = new ServerInfo(
                            nameArea.getText(),
                            Integer.parseInt(conArea.getText()),
                            pasArea.getText());
                    s = new Server(info);
                    s.start();
                    HostWindow window = new HostWindow(info);
                    window.getFrame().setVisible(true);
                    frame.dispose();
                }
                catch (Exception ex)
                {
                    JOptionPane.showMessageDialog(frame,ex.getMessage());
                }
            }
        });
    }

    private void init()
    {
        frame = new JFrame("Create host");
        frame.setBackground(Style.background);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setBounds(50,50,500,400);
        frame.setResizable(false);
        JLabel name = new JLabel("Server name");
        JLabel connections = new JLabel("Max users");
        JLabel password = new JLabel("Server password");
        JLabel access = new JLabel("Users access");
        name.setBounds(40,40,150,30);
        connections.setBounds(40,120,150,30);
        password.setBounds(40,190,150,30);

        nameArea.setBounds(340,45,120,15);
        conArea.setBounds(340,125,120,15);
        pasArea.setBounds(340,195,120,15);
        okBut.setBounds(190,310,100,20);
        frame.add(name);
        frame.add(connections);
        frame.add(password);
        frame.add(access);
        frame.add(nameArea);
        frame.add(conArea);
        frame.add(pasArea);
        frame.add(okBut);
    }
    public static void main(String[] args)
    {
        EventQueue.invokeLater(()-> {
                GraphicsServer cl = new GraphicsServer();
                cl.frame.setVisible(true);
        });
    }
}
