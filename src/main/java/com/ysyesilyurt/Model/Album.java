package com.ysyesilyurt.Model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class Album {

    private Long id;
    private long createdAt;
    private long lastModifiedAt;
    private String title;
    private String description;
    private float totalLength;
    private int songCount;
    private String artistName;
    private Long artistId; // we only keep artist's id in album
    private List<Long> songIds; // we keep songs as a list of song ids in album DTO
}
