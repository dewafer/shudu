package wyq.game.shudu;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import wyq.appengine.component.file.TextFile;

public class Shudu {

	private static List<int[][]> gameStack = new LinkedList<int[][]>();
	private static List<Set<Possible>> possibleStack = new LinkedList<Set<Possible>>();
	private static final int MAX_TRY = Integer.MAX_VALUE;
	private static TextFile logFile;
	private static BufferedWriter writer;
	private static SimpleDateFormat formatter;

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		int[][] arg = new int[9][9];
		String[] lines = new TextFile(Shudu.class, "4.txt").readAll().split(
				TextFile.LINE_SEP);
		logFile = new TextFile(Shudu.class, "output.log");
		if (!logFile.exists()) {
			logFile.createNewFile();
		}
		writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(logFile, true)));
		formatter = new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss.SSS]"
				+ TextFile.LINE_SEP);
		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				arg[x][y] = Integer.parseInt(lines[y].split(",")[x].trim());
			}
		}

		Game g = new Game(arg);
		printNow();
		println(g);
		solve(g);
		// println(g);
		writer.flush();
		writer.close();
	}

	public static int[][] peek() {
		return gameStack.get(gameStack.size() - 1);
	}

	public static int[][] pop() {
		int[][] r = gameStack.remove(gameStack.size() - 1);
		return r;
	}

	public static void solve(Game g) throws IOException {
		gameStack.add(g.toArray());
		boolean solved = g.isSolved();
		int tryCount = 0;
		Set<Possible> triedPossible = new HashSet<Possible>();
		possibleStack.add(triedPossible);
		while (!solved && tryCount < MAX_TRY) {
			tryCount++;
			if (gameStack.isEmpty())
				break;
			Game theGame = new Game(peek());
			triedPossible = possibleStack.get(possibleStack.size() - 1);
			theGame.calc();
			Possible[] ps = theGame.getPossibilities();
			if (ps.length == 0 || triedPossible.containsAll(Arrays.asList(ps))) {
				pop();
				possibleStack.remove(possibleStack.size() - 1);
				if (gameStack.isEmpty()) {
					break;
				}
				continue;
			}
			Iterator<Possible> itr = Arrays.asList(ps).iterator();
			while (itr.hasNext()) {
				Possible p = itr.next();
				if (triedPossible.contains(p))
					continue;
				theGame.get(p.x, p.y).value = p.value;
				theGame.allPossibleNum[p.value - 1] -= 1;
				theGame.calc();
				triedPossible.add(p);
				boolean valid = theGame.isValid();
				printNow();
				println(tryCount + ":" + p);
				println("valid:" + valid);
				println("triedPossible:" + triedPossible);
				println(theGame);
				if (valid) {
					solved = theGame.isSolved();
					if (!solved) {
						possibleStack.add(new HashSet<Possible>());
						gameStack.add(theGame.toArray());
					}
					break;
				} else {
					if (itr.hasNext()) {
						theGame = new Game(peek());
					} else {
						possibleStack.remove(possibleStack.size() - 1);
						pop();
					}
				}
			}
		}
	}

	public static void println(Object o) throws IOException {
		// Date now = Calendar.getInstance().getTime();
		// System.out.println(formatter.format(now) + o);
		// writer.write(formatter.format(now) + o.toString());
		// writer.write(TextFile.LINE_SEP);
		System.out.println(o);
		writer.write(o.toString());
		writer.write(TextFile.LINE_SEP);
	}

	public static void printNow() throws IOException {
		Date now = Calendar.getInstance().getTime();
		System.out.print(formatter.format(now));
		writer.write(formatter.format(now));
	}
}
