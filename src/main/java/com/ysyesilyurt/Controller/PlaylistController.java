package com.ysyesilyurt.Controller;

import com.ysyesilyurt.Dto.PlaylistUpdateDto;
import com.ysyesilyurt.EntityModel.UserEntityModel;
import com.ysyesilyurt.Model.Playlist;
import com.ysyesilyurt.Model.Song;
import com.ysyesilyurt.Rest.RestApiController;
import com.ysyesilyurt.Rest.RestApiResponseBody;
import com.ysyesilyurt.Service.PlaylistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/playlists", headers = "Accept=application/json")
@Slf4j
public class PlaylistController extends RestApiController {

    private PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping
    public ResponseEntity<RestApiResponseBody<Resources<Playlist>>> getAllPlaylists(
            Authentication authentication) {
        List<Playlist> playlists = playlistService.getAllPlaylists();
        return this.successCollection(playlists);
    }

    @GetMapping("/{playlistId}")
    public ResponseEntity<RestApiResponseBody<Resource<Playlist>>> getPlaylistById(
            Authentication authentication,
            @PathVariable Long playlistId) {
        Playlist playlist = playlistService.getPlaylistById(playlistId);
        return this.success(playlist);
    }

    @PostMapping
    public ResponseEntity<RestApiResponseBody<Resource<Playlist>>> createPlaylist(
            Authentication authentication,
            @RequestBody Playlist playlist) {
        playlistService.createPlaylist(playlist, authentication.getName());
        return this.success(playlist);
    }

    /**
     * Only playlist's title, description and category can be updated from here.
     *
     * Playlist's songs can not be changed from this endpoint, user needs to use existing
     * add/delete song from playlist endpoints for this purpose
     * @param playlistId
     * @param requestBody
     * @return
     */
    @PutMapping("/{playlistId}")
    public ResponseEntity<RestApiResponseBody<?>> updatePlaylistById(
            Authentication authentication,
            @PathVariable Long playlistId,
            @RequestBody PlaylistUpdateDto requestBody) {
        playlistService.updatePlaylistById(playlistId, requestBody.getNewTitle(),
                requestBody.getNewDescription(), requestBody.getNewCategory());
        return this.success();
    }

    @DeleteMapping("/{playlistId}")
    public ResponseEntity<RestApiResponseBody<?>> deletePlaylistById(
            Authentication authentication,
            @PathVariable Long playlistId) {
        playlistService.deletePlaylistById(playlistId);
        return this.success();
    }

    /**
     * Since there is a many-to-many relationship between songs and playlists both ends have a list of other end.
     * Thus user can retrieve a list of songs that this playlist has with requesting a GET to this endpoint.
     * @param playlistId
     * @return
     */
    @GetMapping("/{playlistId}/songs")
    public ResponseEntity<RestApiResponseBody<Resources<Song>>> getAllSongsOfPlaylist(
            Authentication authentication,
            @PathVariable Long playlistId) {
        List<Song> songs = playlistService.getAllSongsOfPlaylist(playlistId);
        return this.successCollection(songs);
    }

    /** Since there is a many-to-many relationship between songs and playlists both ends have a list of other end.
     * Thus song addition to playlist can be done from both controllers.
     * @param playlistId
     * @param songId
     * @return
     */
    @PutMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<RestApiResponseBody<?>> addSongToPlaylist(
            Authentication authentication,
            @PathVariable Long playlistId,
            @PathVariable Long songId) {
        playlistService.addSongToPlaylist(playlistId, songId);
        return this.success();
    }

    /** Since there is a many-to-many relationship between songs and playlists both ends have a list of other end.
     * Thus song deletion from playlist can be done from both controllers.
     * @param playlistId
     * @param songId
     * @return
     */
    @DeleteMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<RestApiResponseBody<?>> deleteSongFromPlaylist(
            Authentication authentication,
            @PathVariable Long playlistId,
            @PathVariable Long songId) {
        playlistService.deleteSongFromPlaylist(playlistId, songId);
        return this.success();
    }
}
