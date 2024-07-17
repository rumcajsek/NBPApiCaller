package pl.krol.springtrainer.exceptions;

public class RateListRequestedButCountIsNullException extends NullPointerException{
    public RateListRequestedButCountIsNullException(){
        super("Parameter \"last\" chosen but \"topCount\" is null");
    }
}
