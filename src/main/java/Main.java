import com.sun.source.tree.LambdaExpressionTree;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {


    public static void main(String args[]) {

        ActionListener listener = e ->{
            File selectedFile = ((JFileChooser) e.getSource()).getSelectedFile();
            String leftAlignFormat = "| %-84s | %-23s |%n";

            System.out.format("+--------------------------------------------------------------------------------------+--------------------------+%n");
            System.out.format("|                                Name                                                  |          Header          |%n");
            System.out.format("+--------------------------------------------------------------------------------------+--------------------------+%n");
            Util.iterateAllFilesAndDoAction(selectedFile.getPath(), Util.regexSB, file -> {
                byte[] bytesAfterHeader = Util.get8bytesAfterHeader(file, Header.CDAT);
                String normBytes = Util.getNormBytes(bytesAfterHeader);
                System.out.format(leftAlignFormat, file.getName(), normBytes);

            });

            System.out.format("+--------------------------------------------------------------------------------------+--------------------------+%n");
        };

        // Вырезка dds сегмета и создание нормального dds c заголоаком dds
        FileAction action = file -> {

            byte[] byteFile = Util.fileAsByteArray(file);
            int DDSBegin = Util.findHeaderInByteFile(byteFile, Header.BARG.getValue());


            if(DDSBegin == -1) {
                System.out.println("DDS not found in " + file.getAbsolutePath());
                return;
            }


            DDSBegin += Header.BARG.getLength();

            int len = byteFile.length - DDSBegin;
            byte[] newByteFile = new byte[len];

            System.arraycopy(byteFile, DDSBegin, newByteFile, 0, newByteFile.length);
            File t = new File(file.getParent() + File.separator + file.getName() + ".dds");
            //System.out.println(t.getAbsolutePath());

            try{
                t.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(t);
                outputStream.write(Util.DDS_NORM_HEADER);
                outputStream.write(newByteFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        };

        FileAction HeaderAction = file -> {
            System.out.println(file.getAbsolutePath());
            Header[] values = Header.values();
            for (int i = 0; i < values.length - 1; i++) {
                int headerOffset = Util.findHeaderInFile(file, values[i]);

                System.out.println("offset( " + values[i] + " ) = " + headerOffset);

            }
            System.out.println("_________________");
        };

        FileAction fileAction = file -> {
            System.out.println("name = " + file.getName());
            ArrayList<String> dataAfterHeader = Util.getDataAfterHeader(file, Header.CDAT);
            for (int i = 0; i < dataAfterHeader.size(); i++) {
                System.out.println("\t" + i + " = " + dataAfterHeader.get(i));
            }
            System.out.println("______________________");
        };

        //Сравнение оригинальных и сжатых файлов
        FileAction zip = file -> {

            long beforeZip = Util.getSizeOf(file);

            File tempZip = Util.createTempFile("lol", ".test");

            ZipOutputStream zipOutputStream = new ZipOutputStream(Util.newFileOutputStream(tempZip));

            ZipEntry entry = new ZipEntry("1");

            try {
                zipOutputStream.putNextEntry(entry);

                byte[] buffer = Util.fileAsByteArray(file);

                zipOutputStream.write(buffer);

                zipOutputStream.closeEntry();
            } catch (IOException e) {
                e.printStackTrace();
            }

            long afterZip = Util.getSizeOf(tempZip);

            double k = (afterZip * 1.0) / (beforeZip * 1.0);

            if(k > 0.75)
            System.out.println(file.getName() + " - До сжатия " + beforeZip + ", после сжатия " + afterZip
                    + ", коэфициент = " + (afterZip * 1.0) / (beforeZip * 1.0));
            tempZip.delete();
        };

        FileAction sbin = file -> {
            System.out.print(Util.getNormBytes(Util.get8bytesAfterHeader(file, Header.SBIN)) + " ");
            System.out.print(Util.getNormBytes(Util.get8bytesAfterHeader(file, Header.ENUM)) + " ");
            System.out.println(file.getName());
        };


        GameCache cache = new GameCache("D:\\com.ea.games.nfs13_na\\");


        //Util.iterateAllFilesAndDoAction(cache.getMainPath(), Util.regexSB_SBA, sbin);

        File playlists = new File("D:\\com.ea.games.nfs13_na\\files\\published\\sounds\\music\\playlists.sb");
        int count = 0;
        for (String s : Util.getDataAfterHeader(playlists, Header.CDAT)) {
            if(s.contains("published/")) count++;
            System.out.println(s);
        }
        System.out.println(count);
        Sba a;
    }

}
