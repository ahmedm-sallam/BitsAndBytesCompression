import java.io.*;
import java.util.*;


public class VectorQuantization
{
    // calculate the average of all the vectors
    public static Vector<Integer> vectorAverage(Vector<Vector<Integer>> Vectors)
    {
        int[] summation = new int[Vectors.get(0).size()];   //summation's size = the size of any vector

        for (Vector<Integer> vector : Vectors )    //adds all the vectors into one vector (summation)
        {
            for (int i = 0; i < vector.size(); i++)
                summation[i] += vector.get(i);
        }

        Vector<Integer> averageVector = new Vector<>();   // will store the average vector
        for (int i = 0; i < summation.length; i++)      //loops over each value in summation and divide it over the size
            averageVector.add(summation[i] / Vectors.size());

        return averageVector;         //return average
    }


    // getting the distance between two vectors
    public static int calculateDistance(Vector<Integer> x, Vector<Integer> y, int splitNumber) //splitNumber = 1 or -1
    {
        int distance = 0;
        for (int i = 0; i < x.size(); i++)
            distance += Math.pow(x.get(i) - y.get(i) + splitNumber, 2);

        int finalDistance = (int) Math.sqrt(distance);
        return finalDistance ;
    }


    public static void calculateCodeBook(int codeBookSize, Vector<Vector<Integer>> Vectors, Vector<Vector<Integer>> codeBook)
    {
        if(codeBookSize == 1 || Vectors.size() == 0)
        {
            if(Vectors.size() > 0)
                codeBook.add(vectorAverage(Vectors));
            return;
        }
        //Split
        Vector<Vector<Integer>> leftVectors = new Vector<>();
        Vector<Vector<Integer>> rightVectors =  new Vector<>();

        //Calculate Average Vector
        Vector<Integer> Average = vectorAverage(Vectors);   //root

        //Calculate Euclidean Distance
        for (Vector<Integer> vec : Vectors )
        {
            int distanceRight = calculateDistance(vec, Average,  1);
            int distanceLeft = calculateDistance(vec, Average, -1);

            //Add To Right OR Left Vector
            if(distanceLeft <= distanceRight)
                leftVectors.add(vec);
            else
                rightVectors.add(vec);
        }

        //Recurse
        calculateCodeBook(codeBookSize / 2, leftVectors, codeBook);
        calculateCodeBook(codeBookSize / 2, rightVectors, codeBook);
    }  //End of calculateCodeBook


    //Default Value Overwriting
    public static int calculateDistance(Vector<Integer> x, Vector<Integer> y)
    {
        return calculateDistance(x, y, 0);
    }

    public static Vector<Integer> getCompressedVector(Vector<Vector<Integer>> Vectors, Vector<Vector<Integer>> codeBook)
    {
        Vector<Integer> compressedVector = new Vector<>();  // hold indices of codebook based on the original vector

        for (Vector<Integer> vector : Vectors )
        {
            int smallestDistance = calculateDistance(vector, codeBook.get(0));  //initial value
            int smallestIndex = 0;

            //Find the minimum Distance
            for (int i = 1; i < codeBook.size(); i++)
            {
                int tempDistance = calculateDistance(vector, codeBook.get(i));
                if(tempDistance < smallestDistance)
                {
                    smallestDistance = tempDistance;
                    smallestIndex = i;
                }
            }

            //Map the i'th Vector to the [i] in codeBook
            compressedVector.add(smallestIndex);
        }
        return compressedVector;
    } //End of getCompressedVector


