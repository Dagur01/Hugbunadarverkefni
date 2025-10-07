package is.hi.hbv501g.verkefni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.GetMapping;


@SpringBootApplication
@EnableJpaRepositories
public class VerkefniApplication {

    @GetMapping("/")
    public String home() {
        return "Hello, World!";
    }
    public static void main(String[] args) {
        SpringApplication.run(VerkefniApplication.class, args);
    }

}
