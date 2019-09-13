package com.ysyesilyurt.Service;

import com.ysyesilyurt.EntityModel.PlaylistEntityModel;
import com.ysyesilyurt.EntityModel.SongEntityModel;
import com.ysyesilyurt.Enum.PlaylistCategory;
import com.ysyesilyurt.Exception.ResourceNotFoundException;
import com.ysyesilyurt.Mapper.PlaylistMapper;
import com.ysyesilyurt.Mapper.SongMapper;
import com.ysyesilyurt.Model.Playlist;
import com.ysyesilyurt.Model.Song;
import com.ysyesilyurt.Repository.PlaylistRepository;
import com.ysyesilyurt.Repository.SongRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PlaylistService {

    private PlaylistRepository playlistRepository;
    private PlaylistMapper playlistMapper;
    private SongRepository songRepository;
    private SongMapper songMapper;

    public PlaylistService(PlaylistRepository playlistRepository,
                           PlaylistMapper playlistMapper,
                           SongRepository songRepository,
                           SongMapper songMapper) {
        this.playlistRepository = playlistRepository;
        this.playlistMapper = playlistMapper;
        this.songRepository = songRepository;
        this.songMapper = songMapper;
    }

    public List<Playlist> getAllPlaylists() {

        List<PlaylistEntityModel> queryResult = playlistRepository.findAll();
        return playlistMapper.toPlaylistList(queryResult);
    }

    public List<Song> getAllSongsOfPlaylist(Long playlistId) {

        Optional<PlaylistEntityModel> queryResult = playlistRepository.findById(playlistId);
        if (queryResult.isPresent())
            return songMapper.toSongList(queryResult.get().getSongs());
        else
            throw new ResourceNotFoundException(String.format("Playlist not found with id : %d", playlistId));
    }

    public Playlist getPlaylistById(Long playlistId) {

        Optional<PlaylistEntityModel> queryResult = playlistRepository.findById(playlistId);
        if (queryResult.isPresent())
            return playlistMapper.toPlaylist(queryResult.get());
        else
            throw new ResourceNotFoundException(String.format("Playlist not found with id : %d", playlistId));
    }

    public Playlist createPlaylist(Playlist newPlaylist) {

        PlaylistEntityModel playlistEntityModel = this.persistPlaylist(newPlaylist);
        newPlaylist.setId(playlistEntityModel.getId());
        return newPlaylist;
    }

    public void updatePlaylistById(Long playlistId, String newTitle, String newDesc, PlaylistCategory newCategory) {

        Optional<PlaylistEntityModel> queryResult = playlistRepository.findById(playlistId);
        if (!queryResult.isPresent()) {
            throw new ResourceNotFoundException(String.format("Playlist not found with id : %d", playlistId));
        }

        PlaylistEntityModel playlistEntityModel = queryResult.get();

        if (newTitle != null)
            playlistEntityModel.setTitle(newTitle);

        if (newDesc != null)
            playlistEntityModel.setDescription(newDesc);

        if (newCategory != null)
            playlistEntityModel.setCategory(newCategory);

        this.persistPlaylist(playlistEntityModel);
    }

    public void addSongToPlaylist(Long playlistId, Long songId) {

        Optional<PlaylistEntityModel> queryResultPlaylist = playlistRepository.findById(playlistId);
        if (!queryResultPlaylist.isPresent()) {
            throw new ResourceNotFoundException(String.format("Playlist not found with id : %d", playlistId));
        }

        Optional<SongEntityModel> queryResultSong = songRepository.findById(songId);
        if (!queryResultSong.isPresent()) {
            throw new ResourceNotFoundException(String.format("Song not found with id : %d", songId));
        }

        PlaylistEntityModel playlistEntityModel = queryResultPlaylist.get();
        SongEntityModel songEntityModel = queryResultSong.get();
        playlistEntityModel.getSongs().add(songEntityModel);

        this.persistPlaylist(playlistEntityModel);
    }

    public void deleteSongFromPlaylist(Long playlistId, Long songId) {

        Optional<PlaylistEntityModel> queryResultPlaylist = playlistRepository.findById(playlistId);
        if (!queryResultPlaylist.isPresent()) {
            throw new ResourceNotFoundException(String.format("Playlist not found with id : %d", playlistId));
        }

        Optional<SongEntityModel> queryResultSong = songRepository.findById(songId);
        if (!queryResultSong.isPresent()) {
            throw new ResourceNotFoundException(String.format("Song not found with id : %d", songId));
        }

        PlaylistEntityModel playlistEntityModel = queryResultPlaylist.get();
        SongEntityModel songEntityModel = queryResultSong.get();
        playlistEntityModel.getSongs().remove(songEntityModel);

        this.persistPlaylist(playlistEntityModel);
    }

    @Transactional
    public void deletePlaylistById(Long playlistId) {

        Optional<PlaylistEntityModel> queryResult = playlistRepository.findById(playlistId);
        if (queryResult.isPresent()) {
            playlistRepository.delete(queryResult.get());
        }
    }

    @Transactional
    protected PlaylistEntityModel persistPlaylist(Playlist playlist) {

        PlaylistEntityModel playlistEntityModel = playlistMapper.toPlaylistEntityModel(playlist);
        playlistRepository.save(playlistEntityModel);
        return playlistEntityModel;
    }

    @Transactional
    protected void persistPlaylist(PlaylistEntityModel playlistEntityModel) {
        playlistRepository.save(playlistEntityModel);
    }
}
