import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.time.LocalDateTime;

public class Main {

    public static void main(String[] args) throws Exception {
        int rangeStart = 1;
        int rangeEnd = 100000; // Range to search for primes
        int numThreads = 4;    // Best number of threads
        int numProportions = 2; // Best number of proportions (logical proportions of the task)
        int domainSize = 20000; // Best domain size per task

        System.out.println("Testing with the following configuration:");
        System.out.println("Number of Threads: " + numThreads);
        System.out.println("Number of Proportions: " + numProportions);
        System.out.println("Domain Size: " + domainSize);
        System.out.println("--------------------------------------------");

        // Collect initial memory usage and start time
        long initialMemory = getMemoryUsage();
        long startTime = System.nanoTime(); // Start time for execution

        // Find prime numbers with the selected configuration
        List<Integer> primes = findPrimesInRangeParallel(rangeStart, rangeEnd, numThreads, domainSize);

        // Collect end time and memory usage
        long endTime = System.nanoTime();
        long finalMemory = getMemoryUsage();
        long executionTime = (endTime - startTime) / 1_000_000; // Convert nanoseconds to milliseconds

        // Memory consumption details
        long memoryUsed = finalMemory - initialMemory;

        // Print detailed statistics
        System.out.println("Execution Details:");
        System.out.println("Start Time: " + LocalDateTime.now());
        System.out.println("Execution Time: " + executionTime + " ms");
        System.out.println("Initial Memory Usage: " + initialMemory + " bytes");
        System.out.println("Final Memory Usage: " + finalMemory + " bytes");
        System.out.println("Memory Used: " + memoryUsed + " bytes");
        System.out.println("Number of Primes Found: " + primes.size());
        System.out.println("--------------------------------------------");
    }
    // Method to get the current memory usage (Heap memory)
    public static long getMemoryUsage() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapUsage = memoryBean.getHeapMemoryUsage();
        return heapUsage.getUsed();
    }

    // Method to find prime numbers using parallel processing with fixed domain size
    public static List<Integer> findPrimesInRangeParallel(int rangeStart, int rangeEnd, int numThreads, int domainSize) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(numThreads); // Create a thread pool
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





