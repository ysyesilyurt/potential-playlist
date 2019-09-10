package com.ysyesilyurt.Mapper;


import com.ysyesilyurt.EntityModel.PlaylistEntityModel;
import com.ysyesilyurt.EntityModel.SongEntityModel;
import com.ysyesilyurt.Model.Song;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Mapper(componentModel = "spring")
@Component // TODO: check the component.
public interface SongMapper {

    List<Song> toSongList(List<SongEntityModel> songEntityModelList);

    @Mapping(source = "album.artist.fullName", target = "artistName")
    @Mapping(source = "album.id", target = "albumId")
    @Mapping(source = "album.title", target = "albumName")
    Song toSong(SongEntityModel songEntityModel);

    @AfterMapping
    default void afterSongMapping(SongEntityModel songEntityModel,
                                    @MappingTarget Song song) {
        if (songEntityModel.getPlaylists() != null) {
            List<Long> playlistIds = new ArrayList<>();
            for (PlaylistEntityModel playlist: songEntityModel.getPlaylists()) {
                playlistIds.add(playlist.getId());
            }
            song.setPlaylistIds(playlistIds);
        }
        else
            song.setPlaylistIds(null);
    }

    List<SongEntityModel> toSongEntityModelList(List<Song> songList);

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastModifiedAt", ignore = true)
    SongEntityModel toSongEntityModel(Song song);

    @AfterMapping
    default void afterSongEntityModelMapping(Song song,
                                  @MappingTarget SongEntityModel songEntityModel) {
        if (song.getPlaylistIds() != null) {
            List<PlaylistEntityModel> playlists = new ArrayList<>();
            for (Long playlistId: song.getPlaylistIds()) {
                PlaylistEntityModel playlist = new PlaylistEntityModel();
                playlist.setId(playlistId);
                playlists.add(playlist);
            }
            songEntityModel.setPlaylists(playlists);
        }
        else
            songEntityModel.setPlaylists(null);
    }
}
