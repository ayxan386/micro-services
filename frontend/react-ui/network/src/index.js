import React from "react";
import ReactDOM from "react-dom";
import "./styles/index.css";
import App from "./compoenents/App";
import * as serviceWorker from "./serviceWorker";
import { Provider } from "react-redux";
import store from "./redux/Store";
import "./styles/bootstrap.min.css";
import { PersistGate } from "redux-persist/integration/react";

ReactDOM.render(
  <PersistGate loading={null} persistor={store().persistor}>
    <Provider store={store().store}>
      <App />
    </Provider>
  </PersistGate>,
  document.getElementById("root")
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
