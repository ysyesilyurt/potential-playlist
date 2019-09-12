package com.ysyesilyurt.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongUpdateDto extends UpdateDto {
    private float newLength;
    private Long albumId;
}
