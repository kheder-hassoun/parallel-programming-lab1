package me.latencyOptimization;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static final String SOURCE_FILE = "D:\\five\\Parallel Processing\\labs\\parallel-programming-lab1\\src\\main\\java\\me\\latencyOptimization\\resources\\many-flowers.jpg";
    public static final String DESTINATION_FILE = "./out/many-flowers-out.jpg";

    public static void main(String[] args) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));
        BufferedImage resultImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        // Get memory info before processing
        Runtime runtime = Runtime.getRuntime();
        long memoryBefore = runtime.totalMemory() - runtime.freeMemory();

        long startTime = System.currentTimeMillis();

        // Perform multi-threaded recoloring by dividing into blocks
        int numberOfThreads = 4;
        ImageProcessor.recolorMultithreadedBlocks(originalImage, resultImage, numberOfThreads);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        // Get memory info after processing
        long memoryAfter = runtime.totalMemory() - runtime.freeMemory();

        File outputFile = new File(DESTINATION_FILE);
        ImageIO.write(resultImage, "jpg", outputFile);

        // Print statistics
        System.out.println("Time taken: " + duration + " ms");
        System.out.println("Memory used: " + (memoryAfter - memoryBefore) / 1024 + " KB");
    }
}
