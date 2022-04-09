package encryptdecrypt;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        String mode = "enc";
        int idx = 0;
        int key = 0;
        File outFile = null;
        String data = "";
        boolean writeToFile = false;
        boolean useShift = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i].contains("-data")) {
                data = args[i += 1];
            } else if (args[i].contains("-in")) {
                File file = new File(args[i += 1]);
                try {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String st;
                    while ((st = br.readLine()) != null)
                        data = st;
                } catch (IOException ex) {
                    System.out.println("Error");
                }
            }
            if (args[i].contains("-mode")) {
                mode = args[i += 1];
            }
            if (args[i].contains("-key")) {
                key = Integer.parseInt(args[i += 1]);
            }
            if (args[i].contains("-out")) {
                writeToFile = true;
                outFile = new File(args[i += 1]);
            }
            if (args[i].contains("-alg")) {
                if (args[i += 1].equals("shift"))
                    useShift = true;
            }
        }

        if (mode.equals("dec")) {
            if (useShift) {
                if(writeToFile) {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
                    writer.write(shiftDecrypt(data, key));
                    writer.close();
                }
                else {
                    System.out.println(shiftDecrypt(data, key));
                }

            } else
            if(writeToFile) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
                writer.write(decrypt(data, key));
                writer.close();
            }
            else {
                System.out.println(decrypt(data, key));
            }
        } else {
            if (useShift) {
                if(writeToFile) {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
                    writer.write(shiftEncrypt(data, key));
                    writer.close();
                }
                else {
                    System.out.println(shiftEncrypt(data, key));
                }

            } else
            if(writeToFile) {
                BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
                writer.write(encrypt(data, key));
                writer.close();
            }
            else {
                System.out.println(encrypt(data, key));
            }
        }


    }

    static String encrypt(String msg, int shift) {
        String s = "";
        int len = msg.length();
        for (int x = 0; x < len; x++) {
            char c = (char) (msg.charAt(x) + shift);
            s += c;
        }
        return s;
    }

    static String decrypt(String cipher, int key) {
        String s = "";
        int len = cipher.length();
        for (int i = 0; i < len; i++) {
            char c = (char) (cipher.charAt(i) - key);
            s += c;
        }
        return s;
    }

    static String shiftEncrypt(String msg, int shift) {
        String s = "";
        int len = msg.length();
        for (int x = 0; x < len; x++) {
            char c = (char) (msg.charAt(x) + shift);
            char currentChar = msg.charAt(x);
            if((currentChar >=0 && currentChar <=64)||(currentChar >=91 && currentChar <=96)|| (currentChar >=123 && currentChar <=127))
                s+= currentChar;
            else if ((c > 'z')||(c > 'Z' && c < 'a'))
                s += (char) (msg.charAt(x) - (26 - shift));
            else
                s += c;
        }
        return s;
    }

    static String shiftDecrypt(String cipher, int key) {
        String s = "";
        int len = cipher.length();
        for (int i = 0; i < len; i++) {
            char c = (char) (cipher.charAt(i) - key);
            if (c < 'a' || c < 'A') {
                s += (char) (cipher.charAt(i) + (26 - key));
            } else {
                s += c;
            }
        }
        return s;
    }
}
