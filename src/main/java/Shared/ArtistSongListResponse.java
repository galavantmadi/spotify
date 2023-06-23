package Shared;

import java.io.Serializable;
import java.util.List;

public class ArtistSongListResponse extends Response implements Serializable {

    private List<ArtistSongResponse> artistSongResponseList;

    public List<ArtistSongResponse> getArtistSongResponseList() {
        return artistSongResponseList;
    }

    public void setArtistSongResponseList(List<ArtistSongResponse> artistSongResponseList) {
        this.artistSongResponseList = artistSongResponseList;
    }
}
