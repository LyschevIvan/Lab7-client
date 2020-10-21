
package com.company.Application.Commands;


import com.company.Application.Data;
import com.company.Application.Exceptions.NoConnectionException;
import com.company.Application.ProductClasses.Product;

import java.io.IOException;


/**
 * insert with key
 */
class Insert extends AbstractCommand {
    public Insert(ControllersProvider controllersProvider) {
        super(controllersProvider);
    }

    @Override
    public void execute(String[] args) throws IOException, ClassNotFoundException, NoConnectionException {
        Integer k = Integer.parseInt(args[1]);
        Product product = controllersProvider.readProduct();

        Data data = new Data(args,product,2);
        controllersProvider.getClientController().sendData(data);
        controllersProvider.getClientController().receiveData();
    }

    @Override
    public boolean argsIsCorrect(String[] args) {
        if (args.length >= 2){
            try{
                Integer.parseInt(args[1]);
                return true;
            }
            catch (NumberFormatException e){
                return false;
            }
        }
        else return false;
    }


}
