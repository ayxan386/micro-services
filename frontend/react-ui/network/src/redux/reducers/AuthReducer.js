import { LOGIN_SUCCESS, LOGOUT } from "../../actions/ActionNames";

const defState = {
  token: "",
  isLogged: false,
};

export const authReducer = (state = defState, action) => {
  switch (action.type) {
    case "login":
      return Object.assign(state, { username: "someone" });
    case LOGIN_SUCCESS:
      return { ...state, token: action.payload.token, isLogged: true };
    case LOGOUT:
      return { ...state, token: "", isLogged: false };
    default:
      return state;
  }
};
