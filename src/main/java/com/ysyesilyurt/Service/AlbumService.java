package com.ysyesilyurt.Service;

import com.ysyesilyurt.EntityModel.AlbumEntityModel;
import com.ysyesilyurt.EntityModel.ArtistEntityModel;
import com.ysyesilyurt.Exception.PotentialPlaylistRuntimeException;
import com.ysyesilyurt.Exception.ResourceNotFoundException;
import com.ysyesilyurt.Mapper.AlbumMapper;
import com.ysyesilyurt.Model.Album;
import com.ysyesilyurt.Repository.AlbumRepository;
import com.ysyesilyurt.Repository.ArtistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AlbumService {

    private AlbumRepository albumRepository;
    private ArtistService artistService;
    private AlbumMapper albumMapper;
    private ArtistRepository artistRepository;

    public AlbumService(AlbumRepository albumRepository,
                        ArtistService artistService,
                        AlbumMapper albumMapper,
                        ArtistRepository artistRepository) {
        this.albumRepository = albumRepository;
        this.artistService = artistService;
        this.albumMapper = albumMapper;
        this.artistRepository = artistRepository;
    }

    public List<Album> getAllAlbums() {
        List<AlbumEntityModel> queryResult = albumRepository.findAll();
        return albumMapper.toAlbumList(queryResult);
    }

    public Album getAlbumById(Long albumId) {

        Optional<AlbumEntityModel> queryResult = albumRepository.findById(albumId);
        if (queryResult.isPresent())
            return albumMapper.toAlbum(queryResult.get());
        else
            throw new ResourceNotFoundException(String.format("Album not found with id : %d", albumId));
    }

    public Album createAlbum(Album newAlbum, Optional<Long> artistId) {

        if (artistId.isPresent()) {
            newAlbum.setArtistId(artistId.get());
        }
        else {
            if (newAlbum.getArtistId() == null)
                throw new PotentialPlaylistRuntimeException(
                        String.format("Album with title: %s needs an artist to exist!", newAlbum.getTitle()));
        }

        AlbumEntityModel albumEntityModel = this.persistAlbum(newAlbum);
        newAlbum.setId(albumEntityModel.getId());
        return newAlbum;
    }

    public void updateAlbumById(Long albumId, Long artistId, String newTitle, String newDesc) {

        Optional<AlbumEntityModel> queryResult = albumRepository.findById(albumId);
        if (!queryResult.isPresent()) {
            throw new ResourceNotFoundException(String.format("Album not found with id : %d", albumId));
        }

        AlbumEntityModel albumEntityModel = queryResult.get();

        if (artistId != null) {
            Optional<ArtistEntityModel> queryResultArtist = artistRepository.findById(artistId);
            if (!queryResult.isPresent()) {
                throw new ResourceNotFoundException(String.format("Artist not found with id : %d, " +
                        "failed to update album %d", artistId, albumId));
            }
            else
                albumEntityModel.setArtist(queryResultArtist.get());
        }

        if (newTitle != null)
            albumEntityModel.setTitle(newTitle);

        if (newDesc != null)
            albumEntityModel.setDescription(newDesc);

        this.persistAlbum(albumEntityModel);
    }

    @Transactional
    public void deleteAlbumById(Long albumId) {

        Optional<AlbumEntityModel> queryResult = albumRepository.findById(albumId);
        if (queryResult.isPresent()) {
            albumRepository.delete(queryResult.get());
        }
    }

    @Transactional
    protected AlbumEntityModel persistAlbum(Album album) {

        AlbumEntityModel albumEntityModel = albumMapper.toAlbumEntityModel(album);
        albumRepository.save(albumEntityModel);
        return albumEntityModel;
    }

    @Transactional
    protected void persistAlbum(AlbumEntityModel albumEntityModel) {
        albumRepository.save(albumEntityModel);
    }
}
