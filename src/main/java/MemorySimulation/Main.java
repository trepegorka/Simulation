package MemorySimulation;

import MemorySimulation.algorithms.Algorithm;
import MemorySimulation.algorithms.FIFO.FIFO;
import MemorySimulation.algorithms.LRU.LRU;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Integer> sequence = new ArrayList<>();


//        add by hand:
//        sequence.add(1);
//        sequence.add(2);
//        sequence.add(3);
//        sequence.add(1);

//        generating (will be added to file sequence.txt):
//        sequence = Algorithm.generateRandomSequence(10);

//        reading from file sequence.txt:
        sequence = Algorithm.readFromFile();


        Algorithm fifo = new FIFO(3, sequence);
        fifo.run();

        Algorithm lru = new LRU(3, sequence);
        lru.run();
    }
}
