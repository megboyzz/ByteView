import org.apache.commons.codec.binary.Hex;

import java.io.File;
import java.io.FileInputStream;
import java.util.Locale;

public class Util {

    public static byte[] fileAsByteArray(File file){
        byte[] b = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(b);
        } catch (Exception e) {
            return null;
        }
        return b;
    }

    public static void print8bytesAfterCDAT(File file){

        byte[] b = fileAsByteArray(file);
        String buff = "";
        byte[] g = new byte[8];
        for (int i = 0; i < b.length - 3; i++)
            if(
                    b[i] == DHeaders.cdat_header[0] &
                            b[i + 1] == DHeaders.cdat_header[1] &
                            b[i + 2] == DHeaders.cdat_header[2] &
                            b[i + 3] == DHeaders.cdat_header[3]

            ) {
                int c = 0;
                for(int k = i + 4; k < i + 12; k++){
                    buff = buff + " " + (b[k]);
                    g[c] = b[k];
                    c++;
                }
                break;
            }

        System.out.print(file.getAbsolutePath().substring(42) + " | ");
        printBytes(g);
        System.out.println(" | " + b.length);

    }

    public static void printBytes(byte[] bytes){

        if(bytes.length > 8) return;

        String str = new String(Hex.encodeHex(bytes));
        str = str.toUpperCase(Locale.ROOT);

        for(String s : str.split("(?<=\\G.{2})"))
            System.out.print( s + " ");

    }

    public static String getNormBytes(byte[] bytes){

        String str = new String(Hex.encodeHex(bytes));
        str = str.toUpperCase(Locale.ROOT);

        String r = "";
        for(String s : str.split("(?<=\\G.{2})"))
            r += s + " ";

        return r;

    }

    /**
     *
     * @return
     */
    public static Header findAnyHeader(byte[] byteFile, int currentStep){
        if(currentStep > byteFile.length)
            throw new RuntimeException("currentStep > byteFile.length");

        for(int i = 0; i < Header.values().length; i++){
            int count = 0;
            for(int j = 0; j < Header.values()[i].getValue().length; j++){
                if(byteFile[j + currentStep] == Header.values()[i].getValue()[j]) count++;
            }
            if(count == Header.values()[i].getValue().length) return Header.values()[i];
        }
        return Header.NULL;
    }

    public static boolean isHeader(byte[] byteFile, int currentStep){
        return !findAnyHeader(byteFile, currentStep).equals(Header.NULL);
    }

}
