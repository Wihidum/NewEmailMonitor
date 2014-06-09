package org.wso2.cep.email.monitor.query.compiler.siddhi;


import org.wso2.cep.email.monitor.query.api.Query;
import org.wso2.cep.email.monitor.query.compiler.siddhi.utils.ConstantsUtils;

import java.util.HashMap;
import java.util.Map;

public class SiddhiQueryWriter {


    private static SiddhiQueryWriter siddhiQueryWriter;


    private SiddhiQueryWriter() {
    }

    public String[] writeQuery(Query query) {


        SiddhiTemplate siddhiTemplate = TemplatePopulator.convert(query);
        if (siddhiTemplate.isFreqEnabled()) {
            if (siddhiTemplate.getLabelMails() == null) {
                return writeQueryWithFrequencyWithoutLabel(siddhiTemplate);
            } else {
                return writeQueryWithFrequencyWithLabel(siddhiTemplate);
            }

        } else {
            if (siddhiTemplate.getLabelMails() != null) {
                return writeQueryWithoutFrequencyWithLabel(siddhiTemplate);
            } else if (siddhiTemplate.getLabelMails() == null) {
                return writeQueryWithoutFrequencyWithoutLabel(siddhiTemplate);
            }
        }
        return new String[2];
    }


    public String[] writeQueryWithoutFrequencyWithLabel(SiddhiTemplate siddhiTemplate) {
        String[] result = new String[2];
        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append("from " + ConstantsUtils.INPUTSTREAM);
        if (siddhiTemplate.getLabelMails() != null) {
            stringBuffer.append("[");
            stringBuffer.append(siddhiTemplate.getLabelMails());
            stringBuffer.append("]");
            stringBuffer.append("select  threadID, email:getAllSenders(to) as to ,email:getAllsenders(sender) as senders,");
            if (!siddhiTemplate.isSendMailEnabled()) {
                stringBuffer.append(" " + '"' + siddhiTemplate.getLabelName() + '"' + " ");
                stringBuffer.append("as newLabel insert into ");
                stringBuffer.append(ConstantsUtils.FILTERED_EMAIL_DETAILS);
                stringBuffer.append(";");
            }
        }
        result[0] = stringBuffer.toString();
        StringBuffer stringBuffer1 = new StringBuffer();
        stringBuffer1.append("from " + ConstantsUtils.FILTERED_EMAIL_DETAILS);
        stringBuffer1.append("[");
        if (siddhiTemplate.getToMails() != null) {
            stringBuffer1.append("(");
            stringBuffer1.append(siddhiTemplate.getToMails());
            stringBuffer1.append(")");

        }
        if (siddhiTemplate.getFromMails() != null) {
            stringBuffer1.append(siddhiTemplate.getLabelFrom());
            stringBuffer1.append("(");
            stringBuffer1.append(siddhiTemplate.getFromMails());
            stringBuffer1.append(")");
        }
        stringBuffer1.append("]");
        if (!siddhiTemplate.isSendMailEnabled()) {
            stringBuffer1.append("select threadID, newLabel as label insert into " + ConstantsUtils.OUTPUTSTREAM);
            stringBuffer1.append(";");
        }
        result[1] = stringBuffer1.toString();

        return result;
    }

    public String[] writeQueryWithoutFrequencyWithoutLabel(SiddhiTemplate siddhiTemplate) {
        StringBuffer stringBuffer1 = new StringBuffer();
        String[] str = new String[1];

        stringBuffer1.append("from " + ConstantsUtils.INPUTSTREAM);
        stringBuffer1.append("[");
        if (siddhiTemplate.getToMails() != null) {
            stringBuffer1.append("(");
            stringBuffer1.append(siddhiTemplate.getToMails());
            stringBuffer1.append(")");

        }

        if (siddhiTemplate.getFromMails() != null) {
            stringBuffer1.append(siddhiTemplate.getLabelFrom());
            stringBuffer1.append("(");
            stringBuffer1.append(siddhiTemplate.getFromMails());
            stringBuffer1.append(")");
        }
        stringBuffer1.append("]");
        if (!siddhiTemplate.isSendMailEnabled()) {
            stringBuffer1.append("select threadID, ");
            stringBuffer1.append(" " + '"' + siddhiTemplate.getLabelName() + '"' + " ");
            stringBuffer1.append(" as label insert into " + ConstantsUtils.OUTPUTSTREAM);
            stringBuffer1.append(";");
        }
        str[0] = stringBuffer1.toString();
        return str;
    }

