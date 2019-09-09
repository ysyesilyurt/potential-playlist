package com.ysyesilyurt.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Artist {

    private Long id;
    private String fullName;
    private int songCount;
    private int albumCount;
    private List<Album> albums; // we keep albums as a list of albums in artist
}
