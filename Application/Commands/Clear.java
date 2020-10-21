
package com.company.Application.Commands;

import com.company.Application.Data;
import com.company.Application.Exceptions.NoConnectionException;

import java.io.IOException;

/**
 * clears collection
 */
class Clear extends AbstractCommand {
    public Clear(ControllersProvider controllersProvider) {
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
        return true;
    }


}
