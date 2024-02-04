package com.techblogfinder.api.article.domain.repository;

import com.techblogfinder.api.article.domain.Tag;
import org.springframework.data.repository.Repository;

import java.util.Optional;

@org.springframework.stereotype.Repository
public interface TagRepository extends Repository<Tag, Long> {
    Optional<Tag> findByName(String name);

    Tag save(Tag tag);
}
