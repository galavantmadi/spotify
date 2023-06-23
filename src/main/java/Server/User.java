package Server;

public class User extends SuperUser{
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public User(int id, String username, String password, String token, int type, String phone) {
        super(id, username, password, token, type);
        this.phone = phone;
    }

    public User(String username, String password, String token, int type, String phone) {
        super(username, password, token, type);
        this.phone = phone;
    }

    public User(String phone) {
        this.phone = phone;
    }

    public User(int id, String username, String password, String token, int type) {
        super(id, username, password, token, type);
    }

    public User(String username, String password, String token, int type) {
        super(username, password, token, type);
    }

    public User() {
    }

    @Override
    public void crate() {
        System.out.println("Create user user with username "+this.getUsername());
    }

    @Override
    public void login() {
        System.out.println("Login user with username "+this.getUsername());
    }
}
