
package com.company.Application.Commands;

import com.company.Application.Data;
import com.company.Application.Exceptions.NoConnectionException;

import java.io.IOException;

/**
 * shows last 13 entered commands
 */
class History extends AbstractCommand {
    CommandInvoker commandInvoker;
    public History(ControllersProvider controllersProvider, CommandInvoker commandInvoker) {
        super(controllersProvider);
        this.commandInvoker = commandInvoker;
    }

    @Override
    public void execute(String[] args) throws IOException, NoConnectionException {
        Data data = new Data(args, 1);
        controllersProvider.getClientController().sendData(data);
    }

    @Override
    public boolean argsIsCorrect(String[] args) {
        return true;
    }


}
