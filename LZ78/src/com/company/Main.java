package com.company;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // Display the menu
        System.out.println("1. Compression");
        System.out.println("2. Decompression");

        System.out.println("Please enter your choice:");

        //Get user's choice
        int choice = in.nextInt();
        LZ78 code = new LZ78();
        // Do the suitable operation depends on the user's choice
        switch (choice) {
            case 1: code.compression();
                break;
            case 2:
                code.decompression();
                break;
            default: System.out.println("Invalid choice");
        }//end of switch
    }
}
