package br.com.pd.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente implements Runnable {	
	public static void main(String[] args){
		
		final String MENSAGEM_1 = "Lorem ipsum dolor sit amet,\n "
				+ "consectetur adipiscing elit. Donec maximus sit amet purus tincidunt\n "
				+ "pulvinar. Morbi vel gravida erat. Lorem ipsum dolor sit amet,\n "
				+ "consectetur adipiscing elit. Suspendisse convallis dolor eget\n "
				+ "metus vehicula consectetur. Ut egestas sapien ante, id viverra\n "
				+ "urna facilisis nec. Aliquam pellentesque consequat rutrum.\n "
				+ "Ut sem ipsum, cursus interdum bibendum non, maximus ac odio.\n "
				+ "Ut at arcu vitae lectus interdum consectetur. Donec bibendum,\n "
				+ "risus at feugiat dictum, sem arcu egestas tortor, id vestibulum\n "
				+ "turpis lorem sit amet nibh. Sed tempor quis tortor in dictum.\n";
		
		
		final String MENSAGEM_2 = "Donec eleifend fermentum ullamcorper.\n "
				+ "Praesent bibendum hendrerit lobortis. Nulla quis placerat tortor.\n "
				+ "Vivamus in porttitor dui. Aliquam nulla orci, fermentum quis mattis\n "
				+ "et, commodo at nunc. Fusce vel arcu euismod, pharetra orci vel, \n"
				+ "aliquam massa. Mauris rutrum ipsum leo, at tempus purus faucibus in.\n "
				+ "Proin tempor id elit non mollis.";
		
		final String MENSAGEM_3 = "Aliquam blandit erat et finibus aliquet.\n"
				+ " Phasellus vehicula faucibus turpis, et condimentum dolor sodales \n"
				+ "eget. Quisque ipsum lacus, varius ut ipsum sit amet, egestas \n"
				+ "fermentum ligula. Nullam vel nisi sit amet est fringilla auctor.\n "
				+ "Proin urna dui, semper nec mi at, ultrices posuere lorem. \n"
				+ "Pellentesque habitant morbi tristique senectus et netus et \n"
				+ "malesuada fames ac turpis egestas. Nullam consectetur vel ante et\n "
				+ "feugiat. Vivamus eros est, facilisis sed fringilla vitae, molestie\n "
				+ "a elit. Proin semper efficitur consectetur. Nam id nisl luctus,\n "
				+ "efficitur eros et, dapibus massa. In sed dapibus tellus. Integer\n "
				+ "interdum tincidunt ultrices. Nunc pretium turpis nec elit euismod\n "
				+ "suscipit. Aenean eget lobortis sapien. Donec commodo nisi sed diam\n "
				+ "aliquet semper.";
		
		
		Socket socket = null;
		try {
			socket = new Socket("localhost", 4002);
		}catch(IOException e){
			System.out.println("Erro no Cliente ao conectar ao servior: "+e.toString());
		}
		
		Thread tc1 = new Thread(new Cliente(socket, MENSAGEM_1, "Cliente 1"));
		Thread tc2 = new Thread(new Cliente(socket, MENSAGEM_3, "Cliente 2"));		
		Thread tc3 = new Thread(new Cliente(socket, MENSAGEM_2, "Cliente 3"));		
		Thread tc4 = new Thread(new Cliente(socket, MENSAGEM_3, "Cliente 4"));
		
		tc1.start();
		tc2.start();
		tc3.start();
		tc4.start();
	}
	
	public Socket conexao;
	public String mensagem;
	public String nome;

	public Cliente(Socket conexao, String mensagem, String nome) {
		// conectar ao servidor
		this.conexao = conexao;
		this.mensagem = mensagem;
		this.nome = nome;
	}

	@Override
	public void run() {

		DataInputStream entrada;
		DataOutputStream saida;
		
		try {
			// Cria objeto para enviar a mensagem ao servidor
			saida = new DataOutputStream(this.conexao.getOutputStream());
			saida.writeUTF(mensagem);
			System.out.println("\n"+nome+" enviou mensagem para o servidor!!\n");
		} catch (IOException e) {
			System.out.println("Erro no Cliente ao enviar dados: "+e.toString());
		}
		try {
			entrada = new DataInputStream(this.conexao.getInputStream());
            String resposta = entrada.readUTF();
            System.out.println("\n"+nome+" recebeu mensagem do servidor!!\n");
            
            System.out.println("\n\nResposta do servidor para "+nome+": \n\n"+resposta+"\n");
			System.out.println("\nFim do "+nome+"\n");
		} catch (IOException e) {
			System.out.println("Erro no Cliente ao receber dados: "+e.toString());
		}finally{
			try {
				this.conexao.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	

}