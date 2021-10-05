package utils;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Stream;

// https://commons.apache.org/proper/commons-pool/apidocs/org/apache/commons/pool2/ObjectPool.html
// https://en.wikipedia.org/wiki/Object_pool_pattern
public abstract class ObjectPool {
	protected ObjectPool() {
		throw new AssertionError();
	}

	protected static class Pool<T> {
		public static final int DEFAULT_SIZE = 8;

		protected final Set<T> available, in_use;
		protected final Supplier<T> supplier;

		public Pool(final Supplier<T> s, final int count) {
			available = Collections.newSetFromMap(new IdentityHashMap<>());
			in_use = Collections.newSetFromMap(new IdentityHashMap<>());
			supplier = () -> Objects.requireNonNull(s.get());
			addObjects(Math.max(count, DEFAULT_SIZE));
		}

		public void clear() {
			available.clear();
		}

		public void addObjects(final int count) {
			if (!Stream.generate(supplier).limit(count).allMatch(available::add))
				throw new IllegalArgumentException();
		}

		public T borrowObject() {
			if (available.isEmpty()) {
				final T t = supplier.get();
				if (!in_use.add(t))
					throw new IllegalArgumentException();
				return t;
			} else {
				final T t = available.iterator().next();
				if (!in_use.add(t))
					throw new IllegalArgumentException();
				available.remove(t);
				return t;
			}
		}

		public void returnObject(final Object t) {
			invalidateObject(t);
			available.add((T)t);
		}

		public void invalidateObject(final Object t) {
			if (!in_use.remove(t))
				throw new IllegalArgumentException();
		}
	}

	protected static final Map<Class<?>, Pool<?>> pool_map = new IdentityHashMap<>();

	public static <T> void register(final Class<T> c, final Supplier<T> supplier) {
		register(c, supplier, Pool.DEFAULT_SIZE);
	}

	public static <T> void register(final Class<T> c, final Supplier<T> supplier, final int count) {
		pool_map.put(c, new Pool<>(supplier, count));
	}

	public static <T> void clear(final Class<T> c) {
		pool_map.get(c).clear();
	}

	public static <T> void addObject(final Class<T> c) {
		addObjects(c, 1);
	}

	public static <T> void addObjects(final Class<T> c, final int count) {
		pool_map.get(c).addObjects(count);
	}

	public static <T> T borrowObject(final Class<T> c) {
		return (T)pool_map.get(c).borrowObject();
	}

	public static <T> void returnObject(final Class<T> c, final T t) {
		pool_map.get(c).returnObject(t);
	}

	public static <T> void invalidateObject(final Class<T> c, final T t) {
		pool_map.get(c).invalidateObject(t);
	}
}
