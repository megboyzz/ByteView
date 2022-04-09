import java.io.File;

/**
 * Класс описывающий папки в кешу игры
 */
public class GameCache {
    /**
     * Базовый путь к кешу
     */
    private String basePath;

    public GameCache(String basePath) {
        // TODO создать более интелектуальную систему проверки кеша
        if(!basePath.contains("com.ea.games.")) throw new RuntimeException("This is wrong path");
        if(!(basePath.endsWith("\\") | basePath.endsWith("/"))){
            basePath += "\\";
        }
        this.basePath = basePath;
    }

    public File getPublishedX(int x){
        String str = "files\\published\\";
        if(x > 0) str = basePath + "files\\published." + x +"x\\";
        File file = new File(basePath + str);
        if(!file.exists()) throw new RuntimeException("Файл не существует" + file.getAbsolutePath());
        return file;
    }

    public File getMainPath(){
        return new File(basePath + "files\\");
    }

    public File getBRDF(){
        return new File(basePath + "files\\published\\brdf\\");
    }

}
