# BitsAndBytesCompression
This repository contains Java implementations of various compression algorithms, including LZ77, LZ78, Huffman coding, arithmetic coding, and Vector Quantization. These algorithms provide efficient ways to compress and decompress data, reducing file sizes and improving storage and transmission efficiency.

# LZ77 Algorithms

## Introduction
- The provided Java code demonstrates the implementation of the LZ77 compression algorithm and its corresponding decompression process. LZ77 is a dictionary-based algorithm that replaces repeated occurrences of data with references to a previously occurring data string. This approach allows for efficient compression by reducing redundancy in the data.

- The code also includes methods for compression and decompression using user input. The user is presented with a menu to choose between compression and decompression, and the appropriate method is executed accordingly.
## Usage
To use this code, follow the steps below:

1. Clone the repository to your local machine or download the source code.
2. Open the project in your preferred Java development environment.
3. Compile and run the Main.java file.
   
Upon running the program, you will be prompted with a menu offering two options:

1. **Compression:** This option allows you to enter a text string to compress using the LZ77 algorithm.
2. **Decompression:** This option enables you to enter LZ77 tags, consisting of position, length, and symbol information, for decompressing a previously compressed string.
   
Follow the on-screen instructions to perform compression and decompression operations as needed.

## Algorithms
The repository aims to provide implementations of various compression algorithms. The current implementation includes:

- **LZ77:** This algorithm performs compression by replacing repeated occurrences of data with references to previously occurring data strings. It achieves compression by reducing redundancy.

Note that the repository is a work in progress, and additional algorithms will be added in the future.

# LZ78 Algorithms

## Introduction
This repository contains Java implementations of the LZ78 compression algorithm and its corresponding decompression process. LZ78 is a dictionary-based algorithm that achieves compression by replacing repeated occurrences of data with references to previously occurring data strings. The code provided allows users to compress and decompress text strings using LZ78.

## Usage

To use the code provided, follow these steps:

1. Clone the repository or download the source code to your local machine.
2. Open the project in your preferred Java development environment.
3. Compile and run the `Main.java` file.
Upon running the program, you will be presented with a menu offering two options:

1. **Compression:** Selecting this option allows you to enter a text string to be compressed using the LZ78 algorithm.
2. **Decompression:** Choosing this option enables you to enter LZ78 tags, consisting of an index and a symbol, for decompressing a previously compressed string.
Follow the on-screen instructions to perform compression and decompression operations as needed.

## Algorithm
- `compression()`: This method prompts the user to enter a text string to be compressed using the LZ78 algorithm. It then applies the compression process, generating tags representing the compressed data and printing them to the console.

- `decompression()`: This method allows the user to enter LZ78 tags in the form of an index and a symbol. It performs the decompression process, reconstructing the original string and printing it to the console.

# Huffman Compression and Decompression Algorithm

## Introduction

- This code implements Huffman compression and decompression algorithms. Huffman coding is a lossless data compression technique that assigns variable-length codes to different characters based on their frequency of occurrence in the input data. This allows more frequent characters to have shorter codes, resulting in efficient compression.

## Code Structure

The code consists of the following files:

- `Node.java:` Defines the Node class used for building the Huffman tree.
- `Huffman.java:` Implements the Huffman compression and decompression algorithms.
- `input.txt:` The input file containing the data to be compressed.
- `compressed.txt:` The output file where the compressed data will be stored.
- `dictionary.txt:` The file containing the dictionary mapping characters to their corresponding Huffman codes.
- `decompressed.txt:` The output file where the decompressed data will be stored.

## How to Use

1. Run the `Huffman` class, either by executing the main method or running the program through an IDE.
2. The program will display a menu with the following options:
   - **1. Compression:** This option compresses the data from the input.txt file and stores the compressed data in the `compressed.txt` file.
   - **2. Decompression:** This option decompresses the data from the compressed.txt file and stores the decompressed data in the `decompressed.txt` file.
3. Choose the desired operation by entering the corresponding number.
4. The program will perform the selected operation and display the results on the console.

## Notes

- Make sure to have the `input.txt` file with the data you want to compress present in the same directory as the code files.
- The compressed data will be written to the `compressed.txt` file, and the decompressed data will be written to the `decompressed.txt` file.
- The Huffman dictionary, mapping characters to their corresponding codes, will be stored in the `dictionary.txt` file.

