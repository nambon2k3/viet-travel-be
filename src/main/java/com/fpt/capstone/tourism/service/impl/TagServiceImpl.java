package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.model.Tag;
import com.fpt.capstone.tourism.repository.TagRepository;
import com.fpt.capstone.tourism.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<Tag> findAllById(List<Long> tagIds) {
        return tagRepository.findAllById(tagIds);
    }
}
