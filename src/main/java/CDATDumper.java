import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CDATDumper {
    public static void main(String ars[]) throws IOException {

        String cmd = "D:\\offzip\\offzip.exe";
        BufferedReader stdInput = new BufferedReader(new InputStreamReader( Runtime.getRuntime().exec(cmd).getInputStream() ));

        String s ;
        System.out.println(stdInput == null);
        while ((s = stdInput.readLine()) != null) {
            System.out.println(s);
        }
             /*
        if(file.isDirectory()){
            for(String name : file.list())
                finder(new File(file.getAbsolutePath() + "\\" + name));
        }
        else {
            System.out.println(file.getAbsolutePath() + " " + file.isFile());

            //LangDumper.print8byteAfterCDAT(file);
            /*
            System.out.println(file.getAbsolutePath() + " " + file.isFile());
            println(file.getAbsolutePath() + " " + file.isFile());
            String cmd = "D:\\offzip\\offzip.exe -S " + file.getAbsolutePath() + "";
            try {
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(cmd).getInputStream()));
                String s;
                while ((s = stdInput.readLine()) != null) {
                    System.out.println(s);
                    println(s);
                }

            }catch (Exception e){}

            byte[] b = new byte[(int) file.length()];
            try {
                FileInputStream fileInputStream = new FileInputStream(file);
                fileInputStream.read(b);
            } catch (Exception e) {}
            count++;

        }

        */
    }
}
