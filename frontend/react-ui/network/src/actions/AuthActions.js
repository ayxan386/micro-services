import { LOGIN_SUCCESS, LOGOUT, REGISTER_SUCCESS } from "./ActionNames";
import axios from "axios";
import { handleForMe } from "./ErrorHandler";

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
      .catch((err) => {
        handleForMe(dispatch, err);
      });
  };
};

export const register = (username, password, roles) => {
  return (dispatch) => {
    dispatch(logout());
    axios
      .post(`${url}/register`, {
        username,
        password,
        roles,
      })
      .then((res) => {
        dispatch(registerSuccess(res.data.token));
      })
      .catch((err) => {
        handleForMe(dispatch, err);
      });
  };
};

const registerSuccess = (token) => {
  return {
    type: REGISTER_SUCCESS,
    payload: {
      token: token,
    },
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
