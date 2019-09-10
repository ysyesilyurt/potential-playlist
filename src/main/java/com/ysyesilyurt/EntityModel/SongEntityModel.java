package com.ysyesilyurt.EntityModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ysyesilyurt.EntityModel.PlaylistEntityModel;
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
@Entity(name = "Song")
@Table(name = "song")
@EntityListeners(AuditingEntityListener.class)
public class SongEntityModel {

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
    @Column(name = "description", columnDefinition = "TEXT") // columnDefinition = "TEXT" saying create a column of type TEXT
    private String description;

    @NotNull
    @Column(name = "length", columnDefinition = "float default 0.0")
    private float length;

    @Override
    public String toString() {
        return String.format("SongEntityModel with id %d and title %s", id, title);
    }

    @NotNull // A song can not exist without an album
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // a song can have one album
    @JoinColumn(name = "album_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private AlbumEntityModel album;

    // Song may not belong to any playlist
    @ManyToMany(mappedBy = "songs") // a song may appear in more than one playlists /* Info: mappedBy maps field with a field that counters in other entity */
    @OrderBy("created_at DESC")
    private List<PlaylistEntityModel> playlists;
}
