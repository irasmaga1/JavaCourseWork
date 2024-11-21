package org.project.courseWork.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "supliers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Suplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "suplier")
    private List<Material> materialLists;

}
