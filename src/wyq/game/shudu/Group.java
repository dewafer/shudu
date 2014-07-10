package wyq.game.shudu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Group implements Iterable<Cell> {

	private List<Cell> cells = new ArrayList<Cell>();

	@Override
	public Iterator<Cell> iterator() {
		return cells.iterator();
	}

	public boolean add(Cell e) {
		return cells.add(e);
	}

	public boolean isValid() {
		for (int i = 0; i < cells.size(); i++) {
			Cell c = cells.get(i);
			if (c.value == 0 && c.possibleSet.size() == 0)
				return false;
			if (c.value == 0)
				continue;
			for (int j = i + 1; j < cells.size(); j++) {
				if (c.value == cells.get(j).value)
					return false;
			}
		}
		return true;
	}
}
