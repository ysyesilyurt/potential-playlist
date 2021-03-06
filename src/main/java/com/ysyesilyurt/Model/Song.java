package com.ysyesilyurt.Model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Builder
@Getter
@Setter
public class Song {

    private Long id;
    private long createdAt;
    private long lastModifiedAt;
    private String title;
    private String description;
    private float length;
    private String artistName;
    private Long albumId; // we only keep album's id in song
    private String albumName;
    private List<Long> playlistIds; // we keep playlists with their ids in song DTO
}
