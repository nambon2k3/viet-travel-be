package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.constants.Constants;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.BlogDTO;
import com.fpt.capstone.tourism.dto.common.TagDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogServiceImpl implements BlogService {

    private final BlogRepository blogRepository;
    private final UserService userService;
    private final TagService tagService;
    private final BlogMapper blogMapper;

    @Override
    @Transactional
    public GeneralResponse<BlogDTO> saveBlog(BlogDTO blogDTO) {
        try {
            Validator.validateBlog(blogDTO.getTitle(), blogDTO.getDescription(), blogDTO.getContent());
            User user = userService.findById(blogDTO.getAuthor().getId());
            List<Tag> tags = tagService.findAllById(blogDTO.getTags().stream().map(TagDTO::getId).collect(Collectors.toList()));
            Blog blog = blogMapper.toEntity(blogDTO, user, tags);
            blogRepository.save(blog);
            blogDTO.setId(blog.getId());
            return new GeneralResponse<>(HttpStatus.OK.value(), Constants.Message.CREATE_BLOG_SUCCESS_MESSAGE, blogDTO);
        } catch (BusinessException be) {
            throw be;
        }catch (Exception ex) {
            throw BusinessException.of(Constants.Message.CREATE_BLOG_FAIL_MESSAGE, ex);
        }
    }

    @Override
    public GeneralResponse<BlogDTO> getBlogById(Long id) {
        try {
            Blog blog = blogRepository.findById(id).orElseThrow();
            BlogDTO blogDTO = blogMapper.toDTO(blog);
            return new GeneralResponse<>(HttpStatus.OK.value(), Constants.Message.GENERAL_SUCCESS_MESSAGE, blogDTO);
        } catch (BusinessException be) {
            throw be;
        }catch (Exception ex) {
            throw BusinessException.of(Constants.Message.GENERAL_FAIL_MESSAGE, ex);
        }
    }

    @Override
    public GeneralResponse<BlogDTO> changeBlogDeletedStatus(Long id, boolean isDeleted) {
        try {
            Blog blog = blogRepository.findById(id).orElseThrow();
            blog.setDeleted(isDeleted);
            blogRepository.save(blog);
            BlogDTO blogDTO = blogMapper.toDTO(blog);
            return new GeneralResponse<>(HttpStatus.OK.value(), Constants.Message.GENERAL_SUCCESS_MESSAGE, blogDTO);
        } catch (BusinessException be) {
            throw be;
        }catch (Exception ex) {
            throw BusinessException.of(Constants.Message.GENERAL_FAIL_MESSAGE, ex);
        }
    }

    @Override
    public GeneralResponse<PagingDTO<List<BlogDTO>>> getBlogs(int page, int size, String keyword, Boolean isDeleted) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
            Specification<Blog> spec = buildSearchSpecification(keyword, isDeleted);

            Page<Blog> blogPage = blogRepository.findAll(spec, pageable);
            List<BlogDTO> blogDTOs = blogPage.getContent().stream()
                    .map(blogMapper::toDTO)
                    .collect(Collectors.toList());
            return buildPagedResponse(blogPage, blogDTOs);
        } catch (Exception ex) {
            throw BusinessException.of("toang", ex);
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
                predicates.add(cb.equal(root.get("isDeleted"), isDeleted));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private GeneralResponse<PagingDTO<List<BlogDTO>>> buildPagedResponse(Page<Blog> blogPage, List<BlogDTO> blogDTOs) {
        PagingDTO<List<BlogDTO>> pagingDTO = PagingDTO.<List<BlogDTO>>builder()
                .page(blogPage.getNumber())
                .size(blogPage.getSize())
                .total(blogPage.getTotalElements())
                .items(blogDTOs)
                .build();
        return new GeneralResponse<>(HttpStatus.OK.value(), "ngon", pagingDTO);
    }
}
