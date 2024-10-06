
import junit.framework.TestCase;
import me.multithreading.PrimeFinderTask;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class PrimeFinderTest extends TestCase {

    // Test method for findPrimesInRange with a small range
    public void testFindPrimesInRangeWithSmallRange() {
        PrimeFinderTask primeFinderTask = new PrimeFinderTask(1, 10);
        List<Integer> primes = primeFinderTask.findPrimesInRange(1, 10);

        assertNotNull(primes); // Assert that the list is not null
        assertEquals(List.of(2, 3, 5, 7), primes); // Assert that the primes list is as expected
    }

    // Test method for findPrimesInRange with a larger range
    public void testFindPrimesInRangeWithLargerRange() {
        PrimeFinderTask primeFinderTask = new PrimeFinderTask(10, 30);
        List<Integer> primes = primeFinderTask.findPrimesInRange(10, 30);

        assertNotNull(primes); // Assert that the list is not null
        assertEquals(List.of(11, 13, 17, 19, 23, 29), primes); // Assert that the primes list is as expected
    }

    // Test method for the call method
    public void testCallMethod() throws ExecutionException, InterruptedException {
        PrimeFinderTask primeFinderTask = new PrimeFinderTask(1, 10);
        List<Integer> primes = primeFinderTask.call(); // Use the call method

        assertNotNull(primes); // Assert that the list is not null
        assertEquals(List.of(2, 3, 5, 7), primes); // Assert that the primes list is as expected
    }

    // Test method for a range with no primes
    public void testFindPrimesInRangeWithNoPrimes() {
        PrimeFinderTask primeFinderTask = new PrimeFinderTask(14, 16);
        List<Integer> primes = primeFinderTask.findPrimesInRange(14, 16);

        assertNotNull(primes); // Assert that the list is not null
        assertTrue(primes.isEmpty()); // Assert that the list is empty
    }

    // Test method for a single prime number
    public void testFindPrimesInRangeWithSinglePrime() {
        PrimeFinderTask primeFinderTask = new PrimeFinderTask(29, 29);
        List<Integer> primes = primeFinderTask.findPrimesInRange(29, 29);

        assertNotNull(primes); // Assert that the list is not null
        assertEquals(List.of(29), primes); // Assert that the list contains only 29
    }
}
