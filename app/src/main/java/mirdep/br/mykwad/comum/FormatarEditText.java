package mirdep.br.mykwad.comum;

public class FormatarEditText {

    public static String formatarEmail(String email){
        System.out.println(">>>>>>>>>>>>>>>> entrada: "+email);
        email = email.toLowerCase();
        System.out.println(">>>>>>>>>>>>>>>> lower: "+email);
        email = email.replaceAll(" ","");
        System.out.println(">>>>>>>>>>>>>>>> teste1: "+email);
        //email = email.replaceAll(".",".");
        System.out.println(">>>>>>>>>>>>>>>> teste2: "+email);
        return email;
    }
}
