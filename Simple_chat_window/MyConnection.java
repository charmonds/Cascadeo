/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Cham
 */
class MyConnection {
	String name, status = "Online";
	int userNo;
    Socket socket;
    PrintWriter c;
    BufferedReader x;
    public MyConnection(Socket s){
        socket = s;
        try {
            c = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            x = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(MyConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendMessage(String msg){
        try{
            this.c.println(msg);
            this.c.flush();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getMessage(){
        try{
            return this.x.readLine();
        }catch(Exception e){
            e.printStackTrace();
        }

        return "No message has been received.";
    }
}
