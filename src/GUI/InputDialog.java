package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputDialog extends JDialog{
    JLabel label = new JLabel("Input password");
    JTextArea area = new JTextArea();
    JButton ok = new JButton("Connect");
    String pass;
    String input="";
    boolean correct =false;
    public InputDialog(JFrame frame,String pass)
    {
        super(frame,"Input password");
        this.pass = pass;
        System.out.println(pass);
        init(frame);
        events();
    }

    private void events()
    {
        area.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(!e.isControlDown())
                {
                    area.setText(area.getText().replace(e.getKeyChar(),'*'));
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(!e.isControlDown() && !(e.getKeyCode()== KeyEvent.VK_BACK_SPACE))
                {
                    area.setText(area.getText().replace(e.getKeyChar(),'*'));
                    input+=e.getKeyChar();
                }
                if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                    if (input.length()!=0)
                        input = input.substring(0,input.length()-1);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(!e.isControlDown())
                {
                    area.setText(area.getText().replace(e.getKeyChar(),'*'));
                }
            }
        });
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(input.equals(pass)) {
                    correct = true;
                    dispose();
                }
                else
                {
                    label.setText("Incorrect! Try again");
                }
            }
        });
    }

    private void init(JFrame frame)
    {
        setModal(true);
        setResizable(false);
        setLayout(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(50,50,400,200);
        label.setBounds(150,20,200,30);
        area.setBounds(100,60,200,30);
        ok.setBounds(150,100,100,30);
        add(label);
        add(area);
        add(ok);
    }

    public static void main(String[] args)
    {
        InputDialog window = new InputDialog(null,"1245");
    }
}

