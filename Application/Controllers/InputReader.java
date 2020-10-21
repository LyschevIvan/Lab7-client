
package com.company.Application.Controllers;


import com.company.Application.Commands.CommandInvoker;
import com.company.Application.Exceptions.InfiniteCoordinateException;
import com.company.Application.Exceptions.NoConnectionException;
import com.company.Application.Exceptions.WrongArgumentException;
import com.company.Application.ProductClasses.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.PortUnreachableException;
import java.util.Scanner;


/**
 * uses to manage input stream and handle it
 */
public class InputReader {



    private boolean isScript;
    private boolean isOpened;
    private Scanner reader;


    public InputReader(){
        reader = new Scanner(System.in);
        isScript = false;
        isOpened = true;
    }


    /**
     * changes input stream to file
     * @param file File
     */
    public void changeInputStream(File file){
        try {
            reader = new Scanner(file);
            isScript = true;
        } catch (FileNotFoundException e) {
            System.out.println("Не могу открыть файл для чтения, введите команду еще раз");
        }
    }

    /**
     * reads command line
     * @param commandInvoker commandInvoker
     */
    public void readCommand(CommandInvoker commandInvoker){

        if (reader.hasNextLine()) {

            String line = reader.nextLine();
            if (isScript) {
                System.out.println(line);
            }
            String[] args = line.split(" ");
            String command = args[0].toLowerCase();
            if (command.equals("exit")){
                exit();
            }
            else if(commandInvoker.commandExists(command)){
                if(commandInvoker.isArgsCorrect(args)){
                    try {
                        commandInvoker.executeCommand(args);
                    } catch (PortUnreachableException e){
                        System.out.println("Port unreachable!");
                    } catch (IOException e) {
                        System.out.println("Troubles with connection");
                    } catch (ClassNotFoundException e) {
                        System.out.println("Corrupted Data!");
                    } catch (NoConnectionException e) {
                        System.out.println("Connection Lost!");
                    }

                }
                else
                    System.out.println("аргументы команды указаны неверно (введите help для списка команд)");
            }
            else
                System.out.println("команда введена неверно (введите help для списка команд)");
        }
        else{
            this.reader = new Scanner(System.in);
            isScript = false;
            isOpened = true;
        }

    }

    /**
     * false if all streams closed
     * @return boolean
     */
    public boolean isOpened(){
        return isOpened;


    }

    /**
     * closes streams
     */
    public void exit(){
        isOpened = false;
        reader.close();
    }


