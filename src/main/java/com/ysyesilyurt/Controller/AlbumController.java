package com.ysyesilyurt.Controller;

import com.ysyesilyurt.Dto.AlbumUpdateDto;
import com.ysyesilyurt.Model.Album;
import com.ysyesilyurt.Rest.RestApiController;
import com.ysyesilyurt.Rest.RestApiResponseBody;
import com.ysyesilyurt.Service.AlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
public class AlbumController extends RestApiController {

    private AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping("/albums")
    public ResponseEntity<RestApiResponseBody<Resources<Album>>> getAllAlbums() {

        List<Album> albumList = albumService.getAllAlbums();
        return this.successCollection(albumList);
    }

    @GetMapping("/albums/{albumId}")
    public ResponseEntity<RestApiResponseBody<Resource<Album>>> getAlbumById(@PathVariable Long albumId) {
        Album album = albumService.getAlbumById(albumId);
        return this.success(album);
    }

    /**
     * An album can only exist with an artist, so it can be created with 2 different ways:
     * 1- A direct POST request to "/api/albums", in this case artistId needs to be specified in the body of newAlbum.
     * 2- POST request to "/api/artists/{artistId}/albums", in this case artistId is extracted from the path.
     * (Both ways will be dealt by this endpoint)
     * @param newAlbum
     * @param artistId
     * @return ResponseEntity<RestApiResponseBody<Resource<Album>>>
     */
    @PostMapping(value = {"/albums", "/artists/{artistId}/albums"})
    public ResponseEntity<RestApiResponseBody<Resource<Album>>> createAlbum(@RequestBody Album newAlbum,
                                                                            @PathVariable Optional<Long> artistId) {
        albumService.createAlbum(newAlbum, artistId);
        return this.success(newAlbum);
    }

    /**
     * Only album's title, description and artist can be updated from here.
     *
     * Album's artist can ONLY be changed from this endpoint (by specifying artistId in requestBody)
     * @param artistId
     * @param requestBody
     * @return
     */
    @PutMapping("/albums/{albumId}")
    public ResponseEntity<RestApiResponseBody<?>> updateAlbumById(
            @PathVariable Long albumId,
            @RequestBody AlbumUpdateDto requestBody) {
        albumService.updateAlbumById(albumId, requestBody.getArtistId(),
                requestBody.getNewTitle(), requestBody.getNewDescription());
        return this.success();
    }

    @DeleteMapping("/albums/{albumId}")
    public ResponseEntity<RestApiResponseBody<?>> deleteAlbumById(
            @PathVariable Long albumId) {
        albumService.deleteAlbumById(albumId);
        return this.success();
    }
}
