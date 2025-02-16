package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.constants.Constants;
import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.TagDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.mapper.TagMapper;
import com.fpt.capstone.tourism.model.Tag;
import com.fpt.capstone.tourism.repository.TagRepository;
import com.fpt.capstone.tourism.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {


    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public List<Tag> findAllById(List<Long> tagIds) {
        return tagRepository.findAllById(tagIds);
    }

    @Override
    public GeneralResponse<List<TagDTO>> findAll() {
        try {
            List<Tag> tags = tagRepository.findAll();
            List<TagDTO> result = tags.stream().map(tagMapper::toDTO).toList();
            return GeneralResponse.of(result);
        } catch (Exception e) {
            throw BusinessException.of(HttpStatus.NOT_FOUND, Constants.Message.GET_TAGS_NOT_FOUND_MESSAGE, e);
        }
    }


}
