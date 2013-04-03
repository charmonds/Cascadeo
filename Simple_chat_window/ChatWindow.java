import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import java.text.*;

public class ChatWindow extends JFrame implements ActionListener {
	MyConnection conn;
	Socket socket;
	String message = "";
	GetThread t1;
	SendThread t2;
    Container c;
	JButton Enter;
	TextArea Chat = new TextArea("Chat Window\n\n", 5, 20, TextArea.SCROLLBARS_VERTICAL_ONLY);
	TextArea Status = new TextArea("Online Clients\n\n", 5, 20, TextArea.SCROLLBARS_VERTICAL_ONLY);
	TextArea Text = new TextArea("", 5, 20, TextArea.SCROLLBARS_VERTICAL_ONLY);

	public ChatWindow() {
		try{
			socket = new Socket("127.0.0.1",1438);
			conn = new MyConnection(socket);
			t1 = new GetThread(conn, Chat, Status);
			SendThread t2 = new SendThread(conn, message);	
			t2.start();
			t1.start();
			t2.done();
			t1.done();
        }catch(Exception e){
            e.printStackTrace();
        }
		
		this.setSize(675, 400);
		this.setTitle("Cham's Program");
		this.setLocation(0,0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
		c = this.getContentPane();
		GroupLayout layout = new GroupLayout(getContentPane());
        c.setLayout(layout);
		
		Enter = new JButton("Send message");
		Enter.addActionListener(this);
		Enter.setLocation(487, 309);
        Enter.setSize(170, 50);
		
		Chat.setEditable(false);
        Chat.setSize(467, 274);
        Chat.setLocation(10, 10);
        Chat.setBackground(new java.awt.Color(250, 250, 250));
        Chat.setForeground(new java.awt.Color(0, 0, 0));
		
		Text.setEditable(true);
        Text.setSize(467, 50);
        Text.setLocation(10, 309);
        Text.setBackground(new java.awt.Color(250, 250, 250));
        Text.setForeground(new java.awt.Color(0, 0, 0));
		
		Status.setEditable(false);
        Status.setSize(170, 274);
        Status.setLocation(487, 10);
        Status.setBackground(new java.awt.Color(250, 250, 250));
        Status.setForeground(new java.awt.Color(0, 0, 0));

		c.add(Enter);
        c.add(Chat);
		c.add(Text);
		c.add(Status);
		
	}
	
	public void actionPerformed(ActionEvent e) {
		message = Text.getText();
		if(e.getSource() == Enter){
			
			t1 = new GetThread(conn, Chat, Status);
			t2 = new SendThread(conn, message);	
			t2.start();
			t1.start();
			
			Text.setText("");
		}
	}
}