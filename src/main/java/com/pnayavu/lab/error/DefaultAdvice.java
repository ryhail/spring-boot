package com.pnayavu.lab.error;


import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.*;
import java.nio.file.Files;

@RestControllerAdvice
public class DefaultAdvice {
    @ExceptionHandler(value = MyNotFoundException.class)
    public ResponseEntity<byte[]> handleExceptionNotFound(MyNotFoundException exception) {
        File img = new File("src/main/resources/images/404.jpg");
        InputStream imageStream;
        byte[] image;
        try {
            imageStream = Files.newInputStream(img.toPath());
            image = imageStream.readAllBytes();
            imageStream.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.IMAGE_JPEG).body(image);
    }
}
