package edu.rutmiit.demo.demorest;

import org.flywaydb.core.Flyway;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoRestApplication {

    public static void main(String[] args) {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerNames(new String[]{"localhost"});
        ds.setPortNumbers(new int[]{5432});
        ds.setDatabaseName("bank_db");
        ds.setUser("postgres");
        ds.setPassword("postgres");

        Flyway flyway = Flyway.configure(DemoRestApplication.class.getClassLoader())
                .dataSource(ds)
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .load();

        flyway.migrate();

        SpringApplication.run(DemoRestApplication.class, args);
    }
}