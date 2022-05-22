package com.inshorts.reader.controller;

import com.inshorts.reader.entity.ArticleEntity;
import com.inshorts.reader.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class AricleController {
    @Autowired
    private ArticleService articleService;

//    @GetMapping("/article")
//    public List<ArticleEntity> getAllArticles(){
//        return articleService.getAllArticles();
//    }

    @GetMapping("/article/{id}")
    public Optional<ArticleEntity> getArticle(@PathVariable Long id){
        return articleService.getArticle(id);
    }
    @PostMapping("/article")
    public ResponseEntity<String> createArticle(@RequestBody ArticleEntity articleEntity){
        articleService.createArticle(articleEntity);
        return  new ResponseEntity("New Article Created", HttpStatus.CREATED);
    }

    @PutMapping("/article/{id}")
    public String updateArticle(@PathVariable Long id,@RequestBody ArticleEntity articleEntity){
        articleService.updateArticle(id, articleEntity);
        return "Article is updated";
    }

    @PatchMapping("/article/{id}")
    public String updateArticle(@PathVariable Long id, @RequestBody Map<String,Object> request){
        articleService.updateArticle(id, request);
        return "Article is updated";
    }


    @GetMapping("/article")
    public List<ArticleEntity> getAllArticles(@RequestParam(name = "pageNo",required = false) Integer pageNo,@RequestParam(name = "size",required = false) Integer size){
        return articleService.getAllArticles(pageNo,size);
    }
}