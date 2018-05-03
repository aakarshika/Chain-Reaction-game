import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.io.*;
import java.net.*;
import javax.net.ssl.*;

public class ServerCredentials extends JFrame implements  ActionListener{

    JTextField untext, passtext;
    JButton submit;
    JLabel serverLabel, usernamelabel, passlabel;
    Scanner read;
    public ServerCredentials()  {
        draw(); 
    }
    void draw() 
    {
        setLayout(null);
        serverLabel =  new JLabel("Admin Login");
        usernamelabel = new JLabel("Enter Username");
        untext = new JTextField(200);
        passlabel = new JLabel("Enter Password");
        passtext = new JTextField(200);
        submit = new JButton("Submit");
        serverLabel.setBounds(175, 50, 50, 30);
        usernamelabel.setBounds(150, 100, 200, 30);
        untext.setBounds(100, 150, 200, 30);
        passlabel.setBounds(150, 300, 200, 30);
        passtext.setBounds(100, 400, 200, 30);
        submit.setBounds(100, 500, 200, 30);
        submit.addActionListener(this);
        add(serverLabel);
        add(usernamelabel);
        add(untext);
        add(passlabel);
        add(passtext);
        add(submit);
        setVisible(true);
        setSize(400, 700);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    
    }
    Mydb db;
    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        if(arg0.getSource() == submit)
        {
            try {
                String un = untext.getText();
                String pass = passtext.getText();
                //u can leave this part
                db=new Mydb(un, pass);
               
                //ghjjjjjjgfghjhgfc
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } 
        }
    }
    
}
