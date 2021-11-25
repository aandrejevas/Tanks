package chain;

public class French extends Language {
	public static final int FR = 3;

	public French(final Language n) {
		super(n);
	}

	@Override
	public void showHelp(final int l) {
		switch (l) {
			case FR:
				show("Merci");
				return;
			default:
				next.showHelp(l);
				return;
		}
	}
}
