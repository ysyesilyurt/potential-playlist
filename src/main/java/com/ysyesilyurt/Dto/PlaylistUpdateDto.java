package com.ysyesilyurt.Dto;

import com.ysyesilyurt.Enum.PlaylistCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaylistUpdateDto extends UpdateDto {
    private PlaylistCategory newCategory;
}
