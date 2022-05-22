package com.inshorts.reader.service;

import com.inshorts.reader.entity.ArticleEntity;
import com.inshorts.reader.repository.ArticleRepository;
import org.apache.el.util.ReflectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    public List<ArticleEntity> getAllArticles(){
        return articleRepository.findAll();
    }

    public Optional<ArticleEntity> getArticle(Long id){
        return articleRepository.findById(id);
    }

    public void createArticle(ArticleEntity articleEntity){
        articleEntity.setTimestamp(new Timestamp(new Date().getTime()));
        articleRepository.save(articleEntity);
        return;
    }

    public void updateArticle(Long id, ArticleEntity articleEntity){

        articleEntity.setArticleId(id);
        articleEntity.setTimestamp(new Timestamp(new Date().getTime()));
        articleRepository.save(articleEntity);
    }

    public void updateArticle(@PathVariable Long id, Map<String,Object> request){

        ArticleEntity articleEntity = articleRepository.findById(id).get();
        request.forEach((K,V)->{
            Field field=ReflectionUtils.findField(ArticleEntity.class,K);
            field.setAccessible(true);
            ReflectionUtils.setField(field,articleEntity,V);
        });
        articleRepository.save(articleEntity);
    }

    public List<ArticleEntity> getAllArticles(Integer pageNo,Integer size){

        if(Objects.isNull(pageNo) && Objects.isNull(size)){     // if both null, display all articles
            return articleRepository.findAll();
        }
        Pageable pageable = PageRequest.of(pageNo,size, Sort.by("timestamp").descending());
        Page<ArticleEntity> page = articleRepository.findAll(pageable);
        List<ArticleEntity>  currPage = page.stream().collect(Collectors.toList());
        return currPage;
    }
}
