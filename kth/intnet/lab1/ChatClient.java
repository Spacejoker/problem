import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatClient {

	static Socket socket;
	//static List<String> receivedMessages = new ArrayList<String>();
	
	class RecieverThread extends Thread{
		
		@Override
		public void run() {
			
			String message = "";
			BufferedReader indata;
			try {
				indata = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				while ((message = indata.readLine()) != null) {
					System.out.println(message);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	class SenderThread extends Thread {
		String name;
		public SenderThread(String name) {
			this.name = name;
		}
		
		@Override
		public void run() {
			try {
				PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
				String userInput = "";
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				while ((userInput = reader.readLine()) != null) {
				    printWriter.println(name + ": " + userInput);
				    System.out.println("Message sent.");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public ChatClient() throws Throwable {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Name: ");
		String name = reader.readLine();
		System.out.print("IP: ");
		String ip = reader.readLine();
		
		socket = new Socket(ip, 1337);
		
		new RecieverThread().start();
		new SenderThread(name).start();
	}
	
	public static void main(String[] args) throws Throwable {
		new ChatClient();
	}
}