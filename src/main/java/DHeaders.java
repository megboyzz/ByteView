@Deprecated
public class DHeaders {

    //шаболоны заголовков файла .sb/.sba/.bin
    public static final byte[] sbin_header = {0x53, 0x42, 0x49, 0x4E};
    public static final byte[] enum_header = {0x45, 0x4E, 0x55, 0x4D};
    public static final byte[] stru_header = {0x53, 0x54, 0x52, 0x55};
    public static final byte[] fiel_header = {0x46, 0x49, 0x45, 0x4C};
    public static final byte[] en_header   = {0x45, 0x4E};
    public static final byte[] ohdr_header = {0x4F, 0x48, 0x44, 0x52};
    public static final byte[] data_header = {0x44, 0x41, 0x54, 0x41};
    public static final byte[] chdr_header = {0x43, 0x48, 0x44, 0x52};
    public static final byte[] cdat_header = {0x43, 0x44, 0x41, 0x54};
    //*не все возможные

    //Перечисление всех по очереди
    public static final byte[][] commonArr = {
            sbin_header,
            enum_header,
            stru_header,
            fiel_header,
            en_header,
            ohdr_header,
            data_header,
            chdr_header,
            cdat_header
    };

    //обозначения
    public static final int SBIN = 1;
    public static final int ENUM = 2;
    public static final int STRU = 3;
    public static final int FIEL = 4;
    public static final int EN   = 5;
    public static final int OHDR = 6;
    public static final int DATA = 7;
    public static final int CHDR = 8;
    public static final int CDAT = 9;

    //Метод для получения шаболна заголовка
    public static byte[] getTemplate(int templateNumber){

        return switch (templateNumber) {
            case SBIN -> sbin_header;
            case ENUM -> enum_header;
            case STRU -> stru_header;
            case FIEL -> fiel_header;
            case EN   -> en_header;
            case OHDR -> ohdr_header;
            case DATA -> data_header;
            case CHDR -> chdr_header;
            case CDAT -> cdat_header;
            default -> new byte[1];
        };

    }

    public static String getTemplateName(int templateNumber){

        return switch (templateNumber) {
            case SBIN -> "SBIN";
            case ENUM -> "ENUM";
            case STRU -> "STRU";
            case FIEL -> "FIEL";
            case EN   -> "EN";
            case OHDR -> "OHDR";
            case DATA -> "DATA";
            case CHDR -> "CHDR";
            case CDAT -> "CDAT";
            default -> "no Header";
        };

    }


}
