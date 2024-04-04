package com.pnayavu.lab.error;


import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class DefaultAdvice {
  @ExceptionHandler(value = MyNotFoundException.class)
  public ResponseEntity<byte[]> handleExceptionNotFound(MyNotFoundException exception) {
    File img = new File("src/main/resources/images/404.jpg");
    byte[] image;
    try (InputStream imageStream = Files.newInputStream(img.toPath())) {
      image = imageStream.readAllBytes();
      return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.IMAGE_JPEG)
          .body(image);
    } catch (Exception e) {
      return ResponseEntity.internalServerError().build();
    }
  }
}
