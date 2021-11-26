package chain;

public class French extends Language {
	public static final int FR = 1 << 3;

	public French(final Language n) {
		super(n);
	}

	@Override
	public void showHelp(final int l) {
		if ((l & FR) != 0) {
			if (!text.isEmpty()) text.append(System.lineSeparator());
			text.append("Merci");
		}
		next.showHelp(l);
	}
}
