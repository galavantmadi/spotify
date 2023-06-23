package Server;

import Shared.*;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.*;

public class ServerMain  {

    static final String DB_URL = "jdbc:postgresql://localhost/postgres";
    static final String USER = "postgres";
    static final String PASS = "123456";

    private Socket socket   = null;
    private ServerSocket server   = null;
    private ObjectInputStream inServer  =  null;
    private ObjectOutputStream outServer     = null;

    private Admin admin;

    public Admin getAdmin() {
        return admin;
    }

    public ServerMain(int port){
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            socket = server.accept();
            System.out.println("Client accepted");

            // takes input from the client socket
            inServer = new ObjectInputStream (socket.getInputStream());


            // sends output to the socket
            outServer  = new ObjectOutputStream(socket.getOutputStream());

            int id = -1;
            while (id!=0){
                try {
                    RequestClient requestClient =(RequestClient) inServer.readObject();
                    id= requestClient.getId();
                    if(id==1){
                        String username=accountIsExist(requestClient.getUsername());
                        Response response=new Response();
                        if(username.equals("")){
                            User user=new User();
                            user.setUsername(requestClient.getUsername());
                            user.setPassword(requestClient.getPassword());
                            user.setPhone(requestClient.getPhone());
                            user.setType(0);
                            createUser(user);
                            response.setStatus("0");
                            response.setMessage("Success To Create User");
                        }
                        else {

                            response.setStatus("-1");
                            response.setMessage("Fail To Create User");
                            outServer.writeObject(response);
                        }
                        outServer.writeObject(response);

                    }
                    else if (id==2){
                        String username=accountIsExist(requestClient.getUsername());
                        Response response=new Response();
                        if(username.equals("")){
                            Admin user=new Admin();
                            user.setUsername(requestClient.getUsername());
                            user.setPassword(requestClient.getPassword());
                            user.setEmail(requestClient.getEmail());
                            user.setType(1);
                            createAdmin(user);
                            response.setStatus("0");
                            response.setMessage("Success To Create User_Admin");
                        }
                        else {

                            response.setStatus("-1");
                            response.setMessage("Fail To Create User_Admin");
                            outServer.writeObject(response);
                        }
                        outServer.writeObject(response);
                    }
                    else if(id==3){

                        LoginResponse loginResponse=loginUser(requestClient.getUsername(),hashPass(requestClient.getPassword()), requestClient.getType());
                        if(loginResponse.getId()!=0){
                            loginResponse.setStatus("0");
                            loginResponse.setMessage("User has been login");

                        } else {
                            loginResponse.setStatus("-1");
                            loginResponse.setMessage("Fail to User Login , Username or password Not valid");
                        }
                        outServer.writeObject(loginResponse);
                    }
                    else if(id==4){
                        LoginResponse loginResponse=logoutUser(requestClient.getUsername(),hashPass(requestClient.getPassword()));
                        if(loginResponse.getId()!=0){
                            loginResponse.setStatus("0");
                            loginResponse.setMessage("User has been Logout");

                        } else {
                            loginResponse.setStatus("-1");
                            loginResponse.setMessage("Fail to Logout , Username or password Not valid");
                        }
                        outServer.writeObject(loginResponse);
                    } else if (id==5) {
                        ArtistListResponse artistListResponse=searchArtist(requestClient.getArtistName(), requestClient.getArtistNickName());
                        if(artistListResponse.getArtistResponseList().size()>0){
                            artistListResponse.setStatus("0");
                            artistListResponse.setMessage("Artist found");
                        }else {
                            artistListResponse.setStatus("-1");
                            artistListResponse.setMessage("Fail to Search artist");
                        }
                        outServer.writeObject(artistListResponse);
                    }else if(id==6){
                         ArtistAlbumListResponse artistAlbumListResponse=searchArtistAlbum(requestClient.getArtistName(), requestClient.getAlbumName());
                        if(artistAlbumListResponse.getArtistAlbumResponses().size()>0){
                            artistAlbumListResponse.setStatus("0");
                            artistAlbumListResponse.setMessage("Album found");
                        } else {
                            artistAlbumListResponse.setStatus("-1");
                            artistAlbumListResponse.setMessage("Fail to Search Album");
                        }
                        outServer.writeObject(artistAlbumListResponse);
                    }else if(id==7){
                        ArtistSongListResponse artistSongListResponse=searchArtistSongByAlbumName(requestClient.getAlbumName());
                        if(artistSongListResponse.getArtistSongResponseList().size()>0){
                            artistSongListResponse.setStatus("0");
                            artistSongListResponse.setMessage("Song found");
                        }else {
                            artistSongListResponse.setStatus("-1");
                            artistSongListResponse.setMessage("Fail to Search Song");
                        }
                        outServer.writeObject(artistSongListResponse);
                    }else if(id==8){
                        SongInfoResponse songInfoResponse=getSongInfo(requestClient.getSongId());
                        if(songInfoResponse!=null){
                            songInfoResponse.setStatus("0");
                            songInfoResponse.setMessage("Song found");
                        }else {
                            songInfoResponse=new SongInfoResponse();
                            songInfoResponse.setStatus("-1");
                            songInfoResponse.setMessage("Fail to Search Song found");
                        }
                        outServer.writeObject(songInfoResponse);
                    }else if(id==9){
                        Integer result=favoriteIsExist(requestClient.getUserId(),requestClient.getSongId());
                        if(result!=null && result!=0){
                            Response response=new Response();
                            response.setStatus("0");
                            response.setMessage("Song Has been added");
                            outServer.writeObject(response);
                        }else {
                            createFavoriteSong(requestClient.getUserId(),requestClient.getSongId());
                            Response response=new Response();
                            response.setStatus("0");
                            response.setMessage("Success To Create Favorite Song");
                            outServer.writeObject(response);
                        }

                    }else if(id==10){
                        FavoriteSongListResponse favoriteSongListResponse=searchFavoriteSongByUserId(requestClient.getUserId());
                        if(favoriteSongListResponse.getFavoriteSongResponseList().size()>0){
                            favoriteSongListResponse.setStatus("0");
                            favoriteSongListResponse.setMessage("List of Favorite found");
                        }else {
                            favoriteSongListResponse.setStatus("-1");
                            favoriteSongListResponse.setMessage("Fail to find List of Favorite");
                        }
                        outServer.writeObject(favoriteSongListResponse);
                    }
                }catch(IOException i)
                {
                    System.out.println(i);
                }
            }

            System.out.println("Closing connection");

            // close connection
            socket.close();
            inServer.close();

        }catch(IOException i)
        {
            System.out.println(i);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args)throws IOException {
        int rowCountArtist=countTableArtist();
        if(rowCountArtist==0){
            final File folder = new File("./src/main/java/Server/Resources");
            listFilesForFolder(folder);
        }

        int rowCountUser=countTableUser();
        if(rowCountUser==0){
            saveUser("user1","1234","09125694345",0);
            saveUser("admin","1234","093944556677",1);
        }

        ServerMain client = new ServerMain( 5000);
    }

