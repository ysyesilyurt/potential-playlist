package com.ysyesilyurt.Controller;

import com.ysyesilyurt.Dto.SongUpdateDto;
import com.ysyesilyurt.EntityModel.UserEntityModel;
import com.ysyesilyurt.Model.Playlist;
import com.ysyesilyurt.Model.Song;
import com.ysyesilyurt.Rest.RestApiResponseBody;
import com.ysyesilyurt.Rest.RestApiController;
import com.ysyesilyurt.Service.PlaylistService;
import com.ysyesilyurt.Service.SongService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api", headers = "Accept=application/json")
@Slf4j
public class SongController extends RestApiController {

    private SongService songService;
    private PlaylistService playlistService;

    public SongController(SongService songService,
                          PlaylistService playlistService) {
        this.songService = songService;
        this.playlistService = playlistService;
    }

    @GetMapping("/songs")
    public ResponseEntity<RestApiResponseBody<Resources<Song>>> getAllSongs(
            Authentication authentication) {
        List<Song> songList = songService.getAllSongs();
        return this.successCollection(songList);
    }

    @GetMapping("/songs/{songId}")
    public ResponseEntity<RestApiResponseBody<Resource<Song>>> getSongById(
            Authentication authentication,
            @PathVariable Long songId) {
        Song song = songService.getSongById(songId);
        return this.success(song);
    }

    /**
     * A song can only exist within an album, so it can be created with 2 different ways:
     * 1- A direct POST request to "/api/songs", in this case albumId needs to be specified in the body of newSong.
     * 2- POST request to "/api/albums/{albumId}/songs", in this case albumId is extracted from the path.
     * (Both ways will be dealt by this endpoint)
     * @param newSong
     * @param albumId
     * @return ResponseEntity<RestApiResponseBody<Resource<Song>>>
     */
    @PostMapping(value = {"/songs", "/albums/{albumId}/songs"})
    public ResponseEntity<RestApiResponseBody<Resource<Song>>> createSong(
            Authentication authentication,
            @RequestBody Song newSong,
            @PathVariable Optional<Long> albumId) {
        songService.createSong(newSong, albumId);
        return this.success(newSong);
    }

    /**
     * Only song's title, description, length and album can be updated from here.
     *
     * Song's album can ONLY be changed from this endpoint (by specifying albumId in requestBody)
     *
     * Song's playlists can not be changed from this endpoint, user needs to use existing
     * add/delete song from playlist endpoints for this purpose
     * @param songId
     * @param requestBody
     * @return
     */
    @PutMapping("/songs/{songId}")
    public ResponseEntity<RestApiResponseBody<?>> updateSongById(
            Authentication authentication,
            @PathVariable Long songId,
            @RequestBody SongUpdateDto requestBody) {
        songService.updateSongById(songId, requestBody.getAlbumId(), requestBody.getNewTitle(),
                requestBody.getNewDescription(), requestBody.getNewLength());
        return this.success();
    }

    @DeleteMapping("/songs/{songId}")
    public ResponseEntity<RestApiResponseBody<?>> deleteSongById(
            Authentication authentication,
            @PathVariable Long songId) {
        songService.deleteSongById(songId);
        return this.success();
    }

    /**
     * Since there is a many-to-many relationship between songs and playlists both ends have a list of other end.
     * Thus user can retrieve a list of playlists that this song belongs with requesting a GET to this endpoint.
     * @param songId
     * @return
     */
    @GetMapping("/songs/{songId}/playlists")
    public ResponseEntity<RestApiResponseBody<Resources<Playlist>>> getAllPlaylistsThatSongBelongs(
            Authentication authentication,
            @PathVariable Long songId) {
        List<Playlist> playlists = songService.getAllPlaylistsThatSongBelongs(songId);
        return this.successCollection(playlists);
    }

    /**
     * Since there is a many-to-many relationship between songs and playlists both ends have a list of other end.
     * Thus song addition to playlist can be done from both controllers.
     * @param playlistId
     * @param songId
     * @return
     */
    @PutMapping("/songs/{songId}/playlists/{playlistId}")
    public ResponseEntity<RestApiResponseBody<?>> addSongToPlaylist(
            Authentication authentication,
            @PathVariable Long playlistId,
            @PathVariable Long songId) {
        playlistService.addSongToPlaylist(playlistId, songId); /* PlaylistService is utilized */
        return this.success();
    }

    /**
     * Since there is a many-to-many relationship between songs and playlists both ends have a list of other end.
     * Thus song deletion from playlist can be done from both controllers.
     * @param playlistId
     * @param songId
     * @return
     */
    @DeleteMapping("/songs/{songId}/playlists/{playlistId}")
    public ResponseEntity<RestApiResponseBody<?>> deleteSongFromPlaylist(
            Authentication authentication,
            @PathVariable Long playlistId,
            @PathVariable Long songId) {
        playlistService.deleteSongFromPlaylist(playlistId, songId); /* PlaylistService is utilized */
        return this.success();
    }
}
