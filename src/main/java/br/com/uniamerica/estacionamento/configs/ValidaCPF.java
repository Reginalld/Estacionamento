package br.com.uniamerica.estacionamento.configs;

import org.springframework.stereotype.Component;

import java.util.InputMismatchException;

@Component
public class ValidaCPF {
    public boolean isCPF(String Cpf) {
        // considera-se erro CPF's formados por uma sequencia de numeros iguais
        if (Cpf.equals("00000000000") ||
                Cpf.equals("11111111111") ||
                Cpf.equals("22222222222") || Cpf.equals("33333333333") ||
                Cpf.equals("44444444444") || Cpf.equals("55555555555") ||
                Cpf.equals("66666666666") || Cpf.equals("77777777777") ||
                Cpf.equals("88888888888") || Cpf.equals("99999999999") || (Cpf.length() != 11)){
            return(false);
        }

        char dig10, dig11;
        int sm, i, r, num, peso;

        // "try" - protege o codigo para eventuais erros de conversao de tipo (int)
        try {
            // Calculo do 1o. Digito Verificador
            sm = 0;
            peso = 10;

            for (i=0; i<9; i++) {
                // converte o i-esimo caractere do CPF em um numero:
                // por exemplo, transforma o caractere '0' no inteiro 0
                // (48 eh a posicao de '0' na tabela ASCII)
                num = (int)(Cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11)){
                dig10 = '0';
            }
            else {
                dig10 = (char)(r + 48); // converte no respectivo caractere numerico
            }

            // Calculo do 2o. Digito Verificador
            sm = 0;
            peso = 11;

            for(i=0; i<10; i++) {
                num = (int)(Cpf.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }

            r = 11 - (sm % 11);

            if ((r == 10) || (r == 11)) {
                dig11 = '0';
            }
            else {
                dig11 = (char)(r + 48);
            }

            // Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == Cpf.charAt(9)) && (dig11 == Cpf.charAt(10))) {
                return(true);
            }
            else {
                return(false);
            }
        }
        catch (InputMismatchException erro) {
            return(false);
        }
    }

    public String imprimeCPF(String Cpf) {
        return(Cpf.substring(0, 3) + "." + Cpf.substring(3, 6) + "." +
                Cpf.substring(6, 9) + "-" + Cpf.substring(9, 11));
    }


}
