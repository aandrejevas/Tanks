package utils;

public abstract class Drop implements Drawable {
	private final byte name;
	private final int value;

	public Drop(final byte name, final int value) {
		this.name = name;
		this.value = value;
	}

	public byte getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	@Override
	public void draw() {
		throw new UnsupportedOperationException();
	}
}
