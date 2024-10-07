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

        long startTime = System.currentTimeMillis();
        ImageProcessor.recolorMultithreaded(originalImage, resultImage, 4);
        long endTime = System.currentTimeMillis();

        long duration = endTime - startTime;
        System.out.println("Processing took " + duration + "ms");

        File outputFile = new File(DESTINATION_FILE);
        ImageIO.write(resultImage, "jpg", outputFile);
    }
}
