import {
  LOGIN_SUCCESS,
  LOGOUT,
  REGISTER_SUCCESS,
} from "../../actions/ActionNames";

const defState = {
  token: "",
  isLogged: false,
  isRegistered: false,
};

export const authReducer = (state = defState, action) => {
  switch (action.type) {
    case "login":
      return Object.assign(state, { username: "someone" });
    case LOGIN_SUCCESS:
      return { ...state, token: action.payload.token, isLogged: true };
    case LOGOUT:
      return { ...state, ...defState };
    case REGISTER_SUCCESS:
      return { ...state, token: action.payload.token, isRegistered: true };
    default:
      return state;
  }
};
