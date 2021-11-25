package chain;

public class Russian extends Language {
	public static final int RU = 2;

	public Russian(final Language n) {
		super(n);
	}

	@Override
	public void showHelp(final int l) {
		switch (l) {
			case RU:
				show("Спасибо");
				return;
			default:
				next.showHelp(l);
				return;
		}
	}
}
