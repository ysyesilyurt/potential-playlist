package com.ysyesilyurt.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class UpdateDto {

    private String newTitle;
    private String newDescription;
}
