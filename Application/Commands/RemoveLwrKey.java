
package com.company.Application.Commands;

import com.company.Application.Data;
import com.company.Application.Exceptions.NoConnectionException;

import java.io.IOException;

/**
 * removes if key is lower
 */
class RemoveLwrKey extends AbstractCommand {
    public RemoveLwrKey(ControllersProvider controllersProvider) {
        super(controllersProvider);
    }

    @Override
    public void execute(String[] args) throws IOException, ClassNotFoundException, NoConnectionException {
        Data data = new Data(args,2);
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
