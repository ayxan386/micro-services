import Axios from "axios";
import { GET_USER } from "./ActionNames";
import { handleError } from "./ErrorHandler";

const url = "http://172.17.0.1:5002";

export const getMyDetails = () => {
  return (dispatch, getState) => {
    const token = getState().auth.token;
    Axios.get(`${url}/api/user/me`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => {
        dispatch(getUserSuccess(res.data.data));
      })
      .catch((err) => {
        handleError(err.response.data);
      });
  };
};

const getUserSuccess = (data) => {
  return {
    type: GET_USER,
    payload: data,
  };
};

export const updatePhoto = (file) => {
  return (dispatch, getState) => {
    const token = getState().auth.token;
    const formData = new FormData();
    formData.append("file", file);
    Axios.post(`${url}/api/user/save`, formData, {
      headers: {
        Authorization: `Bearer ${token}`,
        "content-type": "multipart/form-data",
      },
    })
      .then((res) => {
        dispatch(getMyDetails());
      })
      .catch((err) => handleError(err.response.data));
  };
};

export const updateUserDetails = (body) => {
  return (dispatch, getState) => {
    const { token } = getState().auth;
    Axios.put(`${url}/api/user`, body, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => {
        dispatch(getUserSuccess(res.data.data));
      })
      .catch((err) => handleError(err.response.data));
  };
};
