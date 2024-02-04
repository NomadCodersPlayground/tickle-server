package com.techblogfinder.api.article.domain.repository;

import com.techblogfinder.api.article.domain.Article;
import com.techblogfinder.api.article.enumerable.IndexingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("select article " +
            "from Article article " +
            "where article.indexingStatus = :indexingStatus ")
    List<Article> findAllByStatus(@Param("indexingStatus") IndexingStatus indexingStatus);
}