    /**
     * uses to enter information about product

     * @return Product
     */
    public Product readProduct(){ // обработка ввода данных о продукте
        Product product = new Product();

        System.out.print("Введите название продукта: ");
        boolean is_correct = false;
        while(!is_correct){
            try {
                product.setName(reader.nextLine());
                is_correct = true;
            } catch (WrongArgumentException e) {
                System.out.print(e.getMessage());
            }

        }
        if(isScript) {
            System.out.println(product.getName());
        }

        Coordinates coordinates = new Coordinates();


        System.out.print("Введите координату x: ");
        is_correct = false;
        while(!is_correct) {
            try {
                coordinates.setX(Integer.valueOf(reader.nextLine()));
                is_correct = true;

            } catch (NumberFormatException e ) {
                System.out.print("Введите правильное значение x (целое число в пределах значений int): ");
            }
        }
        if(isScript) {
            System.out.println(coordinates.getX());
        }


        System.out.print("Введите координату y: ");
        is_correct = false;
        while(!is_correct) {
            try {
                coordinates.setY(Float.valueOf(reader.nextLine()));
                is_correct = true;

            } catch (NumberFormatException e ) {
                System.out.print("Введите правильное значение y (число с палвующей запятой в пределах значения float): ");
            }
            catch (WrongArgumentException e){
                System.out.print(e.getMessage());
            } catch (InfiniteCoordinateException e) {
                System.out.print(e.getMessage());
            }
        }
        if(isScript) {
            System.out.println(coordinates.getY());
        }

        product.setCoordinates(coordinates);



        System.out.print("Введите цену(значение больше нуля): ");
        is_correct = false;
        while(!is_correct) {
            String inp = reader.nextLine();
            try {
                product.setPrice(Float.parseFloat(inp));
                is_correct = true;

            } catch (NumberFormatException e ) {

                System.out.print("Введите правильное значение цены (число с плавающей запитой в пределах значения float): ");
            } catch (WrongArgumentException e) {
                System.out.print(e.getMessage());
            }
        }
        if(isScript) {
            System.out.println(product.getPrice());
        }



        System.out.print("Введите номер детали(минимум 23 символа): ");
        is_correct = false;
        while(!is_correct){
            try {
                product.setPartNumber(reader.nextLine());
                is_correct = true;
            } catch (WrongArgumentException e) {
                System.out.print(e.getMessage());
            }


        }
        if(isScript) {
            System.out.println(product.getPartNumber());
        }


        System.out.print("Введите стоимость изготовления: ");
        is_correct = false;
        while(!is_correct) {
            try {
                product.setManufactureCost(Long.parseLong(reader.nextLine()));
                is_correct = true;
            } catch (NumberFormatException e ) {
                System.out.print("Введите правильное значение(целое число в пределах значения int): ");
            } catch (WrongArgumentException e) {
                System.out.print(e.getMessage());
            }
        }
        if(isScript) {
            System.out.println(product.getManufactureCost());
        }


        System.out.print("Введите единицу измерения(kg,pcs,gr,mg): ");
        is_correct = false;
        while (!is_correct)
        {
            String inp = reader.nextLine().toLowerCase();
            if (inp.matches("(kg)|(pcs)|(gr)|(mg)|( )*")) {
                switch (inp) {
                    case "kg":
                        product.setUnitOfMeasure(UnitOfMeasure.KILOGRAMS);
                        break;
                    case "pcs":
                        product.setUnitOfMeasure(UnitOfMeasure.PCS);
                        break;
                    case "gr":
                        product.setUnitOfMeasure(UnitOfMeasure.GRAMS);
                        break;
                    case "mg":
                        product.setUnitOfMeasure(UnitOfMeasure.MILLIGRAMS);
                        break;
                    default:
                        product.setUnitOfMeasure(null);
                }
                is_correct = true;
            }
            else{
                System.out.print("Введите единицу измерения правильно(kg,pcs,gr,mg): ");
            }
        }
        if(isScript) {
            if(product.getUnitOfMeasure()!=null)
                System.out.println(product.getUnitOfMeasure().toString());
            else
                System.out.println("null");
        }

        Person person = new Person();

        System.out.print("Введите имя владельца: ");
        is_correct = false;
        while(!is_correct){
            try {
                person.setName(reader.nextLine());
                is_correct = true;
            } catch (WrongArgumentException e) {
                System.out.print(e.getMessage());
            }
        }
        if(isScript) {
            System.out.println(person.getName());
        }

        System.out.print("Введите рост владельца: ");
        is_correct = false;
        while (!is_correct){
            try {
                String inp = reader.nextLine();
                person.setHeight(inp.equals("")? null : Long.valueOf(inp));
                is_correct = true;

            } catch (NumberFormatException e ) {
                System.out.print("Введите правильное значение роста(целое число в пределах значения int): ");
            } catch (WrongArgumentException e) {
                System.out.print(e.getMessage());
            }
        }


        if(isScript) {
            System.out.println(person.getHeight());
        }


        System.out.print("Введите толщину владельца: ");
        is_correct = false;
        while (!is_correct){

            try {
                    String inp = reader.nextLine();
                    person.setWeight(inp.equals("")? null : Float.valueOf(inp));
                    is_correct = true;
                }
            catch (WrongArgumentException e) {
                System.out.print(e.getMessage());
            }

            catch (NumberFormatException e ) {
                System.out.print("Введите правильное значение толщины(число с плавающей запятой в пределах значения float): ");
            }
        }
        if(isScript) {
            System.out.println(person.getWeight());
        }


        System.out.print("Введите цвет волос владельца(black,blue,green,orange,red): ");
        is_correct = false;
        while (!is_correct){
            String inp = reader.nextLine().toLowerCase();
            if (inp.matches("(black)|(blue)|(green)|(orange)|(red)|( )*")){
                switch (inp){
                    case "black": person.setHairColor(Color.BLACK); break;
                    case "blue": person.setHairColor( Color.BLUE); break;
                    case "green": person.setHairColor(Color.GREEN); break;
                    case "orange": person.setHairColor(Color.ORANGE); break;
                    case "red": person.setHairColor(Color.RED); break;
                    default: person.setHairColor(null);
                }
                is_correct = true;
            }
            else {
                System.out.print("Введите цвет волос владельца правильно(black,blue,green,orange,red): ");
            }
        }
        if(isScript) {
            if(person.getHairColor()!= null)
                System.out.println(person.getHairColor().toString());
            else
                System.out.println("null");
        }

        try {
            product.setOwner(person);
        } catch (WrongArgumentException e) {
           System.out.println("Ошибка при изменении владельца");
        }
        return product;
    }

