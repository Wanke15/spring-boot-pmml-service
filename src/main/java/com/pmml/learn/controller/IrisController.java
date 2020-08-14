package com.pmml.learn.controller;

import com.pmml.learn.model.IrisPrediction;
import com.pmml.learn.service.IrisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class IrisController {
    @Autowired
    private IrisService irisService; //注入Service

    @ResponseBody
    @RequestMapping(value = "/api/v1.0/iris/predict", method = RequestMethod.GET)
    public Map<String, Object> irisPredict(@RequestParam(value = "sepalLength", required = true) Double sepalLength,
                                              @RequestParam(value = "sepalWidth", required = true) Double sepalWidth,
                                              @RequestParam(value = "petalLength", required = true) Double petalLength,
                                              @RequestParam(value = "petalWidth", required = true) Double petalWidth) {
        Map<String, Object> result = new HashMap<String, Object>();
        IrisPrediction irisPrediction = null;
        try {
            irisPrediction = irisService.predict(sepalLength, sepalWidth, petalLength, petalWidth);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", 500);
            result.put("data", null);
            result.put("message", "模型预测失败！");
            return result;
        }

        result.put("code", 200);
        result.put("data", irisPrediction);
        result.put("message", "success");
        return result;
    }
}

