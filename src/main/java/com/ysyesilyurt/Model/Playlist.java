package com.ysyesilyurt.Model;

import com.ysyesilyurt.Enum.PlaylistCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Playlist {

    private Long id;
    private long createdAt;
    private long lastModifiedAt;
    private String title;
    private String description;
    private PlaylistCategory category;
    private float totalLength;
    private int songCount;
    private List<Song> songs; // we keep songs as a list of songs in playlist

    // TODO: created_by?
}
