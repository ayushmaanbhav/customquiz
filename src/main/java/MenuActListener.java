import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

class MenuActListener implements ActionListener, ChangeListener, ItemListener {
	JSlider slider;
	JDialog jd = null;
	int value = 25;
	CustomQuiz k;

	public MenuActListener(CustomQuiz k) {
		this.k = k;
	}

	public void actionPerformed(ActionEvent e) {
		String choice = e.getActionCommand();
		if (choice == "Exit") {
			System.exit(0);
		} else if (choice == "Options") {
			if (jd == null) {
				int w = 250, h = 150;
				jd = new JDialog(k.frame, "Options: Volume");
				Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
				int X = (screen.width / 2) - (w / 2);
				int Y = (screen.height / 2) - (h / 2);
				jd.setBounds(X, Y, w, h);
				jd.setResizable(false);
				JButton ok, cancel;

				ok = new JButton("Ok");
				ok.addActionListener(this);
				cancel = new JButton("Cancel");
				cancel.addActionListener(this);

				slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 25);
				slider.setDoubleBuffered(true);
				slider.setMinorTickSpacing(2);
				slider.setMajorTickSpacing(10);
				slider.setPaintTicks(true);
				slider.setPaintLabels(true);
				slider.setLabelTable(slider.createStandardLabels(10));
				slider.addChangeListener(this);

				JCheckBox jc1 = new JCheckBox("Mute", false);
				jc1.setDoubleBuffered(true);
				jc1.addItemListener(this);
				JCheckBox jc2 = new JCheckBox("Enable Sound", true);
				jc2.setDoubleBuffered(true);
				jc2.addItemListener(this);

				JPanel as = new JPanel();
				as.add(jc1, BorderLayout.SOUTH);
				as.add(jc2);
				as.add(slider);
				as.add(ok);
				as.add(cancel);

				jd.add(as);

				jd.setVisible(true);
			} else if (jd.isVisible() == false) {
				jd.setVisible(true);
			}
		} else if (choice == "About") {
			JOptionPane.showMessageDialog(k.frame,
					"Designed and Created By:  Ayush Jain 1101CS09", "About",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (choice == "Ok" || choice == "Cancel")
			jd.setVisible(false);
	}

	public void stateChanged(ChangeEvent evt) {
		JSlider slider = (JSlider) evt.getSource();
		// if ((!slider.getValueIsAdjusting())&&jc1.isSelected()==false)
		{
			value = slider.getValue();
			k.sound.volume(value);
		}
	}

	public void itemStateChanged(ItemEvent e) {
		JCheckBox h = (JCheckBox) e.getSource();
		if (e.getStateChange() == ItemEvent.SELECTED && h.getText() == "Mute") {
			k.sound.volume(0);
		} else if (e.getStateChange() == ItemEvent.DESELECTED
				&& h.getText() == "Mute") {
			k.sound.volume(value);
		} else if (e.getStateChange() == ItemEvent.SELECTED
				&& h.getText() == "Enable Sound") {
			k.sound.D = true;
		} else if (e.getStateChange() == ItemEvent.DESELECTED
				&& h.getText() == "Enable Sound") {
			if (k.sound.clip != null)
				k.sound.clip.stop();
			k.sound.D = false;
		}
	}
}
