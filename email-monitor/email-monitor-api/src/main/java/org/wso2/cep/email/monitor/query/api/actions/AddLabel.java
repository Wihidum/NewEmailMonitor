package org.wso2.cep.email.monitor.query.api.actions;


public class AddLabel extends Action {


    public AddLabel(){

    }
    public AddLabel(String label){
     setTo(label);

    }




    @Override
    public String toString() {
        return "add label " + getTo();
    }
}
