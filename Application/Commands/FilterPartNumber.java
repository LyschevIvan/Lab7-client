
package com.company.Application.Commands;

import com.company.Application.Data;
import com.company.Application.Exceptions.NoConnectionException;


import java.io.IOException;


/**
 * shows if PartNumber contains substring
 */
class FilterPartNumber extends AbstractCommand {
    public FilterPartNumber(ControllersProvider controllersProvider) {
        super(controllersProvider);
    }

    @Override
    public void execute(String[] args) throws IOException, ClassNotFoundException, NoConnectionException {
        Data data = new Data(args, 2);
        controllersProvider.getClientController().sendData(data);
        controllersProvider.getClientController().receiveData();
    }

    @Override
    public boolean argsIsCorrect(String[] args) {
        return  args.length >= 2;

    }


}
