package com.example.hashhunter;

import java.util.Comparator;
//https://stackoverflow.com/questions/2784514/sort-arraylist-of-custom-objects-by-property

/**
 * Custom comparator to compare points between gamecodecontroller objects
 */
public class CustomComparator implements Comparator<GameCodeController> {

    @Override
    public int compare(GameCodeController gcc1, GameCodeController gcc2) {
        gcc1.SyncController();
        gcc2.SyncController();
        return gcc1.getPoints().compareTo(gcc2.getPoints());
    }
}