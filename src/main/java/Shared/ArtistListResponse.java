package Shared;

import java.io.Serializable;
import java.util.List;

public class ArtistListResponse extends Response implements Serializable  {
    private List<ArtistResponse>artistResponseList;

    public List<ArtistResponse> getArtistResponseList() {
        return artistResponseList;
    }

    public void setArtistResponseList(List<ArtistResponse> artistResponseList) {
        this.artistResponseList = artistResponseList;
    }
}
