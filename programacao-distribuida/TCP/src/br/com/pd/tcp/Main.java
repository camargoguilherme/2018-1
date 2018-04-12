package br.com.pd.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class Main {

	public static Socket socket;
	public static ServerSocket server;

	public static void main(String[] args) throws UnknownHostException, IOException {
		
		//JOptionPane.showMessageDialog(null, geraPorta());
		int porta = geraPorta();
		server = new ServerSocket(porta);
		socket = new Socket("localhost", porta);
		
		
		Cliente cliente1 = new Cliente(socket, Mensagem.MENSAGEM_1, "Cliente 1");
		Cliente cliente2 = new Cliente(socket, Mensagem.MENSAGEM_1, "Cliente 2");
		Servidor servidor = new Servidor(server);
		
	}
	
	public static int geraPorta() {
		return (int) (Math.floor((Math.random()*9999)));
	}
	
	public static int geraNum() {
		return (int) (Math.floor((Math.random()*3)+1)) ;
	}

}
