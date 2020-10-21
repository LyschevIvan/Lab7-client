package com.company;

import com.company.Application.ApplicationFacade;

public class Main {

    public static void main(String[] args) {
        if (args.length == 1)
        {
            try{
                int port = Integer.parseInt(args[0]);
                ApplicationFacade applicationFacade = new ApplicationFacade(port);
                applicationFacade.start();
            }
            catch (NumberFormatException e){
                System.out.println("Неправильно введён порт!");
            }
        }
        else {
            System.out.println("Lab6-client.jar port");
        }


    }
}
