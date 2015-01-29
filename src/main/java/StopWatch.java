import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.border.*;

@SuppressWarnings("serial")
public class StopWatch extends JPanel {
	private Timer myTimer1, myTimer0;
	public static final int ONE_SEC = 1000; // time step in milliseconds
	public static final int TENTH_SEC = 100;

	private Font myClockFont;

	JButton startBtn, stopBtn, resetBtn, resetBtn1, resetBtn2;
	private JLabel timeLbl;
	private JPanel topPanel, bottomPanel;

	private int clockTick; // number of clock ticks; tick can be 1.0 s or 0.1 s
	private double clockTime; // time in seconds
	private String clockTimeString;
	int i = 0;
	Sound snd, snd2;

	public StopWatch() {
		clockTick = 600; // initial clock setting in clock ticks
		clockTime = ((double) clockTick) / 10.0;

		clockTimeString = new Integer((int) clockTime).toString();
		myClockFont = new Font("Serif", Font.BOLD, 120);
		snd = snd2 = null;

		timeLbl = new JLabel();
		timeLbl.setFont(myClockFont);
		timeLbl.setText(clockTimeString);

		startBtn = new JButton("Start");
		stopBtn = new JButton("Stop");
		resetBtn = new JButton("Res");
		resetBtn1 = new JButton("Reset4");
		resetBtn2 = new JButton("R30");

		myTimer1 = new Timer(TENTH_SEC, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				clockTick--;
				if (clockTick <= 0 && i == 0) {
					i = 1;
					myTimer1.stop();
					myTimer0 = new Timer(300, new ActionListener() {
						public void actionPerformed(ActionEvent evt) {
							if (topPanel.isOpaque()) {
								topPanel.setOpaque(false);
								topPanel.updateUI();
							} else {
								topPanel.setOpaque(true);
								topPanel.setBackground(Color.red);
								topPanel.updateUI();
							}
							try {
								snd.clip.stop();
								snd = null;
							} catch (Exception r) {
							}
							if (snd2 == null) {
								Thread t1 = new Thread() {
									public void run() {
										snd2 = new Sound();
										snd2.newClip(new File(CustomQuiz.resourcesLocation, "buzzer.wav").getAbsolutePath());
										snd2.run();
									}
								};
								t1.start();
							}
						}
					});
					myTimer0.start();
				}
				if (clockTick >= 5 && clockTick <= 100 && clockTick % 5 == 0) {
					if (snd == null) {
						Thread t1 = new Thread() {
							public void run() {
								snd = new Sound();
								snd.newClip(new File(CustomQuiz.resourcesLocation, "last10.wav").getAbsolutePath());
								snd.run();
							}
						};
						t1.start();
					}
					if (topPanel.isOpaque()) {
						topPanel.setOpaque(false);
						topPanel.updateUI();
					} else {
						topPanel.setOpaque(true);
						topPanel.setBackground(Color.yellow);
						topPanel.updateUI();
					}
				}
				clockTime = ((double) clockTick) / 10.0;
				clockTimeString = new Integer((int) clockTime).toString();
				timeLbl.setText(clockTimeString);
				// System.out.println(clockTime);
			}
		});

		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (i == 0)
					myTimer1.start();
			}
		});

		stopBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				myTimer1.stop();
				if (myTimer0 != null)
					myTimer0.stop();
				if (snd != null) {
					snd.clip.stop();
					snd = null;
				}
				if (snd2 != null) {
					snd2.clip.stop();
					snd2 = null;
				}
				topPanel.setOpaque(false);
				topPanel.updateUI();
			}
		});

		resetBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (myTimer0 != null)
					myTimer0.stop();
				if (snd != null) {
					snd.clip.stop();
					snd = null;
				}
				if (snd2 != null) {
					snd2.clip.stop();
					snd2 = null;
				}
				i = 0;
				topPanel.setOpaque(false);
				topPanel.updateUI();
				clockTick = 600;
				clockTime = ((double) clockTick) / 10.0;
				clockTimeString = new Integer((int) clockTime).toString();
				timeLbl.setText(clockTimeString);
			}
		});

		resetBtn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (myTimer0 != null)
					myTimer0.stop();
				if (snd != null) {
					snd.clip.stop();
					snd = null;
				}
				if (snd2 != null) {
					snd2.clip.stop();
					snd2 = null;
				}
				i = 0;
				topPanel.setOpaque(false);
				topPanel.updateUI();
				clockTick = 400;
				clockTime = ((double) clockTick) / 10.0;
				clockTimeString = new Integer((int) clockTime).toString();
				timeLbl.setText(clockTimeString);
			}
		});

		resetBtn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				if (myTimer0 != null)
					myTimer0.stop();
				if (snd != null) {
					snd.clip.stop();
					snd = null;
				}
				if (snd2 != null) {
					snd2.clip.stop();
					snd2 = null;
				}
				i = 0;
				topPanel.setOpaque(false);
				topPanel.updateUI();
				clockTick = 300;
				clockTime = ((double) clockTick) / 10.0;
				clockTimeString = new Integer((int) clockTime).toString();
				timeLbl.setText(clockTimeString);
			}
		});
	}// end of StopWatch constructor

	void reset(final int time) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (myTimer0 != null)
					myTimer0.stop();
				if (snd != null) {
					snd.clip.stop();
					snd = null;
				}
				if (snd2 != null) {
					snd2.clip.stop();
					snd2 = null;
				}
				i = 0;
				topPanel.setOpaque(false);
				topPanel.updateUI();
				clockTick = time*10;
				clockTime = ((double) clockTick) / 10.0;
				clockTimeString = new Integer((int) clockTime).toString();
				timeLbl.setText(clockTimeString);
			}
		});
	}

	public void launchStopWatch() {
		topPanel = new JPanel();
		topPanel.setOpaque(false);
		bottomPanel = new JPanel();
		bottomPanel.setOpaque(false);
		topPanel.add(timeLbl);
		topPanel.setBorder(new CompoundBorder(BorderFactory.createLineBorder(
				Color.black, 1), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		bottomPanel.add(startBtn);
		bottomPanel.add(stopBtn);
		bottomPanel.add(resetBtn);
		bottomPanel.add(resetBtn2);
		bottomPanel.setBorder(new CompoundBorder(BorderFactory
				.createLineBorder(Color.black, 1), BorderFactory
				.createEmptyBorder(5, 5, 5, 5)));

		this.setLayout(new BorderLayout());

		add(topPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		setSize(300, 200);
		setOpaque(false);

	}// end of launchClock

	@Override
	protected void paintComponent(Graphics grphcs) {
		Graphics2D g2d = (Graphics2D) grphcs;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		GradientPaint gp = new GradientPaint(0, 0, getBackground().brighter()
				.brighter(), getWidth(), getHeight(), getBackground().darker()
				.darker());
		g2d.setPaint(gp);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(grphcs);
	}

	// public static void main(String[] args) {
	// MyTestFrame myTestFrame1 = new MyTestFrame();
	// }

}// end of public class

// Testing Code

@SuppressWarnings("serial")
class MyTestFrame extends JFrame {
	StopWatch StopWatch1;

	public MyTestFrame() {
		super("My Stop Watch");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container myPane = getContentPane();

		StopWatch1 = new StopWatch();
		StopWatch1.launchStopWatch();
		myPane.add(StopWatch1);
		pack();
		setVisible(true);
	}
}