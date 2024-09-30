import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws Exception {
        int rangeStart = 1;
        int rangeEnd = 100000; // A larger range to analyze performance effectively
        int numProportions = 4; // Fixed number of parallel tasks (threads)

        // Define various domain sizes to test
        int[] domainSizes = {100, 1000, 5000, 10000, 20000};

        // Test different domain sizes
        for (int domainSize : domainSizes) {
            long startTime = System.nanoTime(); // Start timing
            List<Integer> primes = findPrimesWithDomainSize(rangeStart, rangeEnd, numProportions, domainSize);
            long endTime = System.nanoTime(); // End timing

            // Calculate execution time in milliseconds
            long executionTime = (endTime - startTime) / 1_000_000;

            // Print results and statistics
            System.out.println("Domain Size: " + domainSize);
            System.out.println("Execution Time: " + executionTime + " ms");
            System.out.println("Number of Primes Found: " + primes.size());
            System.out.println("--------------------------------------------");
        }
    }

    // Method to find prime numbers with a fixed domain size for each task
    public static List<Integer> findPrimesWithDomainSize(int rangeStart, int rangeEnd, int numProportions, int domainSize) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(numProportions); // Create a thread pool
        List<Callable<List<Integer>>> tasks = new ArrayList<>(); // List to hold the tasks

        // Create tasks based on the domain size
        int currentStart = rangeStart;

        // Keep creating tasks until the entire range is covered
        while (currentStart <= rangeEnd) {
            int currentEnd = Math.min(currentStart + domainSize - 1, rangeEnd);
            tasks.add(new PrimeFinderTask(currentStart, currentEnd)); // Add task to the list
            currentStart = currentEnd + 1; // Move to the next subrange
        }

        // Execute all tasks and collect the results
        List<Future<List<Integer>>> results = executor.invokeAll(tasks);
        executor.shutdown(); // Shutdown the executor

        // Collect all prime numbers from the results
        List<Integer> allPrimes = new ArrayList<>();
        for (Future<List<Integer>> result : results) {
            allPrimes.addAll(result.get());
        }

        return allPrimes; // Return the list of all prime numbers found
    }
}

// Class that implements the PrimeFinder interface to find primes in a specific range
class PrimeFinderTask implements Callable<List<Integer>>, PrimeFinder {
    private final int start;
    private final int end;

    public PrimeFinderTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    // Implement the findPrimesInRange method from the PrimeFinder interface
    @Override
    public List<Integer> findPrimesInRange(int start, int end) {
        List<Integer> primes = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) {
                primes.add(i); // Add prime number to the list
            }
        }
        return primes;
    }

    // Callable's call method will call the interface method
    @Override
    public List<Integer> call() {
        return findPrimesInRange(start, end);
    }

    // Helper method to check if a number is prime
    private boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
}