import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

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