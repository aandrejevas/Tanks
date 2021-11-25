package chain;

public class English extends Language {
	public static final int EN = 1;

	public English(final Language n) {
		super(n);
	}

	@Override
	public void showHelp(final int l) {
		switch (l) {
			case EN:
				show("Thanks");
				return;
			default:
				next.showHelp(l);
				return;
		}
	}
}
