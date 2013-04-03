/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.net.*;
import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author Cham
 */
class GetThread extends Thread {
    Socket socket;
	TextArea Chat, Status;
	String message;
	String[] onlineStatus = new String[101];
	String[] onlineName = new String[101];
	int user = 0, i, j, clients = -1; //counters
	boolean running, firstLogIn = true;
    MyConnection conn;
    public GetThread(MyConnection c, TextArea ch, TextArea s){
        conn = c;
		Chat = ch;
		Status = s;
    }
    public void run(){
	running = true;
	while(running){
        try{
			do{
				while(true){
					message = conn.getMessage();//System.out.println(message);
					if (message.equals("END") || message.equals("/quit")) break;
					else if(message.startsWith("/status")){
						user = Integer.parseInt(message.substring(message.indexOf(" ") + 1, message.indexOf(" ", message.indexOf(" ") + 1)));
						clients = Integer.parseInt(message.substring(7, message.indexOf(" ")));
						onlineName[user] = message.substring(message.indexOf(" ", message.indexOf(" ") + 1)+1, message.indexOf(" ", message.indexOf(" ", message.indexOf(" ") + 1)+1 ));
						onlineStatus[user] = message.substring(message.indexOf(" ", message.indexOf(" ", message.indexOf(" ") + 1)+1 )+1);
						
					}else if(message.startsWith("/disconnect")){
						//System.out.println("nag disconnect si i tapos si j papalit sa kanya");
						clients--;
						user = Integer.parseInt(message.substring(11, message.indexOf(" ")));
						j =  Integer.parseInt(message.substring(message.indexOf(" ") + 1, message.indexOf(" ", message.indexOf(" ") + 1)));
						onlineName[user] = onlineName[j-1];
						onlineStatus[user] = onlineStatus[j-1];
						onlineName[j-1] = null; onlineStatus[j-1] = null;
						
						Status.setEditable(true);
						Status.setText("Online Clients\n\n");
						for(i = 1; i <= clients; i++) Status.append(onlineName[i] + "- " + onlineStatus[i] + "\n");
						Status.setEditable(false);
						
					}else if(message.startsWith("/changename")){
						user = Integer.parseInt(message.substring(11, message.indexOf(" ")));
						onlineName[user] = message.substring(message.indexOf(" ")+1);
						
						Status.setEditable(true);
						Status.setText("Online Clients\n\n");System.out.println("Dumaan ako dito before the loop " + i + " <= " + clients);
						for(i = 1; i <= clients; i++) { System.out.println("pumasok ako kasi " + i + " <= " + clients); 
							Status.append(onlineName[i] + "- " + onlineStatus[i] + "\n");
						}	System.out.println("Dumaan ako dito after the loop");
						Status.setEditable(false);
					}else if(message.startsWith("/changestatus")){
						user = Integer.parseInt(message.substring(13, message.indexOf(" ")));
						onlineStatus[user] = message.substring(message.indexOf(" ")+1);
						
						Status.setEditable(true);
						Status.setText("Online Clients\n\n"); System.out.println("Dumaan ako dito before the loop" + i + " <= " + clients);
						for(i = 1; i <= clients; i++) { System.out.println("pumasok ako kasi " + i + " <= " + clients);
							Status.append(onlineName[i] + "- " + onlineStatus[i] + "\n");
						}System.out.println("Dumaan ako dito after the loop");
						Status.setEditable(false); 
					}else{
						Chat.setEditable(true);
						Chat.append(message + "\n");
						Chat.setEditable(false);
					}
					if(firstLogIn && clients == user){
						
						Status.setEditable(true);
						for(i = 1; i < clients; i++) Status.append(onlineName[i] + "- " + onlineStatus[i] + "\n");
						Status.setEditable(false);
						firstLogIn = false; user--;
					}else if(message.startsWith("Server message: ") && message.contains(" has connected.")){
						clients = Integer.parseInt(message.substring(20, message.indexOf(" ", 20)));
						Status.setEditable(true);
						Status.append(onlineName[clients] + "- " + onlineStatus[clients] + "\n");
						Status.setEditable(false);
					}
				}
			}while(!message.equals("/quit"));
		running = false;
        }catch(Exception e){
            e.printStackTrace();
        }
		System.exit(0);
	}
	}
	
	public void done(){
		running = false;
	}
}
