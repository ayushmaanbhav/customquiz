import java.io.*;
import java.net.*;
import java.util.*;

class Wifi {

	abstract class Thread1 extends Thread {
		abstract public void sendMessage(String mess);
	}

	private ServerSocket serverSocket;
	private boolean started;
	private Thread serverThread;
	int clientCount = 0;
	static String chatHist = "";
	List<Thread1> clientList;
	String ip;
	int port;

	public Wifi() throws IOException {
		serverSocket = new ServerSocket(9897, 50);
		serverSocket.setReuseAddress(true);
		clientList = new ArrayList<Thread1>();
		started = false;
	}

	public void startServer() {
		if (!started) {
			started = true;
			serverThread = new Thread() {
				public void run() {
					while (Wifi.this.started) {
						Socket clientSocket = null;
						try {
							clientSocket = serverSocket.accept();
							openClient(clientSocket);
							System.out.println("joined");
							try {
								Thread.sleep(10);
							} catch (Exception n) {
							}
						} catch (SocketException e) {
							System.err.println("Server closed.");
						} catch (IOException e) {
							System.err.println("Accept failed.");
						}
					}
				}
			};
			serverThread.start();
			// new BServer(i,p).start();
			System.out.println("started");
		}
	}

	public void stopServer() {
		this.started = false;
		serverThread.interrupt();
		try {
			serverSocket.close();
		} catch (IOException ex) {
			System.err.println("Server stop failed.");
		}
	}

	public void sendMulti(String mess) {
		for (int i = 0; i < clientList.size(); i++) {
			if (clientList.get(i).isAlive())
				clientList.get(i).sendMessage(mess);
			else
				clientList.remove(i--);
		}
	}

	int id = 0;

	public void openClient(final Socket socket) {
		clientCount++;
		Thread1 g = new Thread1() {
			BufferedReader in;
			// PrintWriter out;
			ObjectOutputStream objout;
			public void run() {
				try {
					// out=new PrintWriter(socket.getOutputStream(),true);
					in = new BufferedReader(new InputStreamReader(
							socket.getInputStream()));
					objout = new ObjectOutputStream(socket.getOutputStream());
					// objout.enableReplaceObject(true);
					// objin=new ObjectInputStream(socket.getInputStream());

					String inputLine;//, outputLine;

					while ((inputLine = (String) in.readLine()) != null) {
						// System.out.println(inputLine);
						processCommand(inputLine);
					}

					objout.close();
					in.close();
					socket.close();
				} catch (Exception ex) {
					System.out.println("Disconnected");
				}
			}

			void processCommand(String command) {
				String cmmd[] = command.split(":");
				for (int i = 0; i < cmmd.length; i++)
					cmmd[i] = cmmd[i].trim();
				if (cmmd[0].equals("buzz")) {
					System.out.println("BUZZ from team: " + cmmd[1]);
				} else {
					clientCount--;
					clientList.remove(this);
					try {
						objout.writeObject(new String("101"));
						objout.flush();
						in.close();
						objout.close();
						socket.close();
						return;
					} catch (Exception e) {
						in = null;
						objout = null;
						return;
					}
				}
			}

			public void sendMessage(final String mess) {
				new Thread() {
					public void run() {
						try {
							objout.reset();
							objout.writeObject(mess);
							objout.flush();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}.start();
			}
		};
		g.start();
	}
}