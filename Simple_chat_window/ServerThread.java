/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.*;
import java.text.*;
/**
 *
 * @author Cham
 */
class ServerThread extends Thread {
	String message, whisperTo, invalid;
	int i, j, k;
	MyConnection[] conn;
	MyConnection user;
	
    public ServerThread(MyConnection[] c, MyConnection u){
        conn = c;
		user = u;
    }

    public void run(){
		
		try{
			for(i = 1; conn[i] != null; i++){
				for(j =1; j <= user.userNo; j++){
					conn[j].sendMessage("/status" + user.userNo + " " + conn[i].userNo + " " +conn[i].name + " " + conn[i].status + "\nEND");
				}
			}
			for(j = 1; j <= user.userNo; j++){
				conn[j].sendMessage("Server message: " + user.name + " has connected" + ".\nEND");
			}
			while(true){
					message = user.getMessage();
					if (message.startsWith("/")){
						
/* /changename */		if(message.startsWith("/changename ")){ 
							for(k = 1; conn[k] != null; k++){}
							for(i = 1; conn[i] != null; i++){
								for(j =1; j <= user.userNo; j++){
									conn[j].sendMessage("/status" + (k-1) + " " + conn[i].userNo + " " +conn[i].name + " " + conn[i].status + "\nEND");
								}
							}
							for(j = 1; conn[j] != null; j++){
								conn[j].sendMessage("Server message: " + user.name + " has changed name to " + message.substring(12) + "." + "\nEND");
								conn[j].sendMessage("/changename" + user.userNo + " " + message.substring(12));
							}
							user.name = message.substring(12);
/* /changestatus */		}else if(message.startsWith("/changestatus ")){
							for(k = 1; conn[k] != null; k++){}
							for(i = 1; conn[i] != null; i++){
								for(j =1; j <= user.userNo; j++){
									conn[j].sendMessage("/status" + (k-1) + " " + conn[i].userNo + " " +conn[i].name + " " + conn[i].status + "\nEND");
								}
							}
							for(j = 1; conn[j] != null; j++){
								conn[j].sendMessage("Server message: " + user.name + " has changed status to " + message.substring(14) + "." + "\nEND");
								conn[j].sendMessage("/changestatus" + user.userNo + " " + message.substring(14));
							}
							user.status = message.substring(14);
/* /whisper */			}else if(message.startsWith("/whisper ")){
							whisperTo = message.substring(9, (message.indexOf(" ", 9)));
							for(j = 1; conn[j] != null; j++){
								if(conn[j].name.equals(whisperTo)) break; 
							}
							System.out.println(j + " " + whisperTo);
							if(conn[j] != null && conn[j].name.equals(whisperTo) ) conn[j].sendMessage("[" + user.name + " whispers]: " + message.substring ((message.indexOf(" ", 9) + 1)));
							else user.sendMessage("Server message: " + whisperTo + " is not available.");
/* /quit */				}else if(message.startsWith("/quit")){
							for(i = 1; !conn[i].name.equals(user.name); i++){}
							for(j = 1; conn[j] != null; j++){
								conn[j].sendMessage(user.name + " has disconnected" + "\nEND");
							}for(k = 1; conn[k] != null; k++){
								conn[k].sendMessage("/disconnect" + i + " " + j + " \nEND" );
							}
							conn[i].sendMessage(message);
							conn[j-1].userNo = conn[i].userNo;
							conn[i] = conn[j-1];
							conn[j-1] = null;
							break;
/* invalid */			}else{
							if(message.contains(" ")){
								invalid = message.substring(0, (message.indexOf(" ")));
								user.sendMessage("Invalid command \""+ invalid + "\".\nEND");
							}else user.sendMessage("Invalid command \""+ message + "\".\nEND");
						}
					}
/*messages*/		else{
						for(j = 1; conn[j] != null; j++){
							conn[j].sendMessage(user.name + ": "+ message + "\nEND");
						}
					}
				
			}
		} catch (Exception e){
			e.printStackTrace();
		}
    }
}