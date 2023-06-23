package Shared;

import java.io.Serializable;
import java.util.List;

public class FavoriteSongListResponse extends Response implements Serializable {

    private List<FavoriteSongResponse> favoriteSongResponseList;

    public List<FavoriteSongResponse> getFavoriteSongResponseList() {
        return favoriteSongResponseList;
    }

    public void setFavoriteSongResponseList(List<FavoriteSongResponse> favoriteSongResponseList) {
        this.favoriteSongResponseList = favoriteSongResponseList;
    }
}
