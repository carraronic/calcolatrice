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
                    tokensList.add(Parentesi.PARENTESI_APERTA);
                    if(controlloNum(numero, inLetturaNumero))
                        inLetturaNumero = false;
                    break;
                case ')':
                    tokensList.add(Parentesi.PARENTESI_CHIUSA);
                    if(controlloNum(numero, inLetturaNumero))
                        inLetturaNumero = false;
                    break;
                case '+':
                    tokensList.add(Operatore.ADD);
                    if(controlloNum(numero, inLetturaNumero))
                        inLetturaNumero = false;
                    break;
                case '-':
                    tokensList.add(Operatore.SUB);
                    if(controlloNum(numero, inLetturaNumero))
                        inLetturaNumero = false;
                    break;
                case '*':
                    tokensList.add(Operatore.MULT);
                    if(controlloNum(numero, inLetturaNumero))
                        inLetturaNumero = false;
                    break;
                case '/':
                    tokensList.add(Operatore.DIV);
                    if(controlloNum(numero, inLetturaNumero))
                        inLetturaNumero = false;
                    break;
                case '^':
                    tokensList.add(Operatore.POW);
                    if(controlloNum(numero, inLetturaNumero))
                        inLetturaNumero = false;
                    break;
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9':
                    numero = (numero * 10) + Long.valueOf(Character.toString(carattere));
                    inLetturaNumero = true;
                    break;
                default:
                    throw new ExpressionException("Syntax Error");

            }
        }
    }

    public boolean controlloNum(long n, boolean inLettura){
        if(inLettura){
            tokensList.add(new Frazione(n, 1));
            return true;
        }
        return false;
    }

    public void shuntingYards() throws ExpressionException {

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

    public Frazione risolvi() throws ExpressionException {

        Stack<Frazione> output = new Stack<>();
        for(Object o : rpn){
            if(o instanceof Frazione){
                output.push((Frazione)o);
            }else{
                try{
                    Frazione n2 = output.pop();
                    switch ((Operatore)o){
                        case ADD ->  output.push(output.pop()).add(n2);
                        case SUB -> output.push(output.pop()).sott(n2);
                        case MULT -> output.push(output.pop()).mult(n2);
                        case DIV -> output.push(output.pop()).div(n2);
                        case POW -> output.push(output.pop()).pow(n2);
                    }
                }catch (EmptyStackException ex){
                    throw new ExpressionException(ex.getMessage());
                }
            }
        }
        Frazione risultato = output.pop();
        return risultato;
    }



}
