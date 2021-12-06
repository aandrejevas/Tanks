package visitor;

public class PNGSaver implements SaverVisitor {
	@Override
	public void visit(final Drawer drawer) {
		drawer.draw();
		SaverVisitor.save("png");
	}
}
