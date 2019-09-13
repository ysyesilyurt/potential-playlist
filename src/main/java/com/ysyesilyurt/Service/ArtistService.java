package com.ysyesilyurt.Service;

import com.ysyesilyurt.EntityModel.ArtistEntityModel;
import com.ysyesilyurt.Exception.ResourceNotFoundException;
import com.ysyesilyurt.Mapper.ArtistMapper;
import com.ysyesilyurt.Model.Artist;
import com.ysyesilyurt.Repository.ArtistRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ArtistService {

    private ArtistRepository artistRepository;
    private ArtistMapper artistMapper;

    public ArtistService(ArtistRepository artistRepository,
                         ArtistMapper artistMapper) {
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
    }

    public List<Artist> getAllArtists() {

        List<ArtistEntityModel> queryResult = artistRepository.findAll();
        return artistMapper.toArtistList(queryResult);
    }

    public Artist getArtistById(Long artistId) {

        Optional<ArtistEntityModel> queryResult = artistRepository.findById(artistId);
        if (queryResult.isPresent())
            return artistMapper.toArtist(queryResult.get());
        else
            throw new ResourceNotFoundException(String.format("Artist not found with id : %d", artistId));
    }

    public Artist createArtist(Artist newArtist) {

        ArtistEntityModel artistEntityModel = this.persistArtist(newArtist);
        newArtist.setId(artistEntityModel.getId());
        return newArtist;
    }

    public void updateArtistById(Long artistId, String newName) {

        Optional<ArtistEntityModel> queryResult = artistRepository.findById(artistId);
        if (!queryResult.isPresent()) {
            throw new ResourceNotFoundException(String.format("Artist not found with id : %d", artistId));
        }

        ArtistEntityModel artistEntityModel = queryResult.get();

        if (newName != null)
            artistEntityModel.setFullName(newName);

        this.persistArtist(artistEntityModel);
    }

    @Transactional
    public void deleteArtistById(Long artistId) {

        Optional<ArtistEntityModel> queryResult = artistRepository.findById(artistId);
        if (queryResult.isPresent()) {
            artistRepository.delete(queryResult.get());
        }
    }

    @Transactional
    protected ArtistEntityModel persistArtist(Artist artist) {

        ArtistEntityModel artistEntityModel = artistMapper.toArtistEntityModel(artist);
        artistRepository.save(artistEntityModel);
        return artistEntityModel;
    }

    @Transactional
    protected void persistArtist(ArtistEntityModel artistEntityModel) {
        artistRepository.save(artistEntityModel);
    }
}
