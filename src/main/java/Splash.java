import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class Splash extends Thread {
	int time;
	String file;

	public Splash(String file, int time) {
		this.file = file;
		this.time = time;
	}

	JProgressBar pbar;
	JWindow splash;

	public void run() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					splash = new JWindow();
					pbar = new JProgressBar();
					pbar.setMinimum(0);
					pbar.setMaximum(100);
					JPanel content = (JPanel) splash.getContentPane();
					// int width = 259;
					// int height = 210;
					// Dimension screen =
					// Toolkit.getDefaultToolkit().getScreenSize();
					// int x = (screen.width-width)/2;
					// int y = (screen.height-height)/2;
					JLabel label = new JLabel(new ImageIcon(file));
					content.add(label, BorderLayout.CENTER);
					content.add(pbar, BorderLayout.SOUTH);
					content.setBorder(BorderFactory.createLineBorder(
							Color.black, 1));
					splash.pack();
					Dimension screen = Toolkit.getDefaultToolkit()
							.getScreenSize();
					Dimension sl = splash.getSize();
					int x = (screen.width - sl.width) / 2;
					int y = (screen.height - sl.height) / 2;
					splash.setLocation(x, y);
					splash.setVisible(true);
					pbar.setStringPainted(true);
				}
			});
		} catch (Exception e) {
		}
		updateBar();
	}

	void close() {
		splash.setVisible(false);
	}

	int i = 0;
	Timer ii;

	public void updateBar() {
		ii = new Timer(20, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				try {
					pbar.setValue(i);
					pbar.updateUI();
				} catch (Exception g) {
					ii.stop();
					close();
				}
				i = i + 1;
				if (i > 100) {
					ii.stop();
					close();
				}
			}
		});
		ii.start();
	}
}