    public static boolean Compress(int vectorHeight, int vectorWidth, int codeBookSize, String Path) throws IOException
    {
        //Read Image
        int[][] image = ImageRW.readImage(Path);

        //Calculate new dimensions to vector sizes ratio.
        int originalHeight = ImageRW.height;
        int originalWidth  = ImageRW.width;
        int scaledHeight, scaledWidth;

        if(originalHeight % vectorHeight == 0)
            scaledHeight = originalHeight;
        else
            scaledHeight = ((originalHeight / vectorHeight) + 1) * vectorHeight;

        if(originalWidth % vectorWidth  == 0)
            scaledWidth  = originalWidth;
        else
            scaledWidth = ((originalWidth / vectorWidth) + 1) * vectorWidth;

        //new Scaled Image (Adding Padding)
        int[][] scaledImage = new int[scaledHeight][scaledWidth];

        for (int i = 0; i < scaledHeight; i++)
        {
            int x, y;
            if(i >= originalHeight)  //repeating the pixels of the last row
                x = originalHeight - 1;
            else                    //keeping the row as it is
                x = i;
            for (int j = 0; j < scaledWidth; j++)
            {
                if(j >= originalWidth)  //repeating the pixels of the last column
                    y = originalWidth - 1;
                else                   //keeping the column as it is
                    y = j;

                scaledImage[i][j] = image[x][y];
            }
        }

        //Create Array Of Vectors
        Vector<Vector<Integer>> Vectors = new Vector<>();

        //Divide the scaled image into Vectors and fill the array with Vectors
        for (int i = 0; i < scaledHeight; i+= vectorHeight)
        {
            for (int j = 0; j < scaledWidth; j+= vectorWidth)
            {
                Vectors.add(new Vector<>());                      //initialize the vector
                for (int x = i; x < i + vectorHeight; x++)
                {
                    for (int y = j; y < j + vectorWidth; y++)
                    {
                        Vectors.lastElement().add(scaledImage[x][y]);   //fill the vector
                    }
                }
            }
        }

        //Create Array to hold codeBook Vectors  (2D array)
        Vector<Vector<Integer>> codeBook = new Vector<>();

        //Fill codeBook Vector (The recursive part)
        calculateCodeBook(codeBookSize, Vectors, codeBook);

        //Optimize the vectors (map from code book)
        Vector<Integer> compressedVector = getCompressedVector(Vectors, codeBook);


        //Store the compressed vector (compressed stream) in a file
        PrintWriter outputFile = new PrintWriter("compressedVector.txt");
        for(int i =0; i < compressedVector.size(); i++)
        {
            outputFile.print(compressedVector.get(i) + " ");
        }
        outputFile.close();

        //Store the code Book in a file
        PrintWriter outputFile1 = new PrintWriter("codeBook.txt");
        //store scaled height & scaled width
        outputFile1.println(scaledHeight);
        outputFile1.println(scaledWidth);
        //store vector height & vector width
        outputFile1.println(vectorHeight);
        outputFile1.println(vectorWidth);
        //write the code book on the file
        for(int i =0; i < codeBook.size(); i++)
        {
            for(int j = 0; j < codeBook.get(i).size(); j++)
            {
                outputFile1.print(codeBook.get(i).get(j) + " ");
            }
            outputFile1.println();
        }
        outputFile1.close();

        return true;
    } //End of Compress


    static boolean Decompress(String Path) throws IOException, ClassNotFoundException
    {
        //Read the compressed vector (compressed stream) from the file
        Scanner sc1 = new Scanner(new BufferedReader(new FileReader("compressedVector.txt")));
        Vector <Integer> compressedVector = new Vector<>();
        while(sc1.hasNextLine())
        {
            String[] line = sc1.nextLine().trim().split(" ");
            for (int j = 0; j < line.length; j++)
            {
                compressedVector.add(Integer.parseInt(line[j]));
            }
        }

        //Read the code book from the file
        BufferedReader reader = new BufferedReader(new FileReader("codeBook.txt"));

        int lines = 0;
        while (reader.readLine() != null)
            lines++;
        reader.close();
        //read scaledHeight, scaledWidth, vectorHeight & vectorWidth from the code book file
        Scanner sc = new Scanner(new BufferedReader(new FileReader("codeBook.txt")));
        int z = 4;
        int scaledHeight = 0, scaledWidth = 0;
        int vectorHeight = 0, vectorWidth = 0;
        while(z > 0 && sc.hasNextLine())
        {
            if (z == 4)
                scaledHeight = Integer.parseInt(sc.nextLine());
            if( z == 3)
                scaledWidth = Integer.parseInt(sc.nextLine());
            if( z == 2)
                vectorHeight = Integer.parseInt(sc.nextLine());
            if( z == 1)
                vectorWidth = Integer.parseInt(sc.nextLine());
            z--;
        }

        int c = 1;
        Vector <Vector<Integer>> codeBook = new Vector<>();
        while(sc.hasNextLine())
        {
            if(c == 1 || c == 2 || c==3 ||c==4)
            {
                c++;
                continue;
            }

            String[] line = sc.nextLine().trim().split(" ");
            codeBook.add(new Vector<>());
            for (int j = 0; j < line.length; j++)
            {
                codeBook.lastElement().add(Integer.parseInt(line[j]));
            }

        }

        int[][] newImage = new int[scaledHeight][scaledWidth];
        for (int i = 0; i < compressedVector.size(); i++)
        {
            int x = i / (scaledWidth / vectorWidth);
            int y = i % (scaledWidth / vectorWidth);
            x *= vectorHeight;
            y *= vectorWidth;
            int v = 0; //pointer for each vector inside the codebook 2D vector
            int indx = compressedVector.get(i); //pointer for the vectors in codebook (points vector by vector)
            for (int j = x; j < x + vectorHeight; j++)
            {
                for (int k = y; k < y + vectorWidth; k++)
                {
                    newImage[j][k] = codeBook.get(indx).get(v++);
                }
            }
        }

        String path = Path.substring(0,Path.lastIndexOf('.')) + "_Compressed.jpg";
        ImageRW.writeImage(newImage, scaledWidth, scaledHeight, path);

        return true;
    }


}   //End of vectorQuantization