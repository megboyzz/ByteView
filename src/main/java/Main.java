import java.io.File;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static String filter = ".+(\\.sb|\\.sba)$";

    private static boolean isMatch(String str, String regex){
        return Pattern.matches(regex,str);
    }

    public static void assertion(File file, String regexFilter){
        if(file != null & file.exists())
        for (String name : file.list()) {
            File f = new File(file.getAbsolutePath() + "//" + name);
            if(f.isDirectory()) assertion(f, regexFilter);
            else if(isMatch(f.getName(), regexFilter)){
                System.out.println(f.getAbsolutePath());
                byte[] byteFile = Util.fileAsByteArray(f);
                for(int i = 0; i < byteFile.length; i++){
                    Header anyHeader = Util.findAnyHeader(byteFile, i);
                    if(!anyHeader.equals(Header.NULL)) {
                        System.out.println("|\t" + anyHeader);
                        i += anyHeader.getValue().length;
                    }
                }
                System.out.println();
            }
        }
    }


    public static void main(String args[]) {

        //assertion(new File("D:\\com.ea.games.nfs13_na\\files\\"), filter);

        Util.printBytes("BULK".getBytes());

        //LangDumper.print8bytesAfterCDAT(new File("D:\\com.ea.games.nfs13_na\\files\\published\\layouts\\layouts.sb"));

/*
        Finder finder = null;

        for(int i = Headers.SBIN; i < Headers.CDAT + 1; i++){
            //finder = new Finder("D:/" + Headers.getTemplateName(i) + ".txt");
            //Finder.findAll(new File(Finder.path), i, 8, ".sb");
            System.out.println();
        }*/

    }

}
