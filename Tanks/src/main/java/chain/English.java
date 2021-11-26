package chain;

public class English extends Language {
	public static final int EN = 1 << 1;

	public English(final Language n) {
		super(n);
	}

	@Override
	public void showHelp(final int l) {
		if ((l & EN) != 0) {
			if (!text.isEmpty()) text.append(System.lineSeparator());
			text.append("Thanks");
		}
		next.showHelp(l);
	}
}
