package chain;

public class Russian extends Language {
	public static final int RU = 1 << 2;

	public Russian(final Language n) {
		super(n);
	}

	@Override
	public void showHelp(final int l) {
		if ((l & RU) != 0) {
			if (!text.isEmpty()) text.append(System.lineSeparator());
			text.append("Спасибо");
		}
		next.showHelp(l);
	}
}