    public String[] writeQueryWithFrequencyWithoutLabel(SiddhiTemplate siddhiTemplate) {
        StringBuffer stringBuffer1 = new StringBuffer();
        String[] str = new String[2];

        stringBuffer1.append("from " + ConstantsUtils.INPUTSTREAM);

        if (!siddhiTemplate.isSendMailEnabled()) {
            stringBuffer1.append("#window.externalTime(sentDate,");
            stringBuffer1.append(siddhiTemplate.getTimeExpr());
            stringBuffer1.append(")");
            stringBuffer1.append("select  threadID, count(messageID) as emailCount, email:getAllSenders(to) as to ,email:getAllsenders(sender) as senders,");
            stringBuffer1.append(" " + '"' + siddhiTemplate.getLabelName() + '"' + " ");
            stringBuffer1.append("as newLabel group by threadID  having emailcount ");
            stringBuffer1.append(siddhiTemplate.getCmpAction());
            stringBuffer1.append(" " + siddhiTemplate.getCountValue());
            stringBuffer1.append(" insert into " + ConstantsUtils.THREADSTREAM);
            stringBuffer1.append(";");
        }
        str[0] = stringBuffer1.toString();

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("from " + ConstantsUtils.THREADSTREAM);
        stringBuffer.append("[");
        if (siddhiTemplate.getToMails() != null) {
            stringBuffer.append("(");
            stringBuffer.append(siddhiTemplate.getToMails());
            stringBuffer.append(")");

        }
        if (siddhiTemplate.getFromMails() != null) {
            stringBuffer.append(siddhiTemplate.getLabelFrom());
            stringBuffer.append("(");
            stringBuffer.append(siddhiTemplate.getFromMails());
            stringBuffer.append(")");
        }
        stringBuffer.append("]");
        if (!siddhiTemplate.isSendMailEnabled()) {
            stringBuffer.append("select threadID, newLabel as label insert into " + ConstantsUtils.OUTPUTSTREAM);
            stringBuffer.append(";");
        }

        str[1] = stringBuffer.toString();


        return str;
    }

    public String[] writeQueryWithFrequencyWithLabel(SiddhiTemplate siddhiTemplate) {
        StringBuffer stringBuffer1 = new StringBuffer();
        String[] str = new String[2];

        stringBuffer1.append("from " + ConstantsUtils.INPUTSTREAM);
        stringBuffer1.append("[");
        if (siddhiTemplate.getLabelMails() != null) {
            stringBuffer1.append("(");
            stringBuffer1.append(siddhiTemplate.getLabelMails());
            stringBuffer1.append(")");

        }
        if (!siddhiTemplate.isSendMailEnabled()) {


            stringBuffer1.append("#window.externalTime(sentDate,");
            stringBuffer1.append(siddhiTemplate.getTimeExpr());
            stringBuffer1.append(")");
            stringBuffer1.append("select  threadID, count(messageID) as emailCount, email:getAllSenders(to) as to ,email:getAllsenders(sender) as senders,");
            stringBuffer1.append(" " + '"' + siddhiTemplate.getLabelName() + '"' + " ");
            stringBuffer1.append("as newLabel group by threadID  having emailcount ");
            stringBuffer1.append(siddhiTemplate.getCmpAction());
            stringBuffer1.append(" " + siddhiTemplate.getCountValue());
            stringBuffer1.append(" insert into " + ConstantsUtils.THREADSTREAM);
            stringBuffer1.append(";");
        }
        str[0] = stringBuffer1.toString();

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("from " + ConstantsUtils.THREADSTREAM);
        stringBuffer.append("[");
        if (siddhiTemplate.getToMails() != null) {
            stringBuffer.append("(");
            stringBuffer.append(siddhiTemplate.getToMails());
            stringBuffer.append(")");

        }
        if (siddhiTemplate.getFromMails() != null) {
            stringBuffer.append(siddhiTemplate.getLabelFrom());
            stringBuffer.append("(");
            stringBuffer.append(siddhiTemplate.getFromMails());
            stringBuffer.append(")");
        }
        stringBuffer.append("]");
        if (!siddhiTemplate.isSendMailEnabled()) {
            stringBuffer.append("select threadID, newLabel as label insert into " + ConstantsUtils.OUTPUTSTREAM);
            stringBuffer.append(";");
        }

        str[1] = stringBuffer.toString();


        return str;
    }


    public static SiddhiQueryWriter getInstance() {
        if (siddhiQueryWriter == null) {
            siddhiQueryWriter = new SiddhiQueryWriter();
        }
        return siddhiQueryWriter;
    }


}