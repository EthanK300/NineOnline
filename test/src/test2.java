package test;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class test implements Runnable{
    boolean playing = true;
    public static void main(String[] args) {
	System.out.println("Type 'h' to host a game, and 'j' to join a game");
	Scanner scanner = new Scanner(System.in);
	String inLine = "";
	while(true){
	    inLine = scanner.nextLine();
	    if(inLine == "h"){
		scanner.close();
		host();
		return;
	    }else if(inLine == "j"){
		scanner.close();
		join();
		return;
	    }else{
		System.out.println("Bad input! 'h' to host and 'j' to join!");
	    }
	}
    }

    public void join(){
	while(playing){
	    Scanner scanner = new Scanner(System.in);
	    String hostName = "";
	    String pNumber = "";
	    while(true){
		System.out.println("Enter hostname: ");
		hostName = scanner.nextLine();
		System.out.println("Enter port number: ");
		pNumber = scanner.nextLine();
		int portNumber = 0;
		int hostAddress = 0;
		try{
		    hostAddress = Integer.parseInt(hostName);
		    portNumber = Integer.parseInt(pNumber);
		    Socket socket = new Socket(hostAddress, portNumber);
		}catch(Exception e){
		    System.out.println("Error creating connection! Check hostname and port number availability!");
		    continue;
		}
	    }
	}
    }

    public void host(){

    }



    
    public test(String[] args) {
	hostname = args[0];
	portNumber = Integer.parseInt(args[1]);
    }
    
    public void start(String[] args) {
	if(!(args.length > 0)) {
	    System.out.println("Please enter the hostname and port number!");
	    return;
	}
	connector = new Thread(this);
	while(running) {
	    try {
		if(sSocket == null) {
		    sSocket = new ServerSocket(portNumber - 1);
		}
		if(socketAccepter == null) {
		    System.out.println("Hosting on port " + (portNumber - 1) + ".");
		    socketAccepter = sSocket.accept();
		    //socket
		    if(socketAccepter != null) {
			found = true;
		    }
		    dis = new DataInputStream(socketAccepter.getInputStream());
		}
		//dis.readUTF() is the read in from input stream function
		while(dis != null) {
		    input = (String)dis.readUTF();
		    if(input == "quit") {
			return;
		    }
		}
		System.out.println(input);
		
		
		
		
		sSocket.close();
	    } 
	    catch (UnknownHostException e) {
		System.out.println();
		e.printStackTrace();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    running = false;
	    
	}
    }
    @Override
    public void run() {
	while(!found) {
	    try {
		socketOutgoing = new Socket((String)arguments[0], Integer.parseInt(arguments[1]));
	    }catch(Exception e) {
		
	    }
	}
    }
}
