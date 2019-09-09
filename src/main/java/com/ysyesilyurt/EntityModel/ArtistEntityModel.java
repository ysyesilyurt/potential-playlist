package com.ysyesilyurt.EntityModel;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

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
    @Column(name = "fullName")
    private String fullName;

    @Override
    public String toString() {
        return String.format("ArtistEntityModel with id %d and fullname %s", id, fullName);
    }

    // artist may not have an album
    @OneToMany(cascade = CascadeType.ALL, // an album can at most have 1 artist
            fetch = FetchType.LAZY,
            mappedBy = "artist")
    @OrderBy("created_at DESC")
    private List<AlbumEntityModel> albums;
}
