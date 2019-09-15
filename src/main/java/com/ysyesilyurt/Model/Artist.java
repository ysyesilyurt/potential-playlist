package com.ysyesilyurt.Model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class Artist {

    private Long id;
    private String fullName;
    private int songCount;
    private int albumCount;
    private List<Long> albumIds; // we keep albums as a list of album ids in artist
}
