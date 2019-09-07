package com.ysyesilyurt.EntityModel;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@Entity(name = "Artist")
@Table(name = "artist")
public class ArtistEntityModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "fullname")
    private String fullName;

//    @NotNull
//    @Column(name = "albumCount", columnDefinition = "integer default 0")
//    private int albumCount;
//
//    @NotNull
//    @Column(name = "songCount", columnDefinition = "integer default 0")
//    private int songCount;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "artist")
    @OrderBy("created_at DESC")
    private Set<AlbumEntityModel> albums;
}
