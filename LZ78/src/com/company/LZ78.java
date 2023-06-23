package com.company;

import java.util.*;
import java.util.HashMap;

public class LZ78 {

    // search for substring in dictionary and returns it's key
    public static Integer Search(HashMap <Integer,String> dictionary, String str){
        for (Integer i : dictionary.keySet()) {
            if (dictionary.get(i).equals(str) ) {
                return i;
            }

        }
        return 0;
    }

    public static void compression() {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the text to compress ");
        String str = in.next();  //getting the string to be compressed
        HashMap <Integer,String> dictionary = new HashMap <Integer,String>(); // dictionary to store the data
        dictionary.put(0,"");     //default position
        Integer key = 1;    //next key
        Integer l = str.length();
        for (Integer i = 0 ; i < l ; i++){

            //put the  of uncompressed sequence in Str -starting from i- one by one in s
            String s ="";
            s+= str.charAt(i);

            while (Search(dictionary, s) != 0 && i != l - 1 ) //the string not ended yet and the sequence is in the dictionary
            {
                i++;  // we increase i to include another char to check again
                s += str.charAt(i); //append the new char to the string
            }
            if(i != l - 1) // if the string str not ended yet and the sequence is not in the dictionary
            {
                System.out.println( Search(dictionary, s.substring(0,s.length()-1)) + " " + str.charAt(i) );
                dictionary.put(key++, s); //put the new sequence in the dictionary and increase the key
            }
            else // we reached the end of the string and still remaining one tag
            {
                if(s.length()>1) // need to get value of subsequence from dictionary
                    System.out.println( Search(dictionary, s.substring(0,s.length()-1 ))+ " "+s.charAt(s.length()-1) );
                else
                {
                    if(Search(dictionary, s)==0) { //if what's inside s is not in the dictionary
                        dictionary.put(key, s);  //add it
                    }
                    System.out.println( "0 " + s ); //print its tag
                }
            }
        } // end of outer for loop (end of the string)
        System.out.println(dictionary);
    } // end of compression

    public static void decompression() {
        Scanner in = new Scanner(System.in);
        HashMap <Integer,String> dictionary = new HashMap<Integer,String>(); // dictionary for storing the data
        dictionary.put(0,"");
        Integer key = 1;
        String s="", str = "";
        System.out.println("Please enter number of tags :");
        Integer n = in.nextInt();
        System.out.println("Please enter the tags in this order:\nIndex  Symbol ");
        while(n>0) {
            Integer idx = in.nextInt();
            char symbol = in.next().charAt(0);
            str = dictionary.get(idx) + symbol; // getting the char at index (idx) from  the dictionary and append it with next symbol
            s += str; //append the sequence to string s
            if(Search(dictionary,str) == 0){  //if what's inside str is not in the dictionary
                dictionary.put(key++,str);    // add it and then increase the key
            }
            n--;
        }// end of while

        // printing the deCompressed string
        System.out.println(s);
        System.out.println(dictionary);

    } //end of decompression

}

