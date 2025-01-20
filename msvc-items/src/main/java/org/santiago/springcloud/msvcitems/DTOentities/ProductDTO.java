package org.santiago.springcloud.msvcitems.DTOentities;

import java.time.LocalDate;

public class ProductDTO {
        //Atributos de ProductDTO
    private Long id;
    private String name;
    private String description;
    private Double price;
    private LocalDate createdAt;
    private int port;

        //Constructores de ProductDTO
    public ProductDTO() {    }
    public ProductDTO(Long id, String name, Double price, String description) {
        this();
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

        //Asignadores de atributos de ProductDTO (setters)
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


        //Lectores de atributos de ProductDTO (getters)
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

        //Métodos de ProductDTO
}
