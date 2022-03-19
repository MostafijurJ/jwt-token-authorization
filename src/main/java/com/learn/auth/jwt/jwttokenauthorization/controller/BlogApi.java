package com.learn.auth.jwt.jwttokenauthorization.controller;

import com.learn.auth.jwt.jwttokenauthorization.models.BlogPost;
import com.learn.auth.jwt.jwttokenauthorization.models.core.Response;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Blog API")
public interface BlogApi {

  @GetMapping("/blog")
  public Response<List<BlogPost>> index();


  @GetMapping("/blog/{id}")
  public Response<BlogPost> showBlogPost(@PathVariable String id);
  @PostMapping("/blog/search")
  public Response<List<BlogPost>> searchPosts(@RequestBody String searchTerm);


  @PostMapping("/blog")
  public Response<BlogPost> createPost(@RequestBody BlogPost blogPost);
  @PutMapping("/blog/{id}")
  public Response<BlogPost> updatePost(@PathVariable String id, @RequestBody BlogPost blogPost);

  @DeleteMapping("blog/{id}")
  public Response delete(@PathVariable String id);


}
