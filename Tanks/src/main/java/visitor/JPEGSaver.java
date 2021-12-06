package visitor;

public class JPEGSaver implements SaverVisitor {
	@Override
	public void visit(final Drawer drawer) {
		drawer.draw();
		SaverVisitor.save("jpg");
	}
}
