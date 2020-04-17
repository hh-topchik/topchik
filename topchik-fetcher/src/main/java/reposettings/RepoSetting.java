package reposettings;

/**
 * Класс "Репозиторий", хранящий связку ВЛАДЕЛЕЦ/РЕПОЗИТОРИЙ - ТОКЕН
 */
public class RepoSetting {
  private String path;
  private String token;

  RepoSetting(String path, String token) {
    this.path = path;
    this.token = token;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public String toString() {
    return "Repo{" +
        "repo='" + path + '\'' +
        ", token='" + token + '\'' +
        '}';
  }

}
