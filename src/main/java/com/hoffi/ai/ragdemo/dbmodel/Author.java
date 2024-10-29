package com.hoffi.ai.ragdemo.dbmodel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
//@Table
@Builder
public class Author {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq_generator")
  @SequenceGenerator(name = "author_seq_generator", sequenceName = "author_id_seq", allocationSize = 1)
  @NotNull
  private Integer id;
  @NotNull
  private String username;
  @Column
  private String email;
  @Column
  private String bio;
//  @JsonIgnore
//  @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
//  private List<Post> posts;
}
