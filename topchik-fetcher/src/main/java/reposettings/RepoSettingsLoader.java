package reposettings;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс загрузки настроек репозиториев
 */
public class RepoSettingsLoader {
  private static final Logger LOGGER = LogManager.getLogger(RepoSetting.class);
  private String settingsFileName;


  public RepoSettingsLoader(String settingsFileName) {
    this.settingsFileName = settingsFileName;
  }

  /**
   * Метод загрузки настроек репозитория
   * @return - список настроек
   */
  public List<RepoSetting> getRepoSettings() {
    LOGGER.info("Поиск данных о репозиториях");
    final List<RepoSetting> repoSettings = new ArrayList<>();

    try (
        final FileInputStream fis = new FileInputStream(settingsFileName);
        final InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
        final CSVReader reader = new CSVReader(isr)) {
      String[] nextLine;

      while ((nextLine = reader.readNext()) != null) {
        final RepoSetting repoSetting = new RepoSetting(nextLine[0], nextLine[1]);
        repoSettings.add(repoSetting);
      }
      LOGGER.info("Поиск данных о репозиториях завершен успешно");
      System.out.println("Найденные репозитории :");
      for (final RepoSetting repoSetting : repoSettings) {
        System.out.println(repoSetting);
      }
    } catch (CsvValidationException | IOException e) {
      LOGGER.error("Ошибка поиска данных о репозиториях");
    }
    return repoSettings;
  }
}
