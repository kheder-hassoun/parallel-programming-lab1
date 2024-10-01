import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {

    public static void main(String[] args) throws Exception {
        int rangeStart = 1;
        int rangeEnd = 100000; // Large range to analyze performance effectively

        // Get the number of available processors (cores)
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("Available Processors: " + availableProcessors);

        // Vary the number of threads to test the effect on performance (from 1 to number of cores)
        int[] numThreadsList = {1, 2, availableProcessors, availableProcessors * 2};

        // Test with different thread counts and observe performance
        for (int numThreads : numThreadsList) {
            long startTime = System.nanoTime(); // Start timing
            List<Integer> primes = findPrimesInRangeParallel(rangeStart, rangeEnd, numThreads);
            long endTime = System.nanoTime(); // End timing

            // Calculate execution time in milliseconds
            long executionTime = (endTime - startTime) / 1_000_000;

            // Print results and statistics
            System.out.println("Number of Threads: " + numThreads);
            System.out.println("Execution Time: " + executionTime + " ms");
            System.out.println("Number of Primes Found: " + primes.size());
            System.out.println("--------------------------------------------");
        }
    }

    // Method to find prime numbers using parallel processing
    public static List<Integer> findPrimesInRangeParallel(int rangeStart, int rangeEnd, int numThreads) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads); // Create a thread pool
        List<Callable<List<Integer>>> tasks = new ArrayList<>(); // List to hold the tasks

        // Calculate the size of each subrange based on the number of threads
        int rangeSize = (rangeEnd - rangeStart + 1) / numThreads;

        // Create tasks to search for primes in each subrange
        for (int i = 0; i < numThreads; i++) {
            int subrangeStart = rangeStart + i * rangeSize;
            int subrangeEnd = (i == numThreads - 1) ? rangeEnd : subrangeStart + rangeSize - 1;
            tasks.add(new PrimeFinderTask(subrangeStart, subrangeEnd)); // Add task to the list
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





