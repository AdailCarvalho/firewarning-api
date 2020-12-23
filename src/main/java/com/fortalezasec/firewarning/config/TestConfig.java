package com.fortalezasec.firewarning.config;

import com.fortalezasec.firewarning.services.DBService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

  @Autowired
  private DBService dbservice;

  @Override
  public void run(String... args) throws Exception {
    dbservice.instatiateTestDatabase();
  }

}
