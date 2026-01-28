package levi.calcolatrice.model;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class Espressione {
    private String inputExpr;
    ArrayList<Object> tokensList;
    private ArrayList<Object> rpn;

    ArrayList<Object> output = new ArrayList<>();
    Stack<Object> operatori = new Stack<>();

    public Espressione(String inputExpr) {
        tokensList = new ArrayList<>();
        this.inputExpr = inputExpr;
    }

    public void scanner() throws ExpressionException {
        long numero = 0;
        boolean inLetturaNumero = false;
        for (char carattere : inputExpr.toCharArray()) {
            switch (carattere) {
                case '(':
                    if (inLetturaNumero){
                        tokensList.add(new Frazione(numero, 1));
                        inLetturaNumero = false;
                        numero = 0;
                    }
                    tokensList.add(Parentesi.PARENTESI_APERTA);
                    break;
                case ')':
                    if (inLetturaNumero){
                        tokensList.add(new Frazione(numero, 1));
                        inLetturaNumero = false;
                        numero = 0;
                    }
                    tokensList.add(Parentesi.PARENTESI_CHIUSA);
                    break;
                case '+':
                    if (inLetturaNumero){
                        tokensList.add(new Frazione(numero, 1));
                        inLetturaNumero = false;
                        numero = 0;
                    }
                    tokensList.add(Operatore.ADD);
                    break;
                case '-':
                    if (inLetturaNumero){
                        tokensList.add(new Frazione(numero, 1));
                        inLetturaNumero = false;
                        numero = 0;
                    }
                    tokensList.add(Operatore.SUB);
                    break;
                case '*':
                    if (inLetturaNumero){
                        tokensList.add(new Frazione(numero, 1));
                        inLetturaNumero = false;
                        numero = 0;
                    }
                    tokensList.add(Operatore.MULT);
                    break;
                case '/':
                    if (inLetturaNumero){
                        tokensList.add(new Frazione(numero, 1));
                        inLetturaNumero = false;
                        numero = 0;
                    }
                    tokensList.add(Operatore.DIV);
                    break;
                case '^':
                    if (inLetturaNumero){
                        tokensList.add(new Frazione(numero, 1));
                        inLetturaNumero = false;
                        numero = 0;
                    }
                    tokensList.add(Operatore.POW);
                    break;
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9':
                    numero = (numero * 10) + Long.parseLong(Character.toString(carattere));
                    inLetturaNumero = true;
                    break;
                default:
                    throw new ExpressionException("Syntax Error");

            }
        }
        if (inLetturaNumero){
            tokensList.add(new Frazione(numero, 1));
        }
    }

    public void shuntingYards() throws ExpressionException {
        scanner();
        for(Object token : tokensList){
            if(token instanceof Frazione){
                output.add(token);
            }else if(token instanceof Operatore){
                while(!operatori.isEmpty() && operatori.peek() instanceof Operatore){
                    if(((Operatore)operatori.peek()).getPriority() >= ((Operatore)token).getPriority()){
                        output.add(operatori.pop());
                    }else{
                        break;
                    }
                }
                operatori.push(token);
            } else if(token == Parentesi.PARENTESI_APERTA){
                operatori.push(token);
            } else if(token == Parentesi.PARENTESI_CHIUSA){
                while(!(operatori.peek().equals(Parentesi.PARENTESI_APERTA))){
                    output.add(operatori.pop());
                }
                operatori.pop();
            }
        }
        while(!operatori.isEmpty()){
            if(operatori.peek() instanceof Operatore){
                output.add(operatori.pop());
            }else{
                throw new ExpressionException("Syntax error");
            }
        }
        rpn = output;
    }

    public Frazione risultato() throws ExpressionException {
        shuntingYards();
        Stack<Frazione> output = new Stack<>();
        for(Object o : rpn){
            if(o instanceof Frazione){
                output.push((Frazione)o);
            }else{
                try{
                    Frazione n2 = output.pop();
                    Frazione n1 = output.pop();
                    switch ((Operatore)o){
                        case ADD ->  output.push(n1.add(n2));
                        case SUB -> output.push(n1.sott(n2));
                        case MULT -> output.push(n1.mult(n2));
                        case DIV -> output.push(n1.div(n2));
                        case POW -> output.push(n1.pow(n2));
                    }
                }catch (EmptyStackException e){
                    throw new ExpressionException("syntax error");
                }
            }
        }
        Frazione risultato = output.pop();
        System.out.println(risultato);
        return risultato;
    }



}
