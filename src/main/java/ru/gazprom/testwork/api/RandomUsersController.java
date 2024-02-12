package ru.gazprom.testwork.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gazprom.testwork.services.RandomUserService;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RandomUsersController {
    private final RandomUserService randomUserService;
    @GetMapping("/{users-number}")
    public ResponseEntity getRandomUsers(@PathVariable("users-number") String usersNumber){
        try {
           randomUserService.uploadNRandomUsers(Integer.parseInt(usersNumber));
            return ResponseEntity.ok().build();
        }catch (JsonProcessingException e){
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    @PostMapping(value =  "/{users-number}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> getCsv(@PathVariable("users-number") String usersNumber){
        Resource resource = randomUserService.getFileResource(Integer.parseInt(usersNumber));
        return ResponseEntity.status(200).body(resource);
    }
}
