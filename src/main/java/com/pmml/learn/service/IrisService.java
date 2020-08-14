package com.pmml.learn.service;

import org.dmg.pmml.FieldName;
import org.dmg.pmml.PMML;
import org.jpmml.evaluator.*;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class IrisService {
    private Evaluator loadPmml(){
        PMML pmml = new PMML();
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream("src/main/resources/models/iris.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(inputStream == null){
            return null;
        }
        InputStream is = inputStream;
        try {
            pmml = org.jpmml.model.PMMLUtil.unmarshal(is);
        } catch (SAXException | JAXBException e1) {
            e1.printStackTrace();
        } finally {
            //关闭输入流
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ModelEvaluatorFactory modelEvaluatorFactory = ModelEvaluatorFactory.newInstance();
        Evaluator evaluator = modelEvaluatorFactory.newModelEvaluator(pmml);
        pmml = null;
        return evaluator;
    }

    private int predict(Evaluator evaluator,Double sepalLength, Double sepalWidth, Double petalLength, Double petalWidth) {
        Map<String, Double> data = new HashMap<>();
        data.put("sepalLength", sepalLength);
        data.put("sepalWidth", sepalWidth);
        data.put("petalLength", petalLength);
        data.put("petalWidth", petalWidth);
        List<InputField> inputFields = evaluator.getInputFields();

        Map<FieldName, FieldValue> arguments = new LinkedHashMap<>();
        for (InputField inputField : inputFields) {
            FieldName inputFieldName = inputField.getName();
            Object rawValue = data.get(inputFieldName.getValue());
            FieldValue inputFieldValue = inputField.prepare(rawValue);
            arguments.put(inputFieldName, inputFieldValue);
        }

        Map<FieldName, ?> results = evaluator.evaluate(arguments);
        List<TargetField> targetFields = evaluator.getTargetFields();

        TargetField targetField = targetFields.get(0);
        FieldName targetFieldName = targetField.getName();

        Object targetFieldValue = results.get(targetFieldName);
        System.out.println("target: " + targetFieldName.getValue() + " value: " + targetFieldValue);
        int primitiveValue = -1;
        if (targetFieldValue instanceof Computable) {
            Computable computable = (Computable) targetFieldValue;
            primitiveValue = (Integer)computable.getResult();
        }
        return primitiveValue;
    }

    public static void main(String[] args)
    {
        IrisService demo = new IrisService();
        Evaluator model = demo.loadPmml();
        demo.predict(model,7.1,3.0,5.9,2.1); // 2
        demo.predict(model,5.8,2.6,4.0,1.2); // 1
    }
}
