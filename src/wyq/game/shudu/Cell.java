package wyq.game.shudu;

import java.util.HashSet;
import java.util.Set;

public class Cell {

	public int value;

	public Set<Integer> possibleSet = new HashSet<Integer>();

	public Group hGroup;
	public Group vGroup;
	public Group sGroup;

	public int x;
	public int y;

	public Cell() {
		initPossSet();
	}

	private void initPossSet() {
		possibleSet.clear();
		for (int i = 0; i < 9; i++) {
			possibleSet.add(i + 1);
		}
	}

	public void calcPossible() {
		if (value != 0) {
			possibleSet.clear();
			return;
		} else {
			initPossSet();
		}
		removeByGroup(hGroup);
		removeByGroup(vGroup);
		removeByGroup(sGroup);
	}

	private void removeByGroup(Group g) {
		for (Cell c : g) {
			if (c.equals(this)) {
				continue;
			}
			if (c.value == 0)
				continue;
			if (possibleSet.contains(c.value)) {
				possibleSet.remove(c.value);
			}
		}
	}

	@Override
	public String toString() {
		if (value == 0)
			return possibleSet.toString();
		else
			return String.valueOf(value);
	}
}
