package Shared;

import java.io.Serializable;
import java.util.List;

public class  ArtistAlbumListResponse extends Response implements Serializable {

    private List<ArtistAlbumResponse> artistAlbumResponses;

    public List<ArtistAlbumResponse> getArtistAlbumResponses() {
        return artistAlbumResponses;
    }

    public void setArtistAlbumResponses(List<ArtistAlbumResponse> artistAlbumResponses) {
        this.artistAlbumResponses = artistAlbumResponses;
    }
}
