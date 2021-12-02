package utils.Iterator;

import java.nio.CharBuffer;

public class FixedSizeCharList implements CharSequence, Iterable<Character> {
	private final char[] array;
	private int length;

	public FixedSizeCharList(final int capacity) {
		array = new char[capacity];
		length = 0;
	}

	@Override
	public CharSequence subSequence(final int start, final int end) {
		return CharBuffer.wrap(array, start, end - start);
	}

	@Override
	public char charAt(final int index) {
		return array[index];
	}

	@Override
	public int length() {
		return length;
	}

	@Override
	public CharIterator iterator() {
		return new Iterator(this);
	}

	private static class Iterator implements CharIterator {
		private final FixedSizeCharList list;
		private int index;

		public Iterator(final FixedSizeCharList l) {
			list = l;
			index = 0;
		}

		@Override
		public char nextChar() {
			return list.charAt(index++);
		}

		@Override
		public boolean hasNext() {
			return index != list.length();
		}
	}
}
