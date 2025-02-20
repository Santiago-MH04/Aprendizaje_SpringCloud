package org.santiago.springcloud.msvcproducts.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "products")
public class Product {
        //Atributos de Product
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @Transient
    private int port;

        //Constructores de Product
    public Product() { }
    public Product(Long id, String name, String description, Double price) {
        this();
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }
        @PrePersist
        public void prePersist() {
            this.createdAt = LocalDate.now();
        }

        //Asignadores de atributos de Product (setters)
    public void setId(Long id) {
        this.id = id;
    }
        public void setName(String name) {
            this.name = name;
        }
            public void setDescription(String description) {
                this.description = description;
            }
                public void setPrice(Double price) {
                    this.price = price;
                }
                    public void setCreatedAt(LocalDate createdAt) {
                        this.createdAt = createdAt;
                    }
                        public void setPort(int port) {
                            this.port = port;
                        }



        //Lectores de atributos de Product (getters)
    public Long getId() {
        return this.id;
    }
        public String getName() {
            return this.name;
        }
            public String getDescription() {
                return this.description;
            }
                public Double getPrice() {
                    return this.price;
                }
                    public LocalDate getCreatedAt() {
                        return this.createdAt;
                    }
                        public int getPort() {
                            return this.port;
                        }

    //Métodos de Product
}
