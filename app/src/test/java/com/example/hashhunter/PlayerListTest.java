package com.example.hashhunter;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerListTest {
    private PlayerList list;


    @Before
    public void createList() {
        list = new PlayerList();

    }


    @Test
    public void addPlayerList() {
        int listSize = list.getSize();
        list.addPlayerList(new Player());
        assertEquals(listSize+1, list.getSize());
    }


    @Test
    public void findPlayerPosTest() {
        Player player = new Player("testUser1");
        list.addPlayerList(player);
        assertEquals(0, list.findPlayerPos("testUser1"));
    }

    @Test
    public void indexOfPlayerTest() {
        Player player = new Player("testUser1");
        list.addPlayerList(player);
        assertEquals(0, list.indexOfPlayer(player));
    }

}
