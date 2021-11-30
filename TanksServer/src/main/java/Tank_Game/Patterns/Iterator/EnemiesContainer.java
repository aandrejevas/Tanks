package Tank_Game.Patterns.Iterator;

import Tank_Game.Patterns.Command.Invoker;
import Tank_Game.Patterns.Decorator.Decorator;
import processing.net.Client;
import utils.Iterator.Iterate;

import java.util.ArrayList;
import java.util.List;

public class EnemiesContainer implements Iterate<AIterator> {

    private  Invoker[] enemies = new Invoker[1];
    private int size = 0;

    public void add (Invoker enem){
        if (size < enemies.length) {
            enemies[size] = enem;
            size++;
        }else {
            resizeArray();
            enemies[size] = enem;
            size++;
        }
    }

    public void resizeArray(){
        Invoker[] enem = new Invoker[enemies.length + 10];
        for (int i = 0; i < enemies.length; i++){
            enem[i] = enemies[i];
        }
        enemies = enem;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    @Override
    public AIterator createIterator() {
        return new ContainerIterator();
    }

    private class ContainerIterator implements AIterator<Invoker> {

        private int i = 0;
        private Invoker inv = enemies[0];

        @Override
        public boolean hasNext() {
            return i < size;
        }

        @Override
        public Invoker next() {
            inv = enemies[i];
            i++;
            return inv;
        }

        @Override
        public void reset() {
            i = 0;
        }

        @Override
        public Invoker value() {
            return enemies[i];
        }
    }
}
