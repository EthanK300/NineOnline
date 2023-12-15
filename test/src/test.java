package test;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class test implements Runnable{
    private static String hostname;
    private static int portNumber;
    boolean running = true;
    ServerSocket sSocket = null;
    Socket socketAccepter = null;
    Socket socketOutgoing = null;
    String input = null;
    String output = null;
    static Thread connector = null;
    boolean found = false;
    static String[] arguments = null;
    DataInputStream dis = null;
    DataOutputStream dos = null;
    public static void main(String[] args) {
	if(args[0] == null || args[1] == null) {
	    boolean valid = false;
	    boolean valid2 = false;
	    Scanner scanner = new Scanner(System.in);
	    String inLine = "";
	    while(!valid || !valid2) {
		if(args[0] == "") {
		    System.out.println("Enter hostname");
		    inLine = scanner.nextLine();
		}else {
		    inLine = args[0];
		    valid = true;
		}
		if(args[1] == null) {
		    System.out.println("Enter port number");
		    try {
			inLine = scanner.nextLine();
			Socket tempSocket = new Socket("x",Integer.parseInt(inLine));
			inLine = args[1];
			tempSocket.close();
			valid = true;
		    }catch(Exception e) {
			//
		    }
		}
	    }
	    scanner.close();
	}
	test T = new test(args);
	T.start(args);
	arguments = args;
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
