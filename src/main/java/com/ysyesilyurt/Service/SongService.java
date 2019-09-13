package com.ysyesilyurt.Service;

import com.ysyesilyurt.EntityModel.AlbumEntityModel;
import com.ysyesilyurt.EntityModel.SongEntityModel;
import com.ysyesilyurt.Exception.PotentialPlaylistRuntimeException;
import com.ysyesilyurt.Exception.ResourceNotFoundException;
import com.ysyesilyurt.Mapper.PlaylistMapper;
import com.ysyesilyurt.Mapper.SongMapper;
import com.ysyesilyurt.Model.Playlist;
import com.ysyesilyurt.Model.Song;
import com.ysyesilyurt.Repository.AlbumRepository;
import com.ysyesilyurt.Repository.SongRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class SongService {

    private SongRepository songRepository;
    private PlaylistService playlistService;
    private AlbumService albumService;
    private SongMapper songMapper;
    private PlaylistMapper playlistMapper;
    private AlbumRepository albumRepository;

    public SongService(SongRepository songRepository,
                       PlaylistService playlistService,
                       AlbumService albumService,
                       SongMapper songMapper,
                       PlaylistMapper playlistMapper,
                       AlbumRepository albumRepository) {
        this.songRepository = songRepository;
        this.playlistService = playlistService;
        this.albumService = albumService;
        this.songMapper = songMapper;
        this.playlistMapper = playlistMapper;
        this.albumRepository = albumRepository;
    }

    public List<Song> getAllSongs() {
        List<SongEntityModel> queryResult = songRepository.findAll();
        return songMapper.toSongList(queryResult);
    }

    public List<Playlist> getAllPlaylistsThatSongBelongs(Long songId) {

        Optional<SongEntityModel> queryResult = songRepository.findById(songId);
        if (queryResult.isPresent())
            return playlistMapper.toPlaylistList(queryResult.get().getPlaylists());
        else
            throw new ResourceNotFoundException(String.format("Song not found with id : %d", songId));
    }

    public Song getSongById(Long songId) {

        Optional<SongEntityModel> queryResult = songRepository.findById(songId);
        if (queryResult.isPresent())
            return songMapper.toSong(queryResult.get());
        else
            throw new ResourceNotFoundException(String.format("Song not found with id : %d", songId));
    }

    public Song createSong(Song newSong, Optional<Long> albumId) {

        if (albumId.isPresent()) {
            newSong.setAlbumId(albumId.get());
        }
        else {
            if (newSong.getAlbumId() == null)
                throw new PotentialPlaylistRuntimeException(
                        String.format("Song with title: %s needs an album to exist! Could not create song %d",
                                newSong.getTitle(), newSong.getId()));
        }

        SongEntityModel songEntityModel = this.persistSong(newSong);
        newSong.setId(songEntityModel.getId());
        return newSong;
    }

    public void updateSongById(Long songId, Long albumId, String newTitle, String newDesc, Float newLength) {

        Optional<SongEntityModel> queryResult = songRepository.findById(songId);
        if (!queryResult.isPresent()) {
            throw new ResourceNotFoundException(String.format("Song not found with id : %d", songId));
        }

        SongEntityModel songEntityModel = queryResult.get();

        if (albumId != null) {
            Optional<AlbumEntityModel> queryResultAlbum = albumRepository.findById(albumId);
            if (!queryResult.isPresent()) {
                throw new ResourceNotFoundException(String.format("Album not found with id : %d, " +
                        "failed to update song %d", albumId, songId));
            }
            else
                songEntityModel.setAlbum(queryResultAlbum.get());
        }

        if (newTitle != null)
            songEntityModel.setTitle(newTitle);

        if (newDesc != null)
            songEntityModel.setDescription(newDesc);

        if (newLength != null)
            songEntityModel.setLength(newLength);

        this.persistSong(songEntityModel);
    }

    @Transactional
    public void deleteSongById(Long songId) {

        Optional<SongEntityModel> queryResult = songRepository.findById(songId);
        if (queryResult.isPresent()) {
            songRepository.delete(queryResult.get());
        }
    }

    @Transactional
    protected SongEntityModel persistSong(Song song) {

        SongEntityModel songEntityModel = songMapper.toSongEntityModel(song);
        songRepository.save(songEntityModel);
        return songEntityModel;
    }

    @Transactional
    protected void persistSong(SongEntityModel songEntityModel) {
        songRepository.save(songEntityModel);
    }
}
