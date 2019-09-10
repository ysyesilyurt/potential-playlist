package com.ysyesilyurt.Mapper;

import com.ysyesilyurt.EntityModel.AlbumEntityModel;
import com.ysyesilyurt.EntityModel.ArtistEntityModel;
import com.ysyesilyurt.Model.Artist;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface ArtistMapper {

    List<Artist> toArtistList(List<ArtistEntityModel> artistEntityModelList);

    Artist toArtist(ArtistEntityModel artistEntityModel);

    // TODO: List<AlbumEntityModel> => List<Album> ??
    @AfterMapping
    default void afterArtistMapping(ArtistEntityModel artistEntityModel,
                                   @MappingTarget Artist artist) {
        int songCount = 0, albumCount = 0;
        for (AlbumEntityModel albumEntityModel: artistEntityModel.getAlbums()) {
            songCount += albumEntityModel.getSongs().size();
            albumCount++;
        }
        artist.setAlbumCount(albumCount);
        artist.setSongCount(songCount);
    }

    ArtistEntityModel toArtistEntityModel(Artist artist);
    // TODO: List<Album> => List<AlbumEntityModel> ??
}
