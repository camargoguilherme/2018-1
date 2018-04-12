package br.com.pd.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor implements Runnable {

	public Socket conexao;
	public DataInputStream entrada;
	public DataOutputStream saida;

	public static void main(String[] args) throws IOException {
		ServerSocket s = null;
		s = new ServerSocket(4002);

		while (true) {
			Socket socket = s.accept();
			// Cria uma thread do servidor para tratar a conexão
			Servidor tratamento = new Servidor(s.accept());
			Thread ts = new Thread(tratamento);
			// Inicia a thread para o cliente conectado
			ts.start();
		}
	}

	public Servidor(Socket servidor) throws IOException {
		// iniciar o servidor
		this.conexao = servidor;
	}

	@Override
	public void run() {
		
		String mensagem = null;
		System.out.println("\nNova conexao com o cliente " + conexao.getInetAddress().getHostAddress());


		try {	
			entrada = new DataInputStream(conexao.getInputStream());
			mensagem = entrada.readUTF();
			System.out.println("\nMensagem recebida do Cliente");
			
			
			switch (geraNum()) {
				case 1:
					mensagem = mensagem.toUpperCase();
					break;
				case 2:
					mensagem = mensagem.toLowerCase();
					break;
				case 3:
					mensagem = mensagem.replace(" ", "\n");
					break;
				default:
					mensagem = "Mensagem apagada sem querer kkkk";
					break;
			}
		} catch (IOException e) {
			System.out.println("Erro no Servidor ao receber dados: " + e.toString());
		}

		try {	
			saida = new DataOutputStream(conexao.getOutputStream());
			saida.writeUTF(mensagem);
			System.out.println("\nMensagem enviada para Cliente\n");
		} catch (IOException e) {
			System.out.println("Erro no Servidor ao enviar dados: " + e.toString());
		}
	}

	public static int geraNum() {
		return (int) (Math.floor((Math.random() * 3)));
	}

}