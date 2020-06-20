import Axios from "axios";
import { LOAD_POSTS, ADD_POST } from "./ActionNames";
import { handleError } from "./ErrorHandler";

const base_url = "http://172.17.0.1:5002";

export const loadPosts = () => {
  return (dispatch, getState) => {
    const { token } = getState().auth;
    Axios.get(`${base_url}/api/post/all`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => {
        dispatch(loadPosts_success(res.data.data));
      })
      .catch((err) => {
        dispatch(handleError(err.response.data));
      });
  };
};

export const createPost = (title, body) => {
  return (dispatch, getState) => {
    const { token } = getState().auth;
    const data = {
      title,
      body,
    };
    Axios.post(`${base_url}/api/post`, data, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((res) => {
        dispatch(appendPosts_success(res.data.data));
      })
      .catch((err) => {
        console.error(err);

        dispatch(handleError(err.response.data));
      });
  };
};

const loadPosts_success = (posts) => {
  return {
    type: LOAD_POSTS,
    payload: {
      posts,
    },
  };
};

const appendPosts_success = (post) => {
  return {
    type: ADD_POST,
    payload: {
      post,
    },
  };
};
