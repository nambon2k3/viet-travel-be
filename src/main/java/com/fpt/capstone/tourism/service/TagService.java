package com.fpt.capstone.tourism.service;


import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.TagDTO;
import com.fpt.capstone.tourism.model.Tag;

import java.util.List;

public interface TagService {
    List<Tag> findAllById(List<Long> tagIds);
    GeneralResponse<List<TagDTO>> findAll();
}
