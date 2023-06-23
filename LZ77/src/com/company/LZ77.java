package com.company;

import java.util.*;

public class LZ77 {

    public static void compression(){
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the text to compress ");
        String s = in.next();  //getting the string to be compressed
        String previous = "";  //string to put the compressed characters in
        String str;            //string to put the new sequence one by one
        int position, length, indx, tempIndx=0, sLen = s.length();
        char nextSymbol;

        //loops over the whole string S
        for (int i=0; i < sLen; i++){

            // if it's the first occurrence for the character, then push it in PREVIOUS
            if (!previous.contains(Character.toString(s.charAt(i)))){
                position = length = 0;
                nextSymbol = s.charAt(i);
                //printing the tag of the first occurrence of a character
                System.out.println("<" + position + "," + length + "," + nextSymbol + ">");
                previous += s.charAt(i);  //Append the new char -first occurrence- in previous
            }

            //not first occurrence? go inside that else
            else{
                int j= i+1;
                //copies the compressed part of s to previous
                previous = s.substring(0, i);
                int preLen = previous.length();

                for(int k=0; k < preLen; k++){

                    //put the chars of the uncompressed sequence in S -starting from i- one by one in str
                    str = s.substring(i,j);
                    //get the index of str's last occurrence in previous
                    indx = previous.lastIndexOf(str);

                    if (indx != -1) //means that we found str in previous
                    {
                        j++;  // we increase j to include another char to check again
                        tempIndx = indx; // to keep the index of the last occurrence str in the previous

                        if(j > sLen) // reach the end of the string we want to compress & all sequence in the previous
                        {
                            i = sLen; // to break from the outer for loop
                            position = previous.length() - tempIndx;
                            length = str.length() - 1;
                            nextSymbol = '0';
                            System.out.println("<" + position + "," + length + "," + nextSymbol + ">");
                            break;
                        }

                    }

                    //if we entered that else, it means that we found a new sequence that is not in previous
                    else{
                        i= preLen + (str.length()-1); // I = the index of the next character
                        position = preLen - tempIndx;
                        length = str.length() - 1;
                        nextSymbol = s.charAt(j-1);
                        System.out.println("<" + position + "," + length + "," + nextSymbol + ">");
                        break;
                    }
                } // end of k for loop
            }
        } // end of outer for loop (end of the string)
    } //end of compression function

    public static void decompression() {
        Scanner in = new Scanner(System.in);
        String s = "";
        System.out.println("Please enter number of tags :");
        int n = in.nextInt();
        System.out.println("Please enter the tags in this order:\nPosition Length Symbol ");
        while(n>0) {
            int position = in.nextInt();  //how many steps should we go back
            int length = in.nextInt();    //how many character should we take
            char nextSymbol = in.next().charAt(0);

            if(position == 0)      //if it is the first occurrence to the character
                s += nextSymbol;  //we append it directly to the string s

            else
            {
                int l = s.length();
                //initialize i = the position that we should get back to, and keeps going till we take length chars
                for (int i = l-position; i<(l-position)+length; i++) {
                    s += s.charAt(i);  //append each char to S

                }
                if (nextSymbol != '0')  // if the next character != null
                    s += nextSymbol; // we add the new symbol to the string s
            }
            n--;
        } // end of while

        // printing the deCompressed string
        System.out.println(s);

    } //end of decompression function

}
