import java.io.*;

class Ques {
	String ques, opta, optb, optc, optd, ans, sub;
	String img, imgans;
	static int category, category2, qno;

	public Ques(String q, String a, String b, String c, String d, String e,
			String ii, String f) {
		try {
			if (ii.equals(""))
				img = null;
			else
				img = new String(ii).split(":")[0];
		} catch (Exception ab) {
			img = null;
		}
		try {
			if (ii.equals(""))
				imgans = null;
			else
				imgans = ii.split(":")[1];
		} catch (Exception ab) {
			imgans = null;
		}
		try {
			if (f.equals(""))
				sub = null;
			else
				sub = new String(f);
		} catch (Exception ab) {
			sub = null;
		}
		ques = q;
		opta = a;
		optb = b;
		optc = c;
		optd = d;
		ans = e;
	}

	static Ques readNextQues() throws IOException {
		qno++;
		BufferedReader br;
		System.out.println(CustomQuiz.rounds[category].file);
		br = new BufferedReader(
				new FileReader(CustomQuiz.rounds[category].file));
		for (int i = 1; i <= (qno - 1) * 8; i++) {
			br.readLine();
		}
		Ques ques = new Ques(br.readLine(), br.readLine(), br.readLine(),
				br.readLine(), br.readLine(), br.readLine(), br.readLine(),
				br.readLine());
		br.close();
		return ques;
	}

	static Ques readPreviousQues() throws IOException {
		if (qno <= 1)
			return new Ques(null, null, null, null, null, null, null, null);
		qno--;
		BufferedReader br;
		br = new BufferedReader(
				new FileReader(CustomQuiz.rounds[category].file));
		for (int i = 1; i <= (qno - 1) * 8; i++) {
			br.readLine();
		}
		Ques ques = new Ques(br.readLine(), br.readLine(), br.readLine(),
				br.readLine(), br.readLine(), br.readLine(), br.readLine(),
				br.readLine());
		br.close();
		return ques;
	}

	static Answer readAnswer() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(
				"Visual Answers.txt"));
		for (int i = 1; i <= (qno - 1) * 2; i++) {
			br.readLine();
		}
		Answer anss = new Answer(br.readLine(), br.readLine());
		br.close();
		return anss;
	}

	static String[] readCategories() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(
					CustomQuiz.rounds[category].categoryFile));
			String t = "", a[] = null;
			a = br.readLine().split(":");
			br.close();
			int b[] = new int[a.length];
			for (int i = 0; i < b.length; i++) {
				b[i] = 0;
			}
			for (int i = 0; i < a.length; i++) {
				br = new BufferedReader(new FileReader(
						CustomQuiz.rounds[category].file));
				while ((t = br.readLine()) != null) {
					if (t.equals(a[i]))
						b[i]++;
				}
				a[i] += "=" + b[i] + ":0";
			}
			br.close();
			return a;
		} catch (Exception nn) {
			return null;
		}
	}
}

class Answer {
	String ans, des;

	public Answer(String a, String b) {
		ans = a;
		des = b;
	}
}