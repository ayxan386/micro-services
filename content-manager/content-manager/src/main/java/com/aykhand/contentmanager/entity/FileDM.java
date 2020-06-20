package com.aykhand.contentmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "t_contents")
public class FileDM {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  private String name;
  private String type;
  @Lob
  private byte[] content;
}
