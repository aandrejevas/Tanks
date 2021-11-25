package chain;

public class Lithuanian extends Language {
	public static final int LT = 0;

	public Lithuanian(final Language n) {
		super(n);
	}

	@Override
	public void showHelp(final int l) {
		switch (l) {
			case LT:
				show("Ačiū");
				return;
			default:
				next.showHelp(l);
				return;
		}
	}
}
