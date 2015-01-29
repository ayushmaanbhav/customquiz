import javax.sound.sampled.*;
import java.io.*;

class Sound extends Thread {
	boolean D = true;
	float vol = 60;
	String str;
	Clip clip;
	AudioInputStream audioInputStream;
	FloatControl gainControl;

	void newClip(String s) {
		str = s;
		File soundFile = new File(str);
		if (!soundFile.exists()) {
			return;
		}
		AudioInputStream audioInputStream;
		try {
			audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			gainControl = (FloatControl) clip
					.getControl(FloatControl.Type.MASTER_GAIN);
			volume((double) vol);
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
			return;
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
	}

	public void run() {
		if (D == true)
			clip.start();
	}

	public void volume(double gain) { // gain number between 0 and 100 (loudest)
		float dB = (float) (Math.log(gain / 100) / Math.log(10.0) * 20.0);
		if (gainControl != null)
			gainControl.setValue(dB); // Reduce volume by 10 decibels.
		vol = dB;
	}
}