    public static String hashPass(String input){

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public  String accountIsExist(String username){
        String query = "SELECT * FROM user_account WHERE username = ?";
        String result="";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                result=rs.getString("username");
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void createAdmin(Admin admin){
        String sql = "INSERT INTO user_account(username,password,email,type) VALUES (?,?,?,?)";

        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            // Execute a query
            System.out.println("Inserting records into the table User_Account...");
            //preparedStatement.setInt(1, 2);
            preparedStatement.setString(1, admin.getUsername());
            preparedStatement.setString(2, hashPass(admin.getPassword()));
            preparedStatement.setString(3, admin.getEmail());
            preparedStatement.setInt(4, admin.getType());

            preparedStatement.executeUpdate();
            admin.crate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createUser(User user){
        String sql = "INSERT INTO user_account(username,password,phone,type) VALUES (?,?,?,?)";

        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            // Execute a query
            System.out.println("Inserting records into the table user account...");
            //preparedStatement.setInt(1, 2);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, hashPass(user.getPassword()));
            preparedStatement.setString(3, user.getPhone());
            preparedStatement.setInt(4, user.getType());

            preparedStatement.executeUpdate();
            //admin.crate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public LoginResponse loginUser(String username, String password,int type){
        String query = "SELECT * FROM user_account WHERE username = ? and password = ? and type = ?";
        String updateQuery="UPDATE user_account set token = ? WHERE id = ?";
        LoginResponse response=new LoginResponse();
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setInt(3, type);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                response.setId(rs.getInt("id"));
                response.setType(rs.getInt("type"));
                response.setPassword(rs.getString("password"));
                response.setUsername(rs.getString("username"));
                response.setPhone(rs.getString("phone"));
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)){
            preparedStatement.setString( 1, "3ygTGF#iji9bhjg");
            preparedStatement.setLong( 2, response.getId());
            preparedStatement.executeUpdate();
            response.setToken("3ygTGF#iji9bhjg");

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return response;
    }

    public LoginResponse logoutUser(String username, String password){
        String query = "SELECT * FROM user_account WHERE username = ? and password = ?";
        String updateQuery="UPDATE user_account set token = ? WHERE id = ?";
        LoginResponse response=new LoginResponse();
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                response.setId(rs.getInt("id"));
                response.setType(rs.getInt("type"));
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(updateQuery)){
            preparedStatement.setString( 1, "");
            preparedStatement.setLong( 2, response.getId());
            preparedStatement.executeUpdate();
            response.setToken("");

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return response;
    }

    public void createArtist(Artist artist){
        String sql = "INSERT INTO artist(name,family,nickName,bornCity,bornDate) VALUES (?,?,?,?,?)";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            // Execute a query
            System.out.println("Inserting records into the table Artist...");
            //preparedStatement.setInt(1, 2);
            preparedStatement.setString(1, artist.getName());
            preparedStatement.setString(2, artist.getFamily());
            preparedStatement.setString(3, artist.getNickName());
            preparedStatement.setString(4, artist.getBornCity());
            preparedStatement.setString(5, artist.getBornDate());

            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArtistListResponse searchArtist(String name,String nickName){
        String query = "SELECT * FROM artist WHERE 1=1";
        StringBuilder conditions = new StringBuilder(query);
        ArtistListResponse listResponse=new ArtistListResponse();
        List<ArtistResponse> artistResponseList=new ArrayList<>();
        if(name!=null){
            conditions.append(" AND name = ?");
        }
        if(nickName!=null){
            conditions.append(" AND nickName = ?");
        }

        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(conditions.toString())){
            ResultSet rs = null;
            if(name!=null && nickName!=null){
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, nickName);
                rs = preparedStatement.executeQuery();
            }else if(name==null && nickName!=null){
                preparedStatement.setString(1, nickName);
                rs = preparedStatement.executeQuery();
            }
            else if(name!=null && nickName==null){
                preparedStatement.setString(1, name);
                rs = preparedStatement.executeQuery();
            }else {
                rs = preparedStatement.executeQuery();
            }

            while(true){
                assert rs != null;
                if (!rs.next()) break;
                ArtistResponse response=new ArtistResponse();
                response.setId(rs.getInt("id"));
                response.setName(rs.getString("name"));
                response.setFamily(rs.getString("family"));
                response.setNickName(rs.getString("nickName"));
                response.setBornCity(rs.getString("bornCity"));
                response.setBornDate(rs.getString("bornDate"));
                artistResponseList.add(response);

            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        listResponse.setArtistResponseList(artistResponseList);
        return listResponse;
    }

    public ArtistAlbumListResponse searchArtistAlbum(String artistName,String albumName){
        String query = "SELECT alb.id,ar.name,alb.album_name,alb.cover_img from artist_album as alb INNER JOIN artist as ar on alb.artist_id=ar.id WHERE 1=1";
        StringBuilder conditions = new StringBuilder(query);
        ArtistAlbumListResponse artistAlbumListResponse=new ArtistAlbumListResponse();
        List<ArtistAlbumResponse> artistAlbumResponses=new ArrayList<>();
        ResultSet rs = null;
        if(artistName!=null){
            conditions.append(" AND ar.name = ?");
        }
        if(albumName!=null){
            conditions.append(" AND alb.album_name = ?");
        }

        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(conditions.toString())){
            if(artistName!=null && albumName!=null){
                preparedStatement.setString(1, artistName);
                preparedStatement.setString(2, albumName);
                rs = preparedStatement.executeQuery();
            }else if(artistName==null && albumName!=null){
                preparedStatement.setString(1, albumName);
                rs = preparedStatement.executeQuery();
            }
            else if(artistName != null){
                preparedStatement.setString(1, artistName);
                rs = preparedStatement.executeQuery();
            }else {
                rs = preparedStatement.executeQuery();
            }
            while(true){
                assert rs != null;
                if (!rs.next()) break;
                ArtistAlbumResponse response=new ArtistAlbumResponse();
                response.setId(rs.getInt("id"));
                response.setArtistName(rs.getString("name"));
                response.setAlbumName(rs.getString("album_name"));
                response.setCoverImage(rs.getString("cover_img"));
                artistAlbumResponses.add(response);

            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        artistAlbumListResponse.setArtistAlbumResponses(artistAlbumResponses);
        return artistAlbumListResponse;
    }

    public ArtistSongListResponse searchArtistSongByAlbumId(int albumId){
        ArtistSongListResponse artistSongListResponse=new ArtistSongListResponse();
        List<ArtistSongResponse> artistSongResponseList=new ArrayList<>();

        String query = "SELECT ar.name,arl.album_name,arl.cover_img,ars.url ,ars.song_name from artist_song as ars INNER JOIN artist_album as arl on ars.album_id=arl.id " +
                "INNER JOIN artist as ar on arl.artist_id=ar.id WHERE arl.id = ?";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setInt(1, albumId);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                ArtistSongResponse response=new ArtistSongResponse();
                response.setId(rs.getInt("id"));
                response.setArtistName(rs.getString("name"));
                response.setAlbumName(rs.getString("album_name"));
                response.setCoverImg(rs.getString("cover_img"));
                response.setUrl(rs.getString("url"));
                response.setSongName(rs.getString("song_name"));
                artistSongResponseList.add(response);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        artistSongListResponse.setArtistSongResponseList(artistSongResponseList);
        return artistSongListResponse;
    }

    public ArtistSongListResponse searchArtistSongByAlbumName(String albumName){
        ArtistSongListResponse artistSongListResponse=new ArtistSongListResponse();
        List<ArtistSongResponse> artistSongResponseList=new ArrayList<>();

        String query = "SELECT ars.id,ar.name,arl.album_name,arl.cover_img,ars.url ,ars.song_name from artist_song as ars INNER JOIN artist_album as arl on ars.album_id=arl.id " +
                "INNER JOIN artist as ar on arl.artist_id=ar.id WHERE arl.album_name = ?";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setString(1, albumName);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                ArtistSongResponse response=new ArtistSongResponse();
                response.setId(rs.getInt("id"));
                response.setArtistName(rs.getString("name"));
                response.setAlbumName(rs.getString("album_name"));
                response.setCoverImg(rs.getString("cover_img"));
                response.setUrl(rs.getString("url"));
                response.setSongName(rs.getString("song_name"));
                artistSongResponseList.add(response);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        artistSongListResponse.setArtistSongResponseList(artistSongResponseList);
        return artistSongListResponse;
    }

    public SongInfoResponse getSongInfo(int songId){


        String query="SELECT ars.id,arl.album_name,ars.song_name,ars.url,art.name FROM artist_song as ars Inner join artist_album as arl on ars.album_id=arl.id inner join artist as art on arl.artist_id=art.id Where ars.id = ?";

        SongInfoResponse songInfoResponse=new SongInfoResponse();

        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setInt(1, songId);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){

                songInfoResponse.setId(rs.getInt("id"));
                songInfoResponse.setArtistName(rs.getString("name"));
                songInfoResponse.setAlbumName(rs.getString("album_name"));
                songInfoResponse.setSong_name(rs.getString("song_name"));
                songInfoResponse.setUrl(rs.getString("url"));

            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return songInfoResponse;

    }

    public ArtistSongListResponse searchArtistSongByName(String name){
        ArtistSongListResponse artistSongListResponse=new ArtistSongListResponse();
        List<ArtistSongResponse> artistSongResponseList=new ArrayList<>();

        String query = "SELECT ar.name,arl.album_name,arl.cover_img,ars.url ,ars.song_name from artist_song as ars INNER JOIN artist_album as arl on ars.album_id=arl.id " +
                "INNER JOIN artist as ar on arl.artist_id=ar.id WHERE ars.song_name LIKE ";

        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setString(1, "%"+name+"%");
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                ArtistSongResponse response=new ArtistSongResponse();
                response.setId(rs.getInt("id"));
                response.setArtistName(rs.getString("name"));
                response.setAlbumName(rs.getString("album_name"));
                response.setCoverImg(rs.getString("cover_img"));
                response.setUrl(rs.getString("url"));
                response.setSongName(rs.getString("song_name"));
                artistSongResponseList.add(response);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        artistSongListResponse.setArtistSongResponseList(artistSongResponseList);
        return artistSongListResponse;
    }

    public PlayListResponse createPlayList(PlayList playList){
        String sql = "INSERT INTO play_list(user_id,name) VALUES (?,?)";
        PlayListResponse response=new PlayListResponse();
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            // Execute a query
            System.out.println("Inserting records into the table PlayList...");
            //preparedStatement.setInt(1, 2);
            preparedStatement.setInt(1, playList.getUserId());
            preparedStatement.setString(2, playList.getName());

            preparedStatement.executeUpdate();
            response.setStatus("1");
            response.setMessage("Create Play List Success");


        }catch (SQLException e) {
            response.setStatus("0");
            response.setPlayListId(0);
            response.setMessage("Create Play List Fail");
            e.printStackTrace();
        }

        String sqlSelect = "SELECT * FROM play_list WHERE name = ? and user_id = ?";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(sqlSelect)){
            preparedStatement.setString(1, playList.getName());
            preparedStatement.setInt(2, playList.getUserId());
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                response.setPlayListId(rs.getInt("id"));

            }


        }catch (SQLException e) {
            e.printStackTrace();
        }

        return response;
    }

    public Response createPlayListItem(PlayListItem item){
        String sql = "INSERT INTO play_list_item(play_list_id,artist_song_id) VALUES (?,?)";
        Response response=new Response();
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){
            // Execute a query
            System.out.println("Inserting records into the table PlayListItem...");
            preparedStatement.setInt(1, item.getPlayListId());
            preparedStatement.setInt(2, item.getSongId());

            preparedStatement.executeUpdate();
            response.setStatus("1");
            response.setMessage("Create Play List Success");

        }catch (SQLException e) {
            response.setStatus("0");
            response.setMessage("Create Play List Fail");
            e.printStackTrace();
        }

        return response;
    }

