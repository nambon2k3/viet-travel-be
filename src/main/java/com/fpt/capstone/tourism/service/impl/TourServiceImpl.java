package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.dto.common.GeneralResponse;
import com.fpt.capstone.tourism.dto.common.TourDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.mapper.TourMapper;
import com.fpt.capstone.tourism.model.Tour;
import com.fpt.capstone.tourism.repository.TourRepository;
import com.fpt.capstone.tourism.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static com.fpt.capstone.tourism.constants.Constants.Message.GENERAL_SUCCESS_MESSAGE;

@RequiredArgsConstructor
@Service
public class TourServiceImpl implements TourService {
    private final TourRepository tourRepository;
    private final TourMapper tourMapper;

    @Override
    public TourDTO findTopTourOfYear() {
        try{
            List<Long> topTourIds = tourRepository.findTopTourIdsOfCurrentYear();

            if (topTourIds.isEmpty()) {
                Tour tempTour = tourRepository.findNewestTour();
                return tourMapper.toDTO(tempTour);
                //throw BusinessException.of("Top tour of the year not found");
            }

            // Pick a random tour ID from the list
            Long randomTourId = topTourIds.get(new Random().nextInt(topTourIds.size()));

            // Fetch and convert the tour to DTO
            return tourRepository.findById(randomTourId)
                    .map(tourMapper::toDTO).orElseThrow();
        } catch (Exception ex){
            throw BusinessException.of("Error retrieving top tour of year", ex);
        }

    }

    @Override
    public List<TourDTO> findTrendingTours(int numberTour) {
        Pageable pageable = PageRequest.of(0, numberTour);
        List<Long> trendingTourIds = tourRepository.findTrendingTourIds(pageable);

        // Fetch all tours by their IDs and convert to DTOs
        return tourRepository.findAllById(trendingTourIds)
                .stream()
                .map(tourMapper::toDTO)
                .collect(Collectors.toList());
    }
}
