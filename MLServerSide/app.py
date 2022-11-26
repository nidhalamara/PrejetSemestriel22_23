import pandas as pd
from flask import Flask
import requests
import math
import csv
import numpy as np
from sklearn.preprocessing import MinMaxScaler
from keras.models import Sequential
from keras.layers import Dense, LSTM
import pandas_datareader as web
app = Flask(__name__)


@app.route('/')
def hello_world():
    CSV_URL = 'https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY_EXTENDED&symbol=IBM&interval=15min&slice=year1month1&apikey=AE9U2AN0HTR4WKEN'

    with requests.Session() as s:
        download = s.get(CSV_URL)
        decoded_content = download.content.decode('utf-8')
        cr = csv.reader(decoded_content.splitlines(), delimiter=',')
        # my_list = list(cr)

    df=pd.DataFrame(list(cr))
    # df = web.DataReader('AAPL', data_source='yahoo', start='2012-01-01', end='2022-11-19')
    # df=decoded_content
    # df=df.iloc[1:,:]
    # df=open("df","r")
    df = df[1:]
    print(df)

    data = df.filter([4])
    print(data)
    print('okokoko')
    dataset = data.values
    training_data_len = math.ceil( len (dataset)* .8)
    scaler = MinMaxScaler(feature_range=(0,1))
    scaled_data = scaler.fit_transform(dataset)
    print(scaled_data)
    train_data = scaled_data[0:training_data_len, :]
    x_train = []
    y_train = []
    for i in range(60, len(train_data)):
        x_train.append(train_data[i-60:i, 0])
        y_train.append(train_data[i, 0])
    if i<= 60:
        print(x_train)
        print(y_train)
        print()
    x_train, y_train = np.array(x_train), np.array(y_train)
    x_train = np.reshape(x_train, (x_train.shape[0], x_train.shape[1],1))
    x_train.shape
    model= Sequential()
    model.add(LSTM (58, return_sequences=True, input_shape= (x_train.shape[1], 1)))
    model.add(LSTM(50, return_sequences= False))
    model.add(Dense(25))
    model.add(Dense(1))
    model.compile(optimizer='adam', loss='mean_squared_error')
    model.fit(x_train, y_train, batch_size=1, epochs=1)
    test_data= scaled_data[training_data_len - 60: , :]
    x_test= []
    y_test= dataset [training_data_len:, :]
    for i in range(60, len(test_data)):
        x_test.append(test_data[i-60:i, 0])
    x_test= np.array(x_test)
    x_test= np.reshape (x_test, (x_test.shape[0], x_test.shape[1], 1))
    predictions = model.predict(x_test)
    predictions = scaler.inverse_transform(predictions)
    # rmse = np.sqrt(np.mean( ( (float(predictions) - float(y_test))**2) ) )
    train= data[:training_data_len]
    valid = data[training_data_len:]
    valid['Predictions'] = predictions
    print('validoooooo')
    print(valid)
    #same work here as above , shit!
    apple_quote= web.DataReader('AAPL', data_source='yahoo', start='2012-01-01', end='2022-11-19')
    new_df= apple_quote.filter (['Close'])
    last_60_days = new_df[-60:].values
    last_60_days_scaled= scaler.transform(last_60_days)
    X_test= []
    X_test.append(last_60_days_scaled)
    X_test= np.array(X_test)
    X_test= np.reshape (X_test, (X_test.shape[0], X_test.shape[1], 1))
    pred_price= model.predict(X_test)
    pred_price= scaler.inverse_transform(pred_price)
    print(pred_price)

    print('apple_quote2['']')
    return str(pred_price)


if __name__ == '__main__':
    app.run()

