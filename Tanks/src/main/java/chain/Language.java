package chain;

import client.Main;

public abstract class Language {
	protected Language next;

	public Language(final Language n) {
		next = n;
	}

	public abstract void showHelp(final int l);

	protected void show(final String text) {
		Main.self.pushStyle();
		Main.self.textSize(60);
		Main.self.textAlign(Main.CENTER, Main.CENTER);
		Main.self.text(text, 0, 0, Main.D, Main.D);
		Main.self.popStyle();
	}
}
