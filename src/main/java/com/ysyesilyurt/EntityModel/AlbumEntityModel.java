package com.ysyesilyurt.EntityModel;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity(name = "Album")
@Table(name = "album")
@EntityListeners(AuditingEntityListener.class)
public class AlbumEntityModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private long createdAt;

    @LastModifiedDate
    @Column(name = "last_modified_at", nullable = false)
    private long lastModifiedAt;

    @NotNull
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Lob
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Override
    public String toString() {
        return String.format("AlbumEntityModel with id %d and title %s", id, title);
    }

    @OneToMany(cascade = CascadeType.ALL, // an album may have more than one song
            fetch = FetchType.LAZY,
            mappedBy = "album")
    @OrderBy("created_at DESC")
    private List<SongEntityModel> songs;

    @NotNull // An album can not exist without an artist
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // more than one album may belong to one artist
    @JoinColumn(name = "artist_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ArtistEntityModel artist;
}
