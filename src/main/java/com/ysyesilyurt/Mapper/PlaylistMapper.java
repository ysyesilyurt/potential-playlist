package com.ysyesilyurt.Mapper;

import com.ysyesilyurt.EntityModel.PlaylistEntityModel;
import com.ysyesilyurt.EntityModel.SongEntityModel;
import com.ysyesilyurt.Model.Playlist;
import com.ysyesilyurt.Model.Song;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", uses = {SongMapper.class}) // to be able to map song model with its entity
@Component
public interface PlaylistMapper {

    List<Playlist> toPlaylistList(List<PlaylistEntityModel> playlistEntityModelList);

    @Mapping(source = "createdBy.username", target = "createdByName")
    @Mapping(source = "createdBy.id", target = "createdById")
    Playlist toPlaylist(PlaylistEntityModel playlistEntityModel);

    @AfterMapping
    default void afterPlaylistMapping(PlaylistEntityModel playlistEntityModel,
                                  @MappingTarget Playlist playlist) {
        float totalLength = 0;
        int songCount = 0;
        if (playlistEntityModel.getSongs() != null) {
            List<Long> songIds = new ArrayList<>();
            for (SongEntityModel songEntityModel: playlistEntityModel.getSongs()) {
                songIds.add(songEntityModel.getId());
                totalLength += songEntityModel.getLength();
                songCount++;
            }
            playlist.setSongIds(songIds);
            playlist.setTotalLength(totalLength);
            playlist.setSongCount(songCount);
        }
        else {
            playlist.setSongIds(null);
            playlist.setTotalLength(0);
            playlist.setSongCount(0);
        }
    }

    List<PlaylistEntityModel> toPlaylistEntityModelList(List<Playlist> playlists);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    @Mapping(source = "createdById", target = "createdBy.id")
    PlaylistEntityModel toPlaylistEntityModel(Playlist playlist);

    @AfterMapping
    default void afterPlaylistEntityModelMapping(Playlist playlist,
                                             @MappingTarget PlaylistEntityModel playlistEntityModel) {
        if (playlist.getSongIds() != null) {
            List<SongEntityModel> songEntityModelList = new ArrayList<>();
            for (Long songId: playlist.getSongIds()) {
                SongEntityModel songEntityModel = new SongEntityModel();
                songEntityModel.setId(songId);
                songEntityModelList.add(songEntityModel);
            }
            playlistEntityModel.setSongs(songEntityModelList);
        }
        else
            playlistEntityModel.setSongs(null);
    }
}
