package test;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class test2 implements Runnable{
    boolean playing = true;
    DataInputStream dis = null;
    Scanner scanner = new Scanner(System.in);

    public test2(){
        //empty constructor
    }

    public void init(){
	System.out.println("Type 'h' to host a game, and 'j' to join a game");
	String inLine = "";
	Thread thread = new Thread(this);
	thread.start();
	while(true){
	    inLine = scanner.nextLine();
	    if(inLine.equals("h")){
		host();
		return;
	    }else if(inLine.equals("j")){
		join();
		return;
	    }else{
		System.out.println("Bad input! 'h' to host and 'j' to join!");
	    }
	}
    }
    
    public static void main(String[] args) {
	test2 test = new test2();
	test.init();
    }

    public void join(){
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
		break;
	    }catch(IOException ie){
		System.out.println("Error! Data streams died! Try again!");
		continue;
	    }
	}
	System.out.println("Enter message: ");
	String out = "";
	while(true){
	    try{
		out = scanner.nextLine();
		dos.writeUTF(out);
		dos.flush();
	    }catch(Exception e){
		try{
		    dis.readUTF();
		}catch(Exception f){
		    System.out.println("Closed connection.");
		    System.exit(0);
		    return;
		}
		System.out.println("Error sending data!");
		e.printStackTrace();
		return;
	    }
	}
    }

    public void host(){
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
	System.out.println("Waiting for connection...");
	try{
	    socket = serverSocket.accept();
	    //wait for connection
	    dos = new DataOutputStream(socket.getOutputStream());
	    dis = new DataInputStream(socket.getInputStream());
	}catch(IOException e){
	    System.out.println("Error accepting connection");
	}
	System.out.println("Enter message: ");
	String out = "";
	while(true){
	    try{
		out = scanner.nextLine();
		dos.writeUTF(out);
		dos.flush();
	    }catch(Exception e){
		try{
		    dis.readUTF();
		}catch(Exception f){
		    System.out.println("Closed connection.");
		    System.exit(0);
		    return;
		}
		System.out.println("Error sending data!");
		e.printStackTrace();
		return;
	    }
	}
    }

    @Override
    public void run() {
	System.out.println("Started new thread");
	String printer = "";
	boolean wasConnected = false;
	while(true) {
	    try {
		printer = (String)dis.readUTF();
		wasConnected = true;
		System.out.println("* " + printer);
	    }catch(Exception e) {
		if(wasConnected){
		    System.out.println("Connection closed abruptly!");
		    scanner.close();
		    System.exit(0);
		    return;
		}
	    }
	}
    }
}
