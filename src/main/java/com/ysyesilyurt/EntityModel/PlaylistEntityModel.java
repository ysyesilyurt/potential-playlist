package com.ysyesilyurt.EntityModel;

import com.ysyesilyurt.Enum.PlaylistCategory;
import com.ysyesilyurt.EntityModel.SongEntityModel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@Entity(name = "Playlist")
@Table(name = "playlist")
@EntityListeners(AuditingEntityListener.class)
public class PlaylistEntityModel {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private PlaylistCategory category;

//    @NotNull
//    @Column(name = "songCount", columnDefinition = "integer default 0")
//    private int songCount;
//
//    @NotNull
//    @Column(name = "totalLength", columnDefinition = "float default 0.0")
//    private float totalLength;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "songs",
            joinColumns = @JoinColumn(name = "playlist_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id")
    )
    /*
    @JoinTable annotation is used to join two table using a third table.
    This third table associates two table by their Ids. To define @JoinTable we can use below attributes.
        name: This is the name of third table.
        joinColumns: Assign the column of third table related to entity itself.
        inverseJoinColumns: Assign the column of third table related to associated entity.
     */
    @OrderBy("created_at DESC")
    private Set<SongEntityModel> songs;
}
