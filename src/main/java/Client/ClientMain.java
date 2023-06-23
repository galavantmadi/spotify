package Client;

import Shared.*;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClientMain  {
    private Socket socket            = null;
    private DataInputStream inputClient   = null;
    private ObjectOutputStream outClient     = null;
    private ObjectInputStream inServer  =  null;
    public static Response response;
    public static LoginResponse loginResponse;

    public static ArtistListResponse artistListResponse;
    public static ArtistAlbumListResponse artistAlbumListResponse;
    public static ArtistSongListResponse artistSongListResponse;
    public static SongInfoResponse songInfoResponse;
    public static FavoriteSongListResponse favoriteSongListResponse;

    public ClientMain(String address, int port){
        // establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
            inputClient  = new DataInputStream(System.in);

            // sends output to the socket
            outClient  = new ObjectOutputStream(socket.getOutputStream());

            inServer = new ObjectInputStream(socket.getInputStream());
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }

        // string to read message from input
        String line = "";





        /*while (!line.equals("0")){
            runMenu();
            try {
                line = inputClient.readLine();
                switch (line){
                    case "1"://Create Account User:
                        Scanner myObj = new Scanner(System.in);
                        myObj = new Scanner(System.in);
                        System.out.println("Welcome to create User");
                        System.out.println("Enter Username :");
                        String username = myObj.nextLine();
                        System.out.println("Enter Password :");
                        String passwordUser = myObj.nextLine();
                        System.out.println("Enter Phone Number :");
                        String phone = myObj.nextLine();
                        Request request=new Request();
                        request.setId(1);
                        request.setUsername(username);
                        request.setPassword(passwordUser);
                        request.setPhone(phone);
                        outClient.writeObject(request);

                        Response response =(Response) inServer.readObject();
                        System.out.println(response.getMessage());
                        break;

                    case "2"://Create Account User_Admin:
                        Scanner myObj1 = new Scanner(System.in);
                        myObj1 = new Scanner(System.in);
                        System.out.println("Welcome to create Admin");
                        System.out.println("Enter Username :");
                        username = myObj1.nextLine();
                        System.out.println("Enter Password :");
                        passwordUser = myObj1.nextLine();
                        System.out.println("Enter Phone Email :");
                        String email = myObj1.nextLine();
                        request=new Request();
                        request.setId(2);
                        request.setUsername(username);
                        request.setPassword(passwordUser);
                        request.setEmail(email);
                        outClient.writeObject(request);

                        response =(Response) inServer.readObject();
                        System.out.println(response.getMessage());
                        break;

                    case "3"://Login
                        Scanner myObj2 = new Scanner(System.in);
                        System.out.println("Welcome to Login User");
                        System.out.println("Enter Username :");
                        username = myObj2.nextLine();
                        System.out.println("Enter Password :");
                        passwordUser = myObj2.nextLine();
                        request=new Request();
                        request.setId(3);
                        request.setUsername(username);
                        request.setPassword(passwordUser);
                        request.setType(0);
                        outClient.writeObject(request);

                        response =(LoginResponse) inServer.readObject();
                        System.out.println(response.getMessage());
                        break;

                    case "4"://Logout
                        Scanner myObj3 = new Scanner(System.in);
                        System.out.println("Welcome to LogOut User");
                        System.out.println("Enter Username :");
                        username = myObj3.nextLine();
                        System.out.println("Enter Password :");
                        passwordUser = myObj3.nextLine();
                        request=new Request();
                        request.setId(4);
                        request.setUsername(username);
                        request.setPassword(passwordUser);
                        outClient.writeObject(request);

                        response =(Response) inServer.readObject();
                        System.out.println(response.getMessage());
                        break;


                }

            }catch(IOException i)
            {
                System.out.println(i);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }*/

        // close the connection

    }

    public static void main(String[] args) {

        //ClientMain client = new ClientMain("127.0.0.1", 5000);
        Com.Main.main(args);

        //runMenu();
    }

    public void sendRequest(RequestClient requestClient){
        try {
            outClient.writeObject(requestClient);

            response =(Response) inServer.readObject();
        }catch(IOException i)
        {
            System.out.println(i);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendRequestLogin(RequestClient requestClient){
        try {
            outClient.writeObject(requestClient);

            loginResponse =(LoginResponse) inServer.readObject();
        }catch(IOException i)
        {
            System.out.println(i);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendRequestArtist(RequestClient requestClient){
        try {
            outClient.writeObject(requestClient);

            artistListResponse =(ArtistListResponse) inServer.readObject();
        }catch(IOException i)
        {
            System.out.println(i);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendRequestAlbum(RequestClient requestClient){
        try {
            outClient.writeObject(requestClient);

            artistAlbumListResponse =(ArtistAlbumListResponse) inServer.readObject();
        }catch(IOException i)
        {
            System.out.println(i);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendRequestSong(RequestClient requestClient){
        try {
            outClient.writeObject(requestClient);

            artistSongListResponse =(ArtistSongListResponse) inServer.readObject();
        }catch(IOException i)
        {
            System.out.println(i);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendRequestSongInfo(RequestClient requestClient){
        try {
            outClient.writeObject(requestClient);

            songInfoResponse =(SongInfoResponse) inServer.readObject();
        }catch(IOException i)
        {
            System.out.println(i);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void  sendCreateUser(RequestClient requestClient){
        try {
            outClient.writeObject(requestClient);
            response =(Response) inServer.readObject();
        }catch(IOException i)
        {
            System.out.println(i);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void  sendRequestFavorite(RequestClient requestClient){
        try {
            outClient.writeObject(requestClient);
            response =(Response) inServer.readObject();
        }catch(IOException i)
        {
            System.out.println(i);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public void  sendRequestFavoriteList(RequestClient requestClient){
        try {
            outClient.writeObject(requestClient);
            favoriteSongListResponse =(FavoriteSongListResponse) inServer.readObject();
        }catch(IOException i)
        {
            System.out.println(i);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeConnection(){
        try
        {

               RequestClient requestClient =new RequestClient();
                requestClient.setId(0);
                outClient.writeObject(requestClient);
                inputClient.close();
                outClient.close();
                socket.close();


        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

   /* public void init(Request request){
        //ClientMain client = new ClientMain("127.0.0.1", 5000);
        if(request!=null){
            System.out.println("AAAAA");
        }
    }*/



   /* public static void runMenu(){
        String[] options = {
                "0- Exist Menu",
                "1- Create Account",
                "2- Create Admin",
                "3- Login",
                "4- LogOut",


        };
        System.out.println("------------------MAIN MENU-----------------");

        for (String option : options){
            System.out.println(option);
        }
        System.out.print("Choose your option : ");
    }*/
}
