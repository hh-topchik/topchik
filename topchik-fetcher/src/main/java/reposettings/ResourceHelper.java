package reposettings;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Класс для работы с ресурсами
 */
public final class ResourceHelper {

  private ResourceHelper() throws Exception {
    throw new Exception("Этот статический класс!");
  }

  /**
   * Метод для получения файла ресурса
   * @param relativePath - имя файла ресурса
   * @return - файл ресурса
   */
  public static File getResourceFile(String relativePath) {
    final File resourceFile;
    final URL location = ResourceHelper.class.getProtectionDomain().getCodeSource().getLocation();
    final String codeLocation = location.toString();
    try {
      if (codeLocation.endsWith(".jar")) {
        //Call from jar
        Path path = Paths.get(location.toURI()).resolve("../classes/" + relativePath).normalize();
        resourceFile = path.toFile();
      }
      else {
        //Call from IDE
        resourceFile = new File(Objects.requireNonNull(ResourceHelper.class.getClassLoader().getResource(relativePath)).getPath());
      }
      return resourceFile;
    } catch(URISyntaxException ex) {
      ex.printStackTrace();
    }
    return null;
  }
}
