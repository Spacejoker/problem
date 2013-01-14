import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

	List<ClientHandler> clients= new ArrayList<ClientHandler>();

	public ChatServer() throws Throwable {

		ServerSocket serverSocket = new ServerSocket(1337);
		
		while (true) {
			ClientHandler clientHandler = new ClientHandler(serverSocket.accept(), this);
			clientHandler.start();
			
			clients.add(clientHandler);
		}
	}

	public static void main(String[] args) throws Throwable {
		new ChatServer();
	}

	public void broadcast(String message, ClientHandler master) {
		for (ClientHandler client: clients) {
			if (client != master)
				client.sendMessage(message);
		}
	}

}
