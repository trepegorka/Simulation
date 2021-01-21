package MemorySimulation.algorithms.LRU;

import MemorySimulation.algorithms.Algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Least recently used
 */
public class LRU extends Algorithm {
    private static int time = 0;
    //link to page / added time
    private Map<Integer, Integer> frame = new LinkedHashMap<>();
    private ArrayList<Map<Integer, Integer>> repository = new ArrayList<>();
    private ArrayList<Integer> sequenceInTurn = new ArrayList<>();


    public LRU(int pageFaults, ArrayList<Integer> sequence) {
        super(pageFaults, sequence);
    }

    public ArrayList<Map<Integer, Integer>> getRepository() {
        return repository;
    }

    public void addToRepository(Map<Integer, Integer> frame) {
        repository.add(frame);
    }

    public Map<Integer, Integer> getFrame() {
        return frame;
    }

    public void setFrame(Map<Integer, Integer> frame) {
        this.frame = frame;
    }


    @Override
    public void addToFrame(int toAdd) {
        Map<Integer, Integer> newFrame = new LinkedHashMap<>(getFrame());
        if (newFrame.size() == getPageFaults()) {
            if (!newFrame.containsKey(toAdd)) {
                ArrayList<Integer> vals = new ArrayList<>(newFrame.values());
                int keyToDrop = 0;
                int minTime = Collections.min(vals);
                for(Map.Entry<Integer, Integer> page : newFrame.entrySet()){
                    if(page.getValue()==minTime){
                        keyToDrop = page.getKey();
                    }
                }
                newFrame.remove(keyToDrop);
                newFrame.put(toAdd, time);
            }
        }
        if (!newFrame.containsKey(toAdd)) {
            newFrame.put(toAdd, time);
        }
        if (!getFrame().keySet().containsAll(newFrame.keySet())) {
            setPF(getPF() + 1);
        }
        if(newFrame.keySet().containsAll(getFrame().keySet())){
            newFrame.remove(toAdd);
            newFrame.put(toAdd, time);
        }
        setFrame(newFrame);
        addToRepository(newFrame);
    }

    @Override
    public void run() {
        for (int i = 0; i < getSequence().size(); i++) {
            time++;
            sequenceInTurn.add(getSequence().get(i));
            addToFrame(getSequence().get(i));
        }
        showFifoRepo();
        System.out.println("\nTotal amount of PF: " + getPF());
    }

    private void showFifoRepo() {
        int maxRep = 0;
        int maxFrame = 0;
        for (Map<Integer, Integer> frame : getRepository()) {
            maxFrame = Collections.max(frame.keySet());
            if (maxFrame > maxRep) {
                maxRep = maxFrame;
            }
        }
        System.out.println("\nLRU REALISATION:\n");

        System.out.println("****in case of storing in sequence numbers greater than 9, the output will be modified***");
        showLRURepoGreater9();

    }

    private void showLRURepoGreater9() {
        for (Map<Integer, Integer> frame : getRepository()) {
            for (Map.Entry<Integer, Integer> page : frame.entrySet()) {
                System.out.print(page.getKey() + " ");
            }
            System.out.println();
        }
    }
}
