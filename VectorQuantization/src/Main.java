import java.io.IOException;
import java.util.*;


public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // write your code here
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the image name with extension .jpg : ");
        String path = input.nextLine();

        System.out.println("Do you want to perform \n1-Compression  \n2-Decompression");
        int choice = input.nextInt();

        VectorQuantization obj = new VectorQuantization();

        if(choice == 1)                      //compression
        {
            System.out.println("Please, Enter Vector height");
            int height = input.nextInt();

            System.out.println("Please, Enter Vector width");
            int width = input.nextInt();

            System.out.println("Please, Enter Vector code book size");
            int codeBookSize = input.nextInt();

            boolean flag = obj.Compress(height, width, codeBookSize, path);
            System.out.println(flag);
        }
        else if (choice == 2)               //decompression
        {
            boolean flag = obj.Decompress(path);
            System.out.println(flag);
        }
        else
        {
            System.out.println("Invalid input");
        }
    }


}
