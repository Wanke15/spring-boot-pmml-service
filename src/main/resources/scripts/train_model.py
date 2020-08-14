import numpy as np
import pandas as pd

from sklearn2pmml import PMMLPipeline, sklearn2pmml

from sklearn_pandas import DataFrameMapper

from sklearn import datasets
from sklearn.model_selection import train_test_split
from sklearn.metrics import precision_score

import xgboost as xgb


iris = datasets.load_iris()
X = iris.data
y = iris.target

feat_names = ['sepalLength', 'sepalWidth', 'petalLength', 'petalWidth']
X = pd.DataFrame(X, columns=feat_names)
y = pd.DataFrame(y, columns=['Species'])


X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.20, random_state=42)


dtrain = xgb.DMatrix(X_train, label=y_train)
dtest = xgb.DMatrix(X_test, label=y_test)

parameters = {
    'eta': 0.3,
    'silent': True,  # option for logging
    'objective': 'multi:softprob',  # error evaluation for multiclass tasks
    'num_class': 3,  # number of classes to predic
    'max_depth': 3  # depth of the trees in the boosting process
}
num_round = 20  # the number of training iterations

model = xgb.XGBClassifier(**parameters)

# model.fit(X_train, y_train)
# preds = model.predict(X_test)

default_mapper = DataFrameMapper([(i, None) for i in feat_names])

pipeline = PMMLPipeline([
    ('mapper', default_mapper),
    ("classifier", model)
])

pipeline.fit(X_train, y_train)

preds = pipeline.predict(X_test)
y_test_trans = np.array([_[0] for _ in y_test.values])

print(precision_score(y_test, preds, average='macro')) # 各类别分别计算，然后平均

print(precision_score(y_test, preds, average='micro')) # 全局，不区分类别

sklearn2pmml(pipeline, "iris_v2.pmml", with_repr=True)
# sklearn2pmml(estimator=model, mapper=default_mapper, pmml='iris_v2.xml')
