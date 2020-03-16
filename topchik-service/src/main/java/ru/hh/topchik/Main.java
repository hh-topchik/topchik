package ru.hh.topchik;

import ru.hh.nab.starter.NabApplication;
import ru.hh.topchik.config.Config;
import ru.hh.topchik.config.JerseyConfig;

public class Main {

  public static void main(String[] args) {
    NabApplication.builder()
        .configureJersey(JerseyConfig.class).bindToRoot()
        .build().run(Config.class);
  }
}
