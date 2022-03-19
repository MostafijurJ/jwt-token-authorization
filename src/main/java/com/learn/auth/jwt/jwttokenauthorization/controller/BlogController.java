package com.learn.auth.jwt.jwttokenauthorization.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.learn.auth.jwt.jwttokenauthorization.models.BlogPost;
import com.learn.auth.jwt.jwttokenauthorization.models.core.Response;
import com.learn.auth.jwt.jwttokenauthorization.repository.BlogRepository;
import com.learn.auth.jwt.jwttokenauthorization.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@JsonIgnoreProperties
public class BlogController {

  @Autowired
  private BlogPostService blogPostService;
  final private BlogRepository blogRepository;

  public BlogController(BlogRepository blogRepository) {
    this.blogRepository = blogRepository;
  }

  @GetMapping("/blog")
  public Response<List<BlogPost>> index() {
    return blogPostService.getAllPosts();
  }

  @GetMapping("/blog/{id}")
  public Response<BlogPost> showBlogPost(@PathVariable String id) {

    return blogPostService.getPostById(Integer.parseInt(id));
  }

  @PostMapping("/blog/search")
  public Response<List<BlogPost>> searchPosts(@RequestBody String searchTerm) {
    return blogPostService.searchPosts(searchTerm);
  }


  @PostMapping("/blog")
  public Response<BlogPost> createPost(@RequestBody BlogPost blogPost) {
    return blogPostService.createPost(blogPost);
  }

  @PutMapping("/blog/{id}")
  public Response<BlogPost> updatePost(@PathVariable String id, @RequestBody BlogPost blogPost) {
    Integer blogId = Integer.parseInt(id);
    return blogPostService.updatePost(blogId, blogPost);
  }

  @DeleteMapping("blog/{id}")
  public Response delete(@PathVariable String id){
    return blogPostService.deletePost(Integer.parseInt(id));
  }

}