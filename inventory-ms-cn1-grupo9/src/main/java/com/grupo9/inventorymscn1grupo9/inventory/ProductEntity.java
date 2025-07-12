package com.grupo9.inventorymscn1grupo9.inventory;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "PRODUCTS", schema = "CN1G9")
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID", nullable = false)
  private Long id;

  @Column(name = "TITLE", nullable = false, length = 255)
  private String title;

  @Column(name = "DESCRIPTION", length = 1000)
  private String description;

  @Column(name = "IMAGE_URL", length = 1000)
  private String imageUrl;

  @Column(name = "PRICE", precision = 10, scale = 2)
  private BigDecimal price;

  @Column(name = "OWNER_ID", length = 50)
  private String ownerId;

  @Column(name = "DISCOUNT", precision = 3, scale = 0)
  private Integer discount;

  @Column(name = "ENDS_AT")
  private LocalDateTime endsAt;

}