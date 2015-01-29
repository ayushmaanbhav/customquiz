import java.io.*;

import javax.swing.*;
import javax.swing.text.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.border.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.sun.jna.Native;
import com.sun.jna.NativeLibrary;

import java.awt.image.BufferedImage;
import java.net.URISyntaxException;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

class CustomQuiz implements ActionListener {
	JFrame frame;
	JTextPane textPane;
	JButton opt[], next, prev, cat[], answer, options, pass, lf1, lf2;
	JScrollPane qq;
	Answer an;
	String subQues[] = null;
	JDialog jd;
	JInternalFrame internalFrame, internalFrame2, internalFrame3,
			internalFrame4, internalFrame5;
	Ques q;
	Thread t = null, t2 = null, t3 = null, t4 = null;
	Color color, yellow = Color.yellow.brighter().brighter();
	MenuActListener mal;
	int bbb = 0;
	Sound sound;
	int k = 0, l = 0;
	boolean showpic, showtxt;
	KeyEventDispatcher ked, ked2;
	StopWatch sw;
	int ques;
	BufferedImage image;
	Ques bon = null;
	float ii;
	MediaPlayerFactory factory;
	CustomQuiz var;
	Timer alphaChanger;
	EmbeddedMediaPlayerComponent mediaPlayer;

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent ae) {
		final String ac = ae.getActionCommand();
		if ((k == 2) && (ac.equals("Pass Question"))) {
			opt[0].setBackground(color);
			opt[1].setBackground(color);
			opt[2].setBackground(color);
			opt[3].setBackground(color);
			opt[0].updateUI();
			opt[1].updateUI();
			opt[2].updateUI();
			opt[3].updateUI();
			sw.stopBtn.doClick();
			sw.reset(Integer.parseInt(rounds[Ques.category].passingTime));
			t3 = new Thread() {
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (Exception cf) {
					}
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							sw.startBtn.doClick();
						}
					});
				}
			};
			t3.start();
		} else if ((k == 2)
				&& (ac.equals("10") || ac.equals("11") || ac.equals("12") || ac
						.equals("13"))) {
			opt[0].setBackground(color);
			opt[1].setBackground(color);
			opt[2].setBackground(color);
			opt[3].setBackground(color);
			opt[0].updateUI();
			opt[1].updateUI();
			opt[2].updateUI();
			opt[3].updateUI();
			opt[Integer.parseInt(ac) - 10].setBackground(yellow);
			opt[Integer.parseInt(ac) - 10].updateUI();
		} else if (ac.equals("Next Ques")) {
			k = 0;
			l = 0;
			try {
				t.stop();
				t3.stop();
				t4.stop();
			} catch (Exception mm) {
			}
			t = new Thread() {
				public void run() {
					try {
						t2.stop();
					} catch (Exception mm) {
					}
					try {
						/*
						 * if(bbb==1) { q=bon; } else
						 */
						{
							q = Ques.readNextQues();
						}
						mediaPlayer.getMediaPlayer().playMedia(background);
						// label.setIcon(new ImageIcon((new
						// ImageIcon("default.jpg").getImage()).getScaledInstance(label.getWidth(),label.getHeight(),Image.SCALE_SMOOTH)));
					} catch (Exception e) {
						JOptionPane.showMessageDialog(frame,
								"Question Not Found.", "Error:",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					SwingUtilities.invokeLater(new Runnable() {
						@SuppressWarnings({ "restriction", "serial" })
						public void run() {
							if (q.ques == null) {
								JOptionPane.showMessageDialog(frame,
										"Question Not Found.", "Error:",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if (rounds[Ques.category].type.equals("category"))// q.sub!=null
																				// &&
							// subQues!=null && bbb==0)
							{
								internalFrame2.setTitle("Question:");
								internalFrame2.updateUI();
								textPane.setText(" ");
								textPane.updateUI();
								opt[0].setText(" ");
								opt[1].setText(" ");
								opt[2].setText(" ");
								opt[3].setText(" ");
								opt[0].setBackground(color);
								opt[1].setBackground(color);
								opt[2].setBackground(color);
								opt[3].setBackground(color);
								opt[0].updateUI();
								opt[1].updateUI();
								opt[2].updateUI();
								opt[3].updateUI();
								jd = new JDialog() {
									@Override
									public void setVisible(boolean b) {
										try {
											if (b == false)
												manager.removeKeyEventDispatcher(ked2);
											else
												manager.addKeyEventDispatcher(ked2);
										} catch (Exception e) {
										}
										super.setVisible(b);
									}
								};
								ked2 = new KeyEventDispatcher() {
									public boolean dispatchKeyEvent(KeyEvent e) {
										if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
											jd.setVisible(false);
										}
										return false;
									}
								};
								jd.setUndecorated(true);
								JPanel contentPane = new JPanel() {
									@Override
									protected void paintComponent(
											Graphics grphcs) {
										Graphics2D g2d = (Graphics2D) grphcs;
										g2d.setRenderingHint(
												RenderingHints.KEY_ANTIALIASING,
												RenderingHints.VALUE_ANTIALIAS_ON);
										GradientPaint gp = new GradientPaint(0,
												0, getBackground().brighter()
														.brighter(), 0,
												getHeight(), getBackground()
														.darker().darker());
										g2d.setPaint(gp);
										g2d.fillRect(0, 0, getWidth(),
												getHeight());
										super.paintComponent(grphcs);
									}
								};
								contentPane.setOpaque(false);
								jd.setContentPane(contentPane);
								contentPane.setLayout(new GridLayout((int) Math
										.ceil(Math.sqrt(subQues.length)),
										(int) Math.floor(Math
												.sqrt(subQues.length))));
								JButton jj[] = new JButton[subQues.length];
								for (int i = 0; i < jj.length; i++) {
									jj[i] = new JButton(
											subQues[i].split("=")[0]);
									jj[i].setPreferredSize(new Dimension(360,
											203));
									jj[i].addActionListener(var);
									jj[i].setActionCommand("cat:" + i);
									jj[i].setFont(new Font(
											"Lucida Sans Unicode", Font.BOLD,
											25));
									jj[i].setEnabled(Integer
											.parseInt(subQues[i].split("=")[1]
													.split(":")[0]) > 0 ? true
											: false);
									contentPane.add(jj[i]);
								}
								contentPane.setBorder(BorderFactory
										.createLineBorder(Color.black, 1));
								jd.pack();
								jd.setAlwaysOnTop(true);
								jd.setLocationRelativeTo(frame);
								com.sun.awt.AWTUtilities.setWindowOpacity(jd,
										Float.valueOf(0));
								jd.setVisible(true);
								ii = 0;
								alphaChanger = new Timer(70,
										new ActionListener() {

											private float incrementer = .10f;

											public void actionPerformed(
													ActionEvent e) {
												ii = ii + incrementer;
												com.sun.awt.AWTUtilities
														.setWindowOpacity(
																jd,
																Float.valueOf(ii));
												if (ii > ((float) 0.9)) {
													alphaChanger.stop();
													com.sun.awt.AWTUtilities.setWindowOpacity(
															jd,
															Float.valueOf(1));
												}
											}
										});
								alphaChanger.start();
							} else {
								bbb = 0;
								internalFrame2.setTitle("Question:   Level-"
										+ Ques.qno);
								internalFrame2.updateUI();
								textPane.setText(q.ques);
								textPane.updateUI();
								opt[0].setText(" ");
								opt[1].setText(" ");
								opt[2].setText(" ");
								opt[3].setText(" ");
								opt[0].setBackground(color);
								opt[1].setBackground(color);
								opt[2].setBackground(color);
								opt[3].setBackground(color);
								opt[0].updateUI();
								opt[1].updateUI();
								opt[2].updateUI();
								opt[3].updateUI();
								if (q.img != null)
									// label.setIcon(new
									// ImageIcon((q.img.getImage()).getScaledInstance(label.getWidth(),label.getHeight(),Image.SCALE_SMOOTH)));
									mediaPlayer
											.getMediaPlayer()
											.playMedia(
													new File(
															rounds[Ques.category].folder,
															q.img)
															.getAbsolutePath());
								if (rounds[Ques.category].type.equals("normal")) {
									sw.stopBtn.doClick();
									sw.reset(Integer
											.parseInt(rounds[Ques.category].time));
								}

								if (Boolean
										.parseBoolean(rounds[Ques.category].autostart)
										&& rounds[Ques.category].autostartAfter
												.equals("question"))
									sw.startBtn.doClick();

								k = 1;
								if (Boolean
										.parseBoolean(rounds[Ques.category].optionsAutoload)) {
									options.doClick();
								}
							}
						}
					});
				}
			};
			t.start();
		} else if (ac.equals("Prev Ques")) {
			k = 0;
			l = 0;
			try {
				t.stop();
				t3.stop();
				t4.stop();
			} catch (Exception mm) {
			}
			t = new Thread() {
				public void run() {
					try {
						t2.stop();
					} catch (Exception mm) {
					}
					try {
						q = Ques.readPreviousQues();
						mediaPlayer.getMediaPlayer().playMedia(background);
						// label.setIcon(new ImageIcon((new
						// ImageIcon("default.jpg").getImage()).getScaledInstance(label.getWidth(),label.getHeight(),Image.SCALE_SMOOTH)));
					} catch (Exception e) {
						JOptionPane.showMessageDialog(frame,
								"Question Not Found.", "Error:",
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}
					SwingUtilities.invokeLater(new Runnable() {
						@SuppressWarnings({ "serial", "restriction" })
						public void run() {
							if (q.ques == null) {
								JOptionPane.showMessageDialog(frame,
										"Question Not Found.", "Error:",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							if (rounds[Ques.category].type.equals("category")) {
								internalFrame2.setTitle("Question:");
								internalFrame2.updateUI();
								textPane.setText(" ");
								textPane.updateUI();
								opt[0].setText(" ");
								opt[1].setText(" ");
								opt[2].setText(" ");
								opt[3].setText(" ");
								opt[0].setBackground(color);
								opt[1].setBackground(color);
								opt[2].setBackground(color);
								opt[3].setBackground(color);
								opt[0].updateUI();
								opt[1].updateUI();
								opt[2].updateUI();
								opt[3].updateUI();
								jd = new JDialog() {
									@Override
									public void setVisible(boolean b) {
										try {
											if (b == false)
												manager.removeKeyEventDispatcher(ked2);
											else
												manager.addKeyEventDispatcher(ked2);
										} catch (Exception e) {
										}
										super.setVisible(b);
									}
								};
								ked2 = new KeyEventDispatcher() {
									public boolean dispatchKeyEvent(KeyEvent e) {
										if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
											jd.setVisible(false);
										}
										return false;
									}
								};
								jd.setUndecorated(true);
								JPanel contentPane = new JPanel() {
									@Override
									protected void paintComponent(
											Graphics grphcs) {
										Graphics2D g2d = (Graphics2D) grphcs;
										g2d.setRenderingHint(
												RenderingHints.KEY_ANTIALIASING,
												RenderingHints.VALUE_ANTIALIAS_ON);
										GradientPaint gp = new GradientPaint(0,
												0, getBackground().brighter()
														.brighter(), 0,
												getHeight(), getBackground()
														.darker().darker());
										g2d.setPaint(gp);
										g2d.fillRect(0, 0, getWidth(),
												getHeight());
										super.paintComponent(grphcs);
									}
								};
								contentPane.setOpaque(false);
								jd.setContentPane(contentPane);
								contentPane.setLayout(new GridLayout((int) Math
										.ceil(Math.sqrt(subQues.length)),
										(int) Math.floor(Math
												.sqrt(subQues.length))));
								JButton jj[] = new JButton[subQues.length];
								for (int i = 0; i < jj.length; i++) {
									jj[i] = new JButton(
											subQues[i].split("=")[0]);
									jj[i].setPreferredSize(new Dimension(360,
											203));
									jj[i].addActionListener(var);
									jj[i].setActionCommand("cat:" + i);
									jj[i].setFont(new Font(
											"Lucida Sans Unicode", Font.BOLD,
											25));
									jj[i].setEnabled(Integer
											.parseInt(subQues[i].split("=")[1]
													.split(":")[0]) > 0 ? true
											: false);
									contentPane.add(jj[i]);
								}
								contentPane.setBorder(BorderFactory
										.createLineBorder(Color.black, 1));
								jd.pack();
								jd.setAlwaysOnTop(true);
								jd.setLocationRelativeTo(frame);
								com.sun.awt.AWTUtilities.setWindowOpacity(jd,
										Float.valueOf(0));
								jd.setVisible(true);
								ii = 0;
								alphaChanger = new Timer(70,
										new ActionListener() {

											private float incrementer = .10f;

											public void actionPerformed(
													ActionEvent e) {
												ii = ii + incrementer;
												com.sun.awt.AWTUtilities
														.setWindowOpacity(
																jd,
																Float.valueOf(ii));
												if (ii > ((float) 0.9)) {
													alphaChanger.stop();
													com.sun.awt.AWTUtilities.setWindowOpacity(
															jd,
															Float.valueOf(1));
												}
											}
										});
								alphaChanger.start();
							} else {
								internalFrame2.setTitle("Question:   Level-"
										+ Ques.qno);
								internalFrame2.updateUI();
								textPane.setText(q.ques);
								textPane.updateUI();
								opt[0].setText(" ");
								opt[1].setText(" ");
								opt[2].setText(" ");
								opt[3].setText(" ");
								opt[0].setBackground(color);
								opt[1].setBackground(color);
								opt[2].setBackground(color);
								opt[3].setBackground(color);
								opt[0].updateUI();
								opt[1].updateUI();
								opt[2].updateUI();
								opt[3].updateUI();
								if (q.img != null)
									// label.setIcon(new
									// ImageIcon((q.img.getImage()).getScaledInstance(label.getWidth(),label.getHeight(),Image.SCALE_SMOOTH)));
									mediaPlayer
											.getMediaPlayer()
											.playMedia(
													new File(
															rounds[Ques.category].folder,
															q.img)
															.getAbsolutePath());
								if (rounds[Ques.category].type.equals("normal")) {
									sw.stopBtn.doClick();
									sw.reset(Integer
											.parseInt(rounds[Ques.category].time));
								}
								k = 1;
								if (Boolean
										.parseBoolean(rounds[Ques.category].autostart)
										&& rounds[Ques.category].autostartAfter
												.equals("question"))
									sw.startBtn.doClick();

								if (Boolean
										.parseBoolean(rounds[Ques.category].optionsAutoload)) {
									options.doClick();
								}
							}
						}
					});
				}
			};
			t.start();
		} else if ((k == 1) && ac.equals("Show Options")) {
			t4 = new Thread() {
				public void run() {
					try {
						Thread.sleep(500);
					} catch (Exception cf) {
					}
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							opt[0].setBackground(color);
							opt[1].setBackground(color);
							opt[2].setBackground(color);
							opt[3].setBackground(color);
							opt[0].setText(q.opta);
							opt[0].updateUI();
							opt[1].updateUI();
							opt[2].updateUI();
							opt[3].updateUI();
							textPane.updateUI();
						}
					});
					// try{
					// Thread.sleep(100);
					// }catch(Exception cf){}
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							opt[1].setText(q.optb);
							opt[1].updateUI();
						}
					});
					// try{
					// Thread.sleep(100);
					// }catch(Exception cf){}
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							opt[2].setText(q.optc);
							opt[2].updateUI();
						}
					});
					// try{
					// Thread.sleep(100);
					// }catch(Exception cf){}
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							opt[3].setText(q.optd);
							opt[3].updateUI();
						}
					});
					k = 2;
					try {
						Thread.sleep(200);
					} catch (Exception cf) {
					}
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							if (Boolean
									.parseBoolean(rounds[Ques.category].autostart)
									&& rounds[Ques.category].autostartAfter
											.equals("options"))
								sw.startBtn.doClick();
						}
					});
				}
			};
			t4.start();
		} else if ((k == 2) && ac.equals("Show Answer")) {
			if (!rounds[Ques.category].type.equals("rapidfire"))
				sw.stopBtn.doClick();
			/*
			 * if(Ques.category==1) { Thread t5=new Thread(){ public void run()
			 * { try{ an=Ques.readAnswer(); }catch(Exception e) {
			 * JOptionPane.showMessageDialog
			 * (frame,"Answer Not Found.","Error:",JOptionPane
			 * .INFORMATION_MESSAGE); return; } SwingUtilities.invokeLater(new
			 * Runnable(){ public void run() {
			 * textPane.setText(an.ans+"\n"+an.des); textPane.updateUI(); } });
			 * } }; t5.start(); } else
			 */
			try {
				if (q.imgans != null)
					mediaPlayer.getMediaPlayer().playMedia(
							new File(rounds[Ques.category].folder, q.imgans)
									.getAbsolutePath());
				l = 0;
				opt[Integer.parseInt(q.ans) - 1].setBackground(Color.green
						.brighter().brighter());
				opt[Integer.parseInt(q.ans) - 1].updateUI();
				for (int i = 0; i < 4; i++) {
					if ((opt[i].getBackground() == yellow)
							&& i != (Integer.parseInt(q.ans) - 1)) {
						l++;
						opt[i].setBackground((Color.red).brighter().brighter());
						opt[i].updateUI();
						sound = new Sound();
						sound.newClip(wrongSound);
						sound.run();
					}
				}
				if (l == 0) {
					sound = new Sound();
					sound.newClip(correctSound);
					sound.run();
				}
			} catch (Exception m) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						textPane.setText(q.ans);
						textPane.updateUI();
					}
				});
			}
		} else if (isRounds(ac)) {
			internalFrame2.setTitle("Question:");
			internalFrame2.updateUI();
			textPane.setText(" ");
			textPane.updateUI();
			opt[0].setText(" ");
			opt[1].setText(" ");
			opt[2].setText(" ");
			opt[3].setText(" ");
			opt[0].setBackground(color);
			opt[1].setBackground(color);
			opt[2].setBackground(color);
			opt[3].setBackground(color);
			opt[0].updateUI();
			opt[1].updateUI();
			opt[2].updateUI();
			opt[3].updateUI();
			JButton jj = (JButton) ae.getSource();
			for (int i = 0; i < rounds.length; i++) {
				cat[i].setBackground(color);
				cat[i].updateUI();
			}
			lf1.setEnabled(true);
			lf2.setEnabled(true);
			jj.setBackground(color.darker());
			jj.updateUI();
			Ques.category = Integer.parseInt(ac);
			try {
				subQues = Ques.readCategories();
			} catch (Exception n) {
				subQues = null;
			}
			Ques.qno = 0;
		} else if ((k == 2) && ac.equals("5050")) {
			int g = Integer.parseInt(q.ans) - 1;
			opt[0].setBackground(color);
			opt[1].setBackground(color);
			opt[2].setBackground(color);
			opt[3].setBackground(color);
			int h = 0, l = 4;
			while (h < 2) {
				int i = ((int) (Math.random() * 100)) % 4;
				if (i != g && i != l) {
					opt[i].setText(" ");
					l = i;
					h++;
				}
			}
			opt[0].updateUI();
			opt[1].updateUI();
			opt[2].updateUI();
			opt[3].updateUI();
			lf1.setEnabled(false);
		} else if ((k == 2) && ac.equals("paf")) {
			opt[0].setBackground(color);
			opt[1].setBackground(color);
			opt[2].setBackground(color);
			opt[3].setBackground(color);
			opt[0].updateUI();
			opt[1].updateUI();
			opt[2].updateUI();
			opt[3].updateUI();
			sw.stopBtn.doClick();
			lf2.setEnabled(false);
		} else if (ac.startsWith("cat")) {
			jd.setVisible(false);
			new Thread() {
				// boolean bonus = false;

				public void run() {
					/*
					 * if(Integer.parseInt(subQues[Integer.parseInt(ac.split(":")
					 * [1])].split("=")[1])>1) { bonus=true;
					 * mediaPlayer.getMediaPlayer().playMedia("bonus.mp4"); try{
					 * Thread.sleep(10000); }catch(Exception mm){}
					 * mediaPlayer.getMediaPlayer().playMedia("default.jpg"); }
					 */
					String cc = subQues[Integer.parseInt(ac.split(":")[1])]
							.split("=")[0];
					Ques.qno = 0;
					int lko = Integer.parseInt(subQues[Integer.parseInt(ac
							.split(":")[1])].split("=")[1].split(":")[1]);
					subQues[Integer.parseInt(ac.split(":")[1])] = subQues[Integer
							.parseInt(ac.split(":")[1])].split("=")[0]
							+ "="
							+ (Integer
									.parseInt(subQues[Integer.parseInt(ac
											.split(":")[1])].split("=")[1]
											.split(":")[0]) - 1)
							+ ":"
							+ (lko + 1);
					// System.out.println(subQues[Integer.parseInt(ac.split(":")[1])]);
					bon = null;
					bbb = 0;
					while (true) {
						try {
							q = Ques.readNextQues();
							if (q.sub.equals(cc)) {
								if (bbb == lko)
									break;
								bbb++;
							}
						} catch (Exception mm) {
							break;
						}
					}
					Ques.qno = 0;
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							if (q == null || q.ques == null) {
								JOptionPane.showMessageDialog(frame,
										"Question Not Found.", "Error:",
										JOptionPane.INFORMATION_MESSAGE);
								return;
							}
							{
								internalFrame2.setTitle("Question:");
								internalFrame2.updateUI();
								textPane.setText(q.ques);
								textPane.updateUI();
								opt[0].setText(" ");
								opt[1].setText(" ");
								opt[2].setText(" ");
								opt[3].setText(" ");
								opt[0].setBackground(color);
								opt[1].setBackground(color);
								opt[2].setBackground(color);
								opt[3].setBackground(color);
								opt[0].updateUI();
								opt[1].updateUI();
								opt[2].updateUI();
								opt[3].updateUI();
								if (q.img != null)
									// label.setIcon(new
									// ImageIcon((q.img.getImage()).getScaledInstance(label.getWidth(),label.getHeight(),Image.SCALE_SMOOTH)));
									mediaPlayer
											.getMediaPlayer()
											.playMedia(
													new File(
															rounds[Ques.category].folder,
															q.img)
															.getAbsolutePath());
								sw.stopBtn.doClick();
								sw.reset(Integer
										.parseInt(rounds[Ques.category].time));
								k = 1;
								if (Boolean
										.parseBoolean(rounds[Ques.category].autostart)
										&& rounds[Ques.category].autostartAfter
												.equals("question"))
									sw.startBtn.doClick();

								if (Boolean
										.parseBoolean(rounds[Ques.category].optionsAutoload)) {
									options.doClick();
								}
							}
						}
					});
				}
			}.start();
		}
	}

	private boolean isRounds(String ac) {
		try {
			int aa = Integer.parseInt(ac);
			if (aa >= 0 && aa < rounds.length)
				return true;
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	JButton makeButton(String name) {
		JButton temp = new JButton(name);
		return temp;
	}

	JButton makeButton(ImageIcon img) {
		JButton temp = new JButton(img);
		return temp;
	}

	JMenuBar makeMenu() {
		JMenuBar jmb = new JMenuBar();
		jmb.setBorder(new BevelBorder(BevelBorder.RAISED));
		JMenu fileMenu = new JMenu("Quiz Menu");
		JMenuItem optItem = new JMenuItem("Options");
		optItem.addActionListener(mal);
		fileMenu.add(optItem);
		JMenuItem abtItem = new JMenuItem("About");
		abtItem.addActionListener(mal);
		fileMenu.add(abtItem);
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(mal);
		fileMenu.add(exitItem);
		jmb.add(fileMenu);
		return jmb;
	}

	@SuppressWarnings("serial")
	JPanel getQuestionAndOptionsPanel() {
		JPanel j3 = new JPanel(new GridBagLayout()) {
			@Override
			protected void paintComponent(Graphics grphcs) {
				Graphics2D g2d = (Graphics2D) grphcs;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				GradientPaint gp = new GradientPaint(0, 0, getBackground()
						.brighter().brighter(), getWidth(), getHeight(),
						getBackground().darker().darker());
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(grphcs);
			}
		};
		j3.setOpaque(false);
		// ques=new JTextArea(3,20);
		// ques.setLineWrap(true);
		// ques.setWrapStyleWord(true);
		// ques.setFont(new Font("Lucida Sans Unicode",Font.BOLD,20));
		textPane = new JTextPane();
		textPane.setSize(120, 40);
		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		textPane.setFont(new Font("Lucida Sans Unicode", Font.BOLD, 23));
		textPane.setText("!! Welcome !!");
		textPane.setEditable(false);
		qq = new JScrollPane(textPane,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER) {
			@Override
			protected void paintComponent(Graphics grphcs) {
				Graphics2D g2d = (Graphics2D) grphcs;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				GradientPaint gp = new GradientPaint(0, 0, getBackground()
						.brighter().brighter(), 0, getHeight(), getBackground()
						.darker().darker());
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(grphcs);
			}
		};
		qq.setOpaque(false);
		// ques.setEditable(true);
		qq.setPreferredSize(textPane.getPreferredSize());
		GridBagConstraints tgrid;
		tgrid = new GridBagConstraints();
		tgrid.insets = new Insets(5, 5, 0, 5);
		tgrid.weightx = tgrid.weighty = 1.0;
		tgrid.fill = GridBagConstraints.BOTH;
		tgrid.gridx = 0;
		tgrid.gridy = 0;
		tgrid.gridwidth = 2;
		tgrid.gridheight = 1;
		j3.add(qq, tgrid);
		tgrid.gridwidth = 1;
		tgrid.weightx = 0.5;
		opt[0] = makeButton("A:");
		opt[0].setActionCommand("10");
		opt[1] = makeButton("B:");
		opt[1].setActionCommand("11");
		opt[2] = makeButton("C:");
		opt[2].setActionCommand("12");
		opt[3] = makeButton("D:");
		color = opt[3].getBackground();
		opt[3].setActionCommand("13");
		for (int i = 0; i < 4; i++) {
			opt[i].addActionListener(this);
			opt[i].setFont(new Font("Lucida Sans Unicode", Font.BOLD, 20));
		}
		tgrid.gridx = 0;
		tgrid.gridy = 1;
		j3.add(opt[0], tgrid);
		tgrid.gridx = 1;
		j3.add(opt[1], tgrid);
		tgrid.gridx = 0;
		tgrid.gridy = 2;
		j3.add(opt[2], tgrid);
		tgrid.gridx = 1;
		j3.add(opt[3], tgrid);
		return j3;
	}

	@SuppressWarnings("serial")
	JPanel menu() {
		JPanel jp = new JPanel(new GridBagLayout()) {
			@Override
			protected void paintComponent(Graphics grphcs) {
				Graphics2D g2d = (Graphics2D) grphcs;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				GradientPaint gp = new GradientPaint(0, 0, getBackground()
						.brighter().brighter(), getWidth(), getHeight(),
						getBackground().darker().darker());
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(grphcs);
			}
		};
		jp.setOpaque(false);
		GridBagConstraints tgrid;
		tgrid = new GridBagConstraints();
		tgrid.fill = GridBagConstraints.BOTH;
		tgrid.gridx = 0;
		tgrid.gridy = 0;
		tgrid.gridwidth = 2;
		cat = new JButton[rounds.length];

		int i;
		for (i = 0; i < rounds.length; i++) {
			cat[i] = makeButton(rounds[i].name);
		}

		for (i = 0; i < rounds.length; i++) {
			cat[i].addActionListener(this);
			cat[i].setActionCommand("" + i);
			cat[i].setFont(new Font(cat[i].getFont().getFontName(), Font.BOLD,
					15));
			tgrid.gridy = i;
			jp.add(cat[i], tgrid);
		}
		tgrid.anchor = GridBagConstraints.PAGE_END; // bottom of space
		tgrid.insets = new Insets(20, 0, 0, 0);
		prev = makeButton("Prev Ques");
		prev.setFont(new Font(prev.getFont().getFontName(), Font.BOLD, 15));
		next = makeButton("Next Ques");
		next.setFont(new Font(next.getFont().getFontName(), Font.BOLD, 15));
		tgrid.gridwidth = 1;
		tgrid.gridy = i + 1;
		jp.add(prev, tgrid);
		prev.addActionListener(this);
		tgrid.gridx = 1;
		jp.add(next, tgrid);
		next.addActionListener(this);
		tgrid.gridx = 0;
		tgrid.gridwidth = 2;
		tgrid.gridy = i + 2;
		options = makeButton("Show Options");
		options.setFont(new Font(prev.getFont().getFontName(), Font.BOLD, 15));
		jp.add(options, tgrid);
		options.addActionListener(this);
		tgrid.gridx = 0;
		tgrid.gridwidth = 2;
		tgrid.gridy = i + 3;
		tgrid.insets = new Insets(10, 0, 0, 0);
		answer = makeButton("Show Answer");
		answer.setFont(new Font(prev.getFont().getFontName(), Font.BOLD, 15));
		jp.add(answer, tgrid);
		answer.addActionListener(this);
		tgrid.gridx = 0;
		tgrid.gridwidth = 2;
		tgrid.gridy = i + 4;
		pass = makeButton("Pass Question");
		pass.setFont(new Font(prev.getFont().getFontName(), Font.BOLD, 15));
		jp.add(pass, tgrid);
		pass.addActionListener(this);
		pass.setEnabled(true);
		return jp;
	}

	void unmovable(JInternalFrame jif) {
		BasicInternalFrameUI ui = (BasicInternalFrameUI) jif.getUI();
		Component north = ui.getNorthPane();
		MouseMotionListener[] actions = (MouseMotionListener[]) north
				.getListeners(MouseMotionListener.class);
		for (int i = 0; i < actions.length; i++)
			north.removeMouseMotionListener(actions[i]);
	}

	@SuppressWarnings("serial")
	JPanel lifelines() {
		JPanel jp = new JPanel(new FlowLayout()) {
			@Override
			protected void paintComponent(Graphics grphcs) {
				Graphics2D g2d = (Graphics2D) grphcs;
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
						RenderingHints.VALUE_ANTIALIAS_ON);
				GradientPaint gp = new GradientPaint(0, 0, getBackground()
						.brighter().brighter(), getWidth(), getHeight(),
						getBackground().darker().darker());
				g2d.setPaint(gp);
				g2d.fillRect(0, 0, getWidth(), getHeight());
				super.paintComponent(grphcs);
			}
		};
		jp.setOpaque(false);
		lf1 = new JButton(new ImageIcon(
				new File(resourcesLocation, "5050.gif").getAbsolutePath()));
		lf2 = new JButton(new ImageIcon(
				new File(resourcesLocation, "paf.gif").getAbsolutePath()));
		lf1.setEnabled(false);
		lf2.setEnabled(false);
		jp.add(lf1);
		jp.add(lf2);
		lf1.setActionCommand("5050");
		lf2.setActionCommand("paf");
		lf1.addActionListener(this);
		lf2.addActionListener(this);
		return jp;
	}

	KeyboardFocusManager manager;

	String quizName, quizFolder, quizOrganiser;
	String splashscreenFile, splashscreenTimeout;
	String framewidth, frameheight;
	String lifelinesEnabled;
	String background;
	static File baseLocation, resourcesLocation, quizBase;
	String startAnimationEnabled, startAnimationFile;
	String soundsEnabled, correctSound, wrongSound;

	class Round {
		String name, type, file, folder;
		String optionsAutoload;
		String timerEnabled, time, passingTime, autostart, timeout,
				autostartAfter;
		String passingEnabled;
		String categoryFile;
	}

	static Round rounds[];

	void init() throws URISyntaxException, ParserConfigurationException,
			SAXException, IOException {
		Ques.category = -1;
		Ques.qno = -1;
		manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		var = this;
		LookAndFeel.set();

		File base = new File(CustomQuiz.class.getProtectionDomain()
				.getCodeSource().getLocation().toURI()).getParentFile();
		baseLocation = base;
		resourcesLocation = new File(base, "resources");
		System.out.println(base.getAbsolutePath() + "\n"
				+ new File(base, "lib").getAbsolutePath() + "\njdk version:  "
				+ System.getProperty("sun.arch.data.model") + " bits.");
		System.setProperty("jna.library.path",
				new File(base, "lib").getAbsolutePath());
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), // "C:/Program Files (x86)/VideoLAN/VLC");
				new File(base, "VLC").getAbsolutePath());
		Native.loadLibrary(RuntimeUtil.getLibVlcLibraryName(), LibVlc.class);

		File confg = new File(base, "confg.xml");
		System.out.println(confg.getAbsolutePath());
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(confg);
		doc.getDocumentElement().normalize();
		Element e = (Element) doc.getElementsByTagName("quiz").item(0);
		quizName = e.getAttribute("name");
		quizFolder = e.getAttribute("location");
		quizOrganiser = e.getAttribute("organiser");
		framewidth = e.getAttribute("framewidth");
		frameheight = e.getAttribute("frameheight");

		quizBase = new File(base, quizFolder);

		e = (Element) doc.getElementsByTagName("splashscreen").item(0);
		splashscreenFile = new File(quizBase, e.getAttribute("file"))
				.getAbsolutePath();
		System.out.println(splashscreenFile);
		splashscreenTimeout = e.getAttribute("timeout");

		e = (Element) doc.getElementsByTagName("background").item(0);
		background = new File(quizBase, e.getAttribute("file"))
				.getAbsolutePath();

		e = (Element) doc.getElementsByTagName("startanimation").item(0);
		startAnimationEnabled = e.getAttribute("enabled");
		startAnimationFile = new File(quizBase, e.getAttribute("file"))
				.getAbsolutePath();

		e = (Element) doc.getElementsByTagName("sounds").item(0);
		soundsEnabled = e.getAttribute("enabled");
		correctSound = new File(resourcesLocation, e.getAttribute("correct"))
				.getAbsolutePath();
		wrongSound = new File(resourcesLocation, e.getAttribute("wrong"))
				.getAbsolutePath();

		NodeList nl = doc.getElementsByTagName("round");
		rounds = new Round[nl.getLength()];
		for (int i = 0; i < nl.getLength(); i++) {
			e = (Element) nl.item(i);
			rounds[i] = new Round();
			// System.out.println(e.toString()+""+nl.getLength());
			rounds[i].name = e.getAttribute("name");
			rounds[i].type = e.getAttribute("type");
			rounds[i].folder = new File(quizBase, e.getAttribute("folder"))
					.getAbsolutePath();
			File roundBase = new File(rounds[i].folder);
			rounds[i].file = new File(roundBase, e.getAttribute("file"))
					.getAbsolutePath();

			Element e1 = (Element) e.getElementsByTagName("options").item(0);
			rounds[i].optionsAutoload = e1.getAttribute("autoload");

			e1 = (Element) e.getElementsByTagName("timer").item(0);
			rounds[i].autostart = e1.getAttribute("autostart");
			rounds[i].time = e1.getAttribute("time");
			rounds[i].timerEnabled = e1.getAttribute("enabled");
			rounds[i].passingTime = e1.getAttribute("passing-time");
			rounds[i].timeout = e1.getAttribute("start-timeout");
			rounds[i].autostartAfter = e1.getAttribute("autostart-after");

			e1 = (Element) e.getElementsByTagName("passing").item(0);
			rounds[i].passingEnabled = e1.getAttribute("enabled");
		}

		lifelinesEnabled = "false";
	}

	void main() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}

		new Splash(splashscreenFile, Integer.parseInt(splashscreenTimeout))
				.start();

		try {
			Thread.sleep(2500);
		} catch (Exception e) {
		}

		mal = new MenuActListener(this);
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@SuppressWarnings("serial")
				public void run() {
					frame = new JFrame(quizName + " - " + quizOrganiser
							+ ", Developed by Ayush Jain");
					frame.addWindowListener(new BasicWindowMonitor());
					frame.setUndecorated(true);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setContentPane(new JPanel() {
						@Override
						protected void paintComponent(Graphics grphcs) {
							Graphics2D g2d = (Graphics2D) grphcs;
							g2d.setRenderingHint(
									RenderingHints.KEY_ANTIALIASING,
									RenderingHints.VALUE_ANTIALIAS_ON);
							GradientPaint gp = new GradientPaint(0, 0,
									getBackground().brighter().brighter(),
									getWidth(), getHeight(), getBackground()
											.darker().darker());
							g2d.setPaint(gp);
							g2d.fillRect(0, 0, getWidth(), getHeight());
							super.paintComponent(grphcs);
						}
					});

					opt = new JButton[4];

					mediaPlayer = new EmbeddedMediaPlayerComponent();

					frame.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosing(WindowEvent evt) {
							mediaPlayer.release();
							System.exit(0);
						}
					});

					frame.setJMenuBar(makeMenu());

					JPanel j4 = (JPanel) frame.getContentPane();
					j4.setLayout(new GridBagLayout());
					j4.setOpaque(false);

					sw = new StopWatch();
					sw.launchStopWatch();

					GridBagConstraints mgrid = new GridBagConstraints();
					mgrid.fill = GridBagConstraints.BOTH;

					mgrid.gridx = 0;
					mgrid.gridy = 0;
					mgrid.gridwidth = 1;
					mgrid.gridheight = 2;
					internalFrame4 = new JInternalFrame("Categories", false,
							false, false, false);
					internalFrame4.setContentPane(menu());
					internalFrame4.pack();
					internalFrame4.setVisible(true);
					unmovable(internalFrame4);
					j4.add(internalFrame4, mgrid);

					mgrid.gridx = 3;
					mgrid.gridy = 0;
					mgrid.gridwidth = 1;
					mgrid.gridheight = 1;
					internalFrame = new JInternalFrame("Timer", false, false,
							false, false);
					internalFrame.setSize(sw.getSize());
					internalFrame.setContentPane(sw);
					internalFrame.setVisible(true);
					unmovable(internalFrame);
					j4.add(internalFrame, mgrid);

					mgrid.gridx = 1;
					mgrid.gridy = 0;
					mgrid.gridwidth = 2;
					mgrid.gridheight = 2;
					internalFrame3 = new JInternalFrame(quizName + " - "
							+ quizOrganiser + ", Developed by Ayush Jain",
							false, false, false, false);

					int width = Integer.parseInt(framewidth);
					int height = Integer.parseInt(frameheight);

					internalFrame3.setContentPane(mediaPlayer);
					internalFrame3.setSize(width, height);
					internalFrame3
							.setPreferredSize(new Dimension(width, height));
					// internalFrame3.pack();
					internalFrame3.setVisible(true);
					unmovable(internalFrame3);
					j4.add(internalFrame3, mgrid);

					mgrid.gridx = 0;
					mgrid.gridy = 2;
					mgrid.gridwidth = 4;
					mgrid.gridheight = 1;
					internalFrame2 = new JInternalFrame("Questions", false,
							false, false, false);
					internalFrame2.setContentPane(getQuestionAndOptionsPanel());
					internalFrame2.pack();
					internalFrame2.setVisible(true);
					unmovable(internalFrame2);
					j4.add(internalFrame2, mgrid);

					mgrid.gridx = 3;
					mgrid.gridy = 1;
					mgrid.gridwidth = 1;
					mgrid.gridheight = 1;
					internalFrame5 = new JInternalFrame("Lifelines", false,
							false, false, false);
					internalFrame5.setContentPane(lifelines());
					internalFrame5.pack();
					if (Boolean.parseBoolean(lifelinesEnabled))
						internalFrame5.setVisible(true);
					unmovable(internalFrame5);
					j4.add(internalFrame5, mgrid);

					frame.pack();

					Rectangle scrdim = GraphicsEnvironment
							.getLocalGraphicsEnvironment()
							.getMaximumWindowBounds();
					frame.setSize((int) scrdim.getWidth(),
							(int) scrdim.getHeight());
					// jd2.setLocation((scrdim.width-dim.width),(scrdim.height-dim.height));

					// Dimension
					// scrdim=Toolkit.getDefaultToolkit().getScreenSize();
					// Dimension dim=frame.getSize();
					frame.setLocation(0, 0);
					// frame.setLocation((scrdim.width-dim.width)/2,(scrdim.height-dim.height)/2);
					// frame.setResizable(false);

					ked = new KeyEventDispatcher() {
						public boolean dispatchKeyEvent(KeyEvent e) {
							/*
							 * if(e.getKeyCode()==KeyEvent.VK_1) {
							 * Ques.category2=0; if(Ques.category==3) {
							 * Ques.qno=0; sw.stopBtn.doClick();
							 * sw.resetBtn1.doClick(); } } else
							 * if(e.getKeyCode()==KeyEvent.VK_2) {
							 * Ques.category2=1; if(Ques.category==3) {
							 * Ques.qno=0; sw.stopBtn.doClick();
							 * sw.resetBtn1.doClick(); } } else
							 * if(e.getKeyCode()==KeyEvent.VK_3) {
							 * Ques.category2=2; if(Ques.category==3) {
							 * Ques.qno=0; sw.stopBtn.doClick();
							 * sw.resetBtn1.doClick(); } } else
							 * if(e.getKeyCode()==KeyEvent.VK_4) {
							 * Ques.category2=3; if(Ques.category==3) {
							 * Ques.qno=0; sw.stopBtn.doClick();
							 * sw.resetBtn1.doClick(); } } else
							 * if(e.getKeyCode()==KeyEvent.VK_5) {
							 * Ques.category2=4; if(Ques.category==3) {
							 * Ques.qno=0; sw.stopBtn.doClick();
							 * sw.resetBtn1.doClick(); } } else
							 * if(e.getKeyCode()==KeyEvent.VK_6) {
							 * Ques.category2=5; if(Ques.category==3) {
							 * Ques.qno=0; sw.stopBtn.doClick();
							 * sw.resetBtn1.doClick(); } } else
							 */if (e.getKeyCode() == KeyEvent.VK_A) {
								sound = new Sound();
								sound.newClip(correctSound);
								sound.run();
							} else if (e.getKeyCode() == KeyEvent.VK_S) {
								sound = new Sound();
								sound.newClip(wrongSound);
								sound.run();
							} else if (e.getKeyCode() == KeyEvent.VK_B) {
								if (Boolean.parseBoolean(startAnimationEnabled)) {
									mediaPlayer.getMediaPlayer().playMedia(
											startAnimationFile);
								}
							}

							return false;
						}
					};
					manager.addKeyEventDispatcher(ked);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame.setVisible(true);
		try {
			internalFrame.setSelected(true);
			internalFrame5.setSelected(true);
			internalFrame2.setSelected(true);
			internalFrame3.setSelected(true);
			internalFrame4.setSelected(true);
		} catch (Exception f) {
		}
		if (Boolean.parseBoolean(startAnimationEnabled)) {
			mediaPlayer.getMediaPlayer().playMedia(startAnimationFile);
		} else {
			mediaPlayer.getMediaPlayer().playMedia(background);
		}
	}
}