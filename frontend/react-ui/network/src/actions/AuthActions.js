import { LOGIN_SUCCESS, LOGOUT } from "./ActionNames";
import axios from "axios";

const url = "http://172.17.0.1:5001/auth";

export const login = (username, password) => {
  return (dispatch) => {
    dispatch(logout());
    axios
      .post(`${url}/login`, {
        username,
        password,
      })
      .then((res) => {
        dispatch(loginSuccess(res.data.token));
      })
      .catch((err) => console.error(err));
  };
};

const loginSuccess = (token) => {
  return {
    type: LOGIN_SUCCESS,
    payload: {
      token: token,
    },
  };
};

export const logout = () => {
  return {
    type: LOGOUT,
  };
};
