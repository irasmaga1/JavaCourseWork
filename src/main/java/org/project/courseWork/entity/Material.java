package org.project.courseWork.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table (name = "materials")

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "count")
    private int count;

    @Column(name = "price")
    private int price;

    @Column(name = "brand")
    private String brand;

    @ManyToOne
    @JoinColumn(name = "suplier_id")
    private Suplier suplier;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
