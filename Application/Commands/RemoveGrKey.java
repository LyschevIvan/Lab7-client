
package com.company.Application.Commands;

import com.company.Application.Data;
import com.company.Application.Exceptions.NoConnectionException;

import java.io.IOException;

/**
 * remove if key is greater
 */
class RemoveGrKey extends AbstractCommand {
    public RemoveGrKey(ControllersProvider controllersProvider) {
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
        if(args.length >= 2)
            return args[1].matches("\\d+");

        else return false;
    }


}
