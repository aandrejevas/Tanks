/**
 * @(#) Drop.java
 */

package Tank_Game.Patterns.AbstractFactory;

public abstract class Drop
{
	private byte name;
	private int value;

	public Drop(byte name, int value) {
		this.name = name;
		this.value = value;
	}

	public byte getName() {
		return name;
	}

	public int getValue() {
		return value;
	}
}
