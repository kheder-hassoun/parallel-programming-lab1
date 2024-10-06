import junit.framework.TestCase;
import me.multithreading.Main;

import java.util.List;

public class PrimeFinderParallelTest extends TestCase {

    // Test the parallel prime finding for a small range
    public void testFindPrimesInRangeParallelWithSmallRange() throws Exception {
        List<Integer> primes = Main.findPrimesInRangeParallel(1, 10, 2, 5); // Use 2 threads, domain size 5

        assertNotNull(primes); // Check that the result is not null
        assertEquals(List.of(2, 3, 5, 7), primes); // Check that the result contains the correct prime numbers
    }

    // Test the parallel prime finding for a larger range
    public void testFindPrimesInRangeParallelWithLargerRange() throws Exception {
        List<Integer> primes = Main.findPrimesInRangeParallel(10, 30, 4, 5); // Use 4 threads, domain size 5

        assertNotNull(primes); // Check that the result is not null
        assertEquals(List.of(11, 13, 17, 19, 23, 29), primes); // Check that the result contains the correct prime numbers
    }

    // Test the parallel prime finding for a range with no primes
    public void testFindPrimesInRangeParallelWithNoPrimes() throws Exception {
        List<Integer> primes = Main.findPrimesInRangeParallel(14, 16, 2, 2); // Use 2 threads, domain size 2

        assertNotNull(primes); // Check that the result is not null
        assertTrue(primes.isEmpty()); // Check that the result is empty
    }




}
