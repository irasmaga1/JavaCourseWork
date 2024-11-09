package org.project.courseWork.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "stock_level")

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StockLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "current_count")
    private int currentCount;

    @Column(name = "minimal_quantity")
    private int minimalQuantity;

    @Column(name = "matherial_id")
    private Long matherialId;

}
