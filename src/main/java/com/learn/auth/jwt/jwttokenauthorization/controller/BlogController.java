package com.learn.auth.jwt.jwttokenauthorization.controller;

import com.learn.auth.jwt.jwttokenauthorization.entity.BlogEntity;
import com.learn.auth.jwt.jwttokenauthorization.models.BlogPost;
import com.learn.auth.jwt.jwttokenauthorization.models.core.Response;
import com.learn.auth.jwt.jwttokenauthorization.repository.BlogRepository;
import com.learn.auth.jwt.jwttokenauthorization.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BlogController {

  @Autowired
  private BlogPostService blogPostService;
  final private BlogRepository blogRepository;

  public BlogController(BlogRepository blogRepository) {
    this.blogRepository = blogRepository;
  }

  @GetMapping("/blog")
  public List<BlogEntity> index() {
    return blogRepository.findAll();
  }

  @GetMapping("/blog/{id}")
  public BlogEntity show(@PathVariable String id) {
    int blogId = Integer.parseInt(id);
    return blogRepository.findById(blogId).orElse(new BlogEntity());
  }

  @PostMapping("/blog/search")
  public List<BlogEntity> search(@RequestBody Map<String, String> body) {
    String searchTerm = body.get("text");
    return blogRepository.findByTitleContainingOrContentContaining(searchTerm, searchTerm);
  }


  @PostMapping("/blog")
  public Response<BlogPost> create(@RequestBody BlogPost blogPost) {
    return blogPostService.createPost(blogPost);
  }

  @PutMapping("/blog/{id}")
  public BlogEntity update(@PathVariable String id, @RequestBody Map<String, String> body) {
    int blogId = Integer.parseInt(id);
    // getting blogEntity
    BlogEntity blogEntity = blogRepository.findById(blogId).orElse(new BlogEntity());
    blogEntity.setTitle(body.get("title"));
    blogEntity.setContent(body.get("content"));
    return blogRepository.save(blogEntity);
  }

  @DeleteMapping("blog/{id}")
  public boolean delete(@PathVariable String id){
    int blogId = Integer.parseInt(id);
    blogRepository.deleteById(blogId);
    return true;
  }

}