package visitor;

import client.Main;
import java.io.File;

public interface SaverVisitor {
	void visit(final Drawer drawer);

	public static void save(final String format) {
		Main.self.save(String.format("output%c%d%d%d_%d%d%d.%s", File.separatorChar,
			Main.year(), Main.month(), Main.day(), Main.hour(), Main.minute(), Main.second(), format));
	}
}
