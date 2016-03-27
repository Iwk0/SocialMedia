package com.social.media.service;

import com.social.media.model.Photo;

import java.io.InputStream;
import java.util.List;

public interface PhotoService {

    void addPhoto(Photo photo);

    InputStream getPhoto(String id);

    List<Byte> findAll(String personId);
}