package levi.calcolatrice.model;

public enum Parentesi {
    PARENTESI_APERTA('('), PARENTESI_CHIUSA(')');
    private char simbolo;

    Parentesi(char simbolo) {
        this.simbolo = simbolo;
    }
    @Override
    public String toString(){
        return Character.toString(simbolo);
    }
}
