
package com.company.Application.Commands;

import com.company.Application.Data;
import com.company.Application.Exceptions.NoConnectionException;

import java.io.IOException;

/**
 * remove by key
 */
class Remove extends AbstractCommand {
    public Remove(ControllersProvider controllersProvider) {
        super(controllersProvider);
    }

    @Override
    public void execute(String[] args) throws IOException, ClassNotFoundException, NoConnectionException {
        Data data = new Data(args,1);
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
