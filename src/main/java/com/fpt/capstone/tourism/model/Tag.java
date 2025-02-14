package com.fpt.capstone.tourism.model;


import jakarta.persistence.*;
import lombok.*;

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
    private Boolean deleted;

    @ManyToMany(mappedBy = "tags")
    private List<Tour> tours;

    @ManyToMany(mappedBy = "blogTags")
    @ToString.Exclude
    private List<Blog> blogs;

}
