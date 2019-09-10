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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
@Component
public interface PlaylistMapper {

    List<Playlist> toPlaylistList(List<PlaylistEntityModel> playlistEntityModelList);

    Playlist toPlaylist(PlaylistEntityModel playlistEntityModel);

    // TODO: List<SongEntityModel> => List<Song> ??
    @AfterMapping
    default void afterPlaylistMapping(PlaylistEntityModel playlistEntityModel,
                                  @MappingTarget Playlist playlist) {
        float totalLength = 0;
        int songCount = 0;
        for (SongEntityModel song: playlistEntityModel.getSongs()) {
            totalLength += song.getLength();
            songCount++;
        }
        playlist.setTotalLength(totalLength);
        playlist.setSongCount(songCount);
    }

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    PlaylistEntityModel toPlaylistEntityModel(Playlist playlist);

    // TODO: List<Song> => List<SongEntityModel> ??
}
