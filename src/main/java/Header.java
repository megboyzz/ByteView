import java.nio.charset.StandardCharsets;

/**
 * @author Michael Kits <br>
 * Класс описывающий один из заголовков .sb/.sba файла во время анализа
 */
public enum Header {

    /**
     * Основные заголовки файла
     * (*Не все возможные*)
     */
    SBIN("SBIN", 0),
    ENUM( "ENUM", 1),
    STRU( "STRU", 2),
    FIEL( "FIEL", 3),
    //EN( "EN", 4),
    OHDR( "OHDR", 5),
    DATA( "DATA", 6),
    CHDR( "CHDR", 7),
    CDAT( "CDAT", 8),
    BULK( "BULK", 9),
    BARG("BARG", 10),


    /**
     * Отсутствующий Заголовок
     */
    NULL(null, "NULL", -1);

    private byte[] value;
    private String name;
    private int number;

    Header(String name, int number){
        this.value = name.getBytes(StandardCharsets.UTF_8);
        this.name = name;
        this.number = number;
    }

    Header(byte[] value, String name, int number) {
        this.value = value;
        this.name = name;
        this.number = number;
    }

    public byte[] getValue() {
        return value;
    }

    public int getLength(){
        return value.length;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return name;
    }
}
