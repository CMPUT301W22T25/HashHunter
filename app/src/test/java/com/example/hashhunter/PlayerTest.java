package com.example.hashhunter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class PlayerTest {


    /**
     * Creates a mock player to use for testing
     * @return
     *      returns a player to use for testing
     */
    private Player mockPlayer() {
        Player player = new Player("username");
        player.addGameCode(mockGameCode());
        player.setTotalGameCode(1);
        return player;
    }

    /**
     * creates a mock game code to use in the mock player
     * @return
     *      returns a string representing a mock game code
     */
    private String mockGameCode() {
        return "MockGameCode";
    }


    /**
     * Tests adding a game code to a player
     */
    @Test
    public void testAddGameCode() {
        Player player = mockPlayer();
        assertEquals(1, player.getGameCodeList().size());

        String gameCode = "GameCode";
        player.addGameCode(gameCode);

        assertEquals(2, player.getGameCodeList().size());
        assertTrue(player.getGameCodeList().contains(gameCode));


    }

    /**
     * Tests removing a game code from a player
     */
    @Test
    public void testRemoveGameCode() {
        Player player = mockPlayer();
        assertEquals(1, player.getGameCodeList().size());

        String gameCode = "GameCode";
        player.addGameCode(gameCode);

        assertEquals(2, player.getGameCodeList().size());
        assertTrue(player.getGameCodeList().contains(gameCode));

        player.removeGameCode(gameCode);

        assertEquals(1, player.getGameCodeList().size());
        assertFalse(player.getGameCodeList().contains(gameCode));

    }


    /**
     * Test getting the username from a player
     */
    @Test
    public void testGetUsername() {
        Player player = mockPlayer();
        assertEquals("username", player.getUsername());
    }

    /**
     * Test getting the profile code form a player
     */
    @Test
    public void testGetProfileCode() {
        Player player = mockPlayer();
        assertEquals(null, player.getProfileCode());
    }

    /**
     * Tests getting a list of a players gamecode
     */
    @Test
    public void testGetGameCodeList() {
        Player player = mockPlayer();
        ArrayList<String> list;

        String gameCode = "GameCode";
        player.addGameCode(gameCode);
        list = player.getGameCodeList();

        assertEquals("GameCode", list.get(1));

    }

    /**
     * Tests getting a players total points
     */
    @Test
    public void testGetTotalPoints() {
        Player player = mockPlayer();
        assertEquals(0, player.getTotalPoints());
    }

    /**
     * Tests getting a players total amount of gamecode
     */
    @Test
    public void testGetTotalGameCode() {
        Player player = mockPlayer();
        assertEquals(1, player.getTotalGameCode());
    }

    /**
     * Tests getting a players max gamecode points
     */
    @Test
    public void testGetMaxGameCodePoints() {
        Player player = mockPlayer();
        assertEquals(0, player.getMaxGameCodePoints());
    }


    /**
     * Tests getting a players total points
     */
    @Test
    public void testSetTotalPoints() {
        Player player = mockPlayer();
        player.setTotalPoints(10);
        assertEquals(10, player.getTotalPoints());
    }


    /**
     * Tests setting a players total gamecode
     */
    @Test
    public void testSetTotalGameCode() {
        Player player = mockPlayer();
        player.setTotalGameCode(10);
        assertEquals(10, player.getTotalGameCode());
    }


    /**
     * Test setting a players max gamecode points
     */
    @Test
    public void testSetMaxGameCodePoints() {
        Player player = mockPlayer();
        player.setMaxGameCodePoints(10);
        assertEquals(10, player.getMaxGameCodePoints());
    }


    /**
     * Test setting a players display total
     */
    @Test
    public void testSetDisplayTotal() {
        Player player = mockPlayer();
        player.setDisplayTotal(10);
        assertEquals(10, player.getDisplayTotal());
    }


    /**
     * Tests getting a players display total
     */
    @Test
    public void testGetDisplayTotal() {
        Player player = mockPlayer();
        assertEquals(0, player.getDisplayTotal());
    }



}
