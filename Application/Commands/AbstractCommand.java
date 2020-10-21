
package com.company.Application.Commands;

import com.company.Application.Exceptions.NoConnectionException;

import java.io.IOException;


/**
 * designate all Commands interface
 */
abstract public class AbstractCommand {

    protected ControllersProvider controllersProvider;

    public AbstractCommand(ControllersProvider controllersProvider) {
        this.controllersProvider = controllersProvider;
    }

    /**
     * executes command
     * @param args String[]
     */
    abstract void execute(String[] args) throws IOException, ClassNotFoundException, NoConnectionException;

    /**
     * checks if arguments are correct for
     * @param args String[]
     * @return boolean
     */
    abstract boolean argsIsCorrect(String[] args);


}
