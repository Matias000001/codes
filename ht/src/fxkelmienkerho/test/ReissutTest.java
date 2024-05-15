package fxkelmienkerho.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import fxkelmienkerho.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2024.05.11 13:08:25 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class ReissutTest {



  // Generated by ComTest BEGIN
  /** testLisaa39 */
  @Test
  public void testLisaa39() {    // Reissut: 39
    Reissut reissut = new Reissut(); 
    Reissu r1 = new Reissu(), r2 = new Reissu(); 
    assertEquals("From: Reissut line: 42", 0, reissut.getReissuLkm()); 
    reissut.lisaa(r1); assertEquals("From: Reissut line: 43", 1, reissut.getReissuLkm()); 
    reissut.lisaa(r2); assertEquals("From: Reissut line: 44", 2, reissut.getReissuLkm()); 
    reissut.lisaa(r1); assertEquals("From: Reissut line: 45", 3, reissut.getReissuLkm()); 
    reissut.lisaa(r1); assertEquals("From: Reissut line: 46", 4, reissut.getReissuLkm()); 
    reissut.lisaa(r1); assertEquals("From: Reissut line: 47", 5, reissut.getReissuLkm()); 
  } // Generated by ComTest END
}