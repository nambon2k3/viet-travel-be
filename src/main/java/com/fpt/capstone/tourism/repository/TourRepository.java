package com.fpt.capstone.tourism.repository;

import com.fpt.capstone.tourism.model.Tour;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourRepository  extends JpaRepository<Tour, Long>, JpaSpecificationExecutor<Tour> {
    @Query("SELECT tb.tour.id FROM TourBooking tb " +
            "WHERE YEAR(tb.bookingDate) = YEAR(CURRENT_DATE)" +
            "AND tb.tour.deleted = FALSE " +
            "GROUP BY tb.tour.id " +
            "ORDER BY COUNT(tb.tour.id) DESC")
    List<Long> findTopTourIdsOfCurrentYear();

    @Query("SELECT tb.tour.id FROM TourBooking tb " +
            "WHERE tb.tour.deleted = FALSE " +
            "GROUP BY tb.tour.id " +
            "ORDER BY COUNT(tb.tour.id) DESC")
    List<Long> findTrendingTourIds(Pageable pageable);

    @Query("SELECT t FROM Tour t WHERE t.deleted = FALSE ORDER BY t.createdAt DESC LIMIT 1")
    Tour findNewestTour();
}
