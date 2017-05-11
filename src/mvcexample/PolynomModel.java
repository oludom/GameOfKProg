package mvcexample;


import java.util.Observable;

/**
 * 20.04.2017
 *
 * @author SWirries
 *         <p>
 *         This code is
 *         documentation enough
 */
public class PolynomModel extends Observable {

    int constant;
    int linear;
    int quad;
    int cubic;

    PolynomModel(int constant, int linear, int quad, int cubic){
        this.constant = constant;
        this.linear = linear;
        this.quad = quad;
        this.cubic = cubic;
    }

    public int getConstant () {
        return constant;
    }
    public int getLinear () {
        return linear;
    }
    public int getQuadratic () {
        return quad;
    }
    public int getCubic(){
        return cubic;
    }

    public void setConstant (int n) {
        constant = n;
        setChanged();
        notifyObservers();
    }
    public void setLinear (int n) {
        linear = n;
        setChanged();
        notifyObservers();
    }
    public void setQuadratic (int n) {
        quad = n;
        setChanged();
        notifyObservers();
    }
    public void setCubic (int n) {
        cubic= n;
        setChanged();
        notifyObservers();
    }

}
