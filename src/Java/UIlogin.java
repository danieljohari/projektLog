package Java;

import java.util.Scanner;

// Scanner keyboard = bruger indtaster med given værdi fra keyboard
public class UIlogin{


    //Hvad gør de tre metoder, vi har? Den konfigurerer programmet fra start af.
    Scanner keyboard = new Scanner(System.in);



    // Fra Scanner keyboard indsættes kritisk værdi i terminal
    public String askForCPR(){
        System.out.println("Indtast CPR");
        String CPR = keyboard.nextLine();
        return CPR;

    }

    public String askForMail() {
        System.out.println("Indtast Mail ");
        String Mail = keyboard.nextLine();
        return  Mail;
    }

    public String askForPassword() {
        System.out.println("Indtast Password ");
        String Password = keyboard.nextLine();
        return Password;
    }

}




