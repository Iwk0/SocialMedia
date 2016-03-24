package com.social.media.repository;

import com.social.media.model.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface FriendRepository extends JpaRepository<Friend, String> {

    Set<Friend> findByPersonId(String id);
}