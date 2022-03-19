package com.learn.auth.jwt.jwttokenauthorization;

import com.learn.auth.jwt.jwttokenauthorization.repository.BlogRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class JwtTokenAuthorizationApplicationTests {

	@Autowired
	private BlogRepository blogRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void getSearch(){
		var blogs = blogRepository.findByIsDeletedAndTitleContainingOrContentContaining('N', "Try Auth2", "Try Auth2");
		Assertions.assertNotNull(blogs);
	}


}
