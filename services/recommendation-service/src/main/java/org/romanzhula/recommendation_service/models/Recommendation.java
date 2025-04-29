package org.romanzhula.recommendation_service.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "recommendations")
public class Recommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recommendation_seq")
    @SequenceGenerator(name = "recommendation_seq", sequenceName = "recommendation_seq", allocationSize = 1)
    private Long id;

    private String userId;

    private String productId;

    private double score;

    private String action;

}
