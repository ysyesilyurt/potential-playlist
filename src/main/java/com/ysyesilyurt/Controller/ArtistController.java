package com.ysyesilyurt.Controller;

import com.ysyesilyurt.Dto.ArtistUpdateDto;
import com.ysyesilyurt.EntityModel.UserEntityModel;
import com.ysyesilyurt.Model.Artist;
import com.ysyesilyurt.Rest.RestApiController;
import com.ysyesilyurt.Rest.RestApiResponseBody;
import com.ysyesilyurt.Service.ArtistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping(value = "/api/artists", headers = "Accept=application/json")
@Slf4j
public class ArtistController extends RestApiController {

    private ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping
    public ResponseEntity<RestApiResponseBody<Resources<Artist>>> getAllArtists(
            Authentication authentication) {
        List<Artist> artistList = artistService.getAllArtists();
        return this.successCollection(artistList);
    }

    @GetMapping("/{artistId}")
    public ResponseEntity<RestApiResponseBody<Resource<Artist>>> getArtistById(
            Authentication authentication,
            @PathVariable Long artistId) {
        Artist artist = artistService.getArtistById(artistId);
        return this.success(artist);
    }

    @PostMapping
    public ResponseEntity<RestApiResponseBody<Resource<Artist>>> createArtist(
            Authentication authentication,
            @RequestBody Artist artist) {
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
            Authentication authentication,
            @PathVariable Long artistId,
            @RequestBody ArtistUpdateDto requestBody) {
        artistService.updateArtistById(artistId, requestBody.getNewFullName());
        return this.success();
    }

    @DeleteMapping("/{artistId}")
    public ResponseEntity<RestApiResponseBody<?>> deleteArtistById(
            Authentication authentication,
            @PathVariable Long artistId) {
        artistService.deleteArtistById(artistId);
        return this.success();
    }
}
