import org.apache.commons.codec.binary.Hex;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Класс функций-утилит для работы с фалами sb и sba
 */
public class Util {
    /**
     * Фильтр для всех файлов кеша
     */
    public static final String regexSB_SBA = ".+(\\.sb|\\.sba)$";
    public static final String regexSBA = ".+(\\.sba)$";
    public static final String regexDDS = ".+(\\.dds)$";
    public static final String regexSBA_ALPHA = ".+alpha?(.+)(\\.sba)$";
    public static final String regexSBA_NO_ALPHA = "^((?!alpha).)*(\\.sba)$";
    public static final String regexSB = ".+(\\.sb)$";
    public static final String regexM3G = ".+(\\.m3g)$";
    public static final String regexCONFIG = ".+(\\.config)$";
    public static final String regexBIN = ".+(\\.bin)$";
    public static final String noFilter = null;

    /**
     * Эксперементальный заголовок DDS файла
     */
    public static byte[] DDS_NORM_HEADER = {0x44, 0x44, 0x53, 0x20, 0x7C, 0x00, 0x00, 0x00, 0x07, 0x10, 0x0A, 0x00, 0x00, 0x04, 0x00, 0x00, 0x00, 0x04, 0x00, 0x00, 0x00, 0x00, 0x40, 0x00, 0x00, 0x00, 0x00, 0x00, 0x0B, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x20, 0x00, 0x00, 0x00, 0x41, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x20, 0x00, 0x00, 0x00, 0x00, 0x00, (byte)0xFF, 0x00, 0x00, (byte)0xFF, 0x00, 0x00, (byte)0xFF, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, (byte)0xFF, 0x08, 0x10, 0x40, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, };

    /**
     * @param file
     * @return
     */
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

    public static void printBytes(byte[] bytes){

        if(bytes.length > 8) return;

        String str = new String(Hex.encodeHex(bytes));
        str = str.toUpperCase(Locale.ROOT);

        for(String s : str.split("(?<=\\G.{2})"))
            System.out.print(s + " ");
    }

    public static String getNormBytes(byte[] bytes){

        String str = new String(Hex.encodeHex(bytes));
        str = str.toUpperCase(Locale.ROOT);

        String r = "";
        for(String s : str.split("(?<=\\G.{2})"))
            r += s + " ";

        return r;

    }

    public static long getSizeOf(File file){
        return Objects.requireNonNull(fileAsByteArray(file)).length;
    }

    public static File createTempFile(String prefix, String postfix){
        try {
            return File.createTempFile(prefix, postfix);
        } catch (IOException e) {
            System.out.println("Cant create temp(( " + e);
            e.printStackTrace();
        }
        return new File("");
    }

