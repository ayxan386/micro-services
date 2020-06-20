import { createStore, applyMiddleware } from "redux";
import { persistedReducer } from "./reducers/RootReducer";
import thunk from "redux-thunk";
import { persistStore } from "redux-persist";

const store = createStore(persistedReducer, applyMiddleware(thunk));

export default () => {
  return {
    store,
    persistor: persistStore(store),
  };
};
