
package com.company.Application.Commands;


import com.company.Application.Controllers.InputReader;
import com.company.Application.Exceptions.NoConnectionException;
import com.company.Application.Exceptions.WrongArgumentException;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * manages commands and produce access to some inputReader functions
 */
public class CommandInvoker {

    private final Queue<String> enteredCommands = new LinkedList<>();
    private int enteredCommandsCounter =0;
    private final LinkedHashMap<String, AbstractCommand> commands = new LinkedHashMap<>();


    public CommandInvoker(ControllersProvider controllersProvider) {
        initCommands(controllersProvider);
    }

    private void initCommands(ControllersProvider controllersProvider){
        commands.put("help", new Help(controllersProvider, this));
        commands.put("info", new Info(controllersProvider));
        commands.put("insert", new Insert(controllersProvider));
        commands.put("execute_script", new ExecuteScript(controllersProvider));
        commands.put("show", new Show(controllersProvider));
        commands.put("remove_key", new Remove(controllersProvider));
        commands.put("clear", new Clear(controllersProvider));
        commands.put("update", new Update(controllersProvider));
        commands.put("history", new History(controllersProvider, this));
        commands.put("remove_greater_key", new RemoveGrKey(controllersProvider));
        commands.put("remove_lower_key", new RemoveLwrKey(controllersProvider));
        commands.put("remove_all_by_unit_of_measure", new RemoveByUOM(controllersProvider));
        commands.put("count_less_than_manufacture_cost", new CountLessManCost(controllersProvider));
        commands.put("filter_contains_part_number", new FilterPartNumber(controllersProvider));
        commands.put("reg", new Reg(controllersProvider));
    }


    /**
     * uses to read next Command
     * @param inputReader InputReader
     */
    public void nextCommand(InputReader inputReader){
        inputReader.readCommand(this);
    }





    /**
     * execute command by it's name
     * @param commandLine String[]
     */
    public void executeCommand(String[] commandLine) throws IOException, ClassNotFoundException, NoConnectionException {
        String commandName = commandLine[0].toLowerCase();
        commands.get(commandName).execute(commandLine);

        if(enteredCommandsCounter == 13)
            enteredCommands.remove();
        else
            enteredCommandsCounter++;
        enteredCommands.add(commandName);

    }

    /**
     * check if arguments are correct
     * @param commandLine String[]
     * @return boolean
     */
    public boolean isArgsCorrect(String[] commandLine ){
        String commandName = commandLine[0].toLowerCase();
        return commands.get(commandName).argsIsCorrect(commandLine);
    }

    /**
     *
     * @return Queue entered commands
     */
    Queue<String> getEnteredCommands() {
        return enteredCommands;
    }

    /**
     * check if command is in commands Map
     * @param commandName String
     * @return boolean
     */
    public boolean commandExists(String commandName){
        return commands.containsKey(commandName.toLowerCase());
    }

    /**
     * uses to call function getInfo in each command
     */




}
