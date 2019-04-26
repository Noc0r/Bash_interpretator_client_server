package GUI;

import GUI.Styles.Style;
import Logic.Functionals.AdminFunctional;
import Logic.Server;
import Structs.Configuration;
import Structs.ServerInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;

public class GraphicsServer
{
    private JFrame frame;
    JButton okBut = new JButton("OK");
    JTextArea nameArea = new JTextArea();
    JTextArea conArea = new JTextArea();
    JTextArea pasArea = new JTextArea();

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
                    Server s = new Server(nameArea.getText(),
                            Integer.parseInt(conArea.getText()),
                            pasArea.getText());
                    s.start();
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
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                GraphicsServer cl = new GraphicsServer();
                cl.frame.setVisible(true);
            }
        });
    }
}
