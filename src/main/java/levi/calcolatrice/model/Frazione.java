package levi.calcolatrice.model;

/**
 *
 * denominatore sempre positivo
 * lo zero è rappresentato dalla frazione 0/1
 */

public class Frazione {
    private long numeratore;
    private long denominatore;

    public Frazione(long numeratore, long denominatore) throws ArithmeticException{
        if(denominatore == 0){
            throw new ArithmeticException("Non definita frazione con denominatore uguale a 0");
        }
        if(denominatore < 0){
            numeratore *= -1;
            denominatore *= -1;
        }
        if(numeratore == 0){
            this.numeratore = 0;
            this.denominatore = 1;
        }else{
            long mcd = massimoComuneDivisore(numeratore, denominatore);
            this.numeratore = numeratore / mcd;
            this.denominatore = denominatore / mcd;
        }

    }
    @Override
    public String toString(){
        return "(" + numeratore + "/" + denominatore + ")";
    }

    private static long massimoComuneDivisore(long a1, long b1){
        long a = Math.abs(a1);
        long b = Math.abs(b1);
        while(a != b){
            if(a > b)
                a -= b;
            else
                b -= a;
        }
        return a;
    }

    /**
     * Moltiplicazione
     * @param f secondo operando
     * @return this * f
     */
    public Frazione mult(Frazione f){
        return new Frazione(this.numeratore * f.numeratore, this.denominatore * f.denominatore);
    }
    public Frazione div(Frazione f){
        return this.mult(new Frazione(f.denominatore, f.numeratore));
    }
    public Frazione add(Frazione f){
        long num, den;
        den = massimoComuneDivisore(this.denominatore, f.denominatore);
        num = den / this.denominatore * this.numeratore + den / f.denominatore * f.numeratore;
        return new Frazione(num, den);
    }
    public Frazione sott(Frazione f){
        long num, den;
        den = massimoComuneDivisore(this.denominatore, f.denominatore);
        num = den / this.denominatore * this.numeratore - den / f.denominatore * f.numeratore;
        return new Frazione(num, den);
    }

    public Frazione pow(Frazione f){
        long num, den;
        den = (long) Math.pow(this.denominatore, f.numeratore);
        num = (long) Math.pow(this.numeratore, f.numeratore);
        return new Frazione(num, den);
    }


}