    public static FileOutputStream newFileOutputStream(File file){
        try {
            return new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static FileInputStream newFileInputStream(File file){
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Метод для сохраниния массива байт в файл
     * @param bytes байты которые нужно сохранить
     * @param saveTo файл куда сохранять(Не обязательно должен существовать)
     */
    public static void saveBytesToFile(byte[] bytes, File saveTo){
        try{
            saveTo.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(saveTo);
            outputStream.write(Util.DDS_NORM_HEADER);
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Метод поиска заданного хедера в байтовом файле
     * @param header хедер который нужно найти
     * @return первое вхождение этого хэдера
     */
    public static int findHeaderInFile(File file, Header header){
        return findHeaderInByteFile(fileAsByteArray(file), header.getValue());
    }

    /**
     * Базовая реализация для {@link Util#findHeaderInFile(File, Header)}
     */
    public static int findHeaderInByteFile(byte[] byteFile, byte[] header){
        int[] pf = prefix(header);
        int index = 0;
        for (int i = 0; i < byteFile.length; i++){
            while (index > 0 && header[index] != byteFile[i]) index = pf[index - 1];
            if (header[index] == byteFile[i]) index++;
            if (index == header.length) return i - index + 1;
        }
        return -1;
    }

    /**
     * Префикс функция для алгоритма КМП
     */
    private static int[] prefix(byte[] s) {
        int[] result = new int[s.length];
        result[0] = 0;
        int index = 0;

        for (int i = 1; i < s.length; i++) {
            while (index >= 0 && s[index] != s[i]) { index--; }
            index++;
            result[i] = index;
        }

        return result;
    }


    /**
     * Метод поиска любого заголовка от текущей позиции
     * @return найденный заголовок, если ничего не нашел возвращается {@link Header#NULL}
     */
    public static Header findAnyHeader(byte[] byteFile, int currentStep){
        if(currentStep > byteFile.length)
            throw new RuntimeException("currentStep > byteFile.length");

        /*
         * Header.values().length - 1 - выражение означает
         * перебор всех заголовков кроме последнего который нуль
         */
        for(int i = 0; i < Header.values().length - 1; i++){
            int count = 0;
            for(int j = 0; j < Header.values()[i].getValue().length; j++){
                int index = j + currentStep;
                if(index >= byteFile.length) index = byteFile.length - 1;
                if(byteFile[index] == Header.values()[i].getValue()[j]) count++;
            }
            if(count == Header.values()[i].getValue().length) return Header.values()[i];
        }
        return Header.NULL;
    }

    /**
     * Проверка на заголовок в байтовом файле на конкретном шаге
     * @param byteFile файл
     * @param currentStep шаг внутри файла
     * @return заглоаок или нет
     */
    public static boolean isHeader(byte[] byteFile, int currentStep){
        return !findAnyHeader(byteFile, currentStep).equals(Header.NULL);
    }

    /**
     * Метдо получения 8-ми байт после заголовка
     * @param file проверяемый файл
     * @param header заголовок после которго нужно получить 8 байт
     * @return 8 байт после заголовка
     */
    public static byte[] get8bytesAfterHeader(File file, Header header){
        if(header == Header.NULL | !file.exists()) return new byte[]{0};
        byte[] bytes = new byte[8];
        byte[] byteFile = fileAsByteArray(file);
        int in = findHeaderInByteFile(byteFile, header.getValue()) + header.getLength();
        for(int i = 0; i < 8; i++)
            bytes[i] = byteFile[in + i];
        return bytes;

    }

    /**
     * Метод для получения данных после заголока header
     * @param file
     * @param header
     * @return Строки после header
     */
    public static ArrayList<String> getDataAfterHeader(File file, Header header){
        if(!file.exists()) return new ArrayList<>();

        int headerInFile = findHeaderInFile(file, header);
        if(headerInFile == -1) return new ArrayList<>();
        int cdat = headerInFile + header.getLength() + 9;

        ArrayList<String> strings = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        byte[] bytes = fileAsByteArray(file);
        for (int i = cdat; i < bytes.length; i++) {
            if(bytes[i] != 0){
                builder.append((char) bytes[i]);
            }else{
                strings.add(builder.toString());
                builder = new StringBuilder();
            }
            Header anyHeader = findAnyHeader(bytes, i);
            if(!anyHeader.equals(Header.NULL)) {
                strings.add("ending header is " + anyHeader);
                return strings;
            }
        }
        return strings;

    }

    public static void openFileOrPathAndDoAction(String basePath, int chooserMode, FileAction action){
        JFileChooser fc = new JFileChooser(basePath);
        fc.setFileSelectionMode(chooserMode);
        fc.addActionListener(e -> action.onAction(((JFileChooser) e.getSource()).getSelectedFile()));
        fc.showOpenDialog(fc);
    }


    private static boolean isMatch(String str, String regex){
        return Pattern.matches(regex,str);
    }

    /**
     * Метод для рекурсивного прохода по кешу игры с фильтром по имени файла
     *
     * @param path путь от которго начинаем смотреть файлы
     * @param regexFilter фильтр для файлов
     * @param listener функциональный интерфейс описывающий нужно делать с файлом
     */
    public static void iterateAllFilesAndDoAction(String path, String regexFilter, FileAction listener){
        File file = new File(path);
        if(!file.exists()) return;
        for (String name : file.list()) {
            File f = new File(file.getAbsolutePath() + "/" + name);
            if(f.isDirectory()) iterateAllFilesAndDoAction(f.getPath(), regexFilter, listener);
            else if(regexFilter != null) {
                if (isMatch(f.getName(), regexFilter)) listener.onAction(f);
            }else listener.onAction(f);
        }
    }

    /**
     * Перегрузка метода {@link #iterateAllFilesAndDoAction(File, String, FileAction)}
     * @param file файл вместо строки
     * @param regexFilter то же самое регулярное выражение
     * @param action что нужно сделать с файлом
     */
    public static void iterateAllFilesAndDoAction(File file, String regexFilter, FileAction action){
        iterateAllFilesAndDoAction(file.getAbsolutePath(), regexFilter, action);
    }

}
