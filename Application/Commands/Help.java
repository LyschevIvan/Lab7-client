
package com.company.Application.Commands;

import com.company.Application.Data;
import com.company.Application.Exceptions.NoConnectionException;

import java.io.IOException;

/**
 * shows information about commands
 */
class Help extends AbstractCommand {
    CommandInvoker commandInvoker;
    public Help(ControllersProvider controllersProvider, CommandInvoker commandInvoker) {
        super(controllersProvider);
        this.commandInvoker = commandInvoker;
    }

    @Override
    public void execute(String[] args) throws IOException, ClassNotFoundException, NoConnectionException {

        Data data = new Data(args, 1);
        controllersProvider.getClientController().sendData(data);
        controllersProvider.getClientController().receiveData();
    }

    @Override
    public boolean argsIsCorrect(String[] args) {
        return true;
    }


}