    public Response createArtistWithDetail(String artistName, String artistFamily, String nickName,
                                           String bornCity, String bornDate, String albumName, String coverImg, HashMap<String,String> songList){
        String sql = "INSERT INTO artist(name,family,nickName,bornCity,bornDate) VALUES (?,?,?,?,?)";
        Response response=new Response();
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(sql)){

            // Execute a query
            System.out.println("Inserting records into the table Artist...");
            preparedStatement.setString(1, artistName);
            preparedStatement.setString(2, artistFamily);
            preparedStatement.setString(3, nickName);
            preparedStatement.setString(4, bornCity);
            preparedStatement.setString(5, bornDate);
            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            response.setStatus("0");
            response.setMessage("Create artist fail");
            e.printStackTrace();
        }
        int artistId=0;
        String artistSql="SELECT * FROM artist WHERE name = ? AND family = ?";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(artistSql)){

            // Execute a query
            System.out.println("Fetch data from Artist Table with name = "+artistName+ ", family = "+artistFamily);

            preparedStatement.setString(1, artistName);
            preparedStatement.setString(2, artistFamily);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                artistId=rs.getInt("id");
            }

        }catch (SQLException e) {
            response.setStatus("0");
            response.setMessage("Create artist fail");
            e.printStackTrace();
        }

        String insertAlbum = "INSERT INTO artist_album(artistId,album_name,cover_img) VALUES (?,?,?)";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(insertAlbum)){

            // Execute a query
            System.out.println("Inserting records into the table Artist_Album...");
            preparedStatement.setInt(1, artistId);
            preparedStatement.setString(2, albumName);
            preparedStatement.setString(3, coverImg);
            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            response.setStatus("0");
            response.setMessage("Create artist_album fail");
            e.printStackTrace();
        }

        int albumId=0;
        String albumSql="SELECT * FROM artist_album WHERE artistId = ? AND album_name = ?";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(albumSql)){

            // Execute a query
            System.out.println("Fetch data from Artist_Album table with artistId = "+artistId+ ", album_name = "+albumName);

            preparedStatement.setInt(1, artistId);
            preparedStatement.setString(2, albumName);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                albumId=rs.getInt("id");
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        String insertSong="INSET INTO artist_song(album_id,url,song_name) VALUES (?,?,?)";

        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(insertSong)){

            for(String key: songList.keySet()){
                // Execute a query
                System.out.println("Inserting records into the table Artist_Song...");
                preparedStatement.setInt(1, albumId);
                preparedStatement.setString(2, songList.get(key));
                preparedStatement.setString(3, key);
                preparedStatement.executeUpdate();
            }

            response.setStatus("1");
            response.setMessage("Create artist_song success");


        }catch (SQLException e) {
            response.setStatus("0");
            response.setMessage("Create artist_song fail");
            e.printStackTrace();
        }
        return response;

    }

    public static int countTableArtist(){
        int rowCount=0;
        String query = "SELECT count(*) FROM artist ";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        ) {
            rs.next();
            rowCount= rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }

    public static int countTableUser(){
        int rowCount=0;
        String query = "SELECT count(*) FROM user_account ";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        ) {
            rs.next();
            rowCount= rs.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }

    public static void listFilesForFolder(final File folder)throws IOException{
        int count=0;
        for (final File fileEntry : folder.listFiles()){
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            }else {
                //if(Files.getFileE)
                String extension = "";
                int i = fileEntry.toString().lastIndexOf('.');
                if (i > 0) {
                    extension = fileEntry.toString().substring(i+1);
                }
                if(extension.contains("txt")){
                    Path path = Paths.get(fileEntry.toURI());
                    System.out.println(fileEntry.getAbsolutePath());
                    byte[] bytes = Files.readAllBytes(path);
                    List<String> allLines = Files.readAllLines(path, StandardCharsets.UTF_8);
                    int index=0;
                    for(String str:allLines){
                        if(str.equals("Album Names")){
                            break;
                        }else {
                            index++;
                        }
                    }
                    List<String>albumNames=new ArrayList<>();
                    for(int k=index+1;k<allLines.size();k++){
                        albumNames.add(allLines.get(k));
                    }
                    int lastIndexOf=path.toString().lastIndexOf('\\');
                    String strPath=path.toString().substring(0,lastIndexOf);
                    int indexDot=strPath.indexOf('.');
                    String str1=strPath.substring(0,indexDot);
                    String str2=strPath.substring(indexDot+2);
                    String finalStr=str1+str2;
                    saveArtist(allLines.get(1),allLines.get(2),allLines.get(3),
                            allLines.get(4),allLines.get(5),count,albumNames,finalStr);
                    count++;

                }

            }
        }
    }

    public static void saveArtist(String name,String family,String nickname,String bornCity
    ,String bornDate,int count,List<String>albumNames,String path){
        String sqlInsertArtist = "INSERT INTO artist(id, name, family, \"nickName\", \"bornCity\", \"bornDate\") VALUES (?,?,?,?,?,?)";

        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(sqlInsertArtist)){

            preparedStatement.setInt(1,count==0? 1:count+1);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, family);
            preparedStatement.setString(4, nickname);
            preparedStatement.setString(5, bornCity);
            preparedStatement.setString(6, bornDate);
            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }
        int idArtist=0;
        String sqlSelectArtist="SELECT * FROM artist ORDER BY id DESC";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlSelectArtist);){

            rs.next();
            idArtist=rs.getInt("id");
        }catch (SQLException e) {
            e.printStackTrace();
        }

        String insertAlbum="INSERT INTO artist_album(artist_id,album_name,cover_img) VALUES(?,?,?) ";

        for(String str:albumNames){
            try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement preparedStatement = conn.prepareStatement(insertAlbum)){
                preparedStatement.setInt(1,idArtist);
                preparedStatement.setString(2, str);
                preparedStatement.setString(3, path+"\\"+str+".jpg");
                preparedStatement.executeUpdate();

            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        String sqlArtistAlbum="SELECT * FROM artist_album WHERE artist_id = ?";
        List<Integer> integerList=new ArrayList<>();
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(sqlArtistAlbum)){
            preparedStatement.setInt( 1, idArtist);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                integerList.add(rs.getInt("id"));
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }

        String insertSongs="INSERT INTO artist_song(album_id,url,song_name) VALUES (?,?,?)";
        for(int i:integerList){
            for(int j=1;j<=9;j++){
                try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                    PreparedStatement preparedStatement = conn.prepareStatement(insertSongs)){

                    preparedStatement.setInt(1,i);
                    preparedStatement.setString(2, path+"\\0"+j+".mp3");
                    preparedStatement.setString(3, "0"+j+".mp3");
                    preparedStatement.executeUpdate();

                }catch (SQLException e) {
                    e.printStackTrace();
                }
            }


        }

    }

    public static void saveUser(String username,String password,String phone,int type){
        String sql = "INSERT INTO user_account(username,password,email,type) VALUES (?,?,?,?)";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            // Execute a query
            System.out.println("Inserting records into the table user account...");
            //preparedStatement.setInt(1, 2);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashPass(password));
            preparedStatement.setString(3, phone);
            preparedStatement.setInt(4, type);

            preparedStatement.executeUpdate();
            //admin.crate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createFavoriteSong(int userId,int songId){
        String sql = "INSERT INTO favorite_song(user_id,song_id) VALUES (?,?)";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            // Execute a query
            System.out.println("Inserting records into the table favorite song...");
            //preparedStatement.setInt(1, 2);
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, songId);

            preparedStatement.executeUpdate();
            //admin.crate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public  Integer favoriteIsExist(int userId,int songId){
        String query = "SELECT * FROM favorite_song WHERE user_id = ? and song_id = ?";
        Integer result=0;
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, songId);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                result=rs.getInt("id");
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public FavoriteSongListResponse searchFavoriteSongByUserId(int userId){
        FavoriteSongListResponse favoriteSongListResponse=new FavoriteSongListResponse();
        List<FavoriteSongResponse> favoriteSongResponseList=new ArrayList<>();

        String query = "SELECT fas.id ,art.name,artl.album_name,arts.song_name ,arts.id as songId\n" +
                "FROM favorite_song as fas \n" +
                "inner join artist_song as arts on fas.song_id=arts.id\n" +
                "inner join artist_album as artl on arts.album_id=artl.id\n" +
                "inner join artist as art on artl.artist_id=art.id\n" +
                "inner join user_account as ur on fas.user_id=ur.id\n" +
                "where ur.id = ?";
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            PreparedStatement preparedStatement = conn.prepareStatement(query)){
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()){
                FavoriteSongResponse response=new FavoriteSongResponse();
                response.setId(rs.getInt("id"));
                response.setName(rs.getString("name"));
                response.setAlbumName(rs.getString("album_name"));
                response.setSongName(rs.getString("song_name"));
                response.setSongId(rs.getInt("songId"));
                favoriteSongResponseList.add(response);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        favoriteSongListResponse.setFavoriteSongResponseList(favoriteSongResponseList);
        return favoriteSongListResponse;
    }


}
