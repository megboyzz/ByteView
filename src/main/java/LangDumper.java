import org.apache.commons.codec.binary.Hex;
import java.io.*;
import java.util.Locale;

public class LangDumper {

    private static String path = "D:\\NFSMW\\NFSMWCache\\com.ea.games.nfs13_na\\files\\published\\strings";

    private static String langFileName = "nfsmw_android.sb";

    public static int max = Integer.MIN_VALUE;


    public static void main(String args[]){

        File langPath = new File(path);

        for(String dirLangName : langPath.list()){

            String fullName = path + "\\" + dirLangName + "\\" + langFileName;

            File LangFile = new File(fullName);

            //print8bytesAfterCDAT(LangFile);

            /*
            byte[] b = new byte[(int) LangFile.length()];
            try {
                FileInputStream fileInputStream = new FileInputStream(LangFile);
                fileInputStream.read(b);
            } catch (Exception e) {}

            String buff = "";
            byte[] g = new byte[8];
            for (int i = 0; i < b.length; i++)
                if(
                        b[i] == cdat_header[0] &
                                b[i + 1] == cdat_header[1] &
                                b[i + 2] == cdat_header[2] &
                                b[i + 3] == cdat_header[3]

                ) {
                    int c = 0;
                    for(int k = i + 4; k < i + 12; k++){
                        buff = buff + " " + (b[k]);
                        g[c] = b[k];
                        c++;
                    }
                    break;
                }

            System.out.print(dirLangName + " | ");
            printBytes(g);
            System.out.println(" | " + b.length);*/


        }


    }

}
