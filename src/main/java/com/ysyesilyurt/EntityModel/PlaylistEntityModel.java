package com.ysyesilyurt.EntityModel;

import com.ysyesilyurt.Enum.PlaylistCategory;
import com.ysyesilyurt.EntityModel.SongEntityModel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

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

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private PlaylistCategory category;

    @Override
    public String toString() {
        return String.format("PlaylistEntityModel with id %d and title %s", id, title);
    }

    // a playlist can exist without having any song
    @ManyToMany(cascade = { // a playlist can have more than one song and a song may appear in more than 1 playlist
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
    private List<SongEntityModel> songs;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserEntityModel createdBy;
}
