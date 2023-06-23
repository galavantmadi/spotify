package Server;

public abstract class SuperUser {

    private int id;
    private String username;
    private String password;
    private String token;

    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public SuperUser(int id, String username, String password, String token, int type) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.token = token;
        this.type = type;
    }

    public SuperUser(String username, String password, String token, int type) {
        this.username = username;
        this.password = password;
        this.token = token;
        this.type = type;
    }

    public SuperUser() {
    }

    public abstract void crate();
    public abstract void login();
}
