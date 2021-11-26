package chain;

public class Lithuanian extends Language {
	public static final int LT = 1;

	public Lithuanian(final Language n) {
		super(n);
	}

	@Override
	public void showHelp(final int l) {
		if ((l & LT) != 0) {
			if (!text.isEmpty()) text.append(System.lineSeparator());
			text.append("Ačiū");
		}
		next.showHelp(l);
	}
}
