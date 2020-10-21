package com.company.Application.Commands;

import com.company.Application.Data;
import com.company.Application.Exceptions.NoConnectionException;

import java.io.IOException;

public class Reg extends AbstractCommand{
    public Reg(ControllersProvider controllersProvider) {
        super(controllersProvider);
    }

    @Override
    void execute(String[] args) throws IOException, NoConnectionException, ClassNotFoundException {
        controllersProvider.getClientController().sendData(new Data(args,1));
        controllersProvider.getClientController().receiveData();
    }

    @Override
    boolean argsIsCorrect(String[] args) {
        return args.length >= 2;
    }
}
