import java.io.*;

public class Finder {

    private String path;

    private boolean onlyConsole = false;

    private File MainPath;

    private static FileWriter writer = null;

    //private

    public void print(String text){

        try{
            writer.write(text);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

    }

    public static void println(String text){

        try{
            writer.write(text);
            writer.append('\n');
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

    }



    Finder(String path){
        this.path = path;
        MainPath = new File(path);

    }

    Finder(String FileToWrite, String path){
        this.path = path;
        try {
            writer = new FileWriter(FileToWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }

        MainPath = new File(path);

    }


    public static byte[] fileToBytes(File file){

        byte[] b = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(b);
        } catch (Exception e) {}

        return b;

    }

    static byte[] findBytes(byte[] b, byte[] temp, int offset) {

        if(offset > b.length | offset + temp.length > b.length) return new byte[0];

        byte[] found = new byte[offset];

        int i1 = 0;
        for(int i = 0; i < b.length - temp.length; i++) {
            if(b[i] == temp[0])
                for(int j = 0; j < temp.length; j++) {
                    if (b[i + j] == temp[j]) i1++;
                    else{
                        i1 = 0;
                        break;
                    }
                    if(i1 == temp.length){
                        for(int k = 0; k < offset; k++) {
                            found[k] = b[k + i + temp.length];
                        }
                        return found;

                    }
                }
        }

        return found;

    }

    //Метод для поиска
    public static void findAll(File file, int template, int offset, String filter){

        for(String name : file.list()){
            File f = new File(file.getAbsolutePath() + "//" + name);
            if(f.isDirectory()) findAll(f, template, offset, filter);
            else if(f.getName().endsWith(filter)) {

                byte[] fileInBytes = fileToBytes(f);

                byte[] foundBytes = findBytes(fileInBytes, DHeaders.getTemplate(template), offset);

                String pathToFile = f.getAbsolutePath().substring(42);
                String header     = DHeaders.getTemplateName(template);
                String foundBytesStr = Util.getNormBytes(foundBytes);

                String res = String.format("%160s | %s | %s | bytes: %d", pathToFile, header, foundBytesStr, fileInBytes.length);
                System.out.println(res);
                println(res);
            }
        }
    }



}
