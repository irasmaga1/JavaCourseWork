package org.project.courseWork.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = "matherials")

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

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
    @JoinColumn
//    @Column
            (name = "category_id")
    private Category category;
}
