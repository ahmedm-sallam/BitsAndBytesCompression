import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;
import javax.swing.*;

public class Huffman implements Comparator<Node> , ActionListener{
    protected JButton button1, button2;
    Huffman(){
        JFrame frame = new JFrame("Huffman");

        button1 = new JButton("Compression");
        button2 = new JButton("Decompression");
        button1.addActionListener(this);
        button2.addActionListener(this);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        panel.setLayout(new GridLayout(0,1));
        panel.add(button1);
        panel.add(button2);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Huffman");
        frame.pack();
        frame.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == button1)
        {
            try{
                File file = new File("input.txt");
                BufferedReader br = new BufferedReader(new FileReader(file));
                String S;
                while((S = br.readLine())!=null)
                    Compress(S);
            }catch(IOException e){
                System.out.println("An error occurred.");
            }

        }
        else if (evt.getSource() == button2)
        {
            try{
                File file2 = new File("compressed.txt");
                BufferedReader brr = new BufferedReader(new FileReader(file2));
                String SS;
                while((SS = brr.readLine())!=null)
                    Decompress(SS);
            }catch(IOException e){
                System.out.println("An error occurred.");
            }
        }
    }

    public static HashMap<Character,Integer> map = new HashMap<Character,Integer>();
    public static HashMap<Character, String> dictionaryCode = new HashMap<>();

    @Override
    public int compare(Node o1, Node o2) {
        return o1.probability.compareTo(o2.probability);
    }

    public static void calcSeq(String s){
        for(int i = 0; i < s.length(); i++){
            Character c = s.charAt(i);

            if(map.containsKey(c)){
                map.put(c,map.get(c)+1);
            }else{
                map.put(c,1);
            }

        }
    }

    public static void setCode(Node root){
        if(root == null){
            return;
        }
        if(root.parent == null){
            root.left.code = "1";
            if(root.left.data.length()==1) dictionaryCode.put(root.left.data.charAt(0), root.left.code);
            if (root.right!=null){
                root.right.code = "0";
                if(root.right.data.length()==1) dictionaryCode.put(root.right.data.charAt(0), root.right.code);
            }
            setCode(root.left);
            setCode(root.right);
        }
        else{
            if(root.left!=null){
                root.left.code = root.code + "1";
                if(root.left.data.length()==1) dictionaryCode.put(root.left.data.charAt(0), root.left.code);
                setCode(root.left);
            }
            if(root.right!=null){
                root.right.code = root.code + "0";
                if(root.right.data.length()==1) dictionaryCode.put(root.right.data.charAt(0), root.right.code);
                setCode(root.right);
            }
        }
    }

    public static String code;
    public static void getCode(Node root,String value){
        if(root == null){
            return;
        }
        if (root.data.equals(value)){
            code = root.code;
        }
        else{
            getCode(root.left, value);
            getCode(root.right, value);
        }
    }


    public static void Compress (String s){

        Node root = new Node();
        ArrayList<Node> arr = new ArrayList<>();
        String output="";
        calcSeq(s);
        for ( Map.Entry<Character,Integer> entry : map.entrySet()) {
            Character key = entry.getKey();
            Integer val = entry.getValue();
            Node n = new Node();
            n.data = ""+ key;
            n.probability = val;
            arr.add(n);
        }
        //building nodes
        while(arr.size()>2){
            Collections.sort(arr , new Huffman());
            Node n = new Node();
            n.left = arr.get(0);
            n.right = arr.get(1);
            n.probability = n.left.probability = + n.right.probability;
            n.left.parent = n;
            n.right.parent = n;
            arr.remove(0);
            arr.remove(0);
            arr.add(n);
        }
        //building tree
        if(arr.size()==2){
            root.left = arr.get(0);
            root.right = arr.get(1);
            root.left.parent = root;
            root.right.parent = root;
            arr.remove(0);
            arr.remove(0);
        }else{          //if one character
            root.left = arr.get(0);
            root.left.parent = root;
            arr.remove(0);
        }
        setCode(root);
        for(int i=0;i<s.length();i++) {
            char c = s.charAt(i);
            getCode(root, "" + c);
            output += code;

        }
        // write the compressed code into a file => compressed.txt
        try{
            FileWriter compressed = new FileWriter("compressed.txt");
            compressed.write(output);
            compressed.close();
        }
        catch(IOException e){
            System.out.println("An error occurred.");
        }

        try{
            FileWriter dictionary = new FileWriter("dictionary.txt");
            for ( Map.Entry<Character,String> entry : dictionaryCode.entrySet()) {
                Character key = entry.getKey();
                String val = entry.getValue();
                dictionary.write(key);
                dictionary.write(" ");
                dictionary.write(val);
                dictionary.write("\n");
            }
            dictionary.close();
        }
        catch(IOException e){
            System.out.println("An error occurred.");
        }

        System.out.println(map);
    }//end of compress function


    public static HashMap<Character, String> dictionaryCompressed = new HashMap<>();
    public static void Decompress(String c){
        String output="";
        try {
            FileInputStream fileStream = new FileInputStream("dictionary.txt");
            Scanner sc = new Scanner(fileStream);
            while(sc.hasNextLine())
            {
                String line = sc.nextLine();
                String [] str = line.split(" ",2);
                dictionaryCompressed.put(str[0].charAt(0), str[1]);
            }
            sc.close();
        }
        catch(IOException e){
            System.out.println("An error occurred.");
        }

        String temp = ""+c.charAt(0);
        int size = c.length();
        int tembIndx=-1;
        for(int i=1; i<size; i++){
            boolean flag = false;
            for ( Map.Entry<Character,String> entry : dictionaryCompressed.entrySet()){
                Character key = entry.getKey();
                String val = entry.getValue();
                if(temp.equals(val)){
                    output += key;
                    flag = true;
                    temp=""+c.charAt(i);
                    tembIndx = i;
                    break;
                }
            }
            if(!flag ){
                temp+=c.charAt(i);
            }
        }

       String t = c.substring(tembIndx, size);
        for ( Map.Entry<Character,String> entry : dictionaryCompressed.entrySet()) {
            Character key = entry.getKey();
            String val = entry.getValue();
            if(t.equals(val)) output += key;
        }


        try{
            FileWriter decompressed = new FileWriter("decompressed.txt");
            decompressed.write(output);
            decompressed.close();
        }
        catch(IOException e){
            System.out.println("An error occurred.");
        }

    }//end of decompress


    public static void main(String[] args) throws Exception{
        new Huffman();
        Scanner in = new Scanner(System.in);
        // Display the menu
        System.out.println("1. Compression");
        System.out.println("2. Decompression");

        System.out.println("Please enter your choice:");

        //Get user's choice
        int choice = in.nextInt();
        // Do the suitable operation depends on the user's choice
        switch (choice) {
            case 1:
            {
                File file = new File("input.txt");
                BufferedReader br = new BufferedReader(new FileReader(file));
                String S;
                while((S = br.readLine())!=null)
                    Compress(S);
                break;
            }
            case 2:
            {
                File file2 = new File("compressed.txt");
                BufferedReader brr = new BufferedReader(new FileReader(file2));
                String SS;
                while((SS = brr.readLine())!=null)
                    Decompress(SS);
                break;
            }
            default: System.out.println("Invalid choice");
        }//end of switch

    }//end of main

}//end of huffman class
