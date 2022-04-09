import java.io.File;
import java.io.IOException;

/**
 * Функциональный интерфейс для описания действия с файлом
 */
public interface FileAction {
    void onAction(File file);
}
