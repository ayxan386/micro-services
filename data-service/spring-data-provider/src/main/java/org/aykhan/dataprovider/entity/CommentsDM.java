package org.aykhan.dataprovider.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@Entity
@Table(name = "T_COMMENT")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDM {
  @Id
  @GeneratedValue(strategy = AUTO)
  private Long id;

  @ManyToOne(cascade = ALL, fetch = LAZY)
  @JoinColumn(name = "postId")
  private PostDM postId;

  private String body;
}
