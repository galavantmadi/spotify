package Server;



public class Admin extends SuperUser{

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Admin(int id, String username, String password, String token, int type, String email) {
        super(id, username, password, token, type);
        this.email = email;
    }

    public Admin(String username, String password, String token, int type, String email) {
        super(username, password, token, type);
        this.email = email;
    }

    public Admin(String email) {
        this.email = email;
    }

    public Admin(int id, String username, String password, String token, int type) {
        super(id, username, password, token, type);
    }

    public Admin(String username, String password, String token, int type) {
        super(username, password, token, type);
    }

    public Admin() {
    }

    @Override
    public void crate() {
        System.out.println("Create admin user with username "+this.getUsername());
    }

    @Override
    public void login() {
        System.out.println("Login admin with username "+this.getUsername());
    }
}
