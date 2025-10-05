package is.hi.hbv501g.verkefni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



@SpringBootApplication
@EnableJpaRepositories
public class VerkefniApplication {
    public static void main(String[] args) {
        SpringApplication.run(VerkefniApplication.class, args);
    }

}
