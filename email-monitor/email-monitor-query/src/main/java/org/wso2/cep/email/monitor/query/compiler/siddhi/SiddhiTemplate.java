package org.wso2.cep.email.monitor.query.compiler.siddhi;


import java.util.ArrayList;
import java.util.List;

public class SiddhiTemplate {

    private String toMails;
    private String fromMails;
    private String labelMails;
    private boolean isThreadFre;
    private String timeExpr;
    private int countValue;
    private String cmpAction;
    private String labelName;
    private boolean isSendMailEnabled;
    private String to;
    private String subject;
    private String body;

    public String getLabelMails() {
        return labelMails;
    }

    public void setLabelMails(String labelMails) {
        this.labelMails = labelMails;
    }

    public void setThreadFre(boolean isThreadFre) {
        this.isThreadFre = isThreadFre;
    }




    public void setTimeExpr(String timeExpr) {
        this.timeExpr = timeExpr;
    }

    public void setCountValue(int countValue) {
        this.countValue = countValue;
    }

    public String getToMails() {
        return toMails;
    }

    public void setToMails(String toMails) {
        this.toMails = toMails;
    }

    public String getFromMails() {
        return fromMails;
    }

    public void setFromMails(String fromMails) {
        this.fromMails = fromMails;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public void setSendMailEnabled(boolean isSendMailEnabled) {
        this.isSendMailEnabled = isSendMailEnabled;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public boolean isThreadFre() {
        return isThreadFre;
    }



    public String getTimeExpr() {
        return timeExpr;
    }

    public int getCountValue() {
        return countValue;
    }

    public String getCmpAction() {
        return cmpAction;
    }

    public void setCmpAction(String cmpAction) {
        this.cmpAction = cmpAction;
    }

    public String getLabelName() {
        return labelName;
    }

    public boolean isSendMailEnabled() {
        return isSendMailEnabled;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }


}