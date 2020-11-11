import java.util.Scanner;

// Scanner keyboard = bruger indtaster med given værdi fra keyboard
public class UIPersonData{


    //Hvad gør de tre metoder, vi har? Den konfigurerer programmet fra start af.
    Scanner keyboard = new Scanner(System.in);


    public String askForCPR(){
        System.out.println("Indtast CPR");
        String CPR = keyboard.nextLine();
        return CPR;

    }

    public String askForFornavn() {
        System.out.println("Indtast Fornavn ");
        String Fornavn = keyboard.nextLine();
        return Fornavn;
    }


    public String askForEfternavn() {
        System.out.println("Indtast Efternavn ");
        String Efternavn = keyboard.nextLine();
        return Efternavn;
    }

    public String askForKon() {
        System.out.println("Indtast Køn ");
        String kon = keyboard.nextLine();
        return  kon;
    }


}

