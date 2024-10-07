import junit.framework.TestCase;
import me.multithreading.Main;

public class GetMemoryUsageTest extends TestCase {
    // Test to ensure getMemoryUsage returns a valid non-negative value
    public void testGetMemoryUsage() {
        long memoryUsage = Main.getMemoryUsage();

        // Assert that the memory usage is a non-negative value
        assertTrue("Memory usage should be non-negative", memoryUsage >= 0);
    }
}
