# spring-boot-pmml-service

- `[GET]`
```bash
http://localhost:8080/api/v1.0/iris/predict?sepalLength=7.1&sepalWidth=3.1&petalLength=5.9&petalWidth=2.1
```

response
```json
{
    "code": 200,
    "data": {
        "probability": 0.9910520911216736,
        "categoryId": 2,
        "category": "virginica"
    },
    "message": "success"
}
```

