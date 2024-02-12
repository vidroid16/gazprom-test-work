package ru.gazprom.testwork;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.gazprom.testwork.services.RandomUserService;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
@RequiredArgsConstructor
public class GazpromApplication implements CommandLineRunner {

    private final RandomUserService randomUserService;
    public static final Logger log = LoggerFactory
            .getLogger(GazpromApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GazpromApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Hi! Run command: upload {user-number}, get {user-number} {filename.csv}");
        String filenameRegex = "(.*/)*.+\\.(csv|CSV)$";
        String numberRegex = "[1-9][0-9]*";
        while (true){
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            try {
                String[] tokens = s.split(" ");
                if (tokens[0].equals("upload") && (tokens[1].matches(numberRegex))){
                    randomUserService.uploadNRandomUsers(Integer.parseInt(tokens[1]));
                }
                else if(tokens[0].equals("get") && tokens[1].matches(numberRegex) && tokens[2].matches(filenameRegex)){
                    randomUserService.saveCsvToFile(Integer.parseInt(tokens[1]), tokens[2]);
                }
                else {
                    log.info("Illegal command");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            log.info(s);
        }
    }
}
