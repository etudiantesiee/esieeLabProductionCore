package fr.esiee.pic.production.core;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
 
    static final Logger logger = LogManager.getLogger(Application.class.getName());
    
    public static void main(String[] args) {
        logger.info("entered application");
        
        SpringApplication.run(Application.class, args);
    }
}