# Binary Arithmetic Algorithm

## Introduction

This is a Java program that demonstrates binary arithmetic operations such as compression and decompression using the arithmetic coding technique. The program provides a GUI for easy interaction with the compression and decompression processes.

## Algorithm

The binary arithmetic compression and decompression algorithm implemented in this program follow these steps:

### Compression
1. Read the input text from the specified input file.
2. Calculate the probability of each character in the input text.
3. Create a symbol-to-probability mapping using a HashMap.
4. Calculate the cumulative probability for each symbol, forming a range for each symbol using a HashMap.
5. Determine the value of 'k' based on the smallest probability.
6. Initialize variables for the lower and higher bounds of the arithmetic interval.
7. Iterate through each character in the input text.
   - Find the corresponding range for the current character in the symbol-to-probability mapping.
   - Update the lower and higher bounds of the arithmetic interval based on the range.
   - Normalize the interval until both the higher and lower bounds are within the range [0.5, 0.5).
   - Update the symbol-to-probability mapping using the new lower bound.
8. Append '1' to the compressed code and add 'k-1' '0's to the end to ensure a unique code for decompression.
9. Return the compressed code.
   
### Decompression
1. Read the compressed code and the probability information from the specified files.
2. Create symbol-to-probability, symbol-to-low, and symbol-to-high mappings using HashMaps.
3. Determine the value of 'k' based on the smallest probability.
4. Initialize variables for the current interval, previous interval, and a counter for additional bits ('E').
5. Iterate through the compressed code until there are no more bits or the end flag is reached.
   - Convert a portion of the code to a decimal value within the previous interval.
   - Find the symbol whose range contains the decimal value in the symbol-to-low and symbol-to-high mappings.
   - Append the symbol to the decompressed text.
   - Update the current interval and previous interval based on the chosen symbol's range.
   - Normalize the intervals until both the higher and lower bounds are within the range [0.5, 0.5).
   - Repeat until all bits have been processed or the end flag is reached.
6. Return the decompressed text.
   
# vectorQuantization

## Introduction
The Vector Quantization algorithm is a technique used for image compression, where an image is divided into smaller sub-images called vectors. These vectors are then compared to a codebook, which contains representative vectors. The algorithm finds the best matching vector from the codebook for each sub-image vector and replaces it with the corresponding codebook vector. This process reduces the amount of data required to represent the image, resulting in compression.

The code in this repository provides functionality for compressing and decompressing images using the Vector Quantization algorithm. It includes the following files:

- `ImageRW.java`: This class provides methods for reading and writing images.
- `VectorQuantization.java`: This class implements the Vector Quantization algorithm for image compression and decompression.
- `Main.java`: This is the main entry point of the program.

  ## Algorithm
- The Vector Quantization algorithm is a technique used for image compression and decompression. It aims to reduce the size of an image while preserving its essential visual information. This algorithm works by dividing the image into smaller sections called vectors and representing these vectors with a codebook of predefined code vectors.

- The algorithm consists of two main steps: compression and decompression. During the compression phase, the image is divided into non-overlapping vectors of specified height and width. The codebook, which contains a set of representative code vectors, is created by iteratively splitting the vectors into two groups based on their similarity to the average vector. This process continues recursively until the desired codebook size is reached.

- To compress the image, each vector in the image is compared to the codebook vectors to find the closest match. The index of the closest code vector is assigned to the vector, forming a compressed representation of the image. This compressed representation, along with the codebook, is stored for later use in decompression.

- During decompression, the compressed representation of the image and the codebook are utilized to reconstruct the original image. Each index in the compressed vector is mapped to the corresponding code vector in the codebook, and the pixels of the reconstructed image are set to the values of the code vectors. The decompressed image is then obtained by assembling the reconstructed vectors.

- The Vector Quantization algorithm offers a trade-off between compression ratio and image quality. By adjusting the codebook size, vector dimensions, and other parameters, it is possible to achieve different levels of compression and quality. However, it's important to note that higher compression ratios may result in a loss of image details and fidelity.

- The implementation provided in the code sample performs image compression and decompression using the Vector Quantization algorithm. It allows the user to specify the vector dimensions, codebook size, and input image, providing a practical demonstration of the algorithm's functionality.

# License
This code is available under the [MIT License](https://opensource.org/license/mit/). Feel free to use and modify it as per your requirements.
