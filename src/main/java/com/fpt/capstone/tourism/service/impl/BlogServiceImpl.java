package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.constants.Constants;
import com.fpt.capstone.tourism.dto.common.BlogDTO;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.TagDTO;
import com.fpt.capstone.tourism.dto.request.BlogRequestDTO;
import com.fpt.capstone.tourism.dto.response.BlogResponseDTO;
import com.fpt.capstone.tourism.dto.response.PagingDTO;
import com.fpt.capstone.tourism.dto.response.PublicBlogResponseDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.helper.validator.Validator;
import com.fpt.capstone.tourism.mapper.BlogMapper;
import com.fpt.capstone.tourism.mapper.PublicBlogMapper;
import com.fpt.capstone.tourism.model.Blog;
import com.fpt.capstone.tourism.model.Tag;
import com.fpt.capstone.tourism.model.User;
import com.fpt.capstone.tourism.repository.BlogRepository;
import com.fpt.capstone.tourism.service.BlogService;
import com.fpt.capstone.tourism.service.TagService;
import com.fpt.capstone.tourism.service.UserService;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.fpt.capstone.tourism.constants.Constants.Message.*;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final UserService userService;
    private final TagService tagService;
    private final BlogMapper blogMapper;
    private final PublicBlogMapper publicBlogMapper;

    @Override
    @Transactional
    public GeneralResponse<BlogResponseDTO> createBlog(BlogRequestDTO blogRequestDTO) {
        try {
            // Validate input
            Validator.validateBlog(blogRequestDTO.getTitle(), blogRequestDTO.getDescription(), blogRequestDTO.getContent());
            Blog blog = blogMapper.toEntity(blogRequestDTO);
            blog.setCreatedAt(LocalDateTime.now());
            blog.setDeleted(false);
            Blog savedBlog = blogRepository.save(blog);
            return new GeneralResponse<>(HttpStatus.CREATED.value(), CREATE_BLOG_SUCCESS_MESSAGE,blogMapper.toDTO(savedBlog));
        } catch (BusinessException be) {
            throw be;
        } catch (Exception ex) {
            throw BusinessException.of(CREATE_BLOG_FAIL_MESSAGE, ex);
        }
    }

    @Override
    @Transactional
    public GeneralResponse<BlogResponseDTO> updateBlog(Long id, BlogRequestDTO blogRequestDTO) {
        try {
            // Validate input
            Validator.validateBlog(blogRequestDTO.getTitle(), blogRequestDTO.getDescription(), blogRequestDTO.getContent());

            // Fetch existing blog
            Blog blog = blogRepository.findById(id).orElseThrow(
                    () -> BusinessException.of(HttpStatus.NOT_FOUND,BLOG_NOT_FOUND)
            );
            // Fetch required entities
            User author = userService.findById(blogRequestDTO.getAuthor().getId());
            List<Tag> tags = tagService.findAllById(
                    blogRequestDTO.getTags().stream().map(TagDTO::getId).collect(Collectors.toList())
            );
            // Update fields
            blog.setTitle(blogRequestDTO.getTitle());
            blog.setDescription(blogRequestDTO.getDescription());
            blog.setContent(blogRequestDTO.getContent());
            blog.setThumbnailImageUrl(blogRequestDTO.getThumbnailImageUrl());
            blog.setAuthor(author);
            blog.setBlogTags(tags);

            // Save updated blog
            blogRepository.save(blog);
            // Convert to response DTO
            BlogResponseDTO responseDTO = blogMapper.toDTO(blog);
            return new GeneralResponse<>(HttpStatus.OK.value(), UPDATE_BLOG_SUCCESS_MESSAGE, responseDTO);
        } catch (BusinessException be) {
            throw be;
        } catch (Exception ex) {
            throw BusinessException.of(UPDATE_BLOG_FAIL_MESSAGE, ex);
        }
    }


    @Override
    public GeneralResponse<BlogResponseDTO> getBlogById(Long id) {
        try {
            Blog blog = blogRepository.findById(id).orElseThrow();
            BlogResponseDTO blogResponseDTO = blogMapper.toDTO(blog);
            return new GeneralResponse<>(HttpStatus.OK.value(), GENERAL_SUCCESS_MESSAGE, blogResponseDTO);
        } catch (BusinessException be) {
            throw be;
        } catch (Exception ex) {
            throw BusinessException.of(GENERAL_FAIL_MESSAGE, ex);
        }
    }

    @Override
    public GeneralResponse<BlogResponseDTO> changeBlogDeletedStatus(Long id, boolean isDeleted) {
        try {
            Blog blog = blogRepository.findById(id).orElseThrow();
            blog.setDeleted(isDeleted);
            //blogRepository.save(blog);
            Blog savedBlog = blogRepository.save(blog);
            BlogResponseDTO blogResponseDTO = blogMapper.toDTO(savedBlog);
            return GeneralResponse.of(blogResponseDTO,GENERAL_SUCCESS_MESSAGE);
        } catch (BusinessException be) {
            throw be;
        } catch (Exception ex) {
            throw BusinessException.of(GENERAL_FAIL_MESSAGE, ex);
        }
    }

    @Override
    public GeneralResponse<PagingDTO<List<BlogResponseDTO>>> getBlogs(int page, int size, String keyword, Boolean isDeleted, String sortField, String sortDirection) {
        try {
            // Validate sortField to prevent invalid field names
            List<String> allowedSortFields = Arrays.asList("id", "createdAt", "title");
            if (!allowedSortFields.contains(sortField)) {
                sortField = "createdAt";
            }
            Sort.Direction direction = sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortField));

            // Build search specification
            Specification<Blog> spec = buildSearchSpecification(keyword, isDeleted);

            Page<Blog> blogPage = blogRepository.findAll(spec, pageable);
            List<BlogResponseDTO> blogDTOs = blogPage.getContent().stream()
                    .map(blogMapper::toDTO)
                    .collect(Collectors.toList());

            return buildPagedResponse(blogPage, blogDTOs);
        } catch (Exception ex) {
            throw BusinessException.of(BLOG_RETRIEVED_FAIL_MESSAGE, ex);
        }
    }

    @Override
    public List<BlogResponseDTO> findNewestBlogs(int numberBlog) {
        try{
            Pageable pageable = PageRequest.of(0, numberBlog, Sort.by("createdAt").descending());
            List<Blog> blogList = blogRepository.findTopBlogs(pageable);
            return blogList.stream().map(blogMapper::toDTO).collect(Collectors.toList());
        }catch (Exception ex) {
            throw BusinessException.of("Error retrieving newest blogs", ex);
        }
    }

    private Specification<Blog> buildSearchSpecification(String keyword, Boolean isDeleted) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Normalize Vietnamese text for search (ignore case and accents)
            if (keyword != null && !keyword.trim().isEmpty()) {
                Expression<String> normalizedTitle = cb.function("unaccent", String.class, cb.lower(root.get("title")));
                Expression<String> normalizedDescription = cb.function("unaccent", String.class, cb.lower(root.get("description")));
                Expression<String> normalizedKeyword = cb.function("unaccent", String.class, cb.literal(keyword.toLowerCase()));

                Predicate titlePredicate = cb.like(normalizedTitle, cb.concat("%", cb.concat(normalizedKeyword, "%")));
                Predicate descPredicate = cb.like(normalizedDescription, cb.concat("%", cb.concat(normalizedKeyword, "%")));

                predicates.add(cb.or(titlePredicate, descPredicate));
            }

            // Filter by deletion status
            if (isDeleted != null) {
                predicates.add(cb.equal(root.get("deleted"), isDeleted));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private GeneralResponse<PagingDTO<List<BlogResponseDTO>>> buildPagedResponse(Page<Blog> blogPage, List<BlogResponseDTO> blogDTOs) {
        PagingDTO<List<BlogResponseDTO>> pagingDTO = PagingDTO.<List<BlogResponseDTO>>builder()
                .page(blogPage.getNumber())
                .size(blogPage.getSize())
                .total(blogPage.getTotalElements())
                .items(blogDTOs)
                .build();
        return new GeneralResponse<>(HttpStatus.OK.value(), "Success", pagingDTO);
    }

    @Override
    public List<PublicBlogResponseDTO> getNewestBlogs(int numberBlog) {
        try{
            Pageable pageable = PageRequest.of(0, numberBlog, Sort.by("createdAt").descending());
            List<Blog> blogList = blogRepository.findTopBlogs(pageable);
            return blogList.stream().map(publicBlogMapper::blogToPublicBlogResponseDTO).collect(Collectors.toList());
        }catch (Exception ex) {
            throw BusinessException.of("Error retrieving newest blogs", ex);
        }
    }

    @Override
    public List<PublicBlogResponseDTO> getBlogsByTagName(String tagName, int numberOfBlogs) {
        Pageable pageable = PageRequest.of(0, numberOfBlogs);
        List<Blog> blogs = blogRepository.findByBlogTags_Name(tagName, pageable);
        return blogs.stream()
                .map(publicBlogMapper::blogToPublicBlogResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<PublicBlogResponseDTO> getPublicBlog() {
        // Get 3 blogs with the tag "food & drink"
        List<PublicBlogResponseDTO> foodAndDrinkBlogs = getBlogsByTagName("Food & Drinks", 3);

        // Get 6 blogs with the tag "adventure"
        List<PublicBlogResponseDTO> adventureBlogs = getBlogsByTagName("Adventure", 6);

        // Get 3 blogs with the tag "cultural"
        List<PublicBlogResponseDTO> culturalBlogs = getBlogsByTagName("Cultural", 3);

        // Get 11 newest blogs
        List<PublicBlogResponseDTO> newestBlogs = getNewestBlogs(11);

        List<PublicBlogResponseDTO> allPublicBlogs = new ArrayList<>();
        allPublicBlogs.addAll(foodAndDrinkBlogs);
        allPublicBlogs.addAll(adventureBlogs);
        allPublicBlogs.addAll(culturalBlogs);

        List<PublicBlogResponseDTO> newestBlogsAsPublic = newestBlogs.stream()
                .map(blogResponse -> PublicBlogResponseDTO.builder()
                        .id(blogResponse.getId())
                        .thumbnailImageUrl(blogResponse.getThumbnailImageUrl())
                        .title(blogResponse.getTitle())
                        .build())
                .collect(Collectors.toList());
        allPublicBlogs.addAll(newestBlogsAsPublic);
        return allPublicBlogs;
    }
}
