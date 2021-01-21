package MemorySimulation.algorithms;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Algorithm {
    //количество страниц для хранения / amount of pages for store
    private final int pageFaults;
    //последовательность входа ссылок (1,2,3,4,5,3,1,2) / links entry sequence
    private final ArrayList<Integer> sequence;
    //количество подмен / amount of replacements
    private int PF = 0;

    public int getPageFaults() {
        return pageFaults;
    }

    public int getPF() {
        return PF;
    }

    public void setPF(int PF) {
        this.PF = PF;
    }

    public List<Integer> getSequence() {
        return sequence;
    }

    public static ArrayList<Integer> generateRandomSequence(int size) throws IOException {
        ArrayList<Integer> sequence = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            sequence.add(ThreadLocalRandom.current().nextInt(1, 10));
        }
        writeToFile(sequence);
        return sequence;
    }

    private static void writeToFile(ArrayList<Integer> list) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/MemorySimulation/sequence.txt", false));
        for (int value : list) {
            writer.write(value + "\n");
        }
        writer.flush();
        writer.close();
    }

    public static ArrayList<Integer> readFromFile() throws IOException {
        ArrayList<Integer> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader("src/main/java/MemorySimulation/sequence.txt"));
        String line = reader.readLine();
        while (line != null) {
            list.add(Integer.parseInt(line));
            line = reader.readLine();
        }
        return list;
    }

    public Algorithm(int pageFaults, ArrayList<Integer> sequence) {
        this.pageFaults = pageFaults;
        this.sequence = sequence;
    }

    public abstract void addToFrame(int toAdd);

    public abstract void run();

}
