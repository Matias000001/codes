package fxkelmienkerho.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;
import org.junit.*;
import fxkelmienkerho.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2024.05.11 13:52:39 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class OsallistumisetTest {



  // Generated by ComTest BEGIN
  /** testLisaa56 */
  @Test
  public void testLisaa56() {    // Osallistumiset: 56
    Osallistumiset osallistumiset = new Osallistumiset(); 
    Osallistuminen o1 = new Osallistuminen(), o2 = new Osallistuminen(); 
    assertEquals("From: Osallistumiset line: 59", 0, osallistumiset.osallistumisetLkm()); 
    osallistumiset.lisaa(o1); assertEquals("From: Osallistumiset line: 60", 1, osallistumiset.osallistumisetLkm()); 
    osallistumiset.lisaa(o2); assertEquals("From: Osallistumiset line: 61", 2, osallistumiset.osallistumisetLkm()); 
    osallistumiset.lisaa(o1); assertEquals("From: Osallistumiset line: 62", 3, osallistumiset.osallistumisetLkm()); 
    osallistumiset.lisaa(o1); assertEquals("From: Osallistumiset line: 63", 4, osallistumiset.osallistumisetLkm()); 
    osallistumiset.lisaa(o1); assertEquals("From: Osallistumiset line: 64", 5, osallistumiset.osallistumisetLkm()); 
  } // Generated by ComTest END
}