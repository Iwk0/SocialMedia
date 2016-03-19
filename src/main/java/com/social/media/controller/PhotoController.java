package com.social.media.controller;

import com.social.media.model.Photo;
import com.social.media.service.PhotoService;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Log4j
@Controller
public class PhotoController {

    @Autowired
    @Qualifier(value = "photoService")
    private PhotoService photoService;

    @RequestMapping("/picture/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable(value = "id") String id) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(IOUtils.toByteArray(photoService.getPhoto(id)), headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public String uploadImage(@ModelAttribute(value = "picture") Photo photo,
                              @RequestParam("file") MultipartFile file) {
        try {
            photo.setImage(file.getBytes());
            photoService.addPhoto(photo);
            log.info("Photo added successfully");
        } catch (IOException e) {
            log.error("IOException", e);
        }

        return "redirect:/settings";
    }
}