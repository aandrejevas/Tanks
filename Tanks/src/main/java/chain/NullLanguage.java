package chain;

import client.Main;

public class NullLanguage extends Language {

	public NullLanguage() {
		super(null);
	}

	@Override
	public void showHelp(final int l) {
		Main.self.pushStyle();
		Main.self.textSize(60);
		Main.self.textAlign(Main.CENTER, Main.CENTER);
		Main.self.text(text.toString(), 0, 0, Main.D, Main.D);
		Main.self.popStyle();
		text.setLength(0);
	}
}
