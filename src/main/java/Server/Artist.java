package Server;

public class Artist {
    private int id;
    private String name;
    private String family;
    private String nickName;
    private String bornCity;
    private String bornDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getBornCity() {
        return bornCity;
    }

    public void setBornCity(String bornCity) {
        this.bornCity = bornCity;
    }

    public String getBornDate() {
        return bornDate;
    }

    public void setBornDate(String bornDate) {
        this.bornDate = bornDate;
    }

    public Artist(int id, String name, String family, String nickName, String bornCity, String bornDate) {
        this.id = id;
        this.name = name;
        this.family = family;
        this.nickName = nickName;
        this.bornCity = bornCity;
        this.bornDate = bornDate;
    }

    public Artist(String name, String family, String nickName, String bornCity, String bornDate) {
        this.name = name;
        this.family = family;
        this.nickName = nickName;
        this.bornCity = bornCity;
        this.bornDate = bornDate;
    }
}
