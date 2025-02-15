package com.fpt.capstone.tourism.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tag")
public class Tag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tag_name")
    private String name;

    @Column
    private String description;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private List<Tour> tours;

    @ManyToMany(mappedBy = "blogTags")
    private List<Blog> blogs;

}
