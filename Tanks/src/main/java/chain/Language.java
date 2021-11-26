package chain;

public abstract class Language {
	protected static final StringBuilder text = new StringBuilder();

	protected final Language next;

	public Language(final Language n) {
		next = n;
	}

	public abstract void showHelp(final int l);
}
