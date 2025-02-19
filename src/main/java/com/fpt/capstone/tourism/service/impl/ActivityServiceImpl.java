package com.fpt.capstone.tourism.service.impl;

import com.fpt.capstone.tourism.dto.common.*;
import com.fpt.capstone.tourism.dto.request.GeoPositionRequestDTO;
import com.fpt.capstone.tourism.dto.response.PagingDTO;
import com.fpt.capstone.tourism.exception.common.BusinessException;
import com.fpt.capstone.tourism.helper.validator.Validator;
import com.fpt.capstone.tourism.mapper.ActivityCategoryMapper;
import com.fpt.capstone.tourism.mapper.ActivityMapper;
import com.fpt.capstone.tourism.mapper.GeoPositionMapper;
import com.fpt.capstone.tourism.mapper.LocationMapper;
import com.fpt.capstone.tourism.model.*;
import com.fpt.capstone.tourism.repository.ActivityRepository;
import com.fpt.capstone.tourism.service.ActivityService;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
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
import static com.fpt.capstone.tourism.service.impl.ServiceProviderServiceImpl.removeAccents;


@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepository activityRepository;
    private final ActivityMapper activityMapper;
    private final GeoPositionMapper geoPositionMapper;
    private final LocationMapper locationMapper;
    private final ActivityCategoryMapper activityCategoryMapper;

    @Override
    public List<ActivityDTO> findRecommendedActivities(int numberActivity) {
        List<Activity> randomActivities = activityRepository.findRandomActivities(numberActivity);
        return randomActivities.stream()
                .map(activityMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public GeneralResponse<ActivityDTO> saveActivity(ActivityDTO activityDTO) {
        try{
            //Validate input data
            Validator.validateActivity(activityDTO);

            //Check duplicate location
            if(activityRepository.findByTitle(activityDTO.getTitle()) != null){
                throw BusinessException.of(EXISTED_LOCATION);
            }

            //Save date to database
            Activity activity = activityMapper.toEntity(activityDTO);
            activity.setCreatedAt(LocalDateTime.now());
            activity.setDeleted(false);
            activityRepository.save(activity);

            ActivityDTO responseActivityDTO = activityMapper.toDTO(activity);
            responseActivityDTO.setId(activity.getId());

            return new GeneralResponse<>(HttpStatus.OK.value(), "Create activity successfully", responseActivityDTO);
        }catch (BusinessException be){
            throw be;
        } catch (Exception ex){
            throw BusinessException.of("Create activity fail", ex);
        }
    }

    @Override
    public GeneralResponse<ActivityDTO> getActivityById(Long id) {
        try{
            Activity activity = activityRepository.findById(id).orElseThrow();

            ActivityDTO activityDTO = activityMapper.toDTO(activity);
            return new GeneralResponse<>(HttpStatus.OK.value(), GENERAL_SUCCESS_MESSAGE, activityDTO);
        }catch (BusinessException be){
            throw be;
        } catch (Exception ex){
            throw BusinessException.of(GENERAL_FAIL_MESSAGE, ex);
        }
    }

    @Override
    public GeneralResponse<PagingDTO<List<ActivityDTO>>> getAllActivity(int page, int size, String keyword, Boolean isDeleted, String orderDate, Long categoryId) {
        try {
            // Determine sorting order dynamically
            Sort sort = "asc".equalsIgnoreCase(orderDate) ? Sort.by("createdAt").ascending() : Sort.by("createdAt").descending();
            Pageable pageable = PageRequest.of(page, size, sort);

            Specification<Activity> spec = buildSearchSpecification(keyword, isDeleted, categoryId);

            Page<Activity> activityPage = activityRepository.findAll(spec, pageable);
            List<ActivityDTO> activityDTOS = activityPage.getContent().stream()
                    .map(activityMapper::toDTO)
                    .collect(Collectors.toList());

            return buildPagedResponse(activityPage, activityDTOS);
        } catch (Exception ex) {
            throw BusinessException.of("not ok", ex);
        }
    }

    @Override
    public GeneralResponse<ActivityDTO> updateActivity(Long id, ActivityDTO activityDTO) {
        try{
            //Find in database
            Activity activity = activityRepository.findById(id).orElseThrow();

            //Validate input data
            Validator.validateActivity(activityDTO);

            //Update activity information
            if(!activityDTO.getTitle().equals(activity.getTitle())){
                //Check duplicate activity
                if(activityRepository.findByTitle(activityDTO.getTitle()) != null){
                    throw BusinessException.of("Existed activity");
                }
                activity.setTitle(activityDTO.getTitle());
            }
            if(!activityDTO.getContent().equals(activity.getContent())){
                activity.setContent(activityDTO.getContent());
            }
            if(!activityDTO.getImageUrl().equals(activity.getImageUrl())){
                activity.setImageUrl(activityDTO.getImageUrl());
            }

            GeoPositionRequestDTO currentGeoPosition = GeoPositionRequestDTO.builder()
                    .latitude(activity.getGeoPosition().getLatitude())
                    .longitude(activity.getGeoPosition().getLongitude())
                    .build();
            if(!currentGeoPosition.equals(activityDTO.getGeoPosition())){
                activity.setGeoPosition(geoPositionMapper.toEntity(activityDTO.getGeoPosition()));
            }

            if(activityDTO.getPricePerPerson() != activity.getPricePerPerson()){
                activity.setPricePerPerson(activityDTO.getPricePerPerson());
            }

            if(activityDTO.getLocation().getId() != activity.getLocation().getId()){
                activity.setLocation(locationMapper.toEntity(activityDTO.getLocation()));
            }

            if(activityDTO.getActivityCategory().getId() != activity.getActivityCategory().getId()){
                activity.setActivityCategory(activityCategoryMapper.toEntity(activityDTO.getActivityCategory()));
            }

            activity.setUpdatedAt(LocalDateTime.now());

            activityRepository.save(activity);

            ActivityDTO reponseActivityDTO = activityMapper.toDTO(activity);
            reponseActivityDTO.setId(activity.getId());

            return new GeneralResponse<>(HttpStatus.OK.value(), GENERAL_SUCCESS_MESSAGE, reponseActivityDTO);
        } catch (BusinessException be){
            throw be;
        } catch (Exception ex){
            throw BusinessException.of(GENERAL_FAIL_MESSAGE, ex);
        }
    }

    @Override
    public GeneralResponse<ActivityDTO> deleteActivity(Long id, boolean isDeleted) {
        try{
            Activity activity = activityRepository.findById(id).orElseThrow();

            activity.setDeleted(isDeleted);
            activity.setUpdatedAt(LocalDateTime.now());
            activityRepository.save(activity);

            ActivityDTO activityDTO = activityMapper.toDTO(activity);
            return new GeneralResponse<>(HttpStatus.OK.value(), GENERAL_SUCCESS_MESSAGE, activityDTO);
        }catch (BusinessException be){
            throw be;
        } catch (Exception ex){
            throw BusinessException.of(GENERAL_FAIL_MESSAGE, ex);
        }
    }

    private Specification<Activity> buildSearchSpecification(String keyword, Boolean isDeleted, Long categoryId) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filter by isDeleted status
            if (isDeleted != null) {
                predicates.add(cb.equal(root.get("deleted"), isDeleted));
            }

            // 🔹 Filter by categoryId (if provided)
            if (categoryId != null) {
                Join<Activity, ActivityCategory> categoryJoin = root.join("activityCategory", JoinType.INNER);
                predicates.add(cb.equal(categoryJoin.get("id"), categoryId));
            }

            // Normalize text for search (Ignore case & accents)
            if (keyword != null && !keyword.trim().isEmpty()) {
                // Normalize keyword before passing into the query
                String normalizedKeyword = removeAccents(keyword.toLowerCase());

                // Normalize Activity Title
                Expression<String> normalizedActivityTitle = cb.function("unaccent", String.class, cb.lower(root.get("title")));

                // Normalize Location Name
                Join<Activity, Location> locationJoin = root.join("location", JoinType.LEFT);
                Expression<String> normalizedLocationName = cb.function("unaccent", String.class, cb.lower(locationJoin.get("name")));

                // Normalize Activity Category Name
                Join<Activity, ActivityCategory> categoryJoin = root.join("activityCategory", JoinType.LEFT);
                Expression<String> normalizedCategoryName = cb.function("unaccent", String.class, cb.lower(categoryJoin.get("name")));

                // Compare using LIKE
                Predicate activityTitlePredicate = cb.like(normalizedActivityTitle, "%" + normalizedKeyword + "%");
                Predicate locationNamePredicate = cb.like(normalizedLocationName, "%" + normalizedKeyword + "%");
                Predicate categoryNamePredicate = cb.like(normalizedCategoryName, "%" + normalizedKeyword + "%");

                // Match title OR location OR category
                predicates.add(cb.or(activityTitlePredicate, locationNamePredicate, categoryNamePredicate));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }




    private GeneralResponse<PagingDTO<List<ActivityDTO>>> buildPagedResponse(Page<Activity> activityPage, List<ActivityDTO> activityDTOS) {
        PagingDTO<List<ActivityDTO>> pagingDTO = PagingDTO.<List<ActivityDTO>>builder()
                .page(activityPage.getNumber())
                .size(activityPage.getSize())
                .total(activityPage.getTotalElements())
                .items(activityDTOS)
                .build();

        return new GeneralResponse<>(HttpStatus.OK.value(), "ok", pagingDTO);
    }
}
