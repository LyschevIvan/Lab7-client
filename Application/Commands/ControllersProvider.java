package com.company.Application.Commands;


import com.company.Application.Controllers.ClientController;
import com.company.Application.Controllers.InputReader;
import com.company.Application.ProductClasses.Product;


/**
 *  produce access to controllers
 */
public class ControllersProvider {
    private final InputReader inputReader;
    private final ClientController clientController;



    public ControllersProvider(InputReader inputReader, ClientController clientController){
        this.inputReader = inputReader;
        this.clientController = clientController;
    }

    /**
     * produce access to InputReader class
     * @return InputReader
     */
    public InputReader getInputReader() {
        return inputReader;
    }

    public ClientController getClientController(){
        return clientController;
    }

    /**
     * provides easier way to use readProduct in InputReader
     * @return Product
     */
    Product readProduct(){
        return inputReader.readProduct();
    }
}
