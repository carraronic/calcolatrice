package levi.calcolatrice.model;

public enum Operatore {
    ADD('+', 1), SUB('-', 1), MULT('*', 2), DIV('/', 2), POW('^', 3);
    private char simbolo;
    private int priority;

    Operatore(char simbolo, int priority) {
        this.simbolo = simbolo;
        this.priority = priority;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return Character.toString(simbolo);
    }
}
