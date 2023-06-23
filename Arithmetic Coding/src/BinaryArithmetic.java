import java.util.*;
import java.io.*;

public class BinaryArithmetic {
    public String input() throws FileNotFoundException {
        String path = "E:\\Arithmetic_Coding\\Arithmetic Coding\\src\\Input.txt";
        File myObj = new File(path);
        Scanner MyReader = new Scanner(myObj);
        String str = "";
        while (MyReader.hasNextLine()) {
            String info = MyReader.nextLine();
            str += info;
        }
        MyReader.close();
        System.out.println(str);
        return compress(str);
    }

    public String compress(String text) {
        HashMap<String, Double> probability = new HashMap();
        HashMap<Character, Integer> characterInText = new HashMap();
        for (int i = 0; i < text.length(); i++) {
            if (characterInText.get(text.charAt(i)) != null) {
                characterInText.replace(text.charAt(i), characterInText.get(text.charAt(i)), characterInText.get(text.charAt(i)) + 1);
            } else {
                characterInText.put(text.charAt(i), 1);
            }
        }

        for (char name : characterInText.keySet()) {
            probability.put(String.valueOf(name), (double) (characterInText.get(name)) / text.length());
        }
        HashMap<String, Double> table = new HashMap();
        double prob = 0.0;
        for (String S : probability.keySet()) {
            table.put(S + "l", prob);
            prob += probability.get(S);
            table.put(S + "h", prob);
        }
        HashMap<String, Double> UpdateTable = new HashMap();
        for (String S : table.keySet()) {
            UpdateTable.put(S, table.get(S));
        }
        int k = 1;
        double lowerProbability = 1.0f;
        for (String S : probability.keySet()) {
            if (probability.get(S) < lowerProbability) {
                lowerProbability = probability.get(S);
            }
        }
        while ((1 / Math.pow(k, 2)) >= lowerProbability) {
            k++;
        }
        double lower = 0.0;
        double higher = 0.0;
        double range = 0.0;
        String code = "";
        for (int i = 0; i < text.length(); i++) {
            for (String s : UpdateTable.keySet()) {
                if (s.equals(text.charAt(i) + "l")) {
                    lower = UpdateTable.get(s);
                }
                if (s.equals(text.charAt(i) + "h")) {
                    higher = UpdateTable.get(s);
                }
            }
            while (higher < 0.5 || lower > 0.5) {
                while (higher < 0.5) {
                    //                E1
                    lower *= 2;
                    higher *= 2;
                    code += '0';
                }
                while (lower > 0.5) {
                    //                E2
                    lower = (lower - 0.5) * 2;
                    higher = (higher - 0.5) * 2;
                    code += '1';
                }
            }
            range = higher - lower;
            for (String s : UpdateTable.keySet()) {
                UpdateTable.replace(s, UpdateTable.get(s), lower + (range * table.get(s)));
            }
        }
        code += '1';
        for (int i = 0; i < k - 1; i++) {
            code += '0';
        }

        for (String s : probability.keySet()) {
            System.out.println(s + " " + probability.get(s));
        }
        return code;
    }

    static double codeToDecimal(int n, int k) {
        int num = n;
        int dec_value = 0;
        int base = 1;
        int temp = num;
        while (temp > 0) {
            int last_digit = temp % 10;
            temp = temp / 10;
            dec_value += last_digit * base;
            base = base * 2;
        }
        return dec_value / Math.pow(2, k);
    }

    public String Inputdecompress() throws FileNotFoundException {
        HashMap<String, Double> decprobability = new HashMap();
        String probility = "E:\\Arithmetic_Coding\\Arithmetic Coding\\src\\Decompressprop.txt";
        File myObj = new File(probility);
        Scanner fin = new Scanner(new FileReader(myObj));
        String pattern = " ";
        fin.useDelimiter(pattern);
        int index = 0;
        while (fin.hasNext()) {
            String s = fin.nextLine();
            String[] arr = s.split(pattern);
            String ch = arr[0];
            double prob = Double.valueOf(arr[1]);
            decprobability.put(ch,prob);
            ++index;
        }
        String path = "E:\\Arithmetic_Coding\\Arithmetic Coding\\src\\DecompressInput.txt";
        File myObj1 = new File(path);
        Scanner MyReader = new Scanner(myObj1);
        String str = "";
        while (MyReader.hasNextLine()) {
            String info = MyReader.nextLine();
            str += info;
        }
        MyReader.close();
        return decompress(str,decprobability,index);
    }


    public String decompress(String code,HashMap<String, Double> pro,int index) {
        HashMap<String, Double> symbols = new HashMap<String, Double>();
        HashMap<String, Double> symLow = new HashMap<String, Double>();
        HashMap<String, Double> symHigh = new HashMap<String, Double>();
        double prevProb = 0.0, smallest = 2.0;
        int size = index;
        for (String i : pro.keySet()) {
            String ch = i;
            Double prob = pro.get(i);
            if (prob < smallest)
                smallest = prob;
//            System.out.println(ch+" l "+prob);
            symbols.put(ch, prob);
            if (index == 0) {
                symLow.put(ch, 0.0);
                symHigh.put(ch, prob);
            } else {
                symLow.put(ch, prevProb);
                symHigh.put(ch, prevProb + prob);
            }
            prevProb += prob;
        }
        String bits = code;
//        System.out.println(symbols);
//        System.out.println(symLow);
//        System.out.println(symHigh);
        int k = 0, E = 0;
        while ((1 / Math.pow(2, k)) > smallest) {
            k++;
        }
        String text = "";
        String theSymbol;
        double originalCode, intervalCode;
        double highInterval = 1.0, lowInterval = 0.0, highPrevInterval = 1.0, lowPrevInterval = 0.0;
        int end = 10;
        boolean f = true;
        while (bits.length() > k + E || end != 1) {
            originalCode = codeToDecimal(Integer.parseInt(bits.substring(E, k + E)), k);
            intervalCode = (originalCode - lowPrevInterval) / (highPrevInterval - lowPrevInterval);
//            System.out.println("oCode" + originalCode);
//            System.out.println("ICode" + intervalCode);
            for (String key : symbols.keySet()) {
                if (symHigh.get(key) >= intervalCode && symLow.get(key) <= intervalCode) {
                    theSymbol = key;
                    text += theSymbol;

                    double high = symHigh.get(key);
                    highInterval = lowPrevInterval + (highPrevInterval - lowPrevInterval) * high;
                    double low = symLow.get(key);
                    lowInterval = lowPrevInterval + (highPrevInterval - lowPrevInterval) * low;
                    break;
                }
            }
//            System.out.println(highInterval);
//            System.out.println(lowInterval);
            while (!(highInterval >= 0.5 && lowInterval <= 0.5)) {
//                System.out.println("pppppppp");
                while (highInterval > 0.5 && lowInterval > 0.5) {
                    highInterval = (highInterval - 0.5) * 2;
                    lowInterval = (lowInterval - 0.5) * 2;
                    E++;
                }
                while (highInterval < 0.5 && lowInterval < 0.5) {
                    highInterval *= 2;
                    lowInterval *= 2;
                    E++;
                }
            }
//            System.out.println("E " + E);
            highPrevInterval = highInterval;
            lowPrevInterval = lowInterval;
            end++;
            if (bits.length() == k + E && f) {
                f = false;
                end = 0;
            }
        }
        return text;
    }
}
