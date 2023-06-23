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