    /**
     * uses to update product
     * @param product Product

     * @throws NullPointerException if product with this id doesn't exist
     */
    public Product updateProduct(Product product) throws NullPointerException{
        if (product == null){
            throw new NullPointerException("product is null");
        }
        System.out.println("Чтобы изменить поле введите рядом новое значение\n" +
                "Чтобы оставить прежнее значение введите пустую строку\n" +
                "Чтобы ввести значение null введите null");

        System.out.printf("Название продукта : %s\n", product.getName());
        System.out.print("Введите новое название продукта: ");
        String inp;
        boolean is_correct = false;
        while (!is_correct){
            inp = reader.nextLine();
            if (inp.equals(""))
                is_correct = true;
            else if (inp.toLowerCase().equals("null")){
                System.out.print("Поле не может быть null, введите корректное значение: ");
            }
            else {
                try {
                    product.setName(inp);
                    is_correct = true;
                    if (isScript) {
                        System.out.println(inp);
                    }
                } catch (WrongArgumentException e) {
                    System.out.print(e.getMessage());
                }
            }
        }

        System.out.printf("Координата x = %d\n", product.getCoordinates().getX());
        System.out.print("Введите новое значение(целое число): ");

        is_correct = false;
        while (!is_correct){
            inp = reader.nextLine();
            if (inp.equals(""))
                is_correct = true;
            else if (inp.toLowerCase().equals("null")){
                System.out.print("Поле не может быть null, введите корректное значение: ");
            }
            else {
                try {
                    product.getCoordinates().setX(Integer.valueOf(inp));
                    is_correct = true;
                    if (isScript)
                        System.out.println(inp);
                }
                catch (NumberFormatException e){
                    System.out.print("Введите правильное значение x (целое число в пределах значения int): ");
                }

            }
        }

        System.out.printf("Координата y = %f\n", product.getCoordinates().getY());
        System.out.print("Введите новое значение y (число с плавающей точкой): ");

        is_correct = false;
        while (!is_correct){
            inp = reader.nextLine();
            if (inp.equals(""))
                is_correct = true;
            else if (inp.toLowerCase().equals("null")){
                System.out.print("Поле не может быть null, введите корректное значение: ");
            }
            else {
                try {
                    product.getCoordinates().setY(Float.valueOf(inp));
                    is_correct = true;
                    if (isScript)
                        System.out.println(inp);
                }
                catch (NumberFormatException e){
                    System.out.print("Введите правильное значение y (число с плавающей точкой в пределах значения float): ");
                } catch (WrongArgumentException e) {
                    System.out.print(e.getMessage());
                } catch (InfiniteCoordinateException e) {
                    System.out.print(e.getMessage());
                }

            }
        }

        System.out.printf("Дата создания : %s\n", product.getCreationDate());
        System.out.println("Изменение невозможно");
        System.out.printf("Цена продукта = %f\n", product.getPrice());
        System.out.print("Введите новое значение цены(больше нуля): ");

        is_correct = false;
        while (!is_correct){
            inp = reader.nextLine();
            if (inp.equals(""))
                is_correct = true;
            else if (inp.toLowerCase().equals("null")){
                System.out.print("Поле не может быть null, введите корректное значение: ");
            }
            else {
                try {
                    product.setPrice(Float.parseFloat(inp));
                    is_correct = true;
                    if (isScript)
                        System.out.println(inp);
                }
                catch (WrongArgumentException e) {
                    System.out.print(e.getMessage());
                }
                catch (NumberFormatException e){
                    System.out.print("Введите правильное значение цены (число с плавающей запитой в пределах значения float): ");
                }
            }
        }

        System.out.printf("Номер детали : %s\n", product.getPartNumber());
        System.out.print("Введите новый номер детали(минимум 23 символа): ");
        is_correct = false;
        while(!is_correct){
            inp = reader.nextLine();
            if (inp.equals(""))
                is_correct = true;
            else if (inp.toLowerCase().equals("null")){
                System.out.print("Поле не может быть null, введите корректное значение: ");
            }
            else {
                try {
                    product.setPartNumber(inp);
                    is_correct = true;
                    if(isScript) {
                        System.out.println(inp);
                    }
                } catch (WrongArgumentException e) {
                    System.out.print(e.getMessage());
                }
            }


        }

        System.out.printf("Стоимость изготовления = %d\n", product.getManufactureCost());
        System.out.print("Введите новое значение стоимости изготовления(целое число): ");

        is_correct = false;
        while (!is_correct){
            inp = reader.nextLine();
            if (inp.equals(""))
                is_correct = true;
            else if (inp.toLowerCase().equals("null")){
                System.out.print("Поле не может быть null, введите корректное значение: ");
            }
            else {
                try{
                    product.setManufactureCost(Long.parseLong(inp));
                    is_correct = true;
                    if (isScript){
                        System.out.println(inp);
                    }
                }
                catch (WrongArgumentException e) {
                    System.out.print(e.getMessage());
                }
                catch (NumberFormatException e){
                    System.out.print("Введите правильное значение(целое число в пределах значения int): ");
                }
            }
        }

        System.out.printf("Единица измерения равна %s\n", product.getUnitOfMeasure().toString());
        System.out.print("Введите новое значение единицы измерения(kg,pcs,gr,mg): ");

        is_correct = false;
        while (!is_correct){
            inp = reader.nextLine();
            if (inp.equals(""))
            {
                is_correct = true;
            }
            if (inp.toLowerCase().matches("(kg)|(pcs)|(gr)|(mg)|(null)")) {
                switch (inp) {
                    case "kg":
                        product.setUnitOfMeasure(UnitOfMeasure.KILOGRAMS);
                        break;
                    case "pcs":
                        product.setUnitOfMeasure(UnitOfMeasure.PCS);
                        break;
                    case "gr":
                        product.setUnitOfMeasure(UnitOfMeasure.GRAMS);
                        break;
                    case "mg":
                        product.setUnitOfMeasure(UnitOfMeasure.MILLIGRAMS);
                        break;
                    default:
                        product.setUnitOfMeasure(null);
                }
                is_correct = true;
                if (isScript){
                    System.out.println(inp);
                }
            }
            else{
                System.out.print("Введите единицу измерения правильно(kg,pcs,gr,mg): ");
            }
        }

        System.out.printf("Имя владельца : %s\n", product.getOwner().getName());
        System.out.print("Введите новое имя владельца: ");

        is_correct = false;
        while(!is_correct){
            inp = reader.nextLine();
            if (inp.equals(""))
                is_correct = true;
            else if (inp.toLowerCase().equals("null")){
                System.out.print("Поле не может быть null, введите корректное значение: ");
            }
            else{
                try{
                    product.getOwner().setName(inp);
                    is_correct = true;
                    if (isScript)
                        System.out.println(inp);
                }
                catch (WrongArgumentException e) {
                    System.out.print(e.getMessage());
                }
            }
        }

        System.out.printf("Рост владельца %d\n", product.getOwner().getHeight());
        System.out.print("Введите новый рост владельца(целое число в пределах значения int): ");

        is_correct = false;
        while(!is_correct){
            inp = reader.nextLine();
            if (inp.equals(""))
                is_correct = true;
            else {
                try {
                    product.getOwner().setHeight(inp.equals("null") ? null : Long.valueOf(inp));
                    is_correct = true;
                    if (isScript)
                        System.out.println(inp);


                } catch (WrongArgumentException e) {
                    System.out.print(e.getMessage());
                } catch (NumberFormatException e) {
                    System.out.print("Введите правильное значение роста(целое число): ");
                }
            }

        }

        System.out.printf("Ширина владельца %f\n", product.getOwner().getWeight());
        System.out.print("Введите новую ширину владельца(число с плавающей точкой): ");

        is_correct = false;
        while (!is_correct){
            inp = reader.nextLine();
            if (inp.equals(""))
                is_correct = true;
            else {
                try {
                    product.getOwner().setWeight(inp.equals("null") ? null : Float.valueOf(inp));
                    is_correct = true;
                    if (isScript)
                        System.out.println(inp);

                } catch (WrongArgumentException e) {
                    System.out.print(e.getMessage());
                } catch (NumberFormatException e) {
                    System.out.print("Введите правильное значение ширины(число с плавающей точкой в пределах значения float): ");
                }
            }
        }

        System.out.printf("Цвет волос владельца : %s\n", product.getOwner().getHairColor());
        System.out.print("Введите новое значение цвета волос (black,blue,green,orange,red): ");

        is_correct = false;
        while (!is_correct){
            inp = reader.nextLine();
            if (inp.matches(""))
                is_correct = true;
            else if (inp.toLowerCase().matches("(black)|(blue)|(green)|(orange)|(red)|(null)")){
                switch (inp){
                    case "black": product.getOwner().setHairColor(Color.BLACK); break;
                    case "blue": product.getOwner().setHairColor( Color.BLUE); break;
                    case "green": product.getOwner().setHairColor(Color.GREEN); break;
                    case "orange": product.getOwner().setHairColor(Color.ORANGE); break;
                    case "red": product.getOwner().setHairColor(Color.RED); break;
                    default: product.getOwner().setHairColor(null);
                }
                is_correct = true;
                if(isScript) {
                    System.out.println(inp);
                }
            }
            else {
                System.out.print("Введите цвет волос владельца правильно(black,blue,green,orange,red): ");
            }
        }
        return product;


    }



}
