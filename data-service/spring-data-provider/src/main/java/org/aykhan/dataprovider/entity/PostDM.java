package org.aykhan.dataprovider.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "T_POST")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDM {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  private String body;
  private String title;
  @ManyToOne
  private UserDM author;
  @OneToMany(cascade = ALL, mappedBy = "post")
  private List<CommentsDM> comments;
  private String attachment;
}
