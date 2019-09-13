package com.ysyesilyurt.Mapper;

import com.ysyesilyurt.EntityModel.AlbumEntityModel;
import com.ysyesilyurt.EntityModel.ArtistEntityModel;
import com.ysyesilyurt.EntityModel.SongEntityModel;
import com.ysyesilyurt.Model.Album;
import com.ysyesilyurt.Repository.ArtistRepository;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {SongMapper.class}) // to be able to map other models with their entities
@Component
public interface AlbumMapper {

    List<Album> toAlbumList(List<AlbumEntityModel> albumEntityModelList);

    @Mapping(source = "artist.fullName", target = "artistName")
    @Mapping(source = "artist.id", target = "artistId")
    Album toAlbum(AlbumEntityModel albumEntityModel);

    @AfterMapping
    default void afterAlbumMapping(AlbumEntityModel albumEntityModel,
                                      @MappingTarget Album album) {
        float totalLength = 0;
        int songCount = 0;
        if (albumEntityModel.getSongs() != null) {
            List<Long> songIds = new ArrayList<>();
            for (SongEntityModel songEntityModel: albumEntityModel.getSongs()) {
                songIds.add(songEntityModel.getId());
                totalLength += songEntityModel.getLength();
                songCount++;
            }
            album.setSongIds(songIds);
            album.setTotalLength(totalLength);
            album.setSongCount(songCount);
        }
        else {
            album.setSongIds(null);
            album.setTotalLength(0);
            album.setSongCount(0);
        }
    }

    List<AlbumEntityModel> toAlbumEntityModelList(List<Album> albumList);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(source = "artistId", target = "artist.id")
    AlbumEntityModel toAlbumEntityModel(Album album);

    @AfterMapping
    default void afterAlbumEntityModelMapping(Album album,
                                                 @MappingTarget AlbumEntityModel albumEntityModel) {
        if (album.getSongIds() != null) {
            List<SongEntityModel> songEntityModelList = new ArrayList<>();
            for (Long songId: album.getSongIds()) {
                SongEntityModel songEntityModel = new SongEntityModel();
                songEntityModel.setId(songId);
                songEntityModelList.add(songEntityModel);
            }
            albumEntityModel.setSongs(songEntityModelList);
        }
        else
            albumEntityModel.setSongs(null);
    }

}
