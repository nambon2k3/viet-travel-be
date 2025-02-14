package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.TagDTO;
import com.fpt.capstone.tourism.dto.request.BlogRequestDTO;
import com.fpt.capstone.tourism.dto.response.BlogResponseDTO;
import com.fpt.capstone.tourism.dto.response.PagingDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.helper.validator.Validator;
import com.fpt.capstone.tourism.mapper.BlogMapper;
import com.fpt.capstone.tourism.model.Blog;
import com.fpt.capstone.tourism.model.Tag;
import com.fpt.capstone.tourism.model.User;
import com.fpt.capstone.tourism.repository.BlogRepository;
import com.fpt.capstone.tourism.service.BlogService;
import com.fpt.capstone.tourism.service.TagService;
import com.fpt.capstone.tourism.service.UserService;
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
    public GeneralResponse<PagingDTO<List<BlogResponseDTO>>> getBlogs(int page, int size, String keyword, Boolean isDeleted) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            Specification<Blog> spec = buildSearchSpecification(keyword, isDeleted);

            Page<Blog> blogPage = blogRepository.findAll(spec, pageable);
            List<BlogResponseDTO> blogDTOs = blogPage.getContent().stream()
                    .map(blogMapper::toDTO)
                    .collect(Collectors.toList());

            return buildPagedResponse(blogPage, blogDTOs);
        } catch (Exception ex) {
            throw BusinessException.of("Error retrieving blogs", ex);
        }
    }

    private Specification<Blog> buildSearchSpecification(String keyword, Boolean isDeleted) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (keyword != null && !keyword.isEmpty()) {
                Predicate titlePredicate = cb.like(root.get("title"), "%" + keyword + "%");
                Predicate descPredicate = cb.like(root.get("description"), "%" + keyword + "%");
                predicates.add(cb.or(titlePredicate, descPredicate));
            }
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
}
