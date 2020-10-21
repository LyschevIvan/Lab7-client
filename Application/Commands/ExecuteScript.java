
package com.company.Application.Commands;


import java.io.File;

/**
 * reads commands from file
 */
class ExecuteScript extends AbstractCommand {
    public ExecuteScript(ControllersProvider controllersProvider) {
        super(controllersProvider);
    }

    @Override
    public void execute(String[] args) {
        String fileName = args[1];

        File file = new File(fileName);
        controllersProvider.getInputReader().changeInputStream(file);


    }


    @Override
    public boolean argsIsCorrect(String[] args) {
        if(args.length >= 2){
            File file = new File(args[1]);
            return file.exists();
        }

        else return false;
    }



}
