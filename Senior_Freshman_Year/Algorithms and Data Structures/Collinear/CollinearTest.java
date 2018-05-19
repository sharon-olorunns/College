import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

//-------------------------------------------------------------------------
/**
 *  Test class for Collinear.java
 *
 *  @author
 *  @version 29/09/14 13:22:29
 */
@RunWith(JUnit4.class)
public class CollinearTest
{
    //~ Constructor ........................................................
    @Test
    public void testConstructor()
    {
        new Collinear();
    }

    //~ Public Methods ........................................................

    // ----------------------------------------------------------
    /**
     * Check that the two methods work for empty arrays
     */
    @Test
    public void testEmpty()
    {
        assertEquals("countCollinear failed with 3 empty arrays",       0, Collinear.countCollinear(new int[0], new int[0], new int[0]));
        assertEquals("countCollinearSorted failed with 3 empty arrays", 0, Collinear.countCollinearSorted(new int[0], new int[0], new int[0]));
    }

    // ----------------------------------------------------------
    /**
     * Check for no false positives in a single-element array
     */
    @Test
    public void testSingleFalse()
    {
        int[] a3 = { 15 };
        int[] a2 = { 5 };
        int[] a1 = { 10 };
        assertEquals("countCollinear({10}, {5}, {15})",       0, Collinear.countCollinear(a1, a2, a3) );
        assertEquals("countCollinearSorted({10}, {5}, {15})", 0, Collinear.countCollinearSorted(a1, a2, a3) );
    }

    // ----------------------------------------------------------
    /**
     * Check for no false positives in a single-element array
     */
    @Test
    public void testSingleTrue()
    {
        int[] a3 = { 15, 5 };       int[] a2 = { 5 };       int[] a1 = { 10, 15, 5 };
        int[] a3sorted = { 5, 15 }; int[] a2sorted = { 5 }; int[] a1sorted = { 5, 10, 15 };

        assertEquals("countCollinear(" + Arrays.toString(a1) + "," + Arrays.toString(a2) + "," + Arrays.toString(a3) + ")",                         1, Collinear.countCollinear(a1, a2, a3));
        assertEquals("countCollinearSorted(" + Arrays.toString(a1sorted) + "," + Arrays.toString(a2sorted) + "," + Arrays.toString(a3sorted) + ")", 1, Collinear.countCollinearSorted(a1sorted, a2sorted, a3sorted));
    }


    // TODO: add more tests here. Each line of code in Collinear.java should
    // be executed at least once from at least one test.

    @Test
    public void testBinarySearchTrue()
    {
        int[] a1 = {1, 2, 3};
        assertEquals("binarySearch(" +Arrays.toString(a1) + ")", true, Collinear.binarySearch(a1, 2));
    }



}