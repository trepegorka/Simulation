package MemorySimulation.algorithms.FIFO;

import MemorySimulation.algorithms.Algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * First in, first out
 */
public class FIFO extends Algorithm {
    private ArrayList<Integer> frame = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> repository = new ArrayList<>();

    public FIFO(int pageFaults, ArrayList<Integer> sequence) {
        super(pageFaults, sequence);
    }

    public ArrayList<ArrayList<Integer>> getRepository() {
        return repository;
    }

    public void addToRepository(ArrayList<Integer> frame) {
        repository.add(frame);
    }

    public ArrayList<Integer> getFrame() {
        return frame;
    }

    public void setFrame(ArrayList<Integer> frame) {
        this.frame = frame;
    }


    @Override
    public void addToFrame(int toAdd) {
        ArrayList<Integer> newFrame = new ArrayList<>(getFrame());
        if (newFrame.size() == getPageFaults()) {
            if (!newFrame.contains(toAdd)) {
                newFrame.remove(0);
                newFrame.add(toAdd);
            }
        }
        if (!newFrame.contains(toAdd)) {
            newFrame.add(toAdd);
        }
        if (!getFrame().containsAll(newFrame)) {
            setPF(getPF() + 1);
        }
        setFrame(newFrame);
        addToRepository(newFrame);
    }

    @Override
    public void run() {
        for (int i = 0; i < getSequence().size(); i++) {
            addToFrame(getSequence().get(i));
        }
        showFifoRepo();
        System.out.println("\nTotal amount of PF: " + getPF());
    }

    private void showFifoRepo() {
        int maxRep = 0;
        int maxFrame = 0;
        for (ArrayList<Integer> list : getRepository()) {
            maxFrame = Collections.max(list);
            if (maxFrame > maxRep) {
                maxRep = maxFrame;
            }
        }
        boolean in1_9 = maxRep > 0 && maxRep < 10;
        boolean greater9 = maxRep > 9;
        System.out.println("\nFIFO REALISATION:\n");
        if (greater9) {
            System.out.println("****in case of storing in sequence numbers greater than 9, the output will be modified***");
            showFifoRepoGreater9();
            return;
        }
        for (int i = 0; i < getPageFaults(); i++) {
            for (int j = 0; j < getRepository().size(); j++) {
                try {
                    if (in1_9) {
                        System.out.print(getRepository().get(j).get(i) + " | ");
                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.print("    ");
                }
            }
            System.out.println();
        }
    }

    private void showFifoRepoGreater9() {
        for (ArrayList<Integer> list : getRepository()) {
            System.out.println(list);
        }
    }
}


// Example:
// FRAME/PAGE 1   2   3   1   2   1   4   1   Total PF
// 1          1   1   1   1   1   1   4   4
// 2              2   2   2   2   2   2   1
// 3                  3   3   3   3   3   3
//            PF  PF  PF              PF  PF     5

// List Realisation:
// 1 -> 1 2 -> 1 2 3 -> 1 2 3 -> 1 2 3 -> 1 2 3 -> 2 3 4 -> 3 4 1


