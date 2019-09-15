package com.ysyesilyurt.Controller;

import com.ysyesilyurt.Dto.ArtistUpdateDto;
import com.ysyesilyurt.Model.Artist;
import com.ysyesilyurt.Rest.RestApiController;
import com.ysyesilyurt.Rest.RestApiResponseBody;
import com.ysyesilyurt.Service.ArtistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/api/artists")
@Slf4j
public class ArtistController extends RestApiController {

    private ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public ResponseEntity<RestApiResponseBody<Resources<Artist>>> getAllArtists() {
        List<Artist> artistList = artistService.getAllArtists();
        return this.successCollection(artistList);
    }

    @GetMapping("/{artistId}")
    public ResponseEntity<RestApiResponseBody<Resource<Artist>>> getArtistById(@PathVariable Long artistId) {
        Artist artist = artistService.getArtistById(artistId);
        return this.success(artist);
    }

    @PostMapping
    public ResponseEntity<RestApiResponseBody<Resource<Artist>>> createArtist(@RequestBody Artist artist) {
        artistService.createArtist(artist);
        return this.success(artist);
    }

    /**
     * Only artist's fullName can be updated through here.
     *
     * @param artistId
     * @param requestBody
     * @return
     */
    @PutMapping("/{artistId}")
    public ResponseEntity<RestApiResponseBody<?>> updateArtistById(
            @PathVariable Long artistId,
            @RequestBody ArtistUpdateDto requestBody) {
        artistService.updateArtistById(artistId, requestBody.getNewFullName());
        return this.success();
    }

    @DeleteMapping("/{artistId}")
    public ResponseEntity<RestApiResponseBody<?>> deleteArtistById(
            @PathVariable Long artistId) {
        artistService.deleteArtistById(artistId);
        return this.success();
    }
}
