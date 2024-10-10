package at.ahujaprinc.gk961;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Gk961Application {

    public static void main(String[] args) {
        System.out.println("Hallo".hashCode());
        SpringApplication.run(Gk961Application.class, args);
    }

}
