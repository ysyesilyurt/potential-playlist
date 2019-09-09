package com.ysyesilyurt.Mapper;

import com.ysyesilyurt.EntityModel.AlbumEntityModel;
import com.ysyesilyurt.EntityModel.SongEntityModel;
import com.ysyesilyurt.Model.Album;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AlbumMapper {

    @Mapping(source = "artist.fullName", target = "artistName")
    @Mapping(source = "artist.id", target = "artistId")
    Album toAlbum(AlbumEntityModel albumEntityModel);

    // TODO: List<SongEntityModel> => List<Song> ??
    @AfterMapping
    default void afterAlbumMapping(AlbumEntityModel albumEntityModel,
                                      @MappingTarget Album album) {
        float totalLength = 0;
        int songCount = 0;
        for (SongEntityModel song: albumEntityModel.getSongs()) {
            totalLength += song.getLength();
            songCount++;
        }
        album.setTotalLength(totalLength);
        album.setSongCount(songCount);
    }

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    AlbumEntityModel toAlbumEntityModel(Album album);
    // TODO: List<Song> => List<SongEntityModel> ??
}
