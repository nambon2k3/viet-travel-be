package com.fpt.capstone.tourism.service;


import com.fpt.capstone.tourism.model.Tag;

import java.util.List;

public interface TagService {
    List<Tag> findAllById(List<Long> tagIds);
}
