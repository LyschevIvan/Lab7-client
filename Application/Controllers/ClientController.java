package com.company.Application.Controllers;

import com.company.Application.Data;
import com.company.Application.Exceptions.NoConnectionException;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class ClientController {
    private final static int BUFFER_SIZE = 2048;
    SocketChannel socketChannel;
    SocketAddress socketAddress;
    ByteBuffer byteBuffer;
    public ClientController(InetAddress address, int port) throws IOException, NoConnectionException {
        socketAddress = new InetSocketAddress(address, port);
        connect();
        byteBuffer = ByteBuffer.allocate(BUFFER_SIZE);

    }

    void connect() throws IOException, NoConnectionException {
        boolean notConnected = true;
        int count =0;
        while(notConnected && count < 5){
            try{
                socketChannel = SocketChannel.open(socketAddress);
                System.out.println("Успешное подключение!");
                notConnected = false;
            }
            catch (ConnectException e){
                System.out.println("Не удается подключиться, пробую еще раз");
                count++;
            }

        }
        if (count == 5){
            throw new NoConnectionException();
        }
        socketChannel.configureBlocking(false);
    }

    public void sendData(Data data) throws IOException, NoConnectionException {
        byteBuffer.clear();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(data);
        objectOutputStream.flush();
        byte[] buf = byteArrayOutputStream.toByteArray();
        byteBuffer.put(buf).flip();
        boolean sent = false;
        while (!sent){
            try{
                socketChannel.write(byteBuffer);
                System.out.println("sent");
                sent = true;
            }
            catch (IOException e){
                connect();

            }
        }


    }
    public Data receiveAndGetData() throws IOException, ClassNotFoundException, NoConnectionException {
        byteBuffer.clear();
        boolean hadRead = false;
        Data response = null;
        while (!hadRead){
            boolean ready = false;
            try{
                ready = socketChannel.read(byteBuffer) > 0;
            }
            catch(IOException e){
                connect();
            }
            while (ready) {

                byteBuffer.flip();
                InputStream byteArrayInputStream= new ByteArrayInputStream(byteBuffer.array());
                ObjectInputStream objectInputStream= new ObjectInputStream(byteArrayInputStream);
                response = (Data) objectInputStream.readObject();
                System.out.println("received");
                byteBuffer.clear();

                hadRead = true;
                try{
                    ready = socketChannel.read(byteBuffer) > 0;
                }
                catch(IOException e){
                    connect();
                }
            }

        }
        return response;
    }

    public void receiveData() throws ClassNotFoundException, IOException, NoConnectionException {


        byteBuffer.clear();
        boolean hadRead = false;


        while (!hadRead){
            boolean ready = false;
            try{
                ready = socketChannel.read(byteBuffer) > 0;
            }
            catch(IOException e){
                connect();
            }
            while (ready) {
                byteBuffer.flip();
                InputStream byteArrayInputStream= new ByteArrayInputStream(byteBuffer.array());

                ObjectInputStream objectInputStream= new ObjectInputStream(byteArrayInputStream);
                Data response = (Data) objectInputStream.readObject();
                System.out.println("received");
                System.out.println(response.toString());
                byteBuffer.clear();

                hadRead = true;
                try{
                    ready = socketChannel.read(byteBuffer) > 0;
                }
                catch(IOException e){
                    connect();
                }
            }
        }
    }
}
