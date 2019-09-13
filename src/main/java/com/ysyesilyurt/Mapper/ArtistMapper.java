package com.ysyesilyurt.Mapper;

import com.ysyesilyurt.EntityModel.AlbumEntityModel;
import com.ysyesilyurt.EntityModel.ArtistEntityModel;
import com.ysyesilyurt.Model.Artist;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {AlbumMapper.class}) // to be able to map album model with its entity
@Component
public interface ArtistMapper {

    List<Artist> toArtistList(List<ArtistEntityModel> artistEntityModelList);

    Artist toArtist(ArtistEntityModel artistEntityModel);

    @AfterMapping
    default void afterArtistMapping(ArtistEntityModel artistEntityModel,
                                   @MappingTarget Artist artist) {
        int songCount = 0, albumCount = 0;
        if (artistEntityModel.getAlbums() != null) {
            List<Long> albumIds = new ArrayList<>();
            for (AlbumEntityModel albumEntityModel: artistEntityModel.getAlbums()) {
                albumIds.add(albumEntityModel.getId());
                songCount += albumEntityModel.getSongs().size();
                albumCount++;
            }
            artist.setAlbumIds(albumIds);
            artist.setAlbumCount(albumCount);
            artist.setSongCount(songCount);
        }
        else {
            artist.setAlbumIds(null);
            artist.setAlbumCount(0);
            artist.setSongCount(0);
        }
    }

    List<ArtistEntityModel> toArtistEntityModelList(List<Artist> artistList);

    ArtistEntityModel toArtistEntityModel(Artist artist);

    @AfterMapping
    default void afterAlbumEntityModelMapping(Artist artist,
                                              @MappingTarget ArtistEntityModel artistEntityModel) {
        if (artist.getAlbumIds() != null) {
            List<AlbumEntityModel> albumEntityModelList = new ArrayList<>();
            for (Long albumId: artist.getAlbumIds()) {
                AlbumEntityModel albumEntityModel = new AlbumEntityModel();
                albumEntityModel.setId(albumId);
                albumEntityModelList.add(albumEntityModel);
            }
            artistEntityModel.setAlbums(albumEntityModelList);
        }
        else
            artistEntityModel.setAlbums(null);
    }
}
