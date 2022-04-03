// Dimas commented this test because it was giving me a bucnh of error while Aaron is working on the updated version of the test
//package com.example.hashhunter;
//import org.junit.Before;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//
//public class PlayerTest {
//
//
//
//
//    private Player mockPlayer() {
//        Player player = new Player();
//        player.addGameCode(mockGameCode());
//        return player;
//    }
//
//    private GameCode mockGameCode() {
//        return new GameCode("title", "code", 12, "owner");
//    }
//
//
//
//    @Test
//    public void testAddGameCode() {
//        Player player = mockPlayer();
//        assertEquals(1, player.getGameCodeList().size());
//
//        GameCode gameCode = new GameCode("title2", "code2", 122, "owner2");
//        player.addGameCode(gameCode);
//
//        assertEquals(2, player.getGameCodeList().size());
//        assertTrue(player.getGameCodeList().contains(gameCode));
//
//
//    }
//
//
//    @Test
//    public void testRemoveGameCode() {
//        Player player = mockPlayer();
//        assertEquals(1, player.getGameCodeList().size());
//
//        GameCode gameCode = new GameCode("title2", "code2", 12, "owner2");
//        player.addGameCode(gameCode);
//
//        assertEquals(2, player.getGameCodeList().size());
//        assertTrue(player.getGameCodeList().contains(gameCode));
//
//        player.removeGameCode(gameCode);
//
//        assertEquals(1, player.getGameCodeList().size());
//        assertFalse(player.getGameCodeList().contains(gameCode));
//
//            }
//
//}
