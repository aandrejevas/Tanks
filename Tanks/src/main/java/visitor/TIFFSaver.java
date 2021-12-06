package visitor;

public class TIFFSaver implements SaverVisitor {
	@Override
	public void visit(final Drawer drawer) {
		drawer.draw();
		SaverVisitor.save("tif");
	}
}
