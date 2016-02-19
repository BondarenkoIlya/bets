package model;

/**
 * Created by Дом on 14.02.2016.
 */
public class BetByWinSide extends BetEntity {
    private String nameOfWinSide;
    private double coefficientOfWinSide;

    public BetByWinSide(){

    }

    public BetByWinSide(double value,Match match, String nameOfWinSide, Customer customer){
        this.setValue(value);
        this.match=match;
        this.setNameOfWinSide(nameOfWinSide);
        this.customer=customer;
    }


    public void fillFinalPossibleGain (){
        if (getNameOfWinSide()==match.getNameOfSide1()) {
            this.coefficientOfWinSide=match.getCoefficient1();
        }else if(getNameOfWinSide()==match.getNameOfSide2()){
            this.coefficientOfWinSide=match.getCoefficient2();
        }
        this.setPossibleGain(this.getValue()*this.coefficientOfWinSide);
    }


    public void fillFinalResult() {
        if (match.getNameOfWinSide()==this.getNameOfWinSide()) {
            this.setResult(true);
            System.out.println("Поздравляю,вы выйграли "+this.getPossibleGain());// Как сделать по человечески ? то бы выводил результаты, или нужно просто добавить логи и что бы каждое действие оправляло историю ?
        }else {
            this.setResult(false);
            System.out.println("Извините, вы проиграли "+ this.getValue());
        }


    }

    public String getNameOfWinSide() {
        return nameOfWinSide;
    }

    public void setNameOfWinSide(String nameOfWinSide) {
        this.nameOfWinSide = nameOfWinSide;
    }
}
