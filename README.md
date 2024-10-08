# project sturctur

```css
parallel-programming-lab1/
│
├── .idea/
├── out/
├── src/
│ ├── main/
│ │ └── java/
│ │ └── me/
│ │ └── latencyOptimization/
│ │ ├── resources/
│ │ │ └── many-flowers.jpg
│ │ ├── ImageProcessor.java
│ │ ├── Main.java
│ │ └── PixelUtils.java
│ │ └── multithreading/
│ │ ├── IPrimeFinder.java
│ │ ├── PrimeFinder.java
│ │ ├── Main.java
│ │ └── PrimeFinderTask.java
│
└── test/
└── java/
├── GetMemoryUsageTest.java
├── ImageProcessingTest.java
├── PrimeFinderParallelTest.java
└── PrimeFinderTest.java
```

# Prime Number Finder (Parallel Processing)

This project demonstrates efficient prime number finding using **parallel processing** in Java. By leveraging multithreading, the workload is distributed across multiple threads to improve performance on systems with multiple CPUs.

## Features

- **Multithreading**: Uses `ExecutorService` to run prime number calculations in parallel.
- **Performance Metrics**: Tracks and displays execution time and memory usage.
- **Configurable Parameters**: Adjust the number of threads, task proportions, and domain size to optimize performance.

## Getting Started

### Prerequisites

- **Java 8+** installed on your system.
- **JUnit 5** for running tests (if testing is required).

### Running the Program

1. Configure the parameters (number of threads, task proportions, domain size) in the `Main` class.
2. Run the `Main` class to search for prime numbers and display performance metrics.

### Running Tests

To run tests for the prime-finding functionality:

1. Ensure JUnit is set up.
2. Run the test class `PrimeFinderTest` to validate the correctness of the algorithm.

# Image Processing Optimization with Multithreading

## Overview

This project demonstrates how to optimize image processing tasks by splitting an image into **blocks** for parallel processing using Java's **multithreading**. Instead of processing the image row by row, we divide it into smaller blocks, allowing multiple threads to work on different parts of the image at the same time, which leads to better performance and improved data locality.

## Why Block Splitting?

In certain image processing tasks, like applying filters (e.g., **blur**, **sharpening**) or running **edge detection** algorithms (e.g., Sobel, Canny), each pixel relies on its neighbors for calculations. By splitting the image into blocks, each thread can process a self-contained area of the image, minimizing the need for data sharing across threads and reducing the overhead from inter-thread communication.

### Key Benefits of Block Splitting:

- **Improved Data Locality**: Each thread works on a local block of the image, reducing cache misses and memory access time.
- **Reduced Inter-thread Communication**: Threads process their blocks independently, minimizing the need to share pixel data between neighboring regions.
- **Efficient Parallel Processing**: By distributing the workload evenly, we maximize the use of available CPU cores.

## How It Works

1. The image is loaded and divided into blocks (based on the number of available threads).
2. Each block is processed in parallel by a thread, performing operations such as recoloring or applying filters.
3. We capture statistics like **execution time** and **memory usage** to monitor performance improvements.

## Use Cases

Block-based image processing is particularly useful for:

- **Local Filtering**: Operations like blur or sharpening where neighboring pixels influence each other.
- **Edge Detection**: Algorithms that compute pixel gradients, like Sobel or Canny, which require local neighborhood data.

## Example

Here’s a quick breakdown of how block splitting enhances performance:

### Problem with Rows/Columns:

When splitting the image into rows or columns, neighboring threads need access to pixels from adjacent sections, leading to synchronization overhead and slower performance.

### Block Splitting Solution:

By processing blocks, each thread operates on a more self-contained region, minimizing the need for cross-thread communication and improving overall speed.

## Technologies Used

- **Java Multithreading**: We use `ExecutorService` to manage multiple threads efficiently.
- **BufferedImage**: For handling image manipulation and processing.

## How to Run

1. Clone the repository.
2. Ensure the image path is correctly set in the `SOURCE_FILE` constant.
3. Run the `Main.java` file to process the image and output the results.

```bash
$ javac Main.java
$ java Main
```
