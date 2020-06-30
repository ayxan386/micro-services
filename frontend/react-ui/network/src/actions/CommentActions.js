import { handleError } from "./ErrorHandler";

const { default: Axios } = require("axios");
const { loadPosts } = require("./PostActions");

const base_url = "http://172.17.0.1:5002/api/comment";

export const makeComment = (body, postId) => {
  return (dispatch, getState) => {
    const token = getState().auth.token;
    const data = {
      body,
      postId,
    };
    Axios.post(`${base_url}`, data, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        dispatch(loadPosts());
      })
      .catch((err) => dispatch(handleError(err.response.data)));
  };
};
