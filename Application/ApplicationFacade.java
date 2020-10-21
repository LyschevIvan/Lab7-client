

package com.company.Application;

import com.company.Application.Commands.CommandInvoker;
import com.company.Application.Commands.ControllersProvider;
import com.company.Application.Controllers.ClientController;
import com.company.Application.Controllers.InputReader;
import com.company.Application.Exceptions.NoConnectionException;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Class uses to start the application
 */
public class ApplicationFacade {
    int port;
    public ApplicationFacade(int port) {
        this.port = port;
    }


    /**
     * starts the application
     * exits if input stream is closed
     */
    public void start() {
        InputReader inputReader = new InputReader();
        try{
            InetSocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(), port);
            ClientController clientController = new ClientController(address.getAddress(), port);

            ControllersProvider controllersProvider = new ControllersProvider(inputReader, clientController);
            CommandInvoker commandInvoker = new CommandInvoker(controllersProvider);
            while (inputReader.isOpened()){
                inputReader.readCommand(commandInvoker);

            }
        } catch (IOException e) {
        System.out.println(e.getMessage());
    } catch (NoConnectionException e) {
        System.out.println(e.getMessage());
    }

    }
    
}
