/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Cham
 */
class SendThread extends Thread {
    Socket socket;
	String message;
	boolean running;
    MyConnection conn;
    public SendThread(MyConnection c, String m){
        conn = c;
		message = m;
    }

    public void run(){
	running = true;
	while(running){
        try{
			do{
				if (!message.equals ("")) conn.sendMessage(message);
				message = "/quit";
			}while(!message.equals("/quit"));
		running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
	}
    }
	
	public void done(){
		running = false;
	}
    
}
