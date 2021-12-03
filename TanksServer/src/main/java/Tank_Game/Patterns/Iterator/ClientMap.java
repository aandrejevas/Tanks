package Tank_Game.Patterns.Iterator;

import Tank_Game.Patterns.Command.Invoker;
import Tank_Game.Patterns.Decorator.Decorator;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import processing.net.Client;

public class ClientMap implements Iterable<Map.Entry<Client, Decorator>> {

	private final Map<Client, Invoker> clients = new IdentityHashMap<>();

	public void add(final Client available_client, final Invoker invoker) {
		clients.put(available_client, invoker);
	}

	public boolean isMember(final Client client) {
		return clients.containsKey(client);
	}

	public Map<Client, Invoker> getIdentityHashMap() {
		return clients;
	}

	public Invoker get(final Object available_client) {
		return clients.get(available_client);
	}

	public int size() {
		return clients.size();
	}

	public Set<Map.Entry<Client, Invoker>> entrySet() {
		return clients.entrySet();
	}

	public Collection<Invoker> values() {
		return clients.values();
	}

	@Override
	public MapIterator iterator() {
		return new MapIterator(clients.entrySet());
	}

	private static class MapIterator implements Iterator<Map.Entry<Client, Decorator>> {
		private final Iterator<Map.Entry<Client, Invoker>> entries;

		public MapIterator(final Iterable<Map.Entry<Client, Invoker>> e) {
			entries = e.iterator();
		}

		@Override
		public boolean hasNext() {
			return entries.hasNext();
		}

		@Override
		public Map.Entry<Client, Decorator> next() {
			final Map.Entry<Client, Invoker> entry = entries.next();
			return Map.entry(entry.getKey(), entry.getValue().currentDecorator());
		}
	}
}
