import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread {

	private Socket clientSocket;
	private ChatServer server;
	private PrintWriter printWriter;

	public ClientHandler(Socket socket, ChatServer server) {
		this.clientSocket = socket;
		this.server = server;
	}

	@Override
	public void run() {

		System.out.println("started client: " + clientSocket);
		try {
			this.printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		new RecieverThread().start();
	}

	class RecieverThread extends Thread {

		public void run() {

			String message = "";
			BufferedReader indata;
			try {
				indata = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				while ((message = indata.readLine()) != null) {
					System.out.println("Got message: " + message);
					server.broadcast(message, ClientHandler.this);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void sendMessage(String message) {
		printWriter.println(message);
	}

}
