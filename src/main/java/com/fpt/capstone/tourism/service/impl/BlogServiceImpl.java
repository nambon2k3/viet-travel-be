package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.constants.Constants;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.BlogDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.mapper.BlogMapper;
import com.fpt.capstone.tourism.model.Blog;
import com.fpt.capstone.tourism.model.Tag;
import com.fpt.capstone.tourism.model.User;
import com.fpt.capstone.tourism.repository.BlogRepository;
import com.fpt.capstone.tourism.service.BlogService;
import com.fpt.capstone.tourism.service.TagService;
import com.fpt.capstone.tourism.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

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
            User user = userService.findById(blogDTO.getAuthorId());
            List<Tag> tags = tagService.findAllById(blogDTO.getTagIds());

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


}
