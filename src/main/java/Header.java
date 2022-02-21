/**
 * @author Michael Kits
 * Класс описывающий один из заголовков .sb/.sba файла во время анализа
 *
 */
public enum Header {

    /**
     * Основные заголовки файла
     * (*Не все возможные*)
     */
    SBIN(new byte[]{0x53, 0x42, 0x49, 0x4E}, "SBIN", 0),
    ENUM(new byte[]{0x45, 0x4E, 0x55, 0x4D}, "ENUM", 1),
    STRU(new byte[]{0x53, 0x54, 0x52, 0x55}, "STRU", 2),
    FIEL(new byte[]{0x46, 0x49, 0x45, 0x4C}, "FIEL", 3),
    EN(new byte[]{0x45, 0x4E}, "EN", 4),
    OHDR(new byte[]{0x4F, 0x48, 0x44, 0x52}, "OHDR", 5),
    DATA(new byte[]{0x44, 0x41, 0x54, 0x41}, "DATA", 6),
    CHDR(new byte[]{0x43, 0x48, 0x44, 0x52}, "CHDR", 7),
    CDAT(new byte[]{0x43, 0x44, 0x41, 0x54}, "CDAT", 8),
    BULK(new byte[]{}, "BULK", 9),

    /**
     * Отсутствующий Заголовок
     */
    NULL(new byte[]{0}, "NULL", -1);

    private byte[] value;
    private String name;
    private int number;

    Header(byte[] value, String name, int number) {
        this.value = value;
        this.name = name;
        this.number = number;
    }

    public byte[] getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return name;
    }
}
