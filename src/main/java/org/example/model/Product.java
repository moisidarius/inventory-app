package org.example.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nume;
    private String sku;
    private Double pretBaza;
    private Integer stoc;
    private Integer stocMinim = 5;

    @ManyToOne
    private Category categorie;
}