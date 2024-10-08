package me.latencyOptimization;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ImageProcessor {

    public static void recolorMultithreadedBlocks(BufferedImage originalImage, BufferedImage resultImage, int numberOfThreads) {
        List<Thread> threads = new ArrayList<>();

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        int blockSize = (int) Math.sqrt(numberOfThreads); // Split image into blockSize x blockSize regions

        int blockWidth = width / blockSize;
        int blockHeight = height / blockSize;

        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                final int xOrigin = blockWidth * i;
                final int yOrigin = blockHeight * j;

                Thread thread = new Thread(() -> {
                    recolorImage(originalImage, resultImage, xOrigin, yOrigin, blockWidth, blockHeight);
                });

                threads.add(thread);
            }
        }

        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }

        // Wait for all threads to complete
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage, int leftCorner, int topCorner, int width, int height) {
        for (int x = leftCorner; x < leftCorner + width && x < originalImage.getWidth(); x++) {
            for (int y = topCorner; y < topCorner + height && y < originalImage.getHeight(); y++) {
                PixelUtils.recolorPixel(originalImage, resultImage, x, y);
            }
        }
    }
}
