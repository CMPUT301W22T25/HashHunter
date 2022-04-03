package com.example.hashhunter;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This is a testing class for testing the PlayerList class
 */
public class PlayerListTest {
    private PlayerList list;

    /**
     * sets up a PlayerList to use for testing
     */
    @Before
    public void testCreateList() {
        list = new PlayerList();

    }


    /**
     * Test adding a player to the player list
     */
    @Test
    public void testAddPlayerList() {
        int listSize = list.getSize();
        list.addPlayerList(new Player());
        assertEquals(listSize+1, list.getSize());
    }

    /**
     * Test finding a position of a player in the list based on username
     */
    @Test
    public void testFindPlayerPosTest() {
        Player player = new Player("testUser1");
        list.addPlayerList(player);

        assertEquals(0, list.findPlayerPos("testUser1"));
        assertEquals(-1, list.findPlayerPos("1234"));
    }

    /**
     * Tests finding the position of a player based on the player object in the list
     */
    @Test
    public void testIndexOfPlayerTest() {
        Player player = new Player("testUser1");
        list.addPlayerList(player);

        Player player2 = new Player();

        assertEquals(0, list.indexOfPlayer(player));
        assertEquals(-1, list.indexOfPlayer(player2));
    }

    /**
     * Tests getting the size of the player list
     */
    @Test
    public void testGetSize() {
        assertEquals(0, list.getSize());
    }

    /**
     * Tests getting a player at a certain index
     */
    @Test
    public void testGetPlayer() {
        Player player = new Player();
        list.addPlayerList(player);
        assertEquals(player, list.getPlayer(0));
    }

    /**
     * Tests getting the whole list of players
     */
    @Test
    public void testGetPlayerList() {
        Player player = new Player();
        list.addPlayerList(player);
        assertEquals(1, list.getPlayerList().size());
    }


    /**
     * Tests sorting the player list by each players highest qr score
     */
    @Test
    public void testSortByQRScore() {
        Player player = new Player();
        list.addPlayerList(player);

        Player player2 = new Player();
        list.addPlayerList(player2);

        player2.setMaxGameCodePoints(25);

        assertEquals(player2, list.getPlayer(1));

        list.sortByQRScore();
        assertEquals(player2, list.getPlayer(0));
    }


    /**
     * Tests sorting the player list by each players amount of qr codes scanned
     */
    @Test
    public void testSortByMostQR() {
        Player player = new Player();
        list.addPlayerList(player);

        Player player2 = new Player();
        list.addPlayerList(player2);

        player2.setTotalGameCode(25);

        assertEquals(player2, list.getPlayer(1));

        list.sortByMostQR();
        assertEquals(player2, list.getPlayer(0));
    }


    /**
     * Tests sorting the player list by each players total points
     */
    @Test
    public void testSortByTotalPoints() {
        Player player = new Player();
        list.addPlayerList(player);

        Player player2 = new Player();
        list.addPlayerList(player2);

        player2.setTotalPoints(25);

        assertEquals(player2, list.getPlayer(1));

        list.sortByTotalPoints();
        assertEquals(player2, list.getPlayer(0));
    }


}
