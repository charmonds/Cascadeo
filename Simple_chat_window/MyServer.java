import java.net.*;
import java.util.*;
import java.text.*;

public class MyServer{
	public static void main (String args[]){
		
		MyConnection[] conn = new MyConnection[101];
		int i = 1;
		try{
			Socket socket;
			System.out.println("Server: Starting server...");
			ServerSocket ssocket = new ServerSocket(1438);
			System.out.println("Server: Waiting for connections...");
			for(; ;i++){
				socket = ssocket.accept();
				System.out.println("Server: " + ssocket.getInetAddress() + " connected.");
				conn[i] = new MyConnection(socket);
				conn[i].name = "User" + i;
				conn[i].userNo = i;
				ServerThread server = new ServerThread(conn, conn[i]);
				server.start();
			}
		}catch( Exception e){
			e.printStackTrace();
		}
	}
}
//aclab.dcs.upd.edu.ph