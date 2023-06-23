

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;

public class GUI {
    private String inputFile = "E:\\Arithmetic_Coding\\Arithmetic Coding\\src\\Input.txt";
    private String outputFile = "E:\\Arithmetic_Coding\\Arithmetic Coding\\src\\Decompressprop.txt";
    private String outputFile2 = "E:\\Arithmetic_Coding\\Arithmetic Coding\\src\\DecompressInput.txt";
    JFrame f;
    int n = -1;
    int buffer = -1;
    File outfile = null;

    public GUI() {
        f = new JFrame("Binary Arithmetic");

        JButton b = new JButton("Compress");
        b.setBounds(20, 150, 100, 40);
        JButton b2 = new JButton("Decompress");
        b.setBounds(20, 150, 100, 40);

        JLabel l1, l2;
        l1 = new JLabel("Compress");
        l1.setBounds(200, 50, 100, 30);
        l2 = new JLabel("Decompress");
        l2.setBounds(200, 80, 100, 30);
        JLabel l21 =new JLabel(inputFile);
        JLabel l22 =new JLabel(outputFile);
        JLabel l23 =new JLabel(outputFile2);
        l21.setBounds(200, 80, 100, 30);
        l22.setBounds(200, 80, 100, 30);
        l23.setBounds(200, 80, 100, 30);
        JLabel l24 =new JLabel("");
        l24.setBounds(200, 80, 100, 30);

        JLabel l25 =new JLabel("");
        l24.setBounds(200, 80, 100, 30);


        JPanel panel = new JPanel();

        panel.setBounds(15, 25, 480, 200);
        panel.setBackground(Color.gray);

        panel.setLayout(new GridLayout(4, 1, 20, 25));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        panel.add(l1);
        panel.add(l21);
        panel.add(b);//adding button in JFrame
        panel.add(l25);//adding button in JFrame

        JPanel panel2 = new JPanel();

        panel2.setBounds(15, 230, 480, 200);
        panel2.setBackground(Color.gray);
        panel2.setLayout(new GridLayout(5, 1, 20, 25));
        panel2.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel2.add(l2);
        panel2.add(l22);
        panel2.add(l23);
        panel2.add(b2);
        panel2.add(l24);

        f.add(panel);
        f.add(panel2);

        f.setSize(600, 500);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible

        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                BinaryArithmetic binaryArithmetic = new BinaryArithmetic();
                try {
                   l25.setText(binaryArithmetic.input());
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                BinaryArithmetic binaryArithmetic = new BinaryArithmetic();
                try {
                    l24.setText(binaryArithmetic.Inputdecompress());
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }

            }
        });

    }
}
