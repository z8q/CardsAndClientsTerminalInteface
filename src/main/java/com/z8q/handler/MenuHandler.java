package com.z8q.handler;

import com.z8q.cardpropeties.FormFactor;
import com.z8q.menu.MenuLevels;

import java.util.Scanner;

public class MenuHandler implements HandlerInterface {

    Scanner sc = new Scanner(System.in);

    @Override
    public void checkPAN(String pan) {
        while (pan.length() != 16) {
            System.out.println("Повторите попытку, для выхода просто нажмите Enter");
            pan = sc.nextLine();
            if(pan.length()==0) {
                MenuLevels.mainMenu();
            }
        }
    }
    @Override
    public void checkFormFactor(String form){
        FormFactor formFactor;
        if (form.equals("REAL")) {
            formFactor = FormFactor.REAL;
        } else {
            formFactor = FormFactor.VIRTUAL;
        }
    }
    @Override
    public void checkChip(String chip){
        boolean hasChip = false;
        if (chip.equals("yes")) {
            hasChip = true;
        }
    }
    @Override
    public void checkPin(String pin){
        if(pin.matches("[4]?\\d+")) {

        }
    }

    public void sendCardToIo(){
        //createOperations.createCardObject(cardNumber16DigitsInput, formFactor, x, pinInput);
    }

}
