package ch.bbw.m183.vulnerapp.controller;

import ch.bbw.m183.vulnerapp.datamodel.BlogEntity;
import ch.bbw.m183.vulnerapp.service.BlogService;
import ch.bbw.m183.vulnerapp.service.HealthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
@CrossOrigin
public class BlogController {

    private final BlogService blogService;

    private final HealthService healthService;

    @GetMapping
    public Page<BlogEntity> getBlogs(@PageableDefault(sort = "createdAt", direction = Direction.DESC) Pageable pageable) {
        return blogService.getBlogs(pageable);
    }

    @PostMapping
    public UUID createBlog(@Valid @RequestBody BlogEntity blog) {
        return blogService.createBlog(blog);
    }

    @GetMapping("/health")
    public String health() {
        return healthService.health();
    }
}
