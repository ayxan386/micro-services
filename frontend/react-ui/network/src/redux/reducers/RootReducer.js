import { combineReducers } from "redux";
import { authReducer } from "./AuthReducer";
import { postReducer } from "./PostReducer";
import { errorReducer } from "./ErrorReducer";
import { persistReducer } from "redux-persist";
import storage from "redux-persist/lib/storage";
import { userReducer } from "./UserReducer";

const persistConfig = {
  key: "root",
  storage,
};

const rootReducer = combineReducers({
  auth: authReducer,
  posts: postReducer,
  error: errorReducer,
  user: userReducer,
});

export const persistedReducer = persistReducer(persistConfig, rootReducer);
