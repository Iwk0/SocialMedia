package com.social.media.service;

import com.social.media.model.Photo;

import java.io.InputStream;

public interface PhotoService {

    void addPhoto(Photo photo);

    InputStream getPhoto(String id);
}