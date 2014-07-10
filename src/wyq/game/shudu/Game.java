package wyq.game.shudu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import wyq.appengine.component.file.TextFile;

public class Game implements Iterable<Cell> {

	public Group[] hGroups = new Group[9];
	public Group[] vGroups = new Group[9];
	public Group[] sGroups = new Group[9];

	private List<Cell> allCells = new ArrayList<Cell>();

	public int[] allPossibleNum = { 9, 9, 9, 9, 9, 9, 9, 9, 9 };

	public Game(int[][] arg) {
		init(arg);
		calc();
	}

	private void init(int[][] arg) {
		fillGroups(hGroups);
		fillGroups(vGroups);
		fillGroups(sGroups);
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				Cell c = new Cell();
				c.value = arg[x][y];

				c.vGroup = vGroups[x];
				c.vGroup.add(c);
				c.hGroup = hGroups[y];
				c.hGroup.add(c);

				int sx = x / 3 + y / 3 * 3;
				c.sGroup = sGroups[sx];
				c.sGroup.add(c);

				c.x = x;
				c.y = y;

				allCells.add(c);
			}
		}
	}

	private void fillGroups(Group[] groups) {
		for (int i = 0; i < groups.length; i++) {
			groups[i] = new Group();
		}
	}

	@Override
	public Iterator<Cell> iterator() {
		return allCells.iterator();
	}

	public Cell get(int x, int y) {
		int index = x + y * 9;
		return allCells.get(index);
	}

	public boolean isValid() {
		for (Group g : hGroups) {
			if (!g.isValid())
				return false;
		}
		for (Group g : vGroups) {
			if (!g.isValid())
				return false;
		}
		for (Group g : sGroups) {
			if (!g.isValid())
				return false;
		}
		return true;
	}

	public boolean isSolved() {
		if (!isValid())
			return false;
		for (Cell c : this) {
			if (c.value == 0)
				return false;
		}
		return true;
	}

	public void calc() {
		boolean reCalc;
		do {
			reCalc = false;
			for (Cell c : this) {
				c.calcPossible();
				List<Integer> ns = getPossibleNum(c);
				if (ns.size() == 1) {
					c.value = ns.get(0);
					allPossibleNum[c.value - 1] -= 1;
					reCalc = true;
				}
			}
		} while (reCalc);
	}

	public List<Integer> getPossibleNum(Cell c) {
		List<Integer> r = new ArrayList<Integer>();
		for (int n : c.possibleSet) {
			if (allPossibleNum[n - 1] > 0) {
				r.add(n);
			}
		}
		return r;
	}

	@Override
	public String toString() {
		int maxLength = 0;
		for (Cell c : this) {
			int len = c.toString().length();
			if (len > maxLength)
				maxLength = len;
		}
		StringBuilder sb = new StringBuilder();
		sb.append("  ");
		for (int i = 0; i < 9; i++) {
			String s = String.valueOf(i);
			while (s.length() < maxLength) {
				s = s.concat(" ");
			}
			sb.append(s);
			if (i != 8) {
				sb.append(",");
			}
		}
		sb.append(TextFile.LINE_SEP);
		for (int y = 0; y < 9; y++) {
			sb.append(y + "[");
			for (int x = 0; x < 9; x++) {
				Cell c = get(x, y);
				String cstr = c.toString();
				while (cstr.length() < maxLength) {
					cstr = cstr.concat(" ");
				}
				sb.append(cstr);
				if (x != 8)
					sb.append(",");
			}
			sb.append("]" + TextFile.LINE_SEP);
		}
		return sb.toString();
	}

	public Possible[] getPossibilities() {
		List<Possible> list = new ArrayList<Possible>();
		for (Cell c : this) {
			if (c.value == 0) {
				for (int n : c.possibleSet) {
					Possible e = new Possible();
					e.value = n;
					e.x = c.x;
					e.y = c.y;
					list.add(e);
				}
			}
		}
		Possible[] arr = new Possible[list.size()];
		return list.toArray(arr);
	}

	public int[][] toArray() {
		int[][] data = new int[9][9];
		for (int y = 0; y < 9; y++) {
			for (int x = 0; x < 9; x++) {
				data[x][y] = get(x, y).value;
			}
		}
		return data;
	}
}
