package test;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class test2 implements Runnable{
    boolean playing = true;
    static DataInputStream dis = null;
    public static void main(String[] args) {
	System.out.println("Type 'h' to host a game, and 'j' to join a game");
	Scanner scanner = new Scanner(System.in);
	String inLine = "";
	Thread thread = new Thread();
	thread.start();
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

    public static void join(){
	Scanner scanner = new Scanner(System.in);
	String hostName = "";
	String pNumber = "";
	DataOutputStream dos = null;
	Socket socket = null;
	while(true){
	    System.out.println("Enter hostname: ");
	    hostName = scanner.nextLine();
	    System.out.println("Enter port number: ");
	    pNumber = scanner.nextLine();
	    int portNumber = 0;
	    try{
		portNumber = Integer.parseInt(pNumber);
		socket = new Socket(hostName, portNumber);
	    }catch(Exception e){
		System.out.println("Error creating connection! Check hostname and port number availability!");
		continue;
	    }
	    try{
		dis = new DataInputStream(socket.getInputStream());
		dos = new DataOutputStream(socket.getOutputStream());
	    }catch(IOException ie){
		System.out.println("Error! Data streams died! Try again!");
		continue;
	    }
	    
	}
    }

    public static void host(){
	Scanner scanner = new Scanner(System.in);
	String pNumber = "";
	DataOutputStream dos = null;
	Socket socket = null;
	ServerSocket serverSocket = null;
	while(true){
	    int portNumber = 0;
	    System.out.println("Enter port number to host on: ");
	    pNumber = scanner.nextLine();
	    try{
		portNumber = Integer.parseInt(pNumber);
		serverSocket = new ServerSocket(portNumber);
		break;
	    }catch(Exception e){
		System.out.println("Error creating connection on port "+ portNumber + "! Check and port number availability!");
		continue;
	    }
	}
	try{
	    socket = serverSocket.accept(); //wait for connection
	}catch(IOException e){
	    System.out.println("Error accepting connection");
	}
    }

    @Override
    public void run() {
	String printer = "";
	while(true) {
	    if(dis == null){
		continue;
	    }
	    try {
		printer = (String)dis.readUTF();
		System.out.println("* " + printer);
	    }catch(Exception e) {
		//
	    }
	}
    }
}
