package Tank_Game.Patterns.Iterator;

import Tank_Game.Patterns.Command.Invoker;
import Tank_Game.Patterns.Decorator.Decorator;
import processing.net.Client;
import utils.Iterator.Iterate;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

public class ClientMap implements Iterate {

    private final Map<Client, Invoker> clients = new IdentityHashMap<>();

    public void add(Client available_client, Invoker invoker){
        clients.put(available_client, invoker);
    }

    public boolean isMember(Client client) {
        return clients.containsKey(client);
    }

    public Map<Client, Invoker> getIdentityHashMap() {
        return clients;
    }

    public Invoker get(Object available_client){
        return clients.get(available_client);
    }

    public int size(){
        return clients.size();
    }

    public Set<Map.Entry<Client, Invoker>> entrySet(){
        return clients.entrySet();
    }

    public Collection<Invoker> values() {
        return clients.values();
    }

    @Override
    public TIterator createIterator() {
        return new MapIterator();
    }

    private class MapIterator implements TIterator<Client, Decorator> {

        private java.util.Iterator<Client> key;
        private Client clt;
        private Invoker inv;

        public MapIterator() {
            key = clients.keySet().iterator();
        }

        @Override
        public boolean hasNext() {
            return key.hasNext();
        }

        @Override
        public Decorator next() {
            clt = key.next();
            inv = clients.get(clt);
            return inv.currentDecorator();
        }

        @Override
        public void reset() {
            key = clients.keySet().iterator();
        }

        @Override
        public Client currentKey() {
            return clt;
        }

        @Override
        public Decorator currentValue() {
            return inv.currentDecorator();
        }
    }

}
