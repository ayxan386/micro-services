import { LOGOUT, ERROR, CLEAR_ERROR } from "./ActionNames";

export const handleError = (err) => {
  switch (err.message || err) {
    case "Token expired!":
      return {
        type: LOGOUT,
      };
    default:
      return {
        type: ERROR,
        payload: err.message || err,
      };
  }
};

export const handleForMe = (dispatch, err) => {
  if (err.response) {
    dispatch(handleError(err.response.data));
    setTimeout(() => {
      dispatch({
        type: CLEAR_ERROR,
      });
    }, 4000);
  }
};
