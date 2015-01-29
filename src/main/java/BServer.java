import java.io.*;
import java.net.*;

public class BServer extends Thread {
	protected DatagramSocket socket = null;
	private long SECONDS;
	public boolean started;
	public int port;
	public String iip;

	public BServer(String ip, int p) {
		try {
			iip = ip;
			port = p;
			socket = new DatagramSocket(4445);
			SECONDS = 5000;
			started = true;
		} catch (Exception e) {
		}
	}

	public void run() {
		while (started) {
			try {
				String dString = iip + ":" + port;
				byte[] buf = new byte[128];
				buf = dString.getBytes();
				InetAddress group = InetAddress.getByName("230.0.0.1");
				DatagramPacket packet = new DatagramPacket(buf, buf.length,
						group, 4447);
				socket.send(packet);
				System.out.println("sent");
				try {
					sleep(SECONDS);
				} catch (InterruptedException e) {
				}
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		socket.close();
	}
}