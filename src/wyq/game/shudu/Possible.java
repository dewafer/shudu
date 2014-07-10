package wyq.game.shudu;

public class Possible {

	public int x;
	public int y;
	public int value;

	@Override
	public String toString() {
		return "Possible [x=" + x + ", y=" + y + ", value=" + value + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + value;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Possible)) {
			return false;
		}
		Possible other = (Possible) obj;
		if (value != other.value) {
			return false;
		}
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}
}
