package Tank_Game.Patterns.Iterator;

import Tank_Game.Patterns.Command.Invoker;
import Tank_Game.Patterns.Decorator.Decorator;
import java.util.Iterator;

public class EnemiesContainer implements Iterable<Decorator> {
	private Invoker[] enemies = new Invoker[1];
	private int size = 0;

	public Invoker get(final int index) {
		return enemies[index];
	}

	public void remove(final int index) {
		System.arraycopy(enemies, index + 1, enemies, index, --size - index);
	}

	public void add(final Invoker enem) {
		if (size == enemies.length) {
			resizeArray();
		}
		enemies[size++] = enem;
	}

	public void resizeArray() {
		final Invoker[] enem = new Invoker[enemies.length + 10];
		System.arraycopy(enemies, 0, enem, 0, enemies.length);
		enemies = enem;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public ContainerIterator iterator() {
		return new ContainerIterator(this);
	}

	private static class ContainerIterator implements Iterator<Decorator> {
		private final EnemiesContainer enemies;
		private int i = 0;

		public ContainerIterator(final EnemiesContainer e) {
			enemies = e;
		}

		@Override
		public boolean hasNext() {
			return i != enemies.size();
		}

		@Override
		public Decorator next() {
			return enemies.get(i++).currentDecorator();
		}

		@Override
		public void remove() {
			enemies.remove(--i);
		}
	}
}
