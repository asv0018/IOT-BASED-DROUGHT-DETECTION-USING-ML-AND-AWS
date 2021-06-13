from flask import *
from sklearn import linear_model
import pandas

app = Flask(__name__)

parameters_temperature, parameters_soil_moisture, parameters_humidity = None, None, None

temp, moist, hum = 0, 0, 0
condition, accuracy  =  "Normal", 0


@app.route("/result")
def result():
    global condition, accuracy, temp, moist, hum
    result_dict = {"condition":str(condition),"accuracy":str(accuracy),"temperature":str(temp), "moisture":str(moist), "humidity":str(hum)}
    return str(result_dict)

@app.route("/analyse_manual_data", methods = ['GET'])
def analyse_manual_data():
    temperature = request.args.get('temperature')
    soil_moisture = request.args.get('soil_moisture')
    humidity = request.args.get("humidity")
    value = True
    status = "Normal"
    df = pandas.read_csv("dataset1.csv")
    features = df[['Temperature', 'Humidity','Soil moisture']]
    result = df['status']
    if temperature is None and humidity is None and soil_moisture is None:
        return render_template("DataNotFound.html")

    regr = linear_model.LinearRegression()
    regr.fit(features, result)

    predict = regr.predict([[temperature, humidity, soil_moisture]])
    if predict>1:
        value=False
    else:
        value=True

    print(value)

    if value==True:
        status = "Normal"
        print("normal")
    else:
        status = "Drought"
        print("Drought")

    r2_score = regr.score(features,result)

    print("the accuracy of Linear Regression is =",r2_score*100,'%')

    global accuracy, condition, temp, moist, hum
    condition = status
    accuracy = r2_score*100
    temp = temperature
    moist = soil_moisture
    hum = humidity
    return redirect(url_for('result'))

@app.route('/input_pipe', methods = ['GET'])
def input_pipe():
    global parameters_temperature, parameters_soil_moisture, parameters_humidity
    parameters_temperature = request.args.get('temperature')
    parameters_soil_moisture = request.args.get('soil_moisture')
    parameters_humidity = request.args.get("humidity")
    print(parameters_humidity)
    print(parameters_soil_moisture)
    print(parameters_temperature)
    return "SUCCESS"

@app.route("/access_ml_result")
def access_ml_result():
    global temp, moist, hum
    value = True
    status = "Normal"
    df = pandas.read_csv("dataset1.csv")
    features = df[['Temperature', 'Humidity','Soil moisture']]
    result = df['status']
    global parameters_temperature, parameters_soil_moisture, parameters_humidity, temp, moist, hum
    if parameters_humidity is None and parameters_soil_moisture is None and parameters_temperature is None:
        result_dict = {"condition":"Device is unavailable","accuracy":None,"temperature":None, "moisture":None, "humidity":None}
        return str(result_dict)
    regr = linear_model.LinearRegression()
    regr.fit(features, result)
    temperature = parameters_temperature
    temp = temperature
    humidity = parameters_humidity
    hum = humidity
    soil_moisture = parameters_soil_moisture
    moist = soil_moisture
    predict = regr.predict([[temperature, humidity, soil_moisture]])
    if predict>1:
        value=False
    else:
        value=True

    print(value)

    if value==True:
        status = "Normal"
        print("normal")
    else:
        status = "Drought"
        print("Drought")

    r2_score = regr.score(features,result)

    print("the accuracy of Linear Regression is =",r2_score*100,'%')

    global accuracy, condition
    condition = status
    accuracy = r2_score*100

    return redirect(url_for('result'))

if __name__ == '__main__':
    app.run("0.0.0.0", port=8080, debug=True)