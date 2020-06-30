import { combineReducers } from "redux";
import { authReducer } from "./AuthReducer";
import { postReducer } from "./PostReducer";
import { errorReducer } from "./ErrorReducer";
import { persistReducer } from "redux-persist";
import storage from "redux-persist/lib/storage";
import { userReducer } from "./UserReducer";
import { LOGOUT } from "../../actions/ActionNames";

const persistConfig = {
  key: "root",
  storage,
};

const appReducer = combineReducers({
  auth: authReducer,
  posts: postReducer,
  error: errorReducer,
  user: userReducer,
});

const rootReducer = (state, action) => {
  if (action.type === LOGOUT) {
    storage.removeItem("persist:root");
    state = undefined;
  }
  return appReducer(state, action);
};

export const persistedReducer = persistReducer(persistConfig, rootReducer);
